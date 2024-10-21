package ru.riton.ru.riton.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.stereotype.Component
import java.util.*


@Component
class RequestLoggingFilter: Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        Context.requestId = UUID.randomUUID().toString()
        chain.doFilter(request, response)
    }
}
