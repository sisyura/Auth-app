package ru.nemov.authapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts_data")
data class AccountDataDBO(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo("accountName") val accountName: String,
    @ColumnInfo("secretKey") val secretKey: String
)
