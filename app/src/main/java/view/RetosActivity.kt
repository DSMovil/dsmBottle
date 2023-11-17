package view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.example.bottlegame.R


class RetosActivity : AppCompatActivity() {
    private lateinit var retosViewModel: RetosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retosViewModel = ViewModelProvider(this)[RetosViewModel::class.java]
        retosViewModel.iniciarContext(this)
        setContentView(R.layout.dialog_agregar_reto)

        }
    }


