package view.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NOMBRE, null, DB_VERSION) {
    companion object {
        val DB_VERSION =1
        val DB_NOMBRE = "RETOS_DB"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS reto_tabla (\n" +
                "  reto_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  reto_texto TEXT NOT NULL\n" +
                ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nada por ahora.
    }
}