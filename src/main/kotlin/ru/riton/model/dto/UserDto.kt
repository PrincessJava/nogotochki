package ru.riton.ru.riton.model.dto

import jakarta.persistence.Basic
import jakarta.persistence.Column

class UserDto(
    val name: String,
    var tgId: String? = null,
    val phoneNumber: String
)