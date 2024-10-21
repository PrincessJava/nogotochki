package ru.riton.ru.riton.handlers

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.commands.schedule.DayCommand
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.model.enums.HandlerName

@Component
class WeekNavigationHandler(private val dayCommand: DayCommand) : CallbackHandler {

    override val name: HandlerName = HandlerName.WEEK_NAVIGATION

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: Map<ArgumentCode, String>) {
        val chat = (callbackQuery.message as Message).chat
        val user = callbackQuery.from

        val currentWeekOffset = arguments[ArgumentCode.WEEK_OFFSET]?.toIntOrNull() ?: 0
        val action = arguments[ArgumentCode.ACTION] // Может быть "previous" или "next"

        val newWeekOffset = when (action) {
            "previous" -> currentWeekOffset - 1
            "next" -> currentWeekOffset + 1
            else -> currentWeekOffset
        }

        dayCommand.execute(absSender, user, chat, arrayOf(newWeekOffset.toString()))
    }
}
