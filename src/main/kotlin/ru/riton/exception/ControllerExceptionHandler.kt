package ru.riton.ru.riton.exception

import jakarta.validation.ValidationException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoDataFoundException::class)
    fun handleNodataFoundException(ex: NoDataFoundException, request: WebRequest):
            ResponseEntity<Map<String, Any>> {

        val body = createBodyMessage(ex.message, ex.devMessage)
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException::class)
    fun handleUserExceptions(ex: UserException): ResponseEntity<Map<String, Any>> {
        val body = createBodyMessage(ex.message, ex.devMessage)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    fun handleValidationExceptions(ex: ValidationException): ResponseEntity<Map<String, Any>> {
        val body = createBodyMessage(ex.message, ex.stackTraceToString())
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationExceptions(ex: ConstraintViolationException): ResponseEntity<Map<String, Any>> {
        val body = createBodyMessage(ex.message, ex.stackTraceToString())
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    private fun createBodyMessage(message: String?, devMessage: String?): Map<String, Any> {
        val body: MutableMap<String, Any> = HashMap()
        body["date"] = Date()
        body["message"] = message.orEmpty()
        body["devMessage"] = devMessage.orEmpty()
        return body
    }
}