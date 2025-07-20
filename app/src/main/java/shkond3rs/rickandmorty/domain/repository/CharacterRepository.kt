package shkond3rs.rickandmorty.domain.repository

import kotlinx.coroutines.flow.Flow
import shkond3rs.rickandmorty.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacterById(id: Int): Character?
    suspend fun getAllCharacters(): Flow<List<Character>>
}