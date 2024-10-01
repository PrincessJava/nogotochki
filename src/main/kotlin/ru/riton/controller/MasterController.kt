package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.riton.model.MastersEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.service.MasterService

@RestController
@RequestMapping("/masters")
class MasterController(private val masterService: MasterService) {

    @PostMapping
    fun addMaster(@RequestBody userDto: UserDto): ResponseEntity<MastersEntity> {
        return ResponseEntity.ok(masterService.addMaster(userDto))
    }
}