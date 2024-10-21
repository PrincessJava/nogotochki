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
class MenuServiceCommand(private val userService: UserService,
    private val titleService: TitleService) : BotCommand(CommandName.MENU_SERVICE.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(showMenu(chat, user))
    }

    private fun showMenu(chat: Chat, user: User): SendMessage {
        if (!userService.userExistsByTgId(user.id)) {
            return createMessageWithInlineButtons(
                chat.id.toString(),
                titleService.getNotRegistered(),
                listOf(
                    listOf(
                        pairsToString(
                            ArgumentCode.HANDLER_NAME.text to HandlerName.REGISTER.text,
                            ArgumentCode.COMMAND_NAME.text to CommandName.REGISTER.text
                        ) to titleService.getRegister()
                    )
                )
            )
        }

        val serviceCallback = HandlerName.SERVICE.text
        val masterCallback = HandlerName.MASTER.text
        val serviceCommand = CommandName.SERVICE.text
        val masterCommand = CommandName.MASTER.text

        return createMessageWithInlineButtons(
            chat.id.toString(),
            titleService.getChooseAssign(),
            listOf(
                listOf(
                    pairsToString(
                        ArgumentCode.HANDLER_NAME.text to serviceCallback,
                        ArgumentCode.COMMAND_NAME.text to serviceCommand
                    ) to titleService.getOnClass(),
                    pairsToString(
                        ArgumentCode.HANDLER_NAME.text to masterCallback,
                        ArgumentCode.COMMAND_NAME.text to masterCommand
                    ) to titleService.getToMaster()
                )
            )
        )
    }
}