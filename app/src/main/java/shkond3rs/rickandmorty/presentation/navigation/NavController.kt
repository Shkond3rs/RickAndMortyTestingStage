package shkond3rs.rickandmorty.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import shkond3rs.rickandmorty.presentation.ui.screens.CharacterDetailScreen
import shkond3rs.rickandmorty.presentation.ui.screens.MainScreen
import shkond3rs.rickandmorty.presentation.ui.screens.TestScreen
import shkond3rs.rickandmorty.presentation.viewmodels.DetailViewModel
import shkond3rs.rickandmorty.presentation.viewmodels.MainViewModel

object NavRoutes {
    const val MAIN_SCREEN = "main_screen"
    const val CHARACTER_DETAILS = "character_details/{characterId}"
    const val TEST_SCREEN = "test_screen"
}

@Composable
fun NavController(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TEST_SCREEN
    ) {
        composable(NavRoutes.MAIN_SCREEN) {
            val vm: MainViewModel = hiltViewModel()
            MainScreen(mainVM = vm, onCharacterClick = { id ->
                navController.navigate("character_details/$id")
            })
        }
        composable(
            route = NavRoutes.CHARACTER_DETAILS,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType }),
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(1000),
                    targetOffsetX = { fullWidth -> fullWidth }
                )
            }
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            val vm: DetailViewModel = hiltViewModel()
            if (characterId != null) {
                CharacterDetailScreen(characterId = characterId, vm = vm, onBack = { navController.popBackStack() })
            }
        }
        composable(NavRoutes.TEST_SCREEN) {
            TestScreen {
                navController.navigate("main_screen")
            }
        }
    }
}