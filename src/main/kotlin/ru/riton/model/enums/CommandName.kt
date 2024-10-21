package ru.riton.ru.riton.model.enums

enum class CommandName(val text: String) {
    START("start"),
    REGISTER("register"),
    BUY("buy"),
    MENU_SERVICE("schedule"),
    INFO("info"),
    ADMIN("admin"),

    TIME("time"),
    MASTER("master"),
    DAY("day"),
    SERVICE("service")
}