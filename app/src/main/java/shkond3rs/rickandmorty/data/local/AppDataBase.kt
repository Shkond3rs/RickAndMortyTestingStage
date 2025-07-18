package shkond3rs.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import shkond3rs.rickandmorty.data.local.converters.EpisodeListConverter
import shkond3rs.rickandmorty.data.local.dao.CharacterDao
import shkond3rs.rickandmorty.data.local.dao.EpisodeDao
import shkond3rs.rickandmorty.data.local.dao.LocationDao
import shkond3rs.rickandmorty.data.local.model.CharacterEntity
import shkond3rs.rickandmorty.data.local.model.EpisodeEntity
import shkond3rs.rickandmorty.data.local.model.LocationEntity

@Database(entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class], version = 4)
@TypeConverters(EpisodeListConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao
}