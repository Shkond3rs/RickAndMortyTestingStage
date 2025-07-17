package shkond3rs.rickandmorty.data.mapper

import shkond3rs.rickandmorty.data.local.model.LocationEntity
import shkond3rs.rickandmorty.data.network.model.LocationRemote
import shkond3rs.rickandmorty.domain.model.Location

fun LocationEntity.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationRemote.toEntity(): LocationEntity = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)