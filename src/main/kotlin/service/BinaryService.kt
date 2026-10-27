package org.example.service

import org.example.model.Videojuego
import org.example.repository.BinVideojuegoRepository
import java.nio.file.Path

class BinVideojuegoService(private val repository: BinVideojuegoRepository, private val rutaBin: Path) {

    fun importarVideojuegos(videojuegos: List<Videojuego>) {
        repository.escribirFichero(rutaBin, videojuegos)
    }

    fun leerTodosVideojuegos(): List<Videojuego> {
        return repository.leerFichero(rutaBin)
    }

    fun leerVideojuegoPorId(id: Int): Videojuego? {
        val videojuegos = repository.leerFichero(rutaBin)

        for (videojuego in videojuegos) {
            if (videojuego.idVideojuego == id) {
                return videojuego
            }
        }

        return null
    }

    fun anadirVideojuego(videojuego: Videojuego) {
        repository.anyadirVideojuego(rutaBin, videojuego)
    }

    fun modificarNotaVideojuego(id: Int, nuevaNota: Double) {
        repository.modificarNotaVideojuego(rutaBin, id, nuevaNota)
    }

    fun eliminarVideojuegoPorId(id: Int) {
        repository.eliminarVideojuego(rutaBin, id)
    }
}
