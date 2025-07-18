package shkond3rs.rickandmorty.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shkond3rs.rickandmorty.data.local.dao.CharacterDao
import shkond3rs.rickandmorty.data.repository.CharacterRepositoryImpl
import shkond3rs.rickandmorty.domain.model.Character
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val dao: CharacterDao
) : ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    init {
        fetchCharacters()
    }
    fun fetchCharacters() {
        viewModelScope.launch {
            try {
                _characters.value = repository.getAllCharacters()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка: ${e.message}"
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAllCharacters()
        }
    }
}