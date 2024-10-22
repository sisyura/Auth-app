package ru.nemov.authapp.domain

import ru.nemov.authapp.domain.models.AccountData
import ru.nemov.authapp.data.database.models.AccountDataDBO

internal fun AccountDataDBO.toAccountData() : AccountData {
    return AccountData(id = id, accountName = accountName, secretKey = secretKey)
}

internal fun AccountData.toAccountDataDBO() : AccountDataDBO {
    return AccountDataDBO(id = id, accountName = accountName, secretKey = secretKey)
}