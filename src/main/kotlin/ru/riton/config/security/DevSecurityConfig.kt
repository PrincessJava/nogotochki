package ru.riton.ru.riton.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@Configuration
//@EnableWebSecurity
@Profile("dev")
open class DevSecurityConfig {
}