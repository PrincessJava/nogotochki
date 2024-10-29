package ru.riton.ru.riton.model.dto

data class MenuItemsDto(
//    val company: String,
    val labels: Labels
) {
    data class Labels(
        val master: String,
        val masters: String,
        val masterList: String,
        val addMaster: String
    )
}