package ru.riton.ru.riton.commands.schedule

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.createMessageWithInlineButtons
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.model.enums.WeekDay
import ru.riton.ru.riton.service.ScheduleService
import ru.riton.ru.riton.slotDate
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class DayCommand(private val scheduleService: ScheduleService) : BotCommand(CommandName.DAY.text, "") {
    private val daysMap = WeekDay.toMap()
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val timeCallback = HandlerName.TIME.text
        val weekOffset = arguments.getOrNull(1)?.toIntOrNull() ?: 0 // Определяем смещение недели, 0 по умолчанию
        val service = arguments[0]
        absSender.execute(showMenu(chat, timeCallback, weekOffset, service))
    }

    private fun showMenu(chat: Chat, timeCallback: String, weekOffset: Int, service: String): SendMessage {
        val weekOffsetCallback = HandlerName.WEEK_NAVIGATION.text
        val freeDays = getSlots(weekOffset, service)
        if (freeDays.isEmpty()) {
            return createMessage(chat.id.toString(), "Нет свободных слотов")
        }
        val buttons = getDaysButtons(timeCallback, freeDays) + listOf(
            listOf(
                "$weekOffsetCallback|previous|$weekOffset|$service" to "Пред. неделя",
                "$weekOffsetCallback|next|$weekOffset|$service" to "След. неделя"
            )
        )
        return createMessageWithInlineButtons(chat.id.toString(), "Выберите удобный день", buttons)
    }

    private fun getSlots(weekOffset: Int, service: String): List<ScheduleEntity> {
        // Определяем начало и конец недели на основе смещения
        val now = LocalDateTime.now()
        val startOfWeek = if (now.plusWeeks(weekOffset.toLong()).with(DayOfWeek.MONDAY).isBefore(now)) now
        else now.plusWeeks(weekOffset.toLong()).with(DayOfWeek.MONDAY)
        val endOfWeek = startOfWeek.with(DayOfWeek.SUNDAY).with(LocalTime.MAX)

        val freeDays = scheduleService.getFreeRegularSlotsByRange(startOfWeek, endOfWeek, service)
        return freeDays
    }

    private fun getDaysButtons(callback: String, schedules: List<ScheduleEntity>): List<List<Pair<String, String>>> {
        // Фильтруем расписание по выбранной неделе
        val availableDays = schedules.mapNotNull { schedule ->
            val dayRus = daysMap[schedule.weekDay]
            val date = slotDate(schedule)
            dayRus?.let { "$callback|time|${schedule.start}" to it + date }

        }.distinctBy { it.first }

        return listOf(availableDays)
    }
}