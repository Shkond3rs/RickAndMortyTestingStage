package shkond3rs.rickandmorty.domain.repository

import shkond3rs.rickandmorty.domain.model.Character

interface CharacterRepository {
    suspend fun getAllCharacters(): List<Character>
}