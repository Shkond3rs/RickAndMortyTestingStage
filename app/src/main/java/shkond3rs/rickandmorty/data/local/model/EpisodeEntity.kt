package shkond3rs.rickandmorty.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "air_date") val airDate: String,
    val episode: String
)