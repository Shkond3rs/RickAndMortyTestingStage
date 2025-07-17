package shkond3rs.rickandmorty.data.repository

import shkond3rs.rickandmorty.data.local.dao.LocationDao
import shkond3rs.rickandmorty.data.mapper.toDomain
import shkond3rs.rickandmorty.data.mapper.toEntity
import shkond3rs.rickandmorty.data.network.RickAndMortyApiService
import shkond3rs.rickandmorty.domain.model.Location
import shkond3rs.rickandmorty.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApiService,
    private val dao: LocationDao,
) : LocationRepository {
    override suspend fun getLocationById(id: Int): Location {
        // Берём данные из БД
        val location = dao.getLocationById(id)
        if (location != null) return location.toDomain()

        // Если БД пустая (а при первом обращении она точно будет пустой)
        val remote = api.getLocationById(id)
        val entity = remote.toEntity()

        // Сохраняем в БД
        dao.insertLocation(entity)

        return entity.toDomain()
    }
}