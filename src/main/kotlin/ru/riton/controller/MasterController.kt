package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.MastersEntity
import ru.riton.ru.riton.model.dto.UserRequest
import ru.riton.ru.riton.service.MasterService

@RestController
@RequestMapping("/masters")
class MasterController(private val masterService: MasterService) {

    @PostMapping
    fun addMaster(@RequestBody userRequest: UserRequest): ResponseEntity<MastersEntity> {
        return ResponseEntity.ok(masterService.addMaster(userRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteMaster(@PathVariable id: Int): ResponseEntity<MastersEntity> {
        masterService.deleteMaster(id)
        return ResponseEntity.ok().build()
    }
}