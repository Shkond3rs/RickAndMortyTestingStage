package shkond3rs.rickandmorty.data.repository

import shkond3rs.rickandmorty.data.local.dao.CharacterDao
import shkond3rs.rickandmorty.data.local.dao.EpisodeDao
import shkond3rs.rickandmorty.data.local.dao.LocationDao
import shkond3rs.rickandmorty.data.network.RickAndMortyApiService
import shkond3rs.rickandmorty.data.mapper.toDomain
import shkond3rs.rickandmorty.data.mapper.toEntity
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao,
    private val episodeDao: EpisodeDao
) : CharacterRepository {

    override suspend fun getCharacterById(id: Int): Character? =
        characterDao.getCharacterById(id)?.toDomain()

    override suspend fun getAllCharacters(): List<Character> {
        // При запуске пробуем загрузить данные из БД
        val local = characterDao.getAllCharacters()
        if (local.isNotEmpty()) return local.map { it.toDomain() }

        // Если БД пустая, запрашиваем данные с сервера
        val remote = api.getAllCharacters()
        val entity = remote.results.map { it.toEntity() }

        // Сохраняем полученные данные в БД
        characterDao.insertAll(entity)

        return entity.map { it.toDomain() }
    }
}