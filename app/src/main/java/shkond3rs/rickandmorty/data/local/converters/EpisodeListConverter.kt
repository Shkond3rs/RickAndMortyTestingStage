package shkond3rs.rickandmorty.data.local.converters

import androidx.room.TypeConverter

class EpisodeListConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")
    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isBlank()) emptyList() else data.split(",").mapNotNull { it.toIntOrNull() }
}