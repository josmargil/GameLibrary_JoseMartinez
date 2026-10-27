package org.example.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.model.VideojuegoJSON
import java.nio.file.Files
import java.nio.file.Path

class JsonVideojuegoRepository {
    fun leerFichero(ruta: Path): List<VideojuegoJSON> {
        var videojuegos: List<VideojuegoJSON> = emptyList()

        if (!Files.isReadable(ruta)) {
            println("Error: No se puede leer el fichero en la ruta: $ruta")
        } else {


            val jsonString = Files.readString(ruta)


            videojuegos = Json.decodeFromString<List<VideojuegoJSON>>(jsonString)
            println("--- Información leída con éxito de: $ruta")
        }
        return videojuegos
    }

    fun escribirFichero(ruta: Path, videojuegos: List<VideojuegoJSON>) {
        try {
            val jsonConfigurador = Json { prettyPrint = true }
            val jsonString = jsonConfigurador.encodeToString(videojuegos)

            Files.writeString(ruta, jsonString)
            println("--- Información guardada en: $ruta")
        } catch (e: Exception) {
            println("Error al guardar JSON: ${e.message}")
        }
    }


}