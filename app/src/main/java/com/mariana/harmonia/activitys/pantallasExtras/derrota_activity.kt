package com.mariana.harmonia.activitys.pantallasExtras

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.mariana.harmonia.activitys.JuegoMusicalActivity
import com.mariana.harmonia.activitys.NivelesAventuraActivity
import com.mariana.harmonia.R
import com.mariana.harmonia.utils.Utils


class derrota_activity : AppCompatActivity() {

    private var nivel: Int = 0
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var derrotaTextView: TextView
    private lateinit var emogiTextView: TextView
    private lateinit var frasesTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        nivel = intent.getIntExtra("numeroNivel", 1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_derrota)
        val emojis = arrayOf("😞", "😔", "😕", "🙁", "☹️", "😟", "😢", "😭")
        val frases = arrayOf(
            "¡Sigue intentando!😊",
            "¡Otra vez será...💪",
            "¡Casi lo logras!😅",
            "¡Sigue practicando!🎶",
            "¡A seguir intentando! ",
            "¡Cerca de ganar!",
            "¡Inténtalo otra vez!",
            "¡Persiste y vencerás!🏆",
            "¡Por poco lo consigues!😮",
            "¡No te rindas!🚀",
            "¡Sigue adelante! 🚶‍♂️",
            "¡No te desanimes...😔",
            "¡Sigue practicando más!🔥",
            "¡Mejor suerte luego! 🌟",
            "¡Casi, casi lo tienes!🤞",
            "¡No muy lejos...",
            "¡Ánimo, casi esta!💖",
            "¡Buen esfuerzo!👏",
            "¡A seguir intentándolo!",
            "¡Casi lo alcanzas!",
            "¡Prueba otra vez...!",
            "¡Sigue adelante!",
            "¡Persiste en ello!",
            "¡Casi alcanzas la meta!",
            "¡Casi lo atrapas",
            "¡Casi lo logras... 😓",
            "¡Continúa intentando...",
            "¡Por poco lo logras...😥",
            "¡Sigue practicando duro! 💪🎵",
            "¡Sigue intentándolo",
            "¡Más suerte la próxima...🍀",
            "¡Continúa esforzándote💪",
            "¡Por poco no lo logras...😟",
            "¡Sigue practicando más!",
            "¡No muy lejos ahora... 🌟",
            "¡Casi lo dominas!🎹",
            "¡Sigue esforzándote más💪🔥",
            "¡Mejor suerte la próxima...🍀",
            "¡Por poco no lo tienes...😕",
            "¡No muy lejos!🚶‍♂️",
            "¡Sigue intentándolo!",
            "¡A seguir practicando...",
            "¡Sigue luchando... ",
            "Un mono juega mejor... ",
            "¡Casi lo consigues! 😄",
            "¡Por poco lo alcanzas!😓",
            "¡Sigue intentándolo!",
            "¡No muy lejos!",
            "¡Sigue adelante! "
        )

        emogiTextView = findViewById(R.id.emogiTextView)
        derrotaTextView = findViewById(R.id.derrotaTextView)
        frasesTextView = findViewById(R.id.fraseTextView)
        Utils.degradadoTexto(this, derrotaTextView.id, R.color.rojo, R.color.negro)
        emogiTextView.text = emojis.random().toString()
        frasesTextView.text = frases.random().toString()
        mediaPlayer = MediaPlayer.create(this, R.raw.lose_sound)
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.start()
        val imageView: ImageView = findViewById(R.id.fondoImageView)
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.animacion_pantallas_fin)
        imageView.startAnimation(anim)

    }

    fun irRepetir(view: View) {
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_cuatro)
        mediaPlayer.start()
        val intent = Intent(this, JuegoMusicalActivity::class.java)
        intent.putExtra("numeroNivel", nivel)
        finish()
        startActivity(intent)
    }

    fun irMenu(view: View) {
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_cuatro)
        mediaPlayer.start()
        val intent = Intent(this, NivelesAventuraActivity::class.java)
        finish()
        startActivity(intent)
    }
}