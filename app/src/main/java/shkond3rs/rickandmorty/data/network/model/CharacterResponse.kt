package shkond3rs.rickandmorty.data.network.model

data class CharacterResponse(
    val info: Info,
    val results: List<CharacterRemote>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)

data class CharacterRemote(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class Origin(
    val name: String,
    val url: String,
)

data class Location(
    val name: String,
    val url: String,
)