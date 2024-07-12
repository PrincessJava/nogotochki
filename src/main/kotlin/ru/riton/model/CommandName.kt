package ru.riton.ru.riton.model

enum class CommandName(val text: String) {
    START("start"),
    BUY("buy"),
    SCHEDULE("schedule"),
    INFO("info"),
    ADMIN("admin"),

    TIME("time"),
    MASTER("master"),
    DAY("day")
}