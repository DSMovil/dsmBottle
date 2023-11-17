package view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import view.data.DBHelper

class RetosViewModel() : ViewModel() {
    lateinit var db: SQLiteDatabase
    val retos = mutableListOf<Retos>(
    )
    init {
        // val dbHelper = DBHelper(context)
    }

    @SuppressLint("Range")
    fun iniciarContext(context: Context) {
        db = DBHelper(context).writableDatabase
        db.query("reto_tabla", arrayOf("reto_texto","reto_id"), null,null,null,null,null).apply {
            this.use {
                while (it.moveToNext()) {
                    val columnReto = it.getColumnIndex("reto_texto")
                    val reto_texto = it.getString(columnReto)
                    val columnId = it.getColumnIndex("reto_id")
                    val reto_id = it.getLong(columnId)
                    retos.add(Retos(reto_texto, reto_id))
                }
            }
        }
    }

    fun agregarReto(input: String) {
        db.insert("reto_tabla",null, ContentValues().apply { put("reto_texto", input) }).apply {
            retos.add(Retos(input, this))
        }
    }

    fun eliminarReto(idReto: Number) {
        if (retos.any { it.id == idReto }) {
            db.execSQL("DELETE FROM reto_tabla WHERE reto_id = " + idReto)
            retos.removeIf { it.id == idReto }
        }
    }

    fun mostrarRetos(): MutableList<Retos> {
        return retos
    }

    fun cargaPrueba() {
        //   retos.add(Retos("Muy tarde"))
    }

    fun seleccionarReto(): Retos {
        return this.retos.random()
    }
}

data class Retos(val Reto: String, val id: Number)