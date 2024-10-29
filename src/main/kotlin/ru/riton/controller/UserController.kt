package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserRequest
import ru.riton.ru.riton.model.dto.UserResponse
import ru.riton.ru.riton.service.UserService

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUser(@RequestParam phone: String): ResponseEntity<UserEntity> {
        return ResponseEntity.ok(userService.getUser(phone))
    }

    @PutMapping("/pass")
    fun setPassword(@RequestBody setPasswordDto: SetPasswordDto): ResponseEntity<Unit> {
        userService.setPassword(setPasswordDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun addUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserEntity> {
        return ResponseEntity.ok(userService.addUser(userRequest))
    }

    @GetMapping("/all")
    fun gelAll(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getClients())
    }

    @GetMapping("/admins")
    fun gelAllAdmins(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.gelAdmins())
    }

    @PutMapping("/{id}")
    fun updateInfo(@PathVariable id: Int, @RequestBody userRequest: UserRequest): ResponseEntity<Unit> {
        userService.updateUserInfo(id, userRequest)
        return ResponseEntity.ok().build()
    }
}