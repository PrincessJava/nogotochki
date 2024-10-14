package ru.riton.ru.riton.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import ru.riton.ru.riton.model.enums.Role


@Configuration
@EnableWebSecurity
@Profile("prod")
open class ProdSecurityConfig {

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/**").hasRole(Role.USER.name)
                    .requestMatchers("**/admin/**").hasRole(Role.ADMIN.name)
                    .requestMatchers("**/admin/**").hasRole(Role.OWNER.name)
            }
            .formLogin { f -> f.disable() }
            .requiresChannel { requiresChannel ->
                requiresChannel
                    .anyRequest().requiresSecure()
            }
            .csrf { csrf -> csrf.disable() } //todo add on prod
        return http.build()
    }

    @Throws(java.lang.Exception::class)
    fun configure(auth: AuthenticationManagerBuilder, passwordEncoder: PasswordEncoder) {
        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder)
            .withUser("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(Role.ADMIN.name, Role.USER.name, Role.OWNER.name)
    }
}