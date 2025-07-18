package shkond3rs.rickandmorty.data.mapper

import shkond3rs.rickandmorty.data.local.model.CharacterEntity
import shkond3rs.rickandmorty.data.network.model.CharacterRemote
import shkond3rs.rickandmorty.domain.model.Character

fun CharacterRemote.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    type = type,
    image = image,
    episodeIds = episode.mapNotNull { it.substringAfterLast("/").toIntOrNull() },
    originId = -1,
    locationId = -1
)

fun CharacterRemote.toEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    originId = getIdFromUrl(origin.url),
    locationId = getIdFromUrl(location.url),
    image = image,
    episodeIds = episode.mapNotNull { it.substringAfterLast("/").toIntOrNull() }
)

fun CharacterEntity.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    image = image,
    type = type,
    episodeIds = episodeIds,
    originId = originId,
    locationId = locationId
)

// Если возвращается -1, значит у персонажа неизвестно происхождение
fun getIdFromUrl(url: String?): Int {
    val part = url?.substringAfterLast("/")?.takeIf { it.isNotBlank() } ?: return -1
    return part.toIntOrNull() ?: -1
}