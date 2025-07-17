package shkond3rs.rickandmorty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import shkond3rs.rickandmorty.presentation.ui.screens.MainScreen
import shkond3rs.rickandmorty.presentation.viewmodels.MainViewModel

object NavRoutes {
    const val MAIN_SCREEN = "main_screen"
    const val CHARACTER_DETAILS = "character_details"
}

@Composable
fun NavController(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN_SCREEN
    ) {
        composable(NavRoutes.MAIN_SCREEN) {
            val vm: MainViewModel = hiltViewModel()
            MainScreen(mainVM = vm)
        }
    }
}