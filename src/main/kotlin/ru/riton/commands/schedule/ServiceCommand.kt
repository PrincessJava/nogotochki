package ru.riton.ru.riton.commands.schedule

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessageWithInlineButtons
import ru.riton.ru.riton.createMessageWithInlineButtonsRows
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.pairsToString
import ru.riton.ru.riton.service.DescriptionService

@Component
class ServiceCommand(private val descriptionService: DescriptionService) : BotCommand(CommandName.SERVICE.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val callback = HandlerName.DAY.text

        absSender.execute(showMenu(chat, callback))
    }

    private fun showMenu(chat: Chat, callback: String): SendMessage {
        val buttons = getDescriptions(callback)
        return createMessageWithInlineButtonsRows(chat.id.toString(), "Выберите услугу", buttons)
    }

    private fun getDescriptions(callback: String): List<List<Pair<String, String>>> {
        val descriptions = descriptionService.getAll()
        // Фильтруем расписание по выбранной неделе
        val availableServices = descriptions.map { description ->
            pairsToString(
                ArgumentCode.HANDLER_NAME.text to callback, ArgumentCode.COMMAND_NAME.text to CommandName.DAY.text,
                ArgumentCode.DESCRIPTION_ID.text to description.id.toString()
            ) to description.value!!
        }.distinctBy { it.first }

        return listOf(availableServices)
    }
}