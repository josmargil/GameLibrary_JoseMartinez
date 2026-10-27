package org.example.repository

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.example.model.Videojuego
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections.emptyList

class CsvVideojuegoRepository : IVideojuegoRepository {

    override fun leerFichero(rutaCSV: Path): List<Videojuego> {
        var videojuegos: List<Videojuego> = emptyList()

        if (!Files.isReadable(rutaCSV)) {
            println("Error: no se puede leer el fichero de la ruta: $rutaCSV")
        } else {
            val reader = csvReader {
                delimiter = ';'
            }

            val filas: List<List<String>> = reader.readAll(rutaCSV.toFile())
            videojuegos = filas.mapNotNull { columnas ->
                if (columnas.size >= 5) {
                    try {
                        val idVideojuego = columnas [0].toInt()
                        val titulo = columnas[1]
                        val genero = columnas[2]
                        val anio = columnas[3].toInt()
                        val nota = columnas[4].toDouble()
                        Videojuego(idVideojuego, titulo, genero, anio, nota)
                    } catch (e: Exception) {
                        println("Fila invalida ignorada: $columnas -> Error ${e.message}")
                        null
                    }
                } else {
                    println("Fila con formato incompleto ignorada: $columnas")
                    null
                }
            }
        }
        println("--- Información leída con éxito de: $rutaCSV")
        return videojuegos
    }

    override fun escribirFichero(rutaCSV: Path, videojuegos: List<Videojuego>) {
        try {
            val fichero: File = rutaCSV.toFile()
            csvWriter {
                delimiter = ';'
            }.writeAll(
                videojuegos.map { videojuego ->
                    listOf(
                        videojuego.idVideojuego.toString(),
                        videojuego.titulo,
                        videojuego.genero,
                        videojuego.anio.toString(),
                        videojuego.nota.toString()
                    )
                },
                fichero
            )
            println("--- Información guardada con éxito en: $fichero")
        } catch (e: Exception) {
            println("Error al escribir el fichero CSV: ${e.message}")
        }
    }


}