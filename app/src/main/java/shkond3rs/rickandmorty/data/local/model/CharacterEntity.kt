package shkond3rs.rickandmorty.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,

    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,

    val originId: Int,
    val locationId: Int,

    val image: String,
    val episodeIds: List<Int>
)