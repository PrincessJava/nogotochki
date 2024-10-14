package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @DeleteMapping("/{id}")
    fun deleteMaster(@PathVariable id: Int): ResponseEntity<MastersEntity> {
        masterService.deleteMaster(id)
        return ResponseEntity.ok().build()
    }
}