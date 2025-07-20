package shkond3rs.rickandmorty.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import shkond3rs.rickandmorty.data.local.dao.CharacterDao
import shkond3rs.rickandmorty.data.mapper.toDomain
import shkond3rs.rickandmorty.data.repository.CharacterRepositoryImpl
import shkond3rs.rickandmorty.domain.model.Character
import javax.inject.Inject

sealed class CharactersUiState {
    object Loading : CharactersUiState()
    data class Success(val characters: List<Character>) : CharactersUiState()
    data class Error(val message: String) : CharactersUiState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val dao: CharacterDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState

    val characters: StateFlow<List<Character>> =
        dao.getAllCharacters()
            .map {
                it.map { entity -> entity.toDomain() }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState.Loading
            try {
                repository.refreshCharactersFromApi()
                repository.getAllCharacters().collect { characters ->
                    _uiState.value = CharactersUiState.Success(characters)
                }
            } catch (e: Exception) {
                 _uiState.value = CharactersUiState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun retry() = getCharacters()

    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAllCharacters()
        }
    }
}