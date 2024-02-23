package com.mariana.harmonia

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

import kotlin.random.Random

class NivelesAventuraActivity : AppCompatActivity() {

    private val numCantNiveles = 50
    private lateinit var llBotonera: LinearLayout
    private var botonCorrecto: Int = 0
    private var idNivelNoCompletado: Int = 0
    private lateinit var menuSuperior: LinearLayout
    private lateinit var textViewNivel: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_niveles_aventura)
        idNivelNoCompletado = obtenerIdPrimerNivelNoCompletado()!!
        llBotonera = findViewById(R.id.llBotonera)
        botonCorrecto = Random.nextInt(numCantNiveles)
        menuSuperior = findViewById(R.id.llTopBar)
        textViewNivel = findViewById(R.id.textViewNivel)
        scrollView = findViewById(R.id.scrollView)

        val lp = LinearLayout.LayoutParams(
            resources.getDimensionPixelSize(R.dimen.button_width),
            resources.getDimensionPixelSize(R.dimen.button_height)
        )

        // Margen entre botones y margen superior
        lp.setMargins(
            0,
            resources.getDimensionPixelSize(R.dimen.button_margin_top),
            0,
            resources.getDimensionPixelSize(R.dimen.button_margin)
        )

        for (i in 1 until numCantNiveles) {

            val button: View = if (i > idNivelNoCompletado) {
                createLockedButton()
            } else {
                createUnlockedButton(i)
            }

            // Crear un nuevo conjunto de parámetros de diseño para cada botón
            val lp = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.button_width),
                resources.getDimensionPixelSize(R.dimen.button_height)
            )

            // Configurar el margen aleatorio
            val randomMargin =
                Random.nextInt(0, resources.getDimensionPixelSize(R.dimen.max_margin))
            lp.setMargins(
                if (i % 2 == 0) randomMargin else 0,
                resources.getDimensionPixelSize(R.dimen.button_margin_top),
                if (i % 2 != 0) randomMargin else 0,
                lp.bottomMargin
            )

            if(i > idNivelNoCompletado){
                button.isEnabled = false
                button.setBackgroundResource(getRandomUnlockedButtonDrawable())
            }
            else {

                button.setBackgroundResource(getRandomButtonDrawable())
                button.setOnClickListener {
                    val numeroNivel = button.id
                    val intent = Intent(this, JuegoMusicalActivity::class.java)
                    intent.putExtra("numeroNivel", numeroNivel)
                    startActivity(intent)
                }
            }

            // Configurar la gravedad
            lp.gravity = Gravity.CENTER

            button.layoutParams = lp

            //button.setOnClickListener(buttonClickListener(i))
            llBotonera.addView(button)
        }


        // Agregar un listener al ScrollView para detectar el desplazamiento
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val ratio = scrollY.toFloat() / maxScroll.toFloat()

            // Calcula el color interpolado entre blanco y morado
            val color = interpolateColor(Color.WHITE, Color.MAGENTA, ratio)

            // Establece el color de fondo del ScrollView
            scrollView.setBackgroundColor(color)
        }

        //fin oncreate
        colocarTextViewNivel()
    }

    private fun createLockedButton(): Button {
        val lockedButton = Button(this)
        lockedButton.textSize = 20f
        lockedButton.gravity = Gravity.CENTER
        lockedButton.isEnabled = false

        lockedButton.setBackgroundResource(R.drawable.style_round_button_blue)
        lockedButton.setTextColor(ContextCompat.getColor(this, android.R.color.white))

        val strokeWidth = resources.getDimensionPixelSize(R.dimen.stroke_width)
        val strokeColor = ContextCompat.getColor(this, android.R.color.white)
        val shapeDrawable = ContextCompat.getDrawable(this, R.drawable.style_round_button_blue)!!.mutate()
        shapeDrawable.setTint(strokeColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (lockedButton.background as GradientDrawable).setStroke(strokeWidth, strokeColor)
        }

        val drawableLock = ContextCompat.getDrawable(this, R.drawable.lock) // Reemplaza R.drawable.lock con tu propio recurso de icono de candado

        // Ajusta la posición del icono de candado en el botón para centrarlo horizontalmente
        drawableLock?.setBounds(drawableLock.intrinsicWidth, 0, 0, drawableLock.intrinsicHeight)
        lockedButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLock, null)

        // Establecer el fondo y el borde del botón bloqueado
        lockedButton.background = shapeDrawable

        return lockedButton
    }

    private fun createUnlockedButton(levelNumber: Int): Button {
        val button = Button(this)
        button.textSize = 20f
        button.gravity = Gravity.CENTER

        button.id = levelNumber
        button.text = String.format("%2d", levelNumber)
        button.setBackgroundResource(getRandomButtonDrawable())
        button.setOnClickListener {
            val numeroNivel = button.id
            val intent = Intent(this, JuegoMusicalActivity::class.java)
            intent.putExtra("numeroNivel", numeroNivel)
            startActivity(intent)
        }
        val size = resources.getDimensionPixelSize(R.dimen.button_size)
        val params = LinearLayout.LayoutParams(size, size)
        button.layoutParams = params

        return button
    }


    private fun getRandomButtonDrawable(): Int {
        val buttonDrawables = listOf(
            R.drawable.style_round_button
        )
        return buttonDrawables[Random.nextInt(buttonDrawables.size)]
    }
    private fun getRandomUnlockedButtonDrawable(): Int {
        val buttonDrawables = listOf(
            R.drawable.style_round_button_blue
        )
        return buttonDrawables[Random.nextInt(buttonDrawables.size)]
    }

    private fun obtenerIdPrimerNivelNoCompletado(): Int? {
        val nivelesJson = obtenerNivelesJSON()
        val nivelesArray = nivelesJson?.getJSONArray("niveles")

        if (nivelesArray != null) {
            for (i in 0 until nivelesArray.length()) {
                val nivel = nivelesArray.getJSONObject(i)
                val completado = nivel.getBoolean("completado")

                if (!completado) {
                    return nivel.getInt("id")
                }
            }
        }
        return null
    }
    private fun obtenerNivelesJSON(): JSONObject? {
        var nivelesJson: JSONObject? = null
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.info_niveles)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            nivelesJson = JSONObject(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return nivelesJson
    }


    private fun colocarTextViewNivel(){
        var nivel  = idNivelNoCompletado.toString()
        textViewNivel.text = "Nv. $nivel-$numCantNiveles"
    }
    // Función para interpolar colores
    private fun interpolateColor(colorStart: Int, colorEnd: Int, ratio: Float): Int {
        val colors = intArrayOf(
            Color.WHITE,
            Color.parseColor("#FFD3E6"), // Rosa claro y suave
            Color.parseColor("#FFB6C1"), // Rosa medio y suave
            Color.parseColor("#FF69B4"), // Rosa oscuro y suave
            Color.parseColor("#E7D8F5"), // Morado claro y suave
            Color.parseColor("#D8BFD8"), // Morado medio y suave
            Color.parseColor("#8A2BE2"), // Morado oscuro y suave
            Color.parseColor("#87CEEB"), // Azul cielo suave
            Color.parseColor("#ADD8E6"), // Azul claro y suave
            Color.parseColor("#4682B4"), // Azul acero
            Color.parseColor("#4169E1"), // Azul real
        )

        val startIndex = (ratio * (colors.size - 1)).toInt()
        val endIndex = minOf(startIndex + 1, colors.size - 1)
        val startColor = colors[startIndex]
        val endColor = colors[endIndex]

        val startRatio = 1 - (ratio * (colors.size - 1) - startIndex)
        val endRatio = 1 - startRatio

        val r = (Color.red(startColor) * startRatio + Color.red(endColor) * endRatio).toInt()
        val g = (Color.green(startColor) * startRatio + Color.green(endColor) * endRatio).toInt()
        val b = (Color.blue(startColor) * startRatio + Color.blue(endColor) * endRatio).toInt()

        return Color.rgb(r, g, b)
    }
}