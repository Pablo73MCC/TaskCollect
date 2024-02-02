package com.example.taskcollect

data class Nota(
    val id: Int? = null, // El ID puede ser nulo si la nota es nueva y aún no ha sido insertada en la base de datos
    val titulo: String,
    val descripcion: String,
    val fecha: String? = null, // Asumiendo que almacenarás la fecha como texto, por ejemplo, "2024-02-01"
    val hora: String? = null, // Asumiendo que almacenarás la hora como texto, por ejemplo, "14:30"
    val orden: Int? = null,
    val evento: String? = null, // Este podría ser un ID que referencia a una entrada en una tabla de eventos
    val icono: String? = null, // Este podría ser un recurso o un identificador de un icono
    val notificacion: Int? = null, // Asumiendo que es la cantidad de minutos para la notificación
    val color: String, // Asumiendo que almacenarás el color como texto, por ejemplo, "#FFFFFF"
    val recurrencia: String? = null, // Puede ser "diario", "semanal", etc.
    val intervalo: Int? = null, // Cantidad de tiempo para la recurrencia
    val finIntervalo: String? = null // Fecha de finalización de la recurrencia, si aplica
)