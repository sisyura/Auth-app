package ru.nemov.authapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nemov.authapp.data.database.dao.AccountsDataDao
import ru.nemov.authapp.data.database.models.AccountDataDBO

class AccountsDataDatabase internal constructor(private val database: AccountsDataRoomDatabase) {

    val accountsDataDao: AccountsDataDao
        get() = database.AccountsDataDao()
}

@Database(entities = [AccountDataDBO::class], version = 1, exportSchema = false)
internal abstract class AccountsDataRoomDatabase : RoomDatabase() {
    abstract fun AccountsDataDao() : AccountsDataDao
}

fun AccountsDataDatabase(applicationContext: Context): AccountsDataDatabase {
    val accountsDataDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        AccountsDataRoomDatabase::class.java,
        "otp_database"
    ).build()
    return AccountsDataDatabase(accountsDataDatabase)
}