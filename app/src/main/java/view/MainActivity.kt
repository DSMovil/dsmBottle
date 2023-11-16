package view

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bottlegame.R
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NOMBRE, null, DB_VERSION) {
    companion object {
        val DB_VERSION =1
        val DB_NOMBRE = "RETOS_DB"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("CREATE TABLE IF NOT EXISTS reto_tabla (\n" +
               "  reto_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
               "  reto_texto TEXT NOT NULL\n" +
               ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       // Nada por ahora.
    }
}

data class Retos(val Reto: String)

class RetosViewModel() : ViewModel () {
    lateinit var db: SQLiteDatabase
    val retos = mutableListOf<Retos>(
        Retos(Reto = "Bailar Salsa"),
        Retos(Reto = "Jugar Tenis"),
    )
    init {
        // val dbHelper = DBHelper(context)
    }

    fun iniciarContext(context: Context) {
        db = DBHelper(context).writableDatabase
    }

    fun seleccionarReto(): Retos {
        return this.retos.random()
    }
}

class MainActivity : AppCompatActivity() {

    fun cargarDatosDB(): MutableList<Retos> {
        return mutableListOf<Retos>(Retos(Reto = "Bailar Salsa"),Retos(Reto = "Comprar Casa"))
    }

    fun seleccionarReto(data: MutableList<Retos>): Retos {
        return data.random()
    }
    private lateinit var retosViewModel: RetosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// Cargar View Model
        retosViewModel = ViewModelProvider(this)[RetosViewModel::class.java]
        retosViewModel.iniciarContext(this)


        Thread.sleep(5000)
        screenSplash.setKeepOnScreenCondition{false}


        //Listeners y asignaciones
        val botonGirar: ImageView = findViewById(R.id.botonGirar)
        val botonMusica: ToggleButton = findViewById(R.id.musica)
        val contador: TextView = findViewById(R.id.cuenta)
        val retosDPantalla : TextView = findViewById(R.id.textoReto)
        val musicaFondo = MediaPlayer.create(this, R.raw.portal_radio_loop)
        musicaFondo.start()

        //Listener boton de girar
        botonGirar.setOnClickListener{
            botonGirar.isEnabled=false
            empezarContador(contador,retosDPantalla, botonGirar) { retosViewModel.seleccionarReto() }
        }
        //Logica botón de volumen para cambiar icono
        botonMusica.setOnClickListener{
            if(botonMusica.isChecked){
                botonMusica.setCompoundDrawablesWithIntrinsicBounds(null, resources.getDrawable(R.drawable.volume_off_fill0_wght400_grad0_opsz24), null, null)
                //Parar musica aquí
                pararMusica(musicaFondo)
            }else{
                botonMusica.setCompoundDrawablesWithIntrinsicBounds(null, resources.getDrawable(R.drawable.volume_up_fill0_wght400_grad0_opsz24), null, null)
                //Continuar musica aqui
                tocarMusica(musicaFondo)
            }
        }
    }



    //Funcion que sirve de contador de 3 segundos para
    //mostrar el texto en pantalla y luego hacer girar la botella
    private fun empezarContador(
        contador: TextView,
        retosDPantalla: TextView,
        botonGirar: ImageView,
        retoLlamada: () -> Retos
    ) {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Muestra el texto que hace la cuenta regresiva
                contador.visibility = View.VISIBLE
                contador.text = "${(millisUntilFinished / 1000)}"
            }

            override fun onFinish() {
                //Hace que el texto en pantalla desaparezca cuando llega a 0 la cuenta
                botonGirar.isEnabled = true
                contador.visibility = View.GONE
                retoContador(retosDPantalla, retoFinal = retoLlamada())
                // Ejecutar codigo cuando el contador termine
                //funcionesNecesarias()
            }
        }.start()
    }

    fun retoContador(retosDPantalla: TextView, retoFinal: Retos) {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                retosDPantalla.visibility = View.VISIBLE
                retosDPantalla.text = retoFinal.Reto
            }

            override fun onFinish() {
                retosDPantalla.visibility = View.GONE
            }
        }.start()
    }

    //Funcion que reproduce la musica
    private fun tocarMusica(musicaFondo: MediaPlayer) {
        if (!musicaFondo.isPlaying) {
            musicaFondo.isLooping = true // Set looping to true for continuous playback
            musicaFondo.start() // Start playing the music
        }
    }
    //Funcion que detiene la musica
    private fun pararMusica(musicaFondo: MediaPlayer) {
        if (musicaFondo.isPlaying) {
            musicaFondo.pause() // Pause the music
        }
    }


}