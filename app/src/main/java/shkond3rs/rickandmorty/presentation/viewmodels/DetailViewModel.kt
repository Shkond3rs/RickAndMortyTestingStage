package shkond3rs.rickandmorty.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shkond3rs.rickandmorty.data.repository.CharacterRepositoryImpl
import shkond3rs.rickandmorty.data.repository.EpisodeRepositoryImpl
import shkond3rs.rickandmorty.data.repository.LocationRepositoryImpl
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.domain.model.Episode
import shkond3rs.rickandmorty.domain.model.Location
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterRepos: CharacterRepositoryImpl,
    private val locationRepos: LocationRepositoryImpl,
    private val episodeRepos: EpisodeRepositoryImpl,
) : ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    private val _origin = MutableStateFlow<Location?>(null)
    val origin: StateFlow<Location?> = _origin

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchCharacter(id: Int) {
        viewModelScope.launch {
            _errorMessage.value = null

            try {
                val person = characterRepos.getCharacterById(id)
                _character.value = person

                val originAsync = async {
                    if (person?.originId != null && person.originId != -1)
                        locationRepos.getLocationById(person.originId)
                    else null
                }

                val locationAsync = async {
                    if (person?.locationId != null && person.locationId != -1)
                        locationRepos.getLocationById(person.locationId)
                    else null
                }

                val episodesAsync = async {
                    val ids = person?.episodeIds.orEmpty()
                    when (ids.size) {
                        0 -> emptyList()
                        1 -> listOf(episodeRepos.getEpisodeById(ids[0]))
                        else -> episodeRepos.getEpisodesByIds(ids)
                    }
                }

                _origin.value = originAsync.await()
                _location.value = locationAsync.await()
                _episodes.value = episodesAsync.await()
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка: ${e.message}"
            }
        }
    }
}