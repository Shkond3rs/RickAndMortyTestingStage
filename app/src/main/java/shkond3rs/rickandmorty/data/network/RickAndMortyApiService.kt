package shkond3rs.rickandmorty.data.network

import retrofit2.http.GET
import shkond3rs.rickandmorty.data.network.model.CharacterResponse

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterResponse
}