package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.service.UserService

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUser(@RequestParam phone: String): ResponseEntity<UserEntity> {
        return ResponseEntity.ok(userService.getUser(phone))
    }

    @PutMapping
    fun setPassword(@RequestBody setPasswordDto: SetPasswordDto): ResponseEntity<Unit> {
        userService.setPassword(setPasswordDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun addUser(@RequestBody userDto: UserDto): ResponseEntity<UserEntity> {
        return ResponseEntity.ok(userService.addUser(userDto))
    }
}