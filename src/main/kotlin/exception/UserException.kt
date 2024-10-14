package ru.riton.exception

import java.lang.RuntimeException

class UserException(userMessage: String,
    val devMessage: String) : RuntimeException(userMessage) {
}