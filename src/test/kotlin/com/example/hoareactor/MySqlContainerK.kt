package com.example.hoareactor

import org.testcontainers.containers.MySQLContainer

class MySqlContainerK(image: String) : MySQLContainer<MySqlContainerK>(image)