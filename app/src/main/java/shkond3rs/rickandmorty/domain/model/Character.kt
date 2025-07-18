package shkond3rs.rickandmorty.domain.model


data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val type: String?,
    val image: String,
    val episodeIds: List<Int>,

    val originId: Int,
    val locationId: Int,
)