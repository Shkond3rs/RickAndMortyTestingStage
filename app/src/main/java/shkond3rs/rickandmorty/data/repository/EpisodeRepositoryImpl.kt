package shkond3rs.rickandmorty.data.repository

import shkond3rs.rickandmorty.data.local.dao.EpisodeDao
import shkond3rs.rickandmorty.data.mapper.toDomain
import shkond3rs.rickandmorty.data.mapper.toEntity
import shkond3rs.rickandmorty.data.network.RickAndMortyApiService
import shkond3rs.rickandmorty.domain.model.Episode
import shkond3rs.rickandmorty.domain.repository.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApiService,
    private val dao: EpisodeDao
) : EpisodeRepository {
    override suspend fun getEpisodeById(id: Int): Episode {
        // Получаем данные из БД
        val local = dao.getEpisodeById(id)
        if (local != null) return local.toDomain()

        // Если в БД нет данных
        val remote = api.getEpisodeById(id)
        val entity = remote.toEntity()

        // Сохраняем в БД
        dao.insertEpisode(entity)

        return entity.toDomain()
    }

    override suspend fun getEpisodesByIds(ids: List<Int>): List<Episode> {
        val local = dao.getEpisodesByIds(ids)
        if (local.size == ids.size) return local.map { it.toDomain() }

        // ids.joinToString(",") для запроса к API
        val idsString = ids.joinToString(",")
        val remote = api.getEpisodesByIds(idsString)
        val entity = remote.map { it.toEntity() }

        // Сохраняем все в базу
        dao.insertEpisodes(entity)

        return entity.map { it.toDomain() }
    }
}