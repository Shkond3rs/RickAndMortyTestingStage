package shkond3rs.rickandmorty.data.mapper

import shkond3rs.rickandmorty.data.local.model.EpisodeEntity
import shkond3rs.rickandmorty.data.network.model.EpisodeRemote
import shkond3rs.rickandmorty.domain.model.Episode

fun EpisodeRemote.toDomain(): Episode = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

fun EpisodeRemote.toEntity(): EpisodeEntity = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

// Работает для одного эпизода
fun EpisodeEntity.toDomain(): Episode = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)