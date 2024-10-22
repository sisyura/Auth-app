package ru.nemov.authapp.ui.screen.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base32
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Hex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.nemov.authapp.domain.models.AccountData
import ru.nemov.authapp.domain.interactors.AccountsInteractor
import ru.nemov.authapp.utils.TOTP
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val interactor: Provider<AccountsInteractor>
) : ViewModel() {

    val accounts: StateFlow<List<AccountData>> =
        interactor.get().invoke().stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    fun deleteAccount(accountData: AccountData) {
        viewModelScope.launch {
            interactor.get().deleteAccount(accountData)
        }
    }

    fun getTOTPCode(secretKey: String): String {

        val base32 = Base32()
        val bytes = base32.decode(secretKey)
        val hexKey = Hex.encodeHexString(bytes)
        return try {
            TOTP.getOTP(hexKey)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e)
        }
    }
}