package ru.riton.ru.riton.commands.schedule

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessageWithInlineButtons
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.service.ScheduleService

@Component
class ServiceCommand(private val scheduleService: ScheduleService) : BotCommand(CommandName.SERVICE.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val callback = HandlerName.DAY.text

        absSender.execute(showMenu(chat, callback))
    }

    private fun showMenu(chat: Chat, callback: String): SendMessage {
        val buttons = getDescriptions(callback)
        return createMessageWithInlineButtons(chat.id.toString(), "Выберите услугу", buttons)
    }
    private fun getDescriptions(callback: String): List<List<Pair<String, String>>> {
        val services = scheduleService.getAllServices()
        // Фильтруем расписание по выбранной неделе
        val availableServices = services.map { service ->
            "$callback|day|${service}" to service
        }.distinctBy { it.first }

        return listOf(availableServices)
    }
}