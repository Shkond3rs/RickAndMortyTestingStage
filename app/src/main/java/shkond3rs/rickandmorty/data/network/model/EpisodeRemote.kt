package shkond3rs.rickandmorty.data.network.model

import com.squareup.moshi.Json

data class EpisodeRemote(
    val id: Int,
    val name: String,
    @Json(name = "air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)