package ru.riton.ru.riton

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.enums.WeekDay
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun addButton(text: String, data: String): InlineKeyboardButton {
    val button = InlineKeyboardButton()

    button.text = text
    button.callbackData = data

    return button
}

// Создание клавиатуры с кнопками
fun createMarkup(buttons: ArrayList<InlineKeyboardButton>): InlineKeyboardMarkup {
    val markup = InlineKeyboardMarkup()
    markup.keyboard = listOf(buttons)
    return markup
}

fun createMessageWithInlineButtons(chatId: String, text: String, inlineButtons: List<List<Pair<String, String>>>) =
    createMessage(chatId, text)
        .apply {
            replyMarkup = getInlineKeyboard(inlineButtons)
        }

fun getInlineKeyboard(allButtons: List<List<Pair<String, String>>>): InlineKeyboardMarkup =
    InlineKeyboardMarkup().apply {
        keyboard = allButtons.map { rowButtons ->
            rowButtons.map { (data, buttonText) ->
                InlineKeyboardButton().apply {
                    text = buttonText
                    callbackData = data
                }
            }
        }
    }

fun createMessage(chatId: String, text: String) =
    SendMessage(chatId, text)
        .apply { enableMarkdown(true) }
        .apply { disableWebPagePreview() }

fun <T> distributeEvenly(elements: List<T>, rows: Int): List<List<T>> {
    val result = mutableListOf<List<T>>()

    val totalElements = elements.size
    val baseCount = totalElements / rows // Количество элементов на строку
    val extraCount = totalElements % rows // Количество строк, которые получат на один элемент больше

    var currentElementIndex = 0

    for (row in 0 until rows) {
        // Для первых 'extraCount' строк добавляем на один элемент больше
        val elementsInThisRow = if (row < extraCount) baseCount + 1 else baseCount
        val rowElements = mutableListOf<T>()

        for (element in 0 until elementsInThisRow) {
            if (currentElementIndex < totalElements) {
                rowElements.add(elements[currentElementIndex++])
            }
        }

        result.add(rowElements)
    }

    return result
}

fun getDaysByWeek(
    schedules: List<ScheduleEntity>,
    weekOffset: Int,
    daysMap: Map<WeekDay, String>
): List<List<Pair<String, String>>> {
    val now = LocalDateTime.now()
    val startOfWeek = now.plusWeeks(weekOffset.toLong()).with(DayOfWeek.MONDAY)
    val endOfWeek = startOfWeek.with(DayOfWeek.SUNDAY)

    val availableDays = schedules.mapNotNull { schedule ->
        val scheduleDate = schedule.start.toLocalDateTime()
        if (scheduleDate.toLocalDate() in startOfWeek.toLocalDate()..endOfWeek.toLocalDate()) {
            val dayRus = daysMap[schedule.weekDay]
            val day = scheduleDate.dayOfMonth
            val month = scheduleDate.monthValue
            val date = " $day.$month"
            dayRus?.let { "${schedule.weekDay.name}|time" to it + date }
        } else {
            null
        }
    }.distinctBy { it.first }

    return listOf(availableDays)
}

fun formatTime(timestamp: Timestamp): String {
    val localDateTime: LocalDateTime = timestamp.toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(formatter)
}

fun parseDate(date: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
    return LocalDateTime.parse(date, formatter)
}

fun slotTime(slot: ScheduleEntity): String {
    val start = formatTime(slot.start)
    val finish = formatTime(slot.finish)
    val slotStr = "$start - $finish"
    return slotStr
}

fun slotDate(slot: ScheduleEntity): String {
    val scheduleDate = slot.start.toLocalDateTime()
    val day = scheduleDate.dayOfMonth
    val month = scheduleDate.monthValue
    val date = " $day.$month"
    return date
}

fun editLastMessage(absSender: AbsSender, callbackQuery: CallbackQuery) {
    val messageId = callbackQuery.message.messageId
    val chatId = callbackQuery.message.chatId.toString()
    val buttonText = callbackQuery.data.split("|").last().trim() // This contains the callback data (button identifier)

    val newMessageText = "${callbackQuery.message}\n\nВы выбрали: $buttonText"

    val editMessageText = EditMessageText()
    editMessageText.chatId = chatId
    editMessageText.messageId = messageId
    editMessageText.text = newMessageText

    // Remove the inline buttons
    editMessageText.replyMarkup = InlineKeyboardMarkup(emptyList())

    absSender.execute(editMessageText)
}