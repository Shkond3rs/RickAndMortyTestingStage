package shkond3rs.rickandmorty.data.mapper

import shkond3rs.rickandmorty.data.network.model.CharacterRemote
import shkond3rs.rickandmorty.domain.model.Character

fun CharacterRemote.toDomain(): Character = Character(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    gender = this.gender,
    image = this.image,
)
