package ru.riton.ru.riton.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import ru.riton.ru.riton.model.enums.Role


@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/**").hasRole(Role.USER.name)
                    .requestMatchers("**/admin/**").hasRole(Role.ADMIN.name)
                    .requestMatchers("**/admin/**").hasRole(Role.OWNER.name)
            }
            .formLogin(Customizer.withDefaults())
            .requiresChannel { requiresChannel ->
                requiresChannel
                    .anyRequest().requiresSecure()
            }
        return http.build()
    }

    @Throws(java.lang.Exception::class)
    fun configure(auth: AuthenticationManagerBuilder) {
        // В продакшене используйте надёжный способ шифрования паролей
        val passwordEncoder = NoOpPasswordEncoder.getInstance()

        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder)
            .withUser("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(Role.ADMIN.name, Role.USER.name, Role.OWNER.name)
    }
}