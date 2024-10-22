package ru.nemov.authapp.ui.screen.add_account

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.nemov.authapp.domain.interactors.AccountsInteractor
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val interactor: Provider<AccountsInteractor>
) : ViewModel() {

    var accountName by mutableStateOf("")
        private set

    var key by mutableStateOf("")
        private set

    fun onAccountNameChange(newName: String) {
        accountName = newName
    }

    fun onKeyChange(newKey: String) {
        if (newKey.length <= 32) {
            key = newKey
        }
    }

    fun addAccount() {
        viewModelScope.launch {
            if (accountName.isNotBlank() && key.isNotBlank()) {
                interactor.get().saveAccount(accountName, key)
                accountName = ""
                key = ""
            }
        }
    }

    fun enableAddBtn(): Boolean {
        return accountName.isNotBlank()
    }

    fun changeAccountData(url: Uri) {
        var account = ""
        var website = ""
        println(url)
        if (url.scheme == "otpauth" && url.host == "totp") {
            url.path?.let {
                account = it.let { str ->
                    if (str.startsWith("/") && str.length > 1) {
                        str.substring(1)
                    } else {
                        str
                    }
                }
            }
            url.getQueryParameter("issuer")?.let {
                website = it
            }
            url.getQueryParameter("secret")?.let { key ->
                onKeyChange(key)
            }
            onAccountNameChange(website + account)
        }
    }
}