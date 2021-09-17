package com.example.hoareactor.configuration

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.conf.RenderNameCase.LOWER
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.DriverManager

@Configuration
class RepositoryConfiguration {

    @Bean
    fun dsl(
        @Value("\${spring.datasource.username}")
        dbUserName: String,
        @Value("\${spring.datasource.password}")
        dbPassword: String,
        @Value("\${spring.datasource.url}")
        jdbcUrl: String
    ): DSLContext = DSL.using(
        DriverManager.getConnection(jdbcUrl, dbUserName, dbPassword),
        SQLDialect.MYSQL,
        Settings().withRenderNameCase(LOWER)
    )
}