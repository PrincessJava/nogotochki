package ru.riton.ru.riton.model

import ru.riton.ru.riton.model.enums.UserState

data class UserSession(
    var state: UserState = UserState.ASKING_NAME,
    var name: String? = null,
    var phoneNumber: String? = null
)