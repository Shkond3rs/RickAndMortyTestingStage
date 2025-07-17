package shkond3rs.rickandmorty.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import shkond3rs.rickandmorty.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mod: Modifier = Modifier,
    mainVM: MainViewModel
) {
    val characters by mainVM.characters.collectAsState()
    val errorMessage by mainVM.errorMessage.collectAsState()
    Column(modifier = mod.systemBarsPadding().fillMaxSize().padding(32.dp)) {
        Text(text = "Привет")
        Text(text = "${characters.size}")
        Text(text = errorMessage ?: "Ошибки нет")
        Button(onClick = {
            mainVM.fetchCharacters()
        }) {
            Text(text = "Load")
        }
    }
}