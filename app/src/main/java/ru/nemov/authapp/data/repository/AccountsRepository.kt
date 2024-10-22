package ru.nemov.authapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nemov.authapp.data.database.AccountsDataDatabase
import ru.nemov.authapp.data.database.common.Logger
import ru.nemov.authapp.data.database.models.AccountDataDBO
import ru.nemov.authapp.domain.models.AccountData
import ru.nemov.authapp.domain.toAccountData
import ru.nemov.authapp.domain.toAccountDataDBO
import javax.inject.Inject

class AccountsRepository @Inject constructor(
    private val database: AccountsDataDatabase,
    private val logger: Logger
) {

    fun getAll(): Flow<List<AccountData>> {
        return database.accountsDataDao.getAll().map { dbos -> dbos.map { it.toAccountData() } }
    }

    suspend fun addNewAccountData(accountName: String, secretKey: String) {
        database.accountsDataDao.insert(
            AccountDataDBO(
                accountName = accountName,
                secretKey = secretKey
            )
        )
        logger.d(LOG_TAG, "Account $accountName added successfully!")
    }

    suspend fun deleteAccountData(otpData: AccountData) {
        val dbo = otpData.toAccountDataDBO()
        database.accountsDataDao.delete(dbo)
        logger.d(LOG_TAG, "Account ${otpData.accountName} deleted successfully!")
    }

    private companion object {
        const val LOG_TAG = "AccountsRepository"
    }
}