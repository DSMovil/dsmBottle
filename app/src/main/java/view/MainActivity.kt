package view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bottlegame.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(5000)
        screenSplash.setKeepOnScreenCondition{false}

        //Listeners y asignaciones
        val botonGirar: ImageView = findViewById(R.id.botonGirar)
        val botonMusica: ToggleButton = findViewById(R.id.musica)
        val contador: TextView = findViewById(R.id.cuenta)
        val musicaFondo = MediaPlayer.create(this, R.raw.portal_radio_loop)
        musicaFondo.start()

        botonGirar.setOnClickListener{
            botonGirar.isEnabled=false
            empezarContador(contador, botonGirar)
        }
        //Logica botón de volumen
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

    private fun empezarContador(contador: TextView, botonGirar: ImageView) {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Display the countdown in seconds in the TextView
                contador.visibility = View.VISIBLE
                contador.text = "${(millisUntilFinished / 1000)}"
            }

            override fun onFinish() {
                contador.visibility = View.GONE
                botonGirar.isEnabled = true
                // Execute your code here when the countdown ends
                //funcionesNecesarias()
            }
        }.start()
    }
    private fun tocarMusica(musicaFondo: MediaPlayer) {
        if (!musicaFondo.isPlaying) {
            musicaFondo.isLooping = true // Set looping to true for continuous playback
            musicaFondo.start() // Start playing the music
        }
    }
    private fun pararMusica(musicaFondo: MediaPlayer) {
        if (musicaFondo.isPlaying) {
            musicaFondo.pause() // Pause the music
        }
    }

}