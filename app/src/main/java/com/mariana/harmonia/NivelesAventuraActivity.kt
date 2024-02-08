package com.mariana.harmonia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.random.Random

class NivelesAventuraActivity : AppCompatActivity() {

    private val numBotones = 200
     private lateinit var llBotonera: LinearLayout
     private var botonCorrecto: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_niveles_aventura)


        llBotonera = findViewById(R.id.llBotonera)
            botonCorrecto = Random.nextInt(numBotones)

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

            val constraintSet = ConstraintSet()

            for (i in 0 until numBotones) {
                val button = Button(this)
                button.layoutParams = lp
                button.text = "Botón " + String.format("%02d", i)
                button.setOnClickListener(buttonClickListener(i))

                // Cambia la forma del botón a redonda
                button.setBackgroundResource(getRandomButtonDrawable())

                // Ajusta la gravedad aleatoriamente
                //setRandomGravity(button)

                llBotonera.addView(button)
            }
    }

    private fun buttonClickListener(index: Int): View.OnClickListener? {
        return View.OnClickListener {
            if (index == botonCorrecto) {
                Toast.makeText(
                    this@NivelesAventuraActivity,
                    "¡ME ENCONTRASTE!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@NivelesAventuraActivity,
                    "Sigue buscando",
                    Toast.LENGTH_SHORT
                ).show()
            }
            botonCorrecto = Random.nextInt(numBotones)
        }
    }


    private fun getRandomButtonDrawable(): Int {
        val buttonDrawables = listOf(
            R.drawable.style_round_button
        )
        return buttonDrawables[Random.nextInt(buttonDrawables.size)]
    }

    fun setRandomGravity(button: Button) {
        val layoutParams = button.layoutParams as ConstraintLayout.LayoutParams

        val gravityOptions = listOf(
            ConstraintLayout.LayoutParams.END,
            ConstraintLayout.LayoutParams.START
        )

        val randomGravity = gravityOptions[Random.nextInt(gravityOptions.size)]

        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID

        when (randomGravity) {
            ConstraintLayout.LayoutParams.END -> {
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            }
            ConstraintLayout.LayoutParams.START -> {
                layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }

        button.layoutParams = layoutParams
    }
}