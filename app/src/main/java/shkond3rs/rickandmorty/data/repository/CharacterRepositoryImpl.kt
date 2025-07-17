package shkond3rs.rickandmorty.data.repository

import shkond3rs.rickandmorty.data.network.RickAndMortyApiService
import shkond3rs.rickandmorty.data.mapper.toDomain
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApiService
) : CharacterRepository {
    override suspend fun getAllCharacters(): List<Character> =
        api.getAllCharacters().results.map { it.toDomain() }
}