package com.example.health2u.repo

class Injection {
    companion object {
        fun getRepositoryImpl(): Repository {
            return RepositoryImpl()
        }
    }
}