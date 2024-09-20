package ru.riton.config

import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class SpringLiquibaseConfig(private val dataSource: DataSource) {
    @Bean
    open fun liquibase(): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:migrations/changelog.xml"
        liquibase.dataSource = dataSource
        return liquibase
    }
}
