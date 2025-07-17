package shkond3rs.rickandmorty.data.network.model

data class LocationRemote(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)