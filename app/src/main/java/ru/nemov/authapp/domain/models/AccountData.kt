package ru.nemov.authapp.domain.models

data class AccountData(
    val id: Int,
    val accountName: String,
    val secretKey: String
)

