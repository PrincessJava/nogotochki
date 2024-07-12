package ru.riton.ru.riton

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import ru.riton.ru.riton.config.TelegramProperties
import ru.riton.ru.riton.handlers.CallbackHandler

@Component
class NogotochkiBot(
    private val properties: TelegramProperties,
    commands: Set<BotCommand>,
    callbackHandlers: Set<CallbackHandler>
) : TelegramLongPollingCommandBot(properties.botToken) {

    private lateinit var handlerMapping: Map<String, CallbackHandler>

    init {
        registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }
    }

    fun getCommand(commandName: String): BotCommand {
        return getRegisteredCommand(commandName) as BotCommand
    }

    override fun getBotUsername(): String {
        return properties.botUsername
    }

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
//            do nothing yet
        } else if (update.hasCallbackQuery()) {
            val callbackQuery = update.callbackQuery
            val callbackData = callbackQuery.data

            val callbackQueryId = callbackQuery.id
            execute(AnswerCallbackQuery(callbackQueryId))

            val callbackArguments = callbackData.split("|")
            val callbackHandlerName = callbackArguments.first()

            handlerMapping.getValue(callbackHandlerName)
                .processCallbackData(
                    this,
                    callbackQuery,
                    callbackArguments.subList(1, callbackArguments.size)
                )
        }
    }
}