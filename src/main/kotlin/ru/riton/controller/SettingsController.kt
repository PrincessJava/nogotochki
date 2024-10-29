package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.riton.ru.riton.model.dto.MenuItemsDto
import ru.riton.ru.riton.service.TitleService

@RestController
@RequestMapping("/settings")
class SettingsController(private val titleService: TitleService) {

    @GetMapping("/labels")
    fun getLabels(): ResponseEntity<MenuItemsDto> {
        return ResponseEntity.ok(titleService.getMenuItems())
    }
}