package shkond3rs.rickandmorty.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import shkond3rs.rickandmorty.data.local.dao.CharacterDao
import shkond3rs.rickandmorty.data.local.dao.LocationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDataBase =
        Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "db"
        ).fallbackToDestructiveMigration(true).build()

    @Provides
    fun provideCharacterDao(db: AppDataBase): CharacterDao = db.characterDao()

    @Provides
    fun provideLocationDao(db: AppDataBase): LocationDao = db.locationDao()

}