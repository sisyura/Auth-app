package ru.nemov.authapp.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nemov.authapp.ui.screen.accounts.AccountListMain
import ru.nemov.authapp.ui.screen.add_account.AddAccountMain
import ru.nemov.authapp.ui.theme.AuthAppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        setContent {
            AuthAppTheme {
                Main()
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> { error("not init") }

@Composable
fun Main() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(navController, startDestination = NavRoutes.Accounts.route) {
                composable(NavRoutes.Accounts.route) { AccountListMain() }
                composable(NavRoutes.AddAccount.route) { AddAccountMain() }
                composable(NavRoutes.About.route) { }
            }
        }
    }
}

sealed class NavRoutes(val route: String) {
    data object Accounts : NavRoutes("accounts")
    data object AddAccount: NavRoutes("add_account")
    data object About: NavRoutes("about")
}