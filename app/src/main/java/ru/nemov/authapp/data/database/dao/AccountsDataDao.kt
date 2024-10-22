package ru.nemov.authapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nemov.authapp.data.database.models.AccountDataDBO

@Dao
interface AccountsDataDao{

    @Query("SELECT * FROM accounts_data")
    fun getAll(): Flow<List<AccountDataDBO>>

    @Insert
    suspend fun insert(otpData: AccountDataDBO)

    @Delete
    suspend fun delete(otpData: AccountDataDBO)
}