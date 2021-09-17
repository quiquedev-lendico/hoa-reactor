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
import javax.sql.DataSource

@Configuration
class RepositoryConfiguration {

    @Bean
    fun dsl(dataSource: DataSource): DSLContext =
        DSL.using(
            dataSource,
            SQLDialect.MYSQL,
            Settings().withRenderNameCase(LOWER)
        )


}