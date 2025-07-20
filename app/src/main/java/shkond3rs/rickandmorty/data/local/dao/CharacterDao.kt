package shkond3rs.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import shkond3rs.rickandmorty.data.local.model.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}