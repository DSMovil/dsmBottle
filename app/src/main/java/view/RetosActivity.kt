package view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.example.bottlegame.R


// Actividad que muestra y gestiona los "Retos"
class RetosActivity : AppCompatActivity() {
    // Instancia del ViewModel asociado a esta actividad
    private lateinit var retosViewModel: RetosViewModel

    // Método llamado al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa el ViewModel utilizando el ViewModelProvider
        retosViewModel = ViewModelProvider(this)[RetosViewModel::class.java]
        // Inicia el contexto y carga los retos desde la base de datos
        retosViewModel.iniciarContext(this)
        // Establece el diseño de la actividad desde el archivo XML 'dialog_agregar_reto'
        setContentView(R.layout.dialog_agregar_reto)

        }
    }


