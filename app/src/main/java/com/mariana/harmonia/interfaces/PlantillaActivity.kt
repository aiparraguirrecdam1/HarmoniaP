package com.mariana.harmonia.interfaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.mariana.harmonia.InicioSesionActivity
import com.mariana.harmonia.R
import com.mariana.harmonia.Utilidades

interface PlantillaActivity {
     fun Salir(activity: Activity) {

         Utilidades.salirAplicacion(activity)
    }

    fun IniciarSesion(activity: Activity) {
        val intent = Intent(activity, InicioSesionActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

}