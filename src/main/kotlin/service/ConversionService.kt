package org.example.service

import org.example.model.Videojuego
import org.example.model.VideojuegoJSON
import org.example.model.VideojuegoXML
import org.example.repository.CsvVideojuegoRepository
import org.example.repository.JsonVideojuegoRepository
import org.example.repository.XmlVideojuegoRepository
import java.nio.file.Path

class ConversionService {
    private val csvRepository = CsvVideojuegoRepository()
    private val xmlRepository = XmlVideojuegoRepository()
    private val jsonRepository = JsonVideojuegoRepository()

    fun convertirCsvAXml(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosCSV = csvRepository.leerFichero(rutaInicial)
        val videojuegosXML = mutableListOf<VideojuegoXML>()

        for (videojuego in videojuegosCSV) {
            val nuevoVideojuego = VideojuegoXML(videojuego.idVideojuego,videojuego.titulo,videojuego.genero,videojuego.anio, videojuego.nota)

            videojuegosXML.add(nuevoVideojuego)
        }

        xmlRepository.escribirFichero(rutaFinal,videojuegosXML)
    }

    fun convertirCsvAJson(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosCSV = csvRepository.leerFichero(rutaInicial)
        val videojuegosJSON = mutableListOf<VideojuegoJSON>()

        for (videojuego in videojuegosCSV) {
            val nuevoVideojuego = VideojuegoJSON(videojuego.idVideojuego,videojuego.titulo,videojuego.genero,videojuego.anio, videojuego.nota)

            videojuegosJSON.add(nuevoVideojuego)
        }

        jsonRepository.escribirFichero(rutaFinal,videojuegosJSON)
    }

    fun convertirXmlACsv(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosXML = xmlRepository.leerFichero(rutaInicial)
        val videojuegosCSV = mutableListOf<Videojuego>()

        for (videojuego in videojuegosXML) {
            val nuevoVideojuego = Videojuego(videojuego.idVideojuego, videojuego.titulo, videojuego.genero, videojuego.anio, videojuego.nota)

            videojuegosCSV.add(nuevoVideojuego)
        }

        csvRepository.escribirFichero(rutaFinal,videojuegosCSV)
    }

    fun convertirXmlAJson(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosXML = xmlRepository.leerFichero(rutaInicial)
        val videojuegosJSON = mutableListOf<VideojuegoJSON>()

        for (videojuego in videojuegosXML) {
            val nuevoVideojuego = VideojuegoJSON(videojuego.idVideojuego,videojuego.titulo,videojuego.genero,videojuego.anio, videojuego.nota)

            videojuegosJSON.add(nuevoVideojuego)
        }

        jsonRepository.escribirFichero(rutaFinal,videojuegosJSON)
    }

    fun convertirJsonACsv(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosJSON = jsonRepository.leerFichero(rutaInicial)
        val videojuegosCSV = mutableListOf<Videojuego>()

        for (videojuego in videojuegosJSON) {
            val nuevoVideojuego = Videojuego(videojuego.idVideojuego,videojuego.titulo,videojuego.genero,videojuego.anio, videojuego.nota)

            videojuegosCSV.add(nuevoVideojuego)
        }

        csvRepository.escribirFichero(rutaFinal,videojuegosCSV)
    }

    fun convertirJsonAXml(rutaInicial: Path, rutaFinal: Path) {
        val videojuegosJSON = jsonRepository.leerFichero(rutaInicial)
        val videojuegosXML = mutableListOf<VideojuegoXML>()

        for (videojuego in videojuegosJSON) {
            val nuevoVideojuego = VideojuegoXML(videojuego.idVideojuego,videojuego.titulo,videojuego.genero,videojuego.anio, videojuego.nota)

            videojuegosXML.add(nuevoVideojuego)
        }

        xmlRepository.escribirFichero(rutaFinal,videojuegosXML)
    }
}