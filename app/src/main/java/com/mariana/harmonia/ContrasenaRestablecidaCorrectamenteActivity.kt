package com.mariana.harmonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ContrasenaRestablecidaCorrectamenteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasena_restablecida_correctamente)
    }

    fun volverAlInicio(view: View){
        val intent = Intent(this, InicioSesionActivity::class.java)
        startActivity(intent)
    }
}