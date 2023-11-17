package view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import view.data.DBHelper

//ViewModel para la logica de los retos
class RetosViewModel() : ViewModel() {
    lateinit var db: SQLiteDatabase
    //Lista Mutable que almacenara objeto retos
    val retos = mutableListOf<Retos>(
    )
    init {
        // val dbHelper = DBHelper(context)
    }
    //Función para inicializar el contexto y cargar retos desde la base de datos
    @SuppressLint("Range")
    fun iniciarContext(context: Context) {
        // Obtiene una instancia de la base de datos
        db = DBHelper(context).writableDatabase
        // Consulta la tabla 'reto_tabla' y carga los resultados en la lista retos
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
                                                                //----------Metodos-------------


// Método para agregar un nuevo reto a la base de datos y a la lista retos
    fun agregarReto(input: String) {
        db.insert("reto_tabla",null, ContentValues().apply { put("reto_texto", input) }).apply {
            retos.add(Retos(input, this))
        }
    }

    // Método para eliminar un reto por su ID de la base de datos y de la lista retos
    fun eliminarReto(idReto: Number) {
        if (retos.any { it.id == idReto }) {
            db.execSQL("DELETE FROM reto_tabla WHERE reto_id = " + idReto)
            retos.removeIf { it.id == idReto }
        }
    }
    // Método para obtener la lista actual de retos
    fun mostrarRetos(): MutableList<Retos> {
        return retos
    }
    // Método de prueba para cargar un reto ficticio
    fun cargaPrueba() {
        //   retos.add(Retos("Muy tarde"))
    }
    // Método para seleccionar un reto aleatorio de la lista
    fun seleccionarReto(): Retos {
        return this.retos.random()
    }
}
// Clase de datos que representa un "Reto" con un texto y un ID
data class Retos(val Reto: String, val id: Number)