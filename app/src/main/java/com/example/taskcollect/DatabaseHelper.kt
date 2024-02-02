package com.example.taskcollect

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Informacion de la base de datos
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskCollect.db"

        // Tabla Historico de notas
        private const val TABLE_HIST_NOTAS = "HIST_NOTAS" // Nombre de la tabla jsjsjsj
        private const val COLUMN_HIST_NT_ID  = "HIST_NT_ID" // Id de la tabla
        private const val COLUMN_HIST_NT_TITULO  = "HIST_NT_TITULO" // Titulo de la nota
        private const val COLUMN_HIST_NT_DESCRIPCION  = "HIST_NT_DESCRIPCION" // Contenido de la nota
        private const val COLUMN_HIST_NT_FECHA  = "HIST_NT_FECHA" // Fecha de la nota guardada en el calendario
        private const val COLUMN_HIST_NT_HORA  = "HIST_NT_HORA" // Hora de la nota guardada en el calendario
        private const val COLUMN_HIST_NT_ORDEN  = "HIST_NT_ORDEN" // Orden en que se mostraran las notras en la pantalla
        private const val COLUMN_HIST_NT_EVENTO  = "HIST_NT_EVENTO" // Tipo de evento (esto se define dentro de un catalogo)
        private const val COLUMN_HIST_NT_ICONO  = "HIST_NT_ICONO" // Icono del evento (esto se define dentro del catalogo de arriba)
        private const val COLUMN_HIST_NT_NOTIFICACION  = "HIST_NT_NOTIFICACION" // Tempo antes que se mande la notificacion
        private const val COLUMN_HIST_NT_COLOR  = "HIST_NT_COLOR" // Color del cual se guarda la nota
        private const val COLUMN_HIST_NT_RECURRENCIA  = "HIST_NT_RECURRENCIA" // Tipo de recurrencia, diario, semana, mensual, etc
        private const val COLUMN_HIST_NT_INTERVALO  = "HIST_NT_INTERVALO" // Cada cuando se va a repetir
        private const val COLUMN_HIST_NT_FNINTERVALO  = "HIST_NT_FNINTERVALO" // Si se desea terminar en algun momento la nota


        // Catalogo de Eventos
        private const val TABLE_CAT_EVENTOS = "CAT_EVENTOS" // Nombre de la tabla de catálogo de eventos
        private const val COLUMN_CAT_EV_ID = "CAT_EV_ID" // ID del evento
        private const val COLUMN_CAT_EV_TIPO = "CAT_EV_TIPO" // Tipo de evento
        private const val COLUMN_CAT_EV_ICONO = "CAT_EV_ICONO" // Icono del evento
        private const val COLUMN_CAT_EV_VISIBILIDAD = "CAT_EV_VISIBILIDAD" // Si el usuario podra usar este evento o solo el sistema

        // Tabla NOTIFICACIONES
        private const val TABLE_CAT_NOTIFICACIONES = "CAT_NOTIFICACIONES" // Guardar tabla de notificaciones
        private const val COLUMN_CAT_NOT_ID = "CAT_NOT_ID" // Guardar id
        private const val COLUMN_CAT_NOT_TIEMPO = "CAT_NOT_TIEMPO" // Guardar tiempo en min
        private const val COLUMN_CAT_NOT_DESCRIPCION = "CAT_NOT_DESCRIPCION" // descripcion del tiempo total
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla HIST_NOTAS
        val CREATE_TABLE_HIST_NOTAS = """
        CREATE TABLE $TABLE_HIST_NOTAS (
            $COLUMN_HIST_NT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_HIST_NT_TITULO TEXT,
            $COLUMN_HIST_NT_DESCRIPCION TEXT,
            $COLUMN_HIST_NT_FECHA TEXT,
            $COLUMN_HIST_NT_HORA TEXT,
            $COLUMN_HIST_NT_ORDEN INTEGER,
            $COLUMN_HIST_NT_EVENTO TEXT,
            $COLUMN_HIST_NT_ICONO TEXT,
            $COLUMN_HIST_NT_NOTIFICACION INTEGER,
            $COLUMN_HIST_NT_COLOR TEXT,
            $COLUMN_HIST_NT_RECURRENCIA TEXT,
            $COLUMN_HIST_NT_INTERVALO INTEGER,
            $COLUMN_HIST_NT_FNINTERVALO TEXT
        )
    """.trimIndent()
        db.execSQL(CREATE_TABLE_HIST_NOTAS)

        // Crear tabla CAT_EVENTOS
        val CREATE_TABLE_CAT_EVENTOS = """
        CREATE TABLE $TABLE_CAT_EVENTOS (
            $COLUMN_CAT_EV_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_CAT_EV_TIPO TEXT,
            $COLUMN_CAT_EV_ICONO TEXT,
            $COLUMN_CAT_EV_VISIBILIDAD INTEGER
        )
    """.trimIndent()
        db.execSQL(CREATE_TABLE_CAT_EVENTOS)

        // Crear tabla CAT_NOTIFICACIONES
        val CREATE_TABLE_CAT_NOTIFICACIONES = """
        CREATE TABLE $TABLE_CAT_NOTIFICACIONES (
            $COLUMN_CAT_NOT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_CAT_NOT_TIEMPO INTEGER,
            $COLUMN_CAT_NOT_DESCRIPCION TEXT
        )
    """.trimIndent()
        db.execSQL(CREATE_TABLE_CAT_NOTIFICACIONES)
    }

    // Método para actualizar la base de datos en caso de una nueva versión, esto es lo mas basico del  mundo we
    // No me mates Erick
    // Sexo
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar las tablas existentes
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HIST_NOTAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CAT_EVENTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CAT_NOTIFICACIONES")

        // Crear las tablas de nuevo
        onCreate(db)
    }

    // Metodo para agregar notas
    fun addNota(nota: Nota): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HIST_NT_TITULO, nota.titulo)
            put(COLUMN_HIST_NT_DESCRIPCION, nota.descripcion)
            put(COLUMN_HIST_NT_FECHA, nota.fecha)
            put(COLUMN_HIST_NT_HORA, nota.hora)
            put(COLUMN_HIST_NT_ORDEN, nota.orden)
            put(COLUMN_HIST_NT_EVENTO, nota.evento)
            put(COLUMN_HIST_NT_ICONO, nota.icono)
            put(COLUMN_HIST_NT_NOTIFICACION, nota.notificacion)
            put(COLUMN_HIST_NT_COLOR, nota.color)
            put(COLUMN_HIST_NT_RECURRENCIA, nota.recurrencia)
            put(COLUMN_HIST_NT_INTERVALO, nota.intervalo)
            put(COLUMN_HIST_NT_FNINTERVALO, nota.finIntervalo)
        }

        // Insertar la nota en la base de datos y retornar el ID de la fila nueva
        val id = db.insert(TABLE_HIST_NOTAS, null, values)
        //db.close() // Cerrar la base de datos para liberar recursos
        return id // Retorna el ID de la fila insertada, o -1 si ocurrió un error
    }

    fun getNota(id: Int): Nota? {
        val db = this.readableDatabase
        var nota: Nota? = null
        val selectQuery = "SELECT * FROM $TABLE_HIST_NOTAS WHERE $COLUMN_HIST_NT_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            nota = Nota(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ID)),
                titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_TITULO)),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_DESCRIPCION)),
                fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_FECHA)),
                hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_HORA)),
                orden = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ORDEN)),
                evento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_EVENTO)),
                icono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ICONO)),
                notificacion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_NOTIFICACION)),
                color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_COLOR)),
                recurrencia = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_RECURRENCIA)),
                intervalo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_INTERVALO)),
                finIntervalo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_FNINTERVALO))
            )
        }
        cursor.close()
        //db.close()
        return nota
    }

    // Metodo para obtener las notas
    fun getAllNotas(): MutableList<Nota> {
        val listaDeNotas = mutableListOf<Nota>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_HIST_NOTAS ORDER BY $COLUMN_HIST_NT_ORDEN ASC"

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val nota = Nota(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ID)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_TITULO)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_DESCRIPCION)),
                    fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_FECHA)),
                    hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_HORA)),
                    orden = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ORDEN)),
                    evento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_EVENTO)),
                    icono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_ICONO)),
                    notificacion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_NOTIFICACION)),
                    color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_COLOR)),
                    recurrencia = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_RECURRENCIA)),
                    intervalo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_INTERVALO)),
                    finIntervalo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HIST_NT_FNINTERVALO))
                )
                listaDeNotas.add(nota)
            } while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return listaDeNotas
    }

    // Metodo para Actualizar las notas
    fun updateNota(nota: Nota) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HIST_NT_TITULO, nota.titulo)
            put(COLUMN_HIST_NT_DESCRIPCION, nota.descripcion)
            put(COLUMN_HIST_NT_FECHA, nota.fecha)
            put(COLUMN_HIST_NT_HORA, nota.hora)
            put(COLUMN_HIST_NT_ORDEN, nota.orden)
            put(COLUMN_HIST_NT_EVENTO, nota.evento)
            put(COLUMN_HIST_NT_ICONO, nota.icono)
            put(COLUMN_HIST_NT_NOTIFICACION, nota.notificacion)
            put(COLUMN_HIST_NT_COLOR, nota.color)
            put(COLUMN_HIST_NT_RECURRENCIA, nota.recurrencia)
            put(COLUMN_HIST_NT_INTERVALO, nota.intervalo)
            put(COLUMN_HIST_NT_FNINTERVALO, nota.finIntervalo)
        }

        // Aquí se asume que nota.id no es nulo
        val result = db.update(TABLE_HIST_NOTAS, values, "$COLUMN_HIST_NT_ID = ?", arrayOf(nota.id.toString()))
        //db.close()
    }

    // Metodo para eliminar las notas
    fun deleteNota(id: Int) {
        val db = this.writableDatabase
        // Ejecutar la operación de eliminación
        db.delete(TABLE_HIST_NOTAS, "$COLUMN_HIST_NT_ID = ?", arrayOf(id.toString()))
        //db.close()
    }
}



//⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠉⢁⣀⣀⣀⡈⠉⠛⢿⡿⠿⢿⣿⣿⣿
//⣿⣿⣿⣿⣿⣿⠏⢀⣴⣾⣿⣿⣿⣿⣿⡟⠃⢀⣀⣤⣤⣄⠉⢿⣿
//⣿⣿⣿⣿⣿⡏⠀⣾⣿⣿⣿⣿⣿⣿⠏⠀⣴⣿⣿⣿⣯⣻⣧⠀⢻
//⣿⣿⣿⣿⣿⠁⢸⣿⣿⣿⣿⣿⣿⣿⠀⠸⣿⣿⣿⣿⣿⣿⣿⡇⠈
//⣿⣿⣿⣿⡏⠀⣼⣿⣿⣿⣿⣿⣿⣿⣧⠀⠹⢿⣿⣿⣿⡿⠟⠀⣼
//⣿⣿⣿⡿⠇⠀⠛⠿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣀⡈⠉⠀⠀⣴⣿⣿
//⣿⡿⠁⣀⢠⢤⣤⠀⠀⠉⢀⠀⠀⠈⠉⠻⢿⣿⣿⣿⡇⠀⣿⣿⣿
//⡟⠀⣴⣽⣷⣷⠆⠀⣴⣾⣿⣔⡳⢦⡄⣄⣠⣿⣿⣿⡇⠀⣿⣿⣿
//⠀⢰⣿⣿⣿⠇⠀⣼⣿⣿⣿⣿⣿⣷⣶⣿⣿⣿⣿⣿⣿⠀⢻⣿⣿
//⠀⠸⣾⣿⣿⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿
//⣧⠀⠻⢿⣿⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿
//⣿⣷⣤⣀⣈⠀⠀⠙⢿⣿⣿⣿⣿⣿⣿⠟⠙⣿⣿⣿⡏⠀⣼⣿⣿
//⣿⣿⣿⣿⣿⡇⠀⣄⠀⠙⠛⠿⠿⠛⠁⢀⣼⣿⣿⣿⡇⠀⣿⣿⣿
//⣿⣿⣿⣿⣿⣷⡀⠘⠿⠶⠀⢀⣤⣤⡀⠙⢿⣿⣿⡿⠁⢰⣿⣿⣿
//⢻⣿⣿⣿⣿⣿⣿⣦⣤⣤⣴⣿⣿⣿⣷⣄⣀⠈⠁⣀⣠⣿⣿⣿⣿
//⣹⣿⣿⣿⡿⢋⣩⣬⣩⣿⠃⣿⣿⣿⣿⢸⣿⡿⢋⣡⣬⣩⣿⣿⣿
//⡗⣿⣿⣿⣧⣈⣛⠛⠻⣿⠀⣿⣿⣿⡿⢸⣿⣧⣈⣛⠛⠻⣿⣿⣿
//⣿⣿⣿⣿⠹⣿⣿⡿⠂⣿⣇⠸⣿⣿⠃⣼⣿⠻⣿⣿⡿⠀⣿⣿⣿
//⣿⣿⣿⣿⣶⣤⣤⣴⣾⣿⣿⣶⣤⣤⣾⣿⣿⣶⣤⣤⣴⣾⣿⣿⣿