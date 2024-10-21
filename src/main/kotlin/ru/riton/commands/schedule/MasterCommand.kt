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
import ru.riton.ru.riton.service.TitleService
import java.time.LocalTime
import kotlin.random.Random

@Component
class MasterCommand(private val titleService: TitleService) : BotCommand(CommandName.MASTER.text, "") {
    private val days = listOf(
        "пн" to "mon", "вт" to "tue", "ср" to "wed",
        "чт" to "thu", "пт" to "fri", "сб" to "sat", "вс" to "sun"
    )

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val callback = HandlerName.TIME.text
        absSender.execute(showMenu(chat, callback))
    }

    private fun showMenu(chat: Chat, callback: String): SendMessage =
        createMessageWithInlineButtons(chat.id.toString(), "Выберите удобный день", getFreeDaysByMaster(callback))

    private fun getFreeDaysByMaster(callback: String): List<List<Pair<String, String>>> {
        val rand = Random(LocalTime.now().toNanoOfDay())
        val toIndex = rand.nextInt(7)
        val fromIndex = rand.nextInt(toIndex)

        val availableDays = days.subList(fromIndex, toIndex)

        return listOf(availableDays.map { "$callback|${it.second}|master" to it.first })
    }
}