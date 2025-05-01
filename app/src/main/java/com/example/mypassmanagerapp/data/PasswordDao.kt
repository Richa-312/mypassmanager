import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(entry: PasswordEntry)

    @Query("SELECT * FROM PasswordEntry")
    fun getAll(): Flow<List<PasswordEntry>>

    @Delete
    suspend fun delete(entry: PasswordEntry)
}
