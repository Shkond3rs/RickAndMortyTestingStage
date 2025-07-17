package shkond3rs.rickandmorty.domain.repository

import shkond3rs.rickandmorty.domain.model.Location

interface LocationRepository {
    suspend fun getLocationById(id: Int): Location
}