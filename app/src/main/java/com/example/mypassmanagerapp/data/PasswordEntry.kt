import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val site: String,
    val username: String,
    val encryptedPassword: String
)
