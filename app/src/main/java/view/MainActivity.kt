
package view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bottlegame.R
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.net.Uri
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var retosViewModel: RetosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)

        retosViewModel = ViewModelProvider(this)[RetosViewModel::class.java]
        retosViewModel.iniciarContext(this)


        setContentView(R.layout.activity_main)

        Thread.sleep(5000)
        screenSplash.setKeepOnScreenCondition{false}

        //Listeners y asignaciones
        val botonGirar: ImageView = findViewById(R.id.botonGirar)
        val botonMusica: ToggleButton = findViewById(R.id.musica)
        val contador: TextView = findViewById(R.id.cuenta)
        val botonInstrucciones: ImageButton = findViewById(R.id.instrucciones)
        val botonAgregarreto: ImageButton = findViewById(R.id.retos)
        val musicaFondo = MediaPlayer.create(this, R.raw.portal_radio_loop)
        val botonPuntuar: ImageButton = findViewById(R.id.puntuar)
        val botonCompartir: ImageButton = findViewById(R.id.compartir)
        musicaFondo.start()

        // Listener para el botón de puntuar
        botonPuntuar.setOnClickListener {
            abrirPaginaPlayStore()
        }

        // Listener para el botón de compartir
        botonCompartir.setOnClickListener {
            compartirTexto()
        }

        //Listener boton de girar
        botonGirar.setOnClickListener{
            botonGirar.isEnabled=false
            empezarContador(contador, botonGirar)
        }

        botonInstrucciones.setOnClickListener {
            // Crear un Intent para abrir la actividad de instrucciones
            val intent = Intent(this@MainActivity, InstruccionesActivity::class.java)

            // Iniciar la actividad de instrucciones
            startActivity(intent)
        }

        botonAgregarreto.setOnClickListener{
            mostrarDialogoAgregarReto()
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
    private fun empezarContador(contador: TextView, botonGirar: ImageView) {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Muestra el texto que hace la cuenta regresiva
                contador.visibility = View.VISIBLE
                contador.text = "${(millisUntilFinished / 1000)}"
            }

            override fun onFinish() {
                //Hace que el texto en pantalla desaparezca cuando llega a 0 la cuenta
                contador.visibility = View.GONE
                //Vuelve a activar el boton de girar
                botonGirar.isEnabled = true
                // Ejecutar codigo cuando el contador termine
                //funcionesNecesarias()
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

    // Función para abrir la página de Play Store para calificar el juego
    private fun abrirPaginaPlayStore() {
        val packageName = "com.nequi.MobileApp"
        val marketUrl = "market://details?id=$packageName"
        val webUrl = "https://play.google.com/store/apps/details?id=$packageName"

        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)))
        } catch (e: android.content.ActivityNotFoundException) {
            // Si la aplicación de Play Store no está instalada, abrir en el navegador web
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)))
        }
    }


    // Función para compartir un texto predeterminado por otras aplicaciones
    private fun compartirTexto() {
        val textoCompartir = "App pico botella - Solo los valientes lo juegan !!\nDescarga la app: https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, textoCompartir)

        try {
            startActivity(Intent.createChooser(intent, "Compartir usando"))
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir el selector de aplicaciones.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoAgregarReto() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_agregar_reto, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        val editTextReto = dialogView.findViewById<EditText>(R.id.editTextReto)
        val botonCancelar = dialogView.findViewById<Button>(R.id.botonCancelar)
        val botonGuardar = dialogView.findViewById<Button>(R.id.botonGuardar)

        // Configurar la lógica de botones y cuadro de texto aquí

        botonCancelar.setOnClickListener {
            dialog.dismiss()
        }

        botonGuardar.setOnClickListener {
            // Guardar el reto en la base de datos local (SQLite)
            // Lógica adicional de guardado
            dialog.dismiss()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Habilitar o deshabilitar el botón de guardar según si hay texto o no
                botonGuardar.isEnabled = s?.isNotEmpty() == true
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        editTextReto.addTextChangedListener(textWatcher)



        // Mostrar el cuadro de diálogo
        dialog.show()
    }

}


