package ru.riton.ru.riton.model.dto

data class UserRequest(
    val name: String,
    var tgId: Long? = null,
    val phone: String
)