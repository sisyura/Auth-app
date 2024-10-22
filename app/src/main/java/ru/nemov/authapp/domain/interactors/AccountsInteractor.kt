package ru.nemov.authapp.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.nemov.authapp.data.repository.AccountsRepository
import ru.nemov.authapp.domain.models.AccountData
import javax.inject.Inject

class AccountsInteractor @Inject constructor(
    private val repository: AccountsRepository
) {
    operator fun invoke(): Flow<List<AccountData>> {
        return repository.getAll()
    }

    suspend fun saveAccount(name: String, key: String) {
        repository.addNewAccountData(name, key)
    }

    suspend fun deleteAccount(accountData: AccountData) {
        repository.deleteAccountData(accountData)
    }

}