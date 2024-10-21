package ru.riton.ru.riton.model.enums

enum class ArgumentCode(val text: String) {
    SCHEDULE_ID("si"),
    TIME("tm"),
    TEXT("tx"),
    HANDLER_NAME("hn"),
    COMMAND_NAME("cn"),
    WEEK_OFFSET("wo"),
    ACTION("a"),
    BUTTON_TEXT("bt"),
    DESCRIPTION_ID("di");

    companion object {
        // Метод для поиска элемента Enum по значению
        fun fromValue(value: String): ArgumentCode? {
            return entries.find { it.text == value }
        }
    }
}