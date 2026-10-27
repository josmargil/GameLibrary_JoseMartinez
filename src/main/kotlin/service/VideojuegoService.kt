package org.example.service

import org.example.model.Videojuego
import org.example.model.VideojuegoJSON
import org.example.repository.IVideojuegoRepository
import org.example.model.VideojuegoXML
import java.nio.file.Files
import java.nio.file.Path

class VideojuegoService(private val repository: IVideojuegoRepository, private val rutaInicial: Path, private val rutaFinal: Path) {

    private val videojuegos: MutableList<Videojuego> =
        if (Files.exists(rutaFinal)) {
            repository.leerFichero(rutaFinal).toMutableList()
        } else {
            repository.leerFichero(rutaInicial).toMutableList()
        }

    fun leerTodosVideojuegos(): List<Videojuego> {
        return videojuegos.toList()
    }

    fun leerVideojuegoPorId(id: Int): Videojuego? {
        for (videojuego in videojuegos) {
            if (videojuego.idVideojuego == id) {
                return videojuego
            }
        }
        return null
    }

    fun anyadirVideojuego(videojuego: Videojuego) {
        if (leerVideojuegoPorId(videojuego.idVideojuego) == null) {
            videojuegos.add(videojuego)
            guardarCambiosFichero()
            println("Videojuego añadido correctamente")
        } else {
            println("No se pudo añadir el videojuego")
        }
    }

    fun modificarNotaVideojuego(id: Int, nuevaNota: Double) {
        for (i in videojuegos.indices) {
            if (videojuegos[i].idVideojuego == id) {
                videojuegos[i] = videojuegos[i].copy(nota = nuevaNota)
                guardarCambiosFichero()
                println("Videojuego modificado correctamente")
                return
            }
        }

        println("No se pudo modificar el videojuego")
    }

    fun eliminarVideojuegoPorId(id: Int) {
        for (videojuego in videojuegos) {
            if (videojuego.idVideojuego == id) {
                videojuegos.remove(videojuego)
                guardarCambiosFichero()
                println("Videojuego eliminado correctamente")
                return
            }
        }

        println("No se pudo eliminar el videojuego")
    }

    fun guardarCambiosFichero() {
        repository.escribirFichero(rutaFinal, videojuegos)
    }

    fun integrarVideojuegosXML(videojuegosXML: List<VideojuegoXML>) {
        var numVideojuegosAnyadidos = 0

        for (videojuegoXML in videojuegosXML) {
            if (leerVideojuegoPorId(videojuegoXML.idVideojuego) == null) {
                val videojuego = Videojuego(videojuegoXML.idVideojuego,videojuegoXML.titulo, videojuegoXML.genero, videojuegoXML.anio, videojuegoXML.nota)

                videojuegos.add(videojuego)
                numVideojuegosAnyadidos++
            }
        }

        guardarCambiosFichero()
        println("Se han añadido $numVideojuegosAnyadidos videojuegos nuevos a la colección")
    }

    fun integrarVideojuegosJSON(videojuegosJSON: List<VideojuegoJSON>) {
        var numVideojuegosAnyadidos = 0

        for (videojuegoJSON in videojuegosJSON) {
            if (leerVideojuegoPorId(videojuegoJSON.idVideojuego) == null) {
                val videojuego = Videojuego(videojuegoJSON.idVideojuego,videojuegoJSON.titulo,videojuegoJSON.genero, videojuegoJSON.anio, videojuegoJSON.nota)

                videojuegos.add(videojuego)
                numVideojuegosAnyadidos++
            }
        }

        guardarCambiosFichero()
        println("Se han añadido $numVideojuegosAnyadidos videojuegos nuevos a la colección")
    }
}
