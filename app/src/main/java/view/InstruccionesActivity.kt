package view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ImageButton

import com.example.bottlegame.R
class InstruccionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucciones)
        val backButton: ImageButton= findViewById(R.id.backButton)
        // Configurar el clic del botón de flecha atrás
        backButton.setOnClickListener {
            finish() // Cierra la actividad actual y regresa al home principal
        }
    }
}