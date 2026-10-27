package org.example.repository

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.example.model.VideojuegoXML
import org.example.model.VideojuegosWrapper
import java.nio.file.Files
import java.nio.file.Path

class XmlVideojuegoRepository {
    fun leerFichero(ruta: Path): List<VideojuegoXML> {
        var contenedor = VideojuegosWrapper(emptyList())

        if (!Files.isReadable(ruta)) {
            println("Error: No se puede leer el fichero en la ruta: $ruta")
        } else {
            val fichero = ruta.toFile()
            val xmlMapper = XmlMapper().registerKotlinModule()

            contenedor = xmlMapper.readValue(fichero)
            println("--- Información leída con éxito de: $ruta")
        }
        return contenedor.listaVideojuegos
    }

    fun escribirFichero(ruta: Path, videojuegos: List<VideojuegoXML>) {
        try {
            val fichero = ruta.toFile()
            val contenedor = VideojuegosWrapper(videojuegos)
            val xmlMapper = XmlMapper().registerKotlinModule()

            val xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contenedor)
            fichero.writeText(xmlString)

            println("--- Información guardada en XML: $fichero")
        } catch (e: Exception) {
            println("Error al guardar XML: ${e.message}")
        }
    }
}