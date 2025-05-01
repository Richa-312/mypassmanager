import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.passwordmanagerapp.utils.EncryptionUtil
import kotlinx.coroutines.launch


class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "pass_db").build()
    private val dao = db.passwordDao()
    val passwords = dao.getAll().asLiveData()

    fun addPassword(site: String, username: String, password: String) {
        viewModelScope.launch {
            val encrypted = EncryptionUtil.encrypt(getApplication(), password)
            dao.insert(PasswordEntry(site = site, username = username, encryptedPassword = encrypted))
        }
    }

    fun deletePassword(entry: PasswordEntry) {
        viewModelScope.launch {
            dao.delete(entry)
        }
    }
}
