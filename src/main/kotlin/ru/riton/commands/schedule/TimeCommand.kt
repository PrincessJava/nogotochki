package ru.riton.ru.riton.commands.schedule

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.*
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.service.ScheduleService
import java.time.LocalDateTime

@Component
class TimeCommand(private val scheduleService: ScheduleService) : BotCommand(CommandName.TIME.text, "") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(showMenu(chat, arguments[0]))
    }

    private fun showMenu(chat: Chat, time: String): SendMessage {
        val slots = scheduleService.getFreeRegularSlotsByDay(parseDate(time))
        val buttons = getSlotsButtons(slots)
        return createMessageWithInlineButtons(chat.id.toString(), "Выберите удобное время", buttons)
    }

    private fun getSlotsButtons(slots: List<ScheduleEntity>): List<List<Pair<String, String>>> {
        val callback = HandlerName.APPOINTMENT.text
        val slotButtons = slots.map { slot ->
            // Формируем пару из callback и текста кнопки
            val slotStr = slotTime(slot)
            pairsToString(
                ArgumentCode.HANDLER_NAME.text to callback, ArgumentCode.SCHEDULE_ID.text to slot.id.toString()
            ) to slotStr
        }

        // Группируем кнопки по одной в каждой строке
        return slotButtons.map { listOf(it) }
    }
}