package org.example.repository

import org.example.model.Videojuego
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption

class BinVideojuegoRepository {
    private val TAM_ID = 4
    private val TAM_TITULO = 40
    private val TAM_GENERO = 20
    private val TAM_ANIO = 4
    private val TAM_NOTA = 8

    private val TAM_REGISTRO = TAM_ID + TAM_TITULO + TAM_GENERO + TAM_ANIO + TAM_NOTA


    fun escribirFichero(ruta: Path, videojuegos: List<Videojuego>) {

        try {
            FileChannel.open(ruta, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            ).use { canal ->

                for (videojuego in videojuegos) {

                    val buffer = ByteBuffer.allocate(TAM_REGISTRO)

                    buffer.putInt(videojuego.idVideojuego)

                    val tituloBytes = videojuego.titulo.padEnd(TAM_TITULO, ' ').toByteArray(Charsets.ISO_8859_1)

                    buffer.put(tituloBytes, 0, TAM_TITULO)

                    val generoBytes = videojuego.genero.padEnd(TAM_GENERO, ' ').toByteArray(Charsets.ISO_8859_1)

                    buffer.put(generoBytes, 0, TAM_GENERO)

                    buffer.putInt(videojuego.anio)

                    buffer.putDouble(videojuego.nota)

                    buffer.flip()

                    while (buffer.hasRemaining()) {
                        canal.write(buffer)
                    }
                }
            }

            println("Fichero binario guardado correctamente.")

        } catch (e: Exception) {
            println("Error al escribir el fichero: ${e.message}")
        }
    }


    fun leerFichero(ruta: Path): List<Videojuego> {

        val videojuegos = mutableListOf<Videojuego>()

        try {
            FileChannel.open(ruta, StandardOpenOption.READ).use { canal ->

                val buffer = ByteBuffer.allocate(TAM_REGISTRO)

                while (canal.read(buffer) != -1) {

                    if (buffer.position() == TAM_REGISTRO) {

                        buffer.flip()

                        val id = buffer.int

                        val tituloBytes = ByteArray(TAM_TITULO)
                        buffer.get(tituloBytes)
                        val titulo = String(tituloBytes, Charsets.ISO_8859_1).trim()

                        val generoBytes = ByteArray(TAM_GENERO)
                        buffer.get(generoBytes)
                        val genero = String(generoBytes, Charsets.ISO_8859_1).trim()

                        val anio = buffer.int
                        val nota = buffer.double

                        val videojuego = Videojuego(id, titulo, genero, anio, nota)

                        videojuegos.add(videojuego)

                        buffer.clear()
                    }
                }
            }

        } catch (e: Exception) {
            println("Error al leer el fichero binario: ${e.message}")
        }

        return videojuegos
    }


    fun anyadirVideojuego(ruta: Path, videojuego: Videojuego) {

        val videojuegos = leerFichero(ruta)

        for (v in videojuegos) {
            if (v.idVideojuego == videojuego.idVideojuego) {
                println("Error: Ya existe un videojuego con ese ID.")
                return
            }
        }

        if (videojuego.titulo.length > TAM_TITULO ||
            videojuego.genero.length > TAM_GENERO) {
            println("Error: Título o género demasiado largo.")
            return
        }

        try {
            FileChannel.open(ruta, StandardOpenOption.WRITE, StandardOpenOption.CREATE).use { canal ->

                canal.position(canal.size())

                val buffer = ByteBuffer.allocate(TAM_REGISTRO)

                buffer.putInt(videojuego.idVideojuego)

                val titulo = videojuego.titulo.padEnd(TAM_TITULO, ' ').toByteArray(Charsets.ISO_8859_1)

                buffer.put(titulo, 0, TAM_TITULO)

                val genero = videojuego.genero.padEnd(TAM_GENERO, ' ').toByteArray(Charsets.ISO_8859_1)

                buffer.put(genero, 0, TAM_GENERO)

                buffer.putInt(videojuego.anio)
                buffer.putDouble(videojuego.nota)

                buffer.flip()

                while (buffer.hasRemaining()) {
                    canal.write(buffer)
                }
            }

            println("Videojuego añadido correctamente.")

        } catch (e: Exception) {
            println("Error al añadir el videojuego: ${e.message}")
        }
    }


    fun modificarNotaVideojuego(ruta: Path, id: Int, nuevaNota: Double) {

        try {
            FileChannel.open(ruta, StandardOpenOption.READ, StandardOpenOption.WRITE).use { canal ->

                val buffer = ByteBuffer.allocate(TAM_REGISTRO)
                var encontrado = false

                while (canal.read(buffer) != -1 && !encontrado) {

                    if (buffer.position() == TAM_REGISTRO) {

                        buffer.flip()

                        val idActual = buffer.int

                        if (idActual == id) {

                            val posicionNota = canal.position() - TAM_NOTA

                            canal.position(posicionNota)

                            val bufferNota = ByteBuffer.allocate(TAM_NOTA)
                            bufferNota.putDouble(nuevaNota)
                            bufferNota.flip()

                            canal.write(bufferNota)

                            encontrado = true
                            println("Nota modificada correctamente.")
                        }

                        buffer.clear()
                    }
                }

                if (!encontrado) {
                    println("No existe un videojuego con ese ID.")
                }
            }

        } catch (e: Exception) {
            println("Error al modificar el videojuego: ${e.message}")
        }
    }


    fun eliminarVideojuego(ruta: Path, id: Int) {

        val rutaTemporal = Path.of(ruta.toString() + ".tmp")
        var encontrado = false

        try {
            FileChannel.open(ruta, StandardOpenOption.READ).use { canalLectura ->

                FileChannel.open(rutaTemporal, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).use { canalEscritura ->

                    val buffer = ByteBuffer.allocate(TAM_REGISTRO)

                    while (canalLectura.read(buffer) > 0) {

                        if (buffer.position() == TAM_REGISTRO) {

                            buffer.flip()

                            val idActual = buffer.int

                            if (idActual == id) {
                                encontrado = true
                            } else {
                                buffer.rewind()

                                while (buffer.hasRemaining()) {
                                    canalEscritura.write(buffer)
                                }
                            }

                            buffer.clear()
                        }
                    }
                }
            }

            if (encontrado) {
                Files.move(rutaTemporal, ruta, StandardCopyOption.REPLACE_EXISTING)

                println("Videojuego eliminado correctamente.")
            } else {
                Files.deleteIfExists(rutaTemporal)
                println("No existe un videojuego con ese ID.")
            }

        } catch (e: Exception) {
            println("Error al eliminar el videojuego: ${e.message}")
        }
    }


}