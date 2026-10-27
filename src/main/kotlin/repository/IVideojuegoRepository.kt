package org.example.repository

import org.example.model.Videojuego
import java.nio.file.Path

interface IVideojuegoRepository {

    fun leerFichero(ruta: Path): List<Videojuego>

    fun escribirFichero(ruta: Path, videojuegos: List<Videojuego>)

}