import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypassmanagerapp.screens.AuthScreen

@Composable
fun PasswordManagerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {
       composable("auth") { AuthScreen(navController) }
        composable("home") { PasswordListScreen(navController) }
        composable("add") { AddEditPasswordScreen(navController) }
    }
}
