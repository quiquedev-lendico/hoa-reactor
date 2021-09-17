package com.example.hoareactor.repository

object RepositoryErrors {
    class DatabaseError(t: Throwable? = null) : Throwable("data base error", t)
}
