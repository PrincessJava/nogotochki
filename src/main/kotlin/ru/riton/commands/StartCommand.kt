package ru.riton.ru.riton.commands

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessageWithInlineButtons
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.pairsToString
import ru.riton.ru.riton.service.TitleService
import ru.riton.ru.riton.service.UserService

@Component
class StartCommand(
    private val userService: UserService,
    private val titleService: TitleService
) : BotCommand(CommandName.START.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val callback = HandlerName.MAIN_MENU.text
        absSender.execute(showMenu(chat, user, callback))
    }

    private fun showMenu(chat: Chat, user: User, callback: String): SendMessage {
        val userExists = userService.userExistsByTgId(user.id)
        return if (userExists) buttonsForExistingUser(chat, user, callback)
        else buttonsForNonExistingUser(chat, user, callback)
    }

    private fun buttonsForExistingUser(
        chat: Chat,
        user: User,
        callback: String
    ) = createMessageWithInlineButtons(
        chat.id.toString(),
        String.format(titleService.getGreeting(), user.firstName),
        listOf(
            listOf(
                pairsToString(
                    ArgumentCode.HANDLER_NAME.text to callback,
                    ArgumentCode.COMMAND_NAME.text to CommandName.BUY.text
                ) to titleService.getBuy(),
                pairsToString(
                    ArgumentCode.HANDLER_NAME.text to callback,
                    ArgumentCode.COMMAND_NAME.text to CommandName.MENU_SERVICE.text
                ) to titleService.getAssign()
            ),
            listOf(
                pairsToString(
                    ArgumentCode.HANDLER_NAME.text to callback,
                    ArgumentCode.COMMAND_NAME.text to CommandName.INFO.text
                ) to titleService.getInfo(),
                pairsToString(
                    ArgumentCode.HANDLER_NAME.text to callback,
                    ArgumentCode.COMMAND_NAME.text to CommandName.ADMIN.text
                ) to titleService.getAdmin()
            ),
        )
    )

    private fun buttonsForNonExistingUser(
        chat: Chat,
        user: User,
        callback: String
    ) = createMessageWithInlineButtons(
        chat.id.toString(),
        String.format(titleService.getGreeting(), user.firstName),
        listOf(
            listOf(
                pairsToString(
                    ArgumentCode.HANDLER_NAME.text to callback,
                    ArgumentCode.COMMAND_NAME.text to CommandName.REGISTER.text
                ) to titleService.getRegister()
            )
        )
    )
}