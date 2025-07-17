package shkond3rs.rickandmorty.data.network.model

data class EpisodeRemote(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)