package ru.riton.ru.riton

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


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