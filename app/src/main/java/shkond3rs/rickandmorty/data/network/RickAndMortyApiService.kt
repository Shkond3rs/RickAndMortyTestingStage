package shkond3rs.rickandmorty.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import shkond3rs.rickandmorty.data.network.model.CharacterResponse
import shkond3rs.rickandmorty.data.network.model.EpisodeRemote
import shkond3rs.rickandmorty.data.network.model.LocationRemote

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterResponse

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationRemote

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id")id: Int): EpisodeRemote

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids")ids: String): List<EpisodeRemote>
}