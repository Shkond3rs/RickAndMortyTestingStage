package shkond3rs.rickandmorty.domain.repository

import shkond3rs.rickandmorty.domain.model.Episode

interface EpisodeRepository {
    suspend fun getEpisodeById(id: Int): Episode
    suspend fun getEpisodesByIds(ids: List<Int>): List<Episode>
}