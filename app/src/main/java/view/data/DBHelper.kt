package view.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Clase DBHelper que gestiona la creación y actualizacion de la base de datos SQLite
class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NOMBRE, null, DB_VERSION) {
    companion object {
        // Versión de la base de datos y nombre de la base de datos
        val DB_VERSION =1
        val DB_NOMBRE = "RETOS_DB"
    }
    // Método llamado cuando la base de datos es creada por primera vez
    override fun onCreate(db: SQLiteDatabase?) {
        // Ejecuta una consulta SQL para crear la tabla 'reto_tabla'
        db?.execSQL("CREATE TABLE IF NOT EXISTS reto_tabla (\n" +
                "  reto_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  reto_texto TEXT NOT NULL\n" +
                ");")
    }
    // Método llamado cuando se actualiza la versión de la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nada por ahora.
    }
}