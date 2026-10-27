package org.example

import org.example.model.Videojuego
import org.example.repository.BinVideojuegoRepository
import org.example.repository.CsvVideojuegoRepository
import org.example.repository.JsonVideojuegoRepository
import org.example.repository.XmlVideojuegoRepository
import org.example.service.BinVideojuegoService
import org.example.service.ConversionService
import org.example.service.VideojuegoService
import java.nio.file.Path
import java.nio.file.Files

val rutaInicialCsv: Path = Path.of("datos", "datos_ini", "videojuegos.csv")
val rutaFinalCsv: Path = Path.of("datos", "datos_fin", "videojuegos.csv")
val rutaInicialXml: Path = Path.of("datos", "datos_ini", "videojuegos.xml")
val rutaInicialJson: Path = Path.of("datos", "datos_ini", "videojuegos.json")
val rutaBin = Path.of("datos", "datos_fin", "videojuegos.dat")

val repository = CsvVideojuegoRepository()
val xmlRepository = XmlVideojuegoRepository()
val jsonRepository = JsonVideojuegoRepository()
val binRepository = BinVideojuegoRepository()
val service = VideojuegoService(repository, rutaInicialCsv, rutaFinalCsv)
val conversionService = ConversionService()
val binService = BinVideojuegoService(binRepository, rutaBin)

fun main() {

    logicaMenuPrincipal(service, xmlRepository, jsonRepository, conversionService)
}

fun menuPrincipal() {
    println("======= CATALOGO DE VIDEOJUEGOS =======\n" +
            "1. Gestion CSV\n" +
            "2. Leer catalogo XML\n" +
            "3. Leer catalogo JSON\n" +
            "4. Conversión entre formatos\n" +
            "5. Gestión fichero BIN\n" +
            "0. Salir\n" +
            "=======================================")
}

fun logicaMenuPrincipal(service: VideojuegoService, xmlRepository: XmlVideojuegoRepository, jsonRepository: JsonVideojuegoRepository, conversionService: ConversionService) {
    var opcion: Int
    do {
        menuPrincipal()
        opcion = eligeOpcion()

        when (opcion) {
            1 -> logicaMenuCSV(service)
            2 -> logicaMenuXML(xmlRepository, service)
            3 -> logicaMenuJSON(jsonRepository, service)
            4 -> logicaMenuConversion(conversionService)
            5 -> logicaMenuBinario()
            0 -> println("Saliendo del programa...")
            else -> println("Número incorrecto. Pruebe de nuevo.")
        }
    } while (opcion != 0)
}

fun menuGestionCSV() {
    println("======= GESTIÓN CSV =======\n" +
            "1. Listar todos los videojuegos\n" +
            "2. Buscar videojuego por ID\n" +
            "3. Añadir nuevo videjuego\n" +
            "4. Modificar nota de un videojuego\n" +
            "5. Eliminar videojuego\n" +
            "0. Volver al menú principal\n" +
            "===========================")
}

fun logicaMenuCSV(service: VideojuegoService) {
    var opcion: Int

    do {
        menuGestionCSV()
        opcion = eligeOpcion()

        when (opcion) {
            1 -> leerTodosVideojuego(service)
            2 -> leerVideojuegoPorId(service)
            3 -> anyadirVideojuego(service)
            4 -> modificarNotaVideojuego(service)
            5 -> eliminarVideojuego(service)
            0 -> println("Volviendo atrás...")
            else -> println("Número incorrecto. Prueba de nuevo.")
        }
    } while (opcion != 0)
}

fun leerTodosVideojuego(service: VideojuegoService) {
    val videojuegos = service.leerTodosVideojuegos()

    println("======= VIDEOJUEGOS CSV =======")
    for (videojuego in videojuegos) {
        println(videojuego)
    }
    println()
}

fun leerVideojuegoPorId(service: VideojuegoService) {
    println("Introduce el ID: ")
    val id = readln().toIntOrNull()

    if (id == null) {
        println("Dato incorrecto")
        return
    }

    val videojuego = service.leerVideojuegoPorId(id)

    if (videojuego != null) {
        println(videojuego)
    } else {
        println("No existe un videojuego con ese ID")
    }

    println()
}

fun anyadirVideojuego(service: VideojuegoService) {

    println("Introduce el ID: ")
    val id = readln().toIntOrNull()

    println("Introduce el Titulo: ")
    val titulo = readlnOrNull()

    println("Introduce el Genero: ")
    val genero = readlnOrNull()

    println("Introduce el Año: ")
    val anio = readln().toIntOrNull()

    println("Introduce la Nota: ")
    val nota = readln().toDoubleOrNull()

    if (id == null || titulo == null || genero == null || anio == null || nota == null) {
        println("Datos incorrectos")
        return
    }

    val videojuego = Videojuego(id, titulo, genero, anio, nota)

    service.anyadirVideojuego(videojuego)
    println()
}

fun modificarNotaVideojuego(service: VideojuegoService) {

    println("Introduce el ID: ")
    val id = readln().toIntOrNull()

    println("Introduce la Nota: ")
    val nota = readln().toDoubleOrNull()

    if (nota == null || id == null) {
        println("Datos incorrectos")
        return
    }

    service.modificarNotaVideojuego(id,nota)
    println()
}

fun eliminarVideojuego(service: VideojuegoService) {

    println("Introduce el ID: ")
    val id = readln().toIntOrNull()

    if (id == null) {
        println("Dato incorrecto")
        return
    }

    service.eliminarVideojuegoPorId(id)
    println()
}

// Futura Actualización
/* fun menuGestionXML() {
    println("======= GESTIÓN XML =======\n" +
            "1. Listar todos los videojuegos\n" +
            "2. Buscar videojuego por ID\n" +
            "3. Añadir nuevo videjuego\n" +
            "4. Modificar nota de un videojuego\n" +
            "5. Eliminar videojuego\n" +
            "0. Volver al menú principal\n" +
            "===========================")
}

*/

fun logicaMenuXML(xmlRepository: XmlVideojuegoRepository, service: VideojuegoService) {

    leerTodosVideojuegosXml(xmlRepository, service)

    // Futuras implementaciones

}

fun leerTodosVideojuegosXml(xmlRepository: XmlVideojuegoRepository, service: VideojuegoService) {

    val videojuegosXML = xmlRepository.leerFichero(rutaInicialXml)

    println("======= VIDEOJUEGOS XML =======")
    for (videojuego in videojuegosXML) {
        println(videojuego)
    }

    service.integrarVideojuegosXML(videojuegosXML)

    println()
}

// Futura actualización
/* fun menuGestionJSON() {
    println("======= GESTIÓN JSON =======\n" +
            "1. Listar todos los videojuegos\n" +
            "2. Buscar videojuego por ID\n" +
            "3. Añadir nuevo videjuego\n" +
            "4. Modificar nota de un videojuego\n" +
            "5. Eliminar videojuego\n" +
            "0. Volver al menú principal\n" +
            "===========================")
}
*/

fun logicaMenuJSON(jsonRepository: JsonVideojuegoRepository, service: VideojuegoService) {
    val videojuegosJSON = jsonRepository.leerFichero(rutaInicialJson)

    println("======= VIDEOJUEGOS JSON =======")

    for (videojuego in videojuegosJSON) {
        println(videojuego)
    }

    service.integrarVideojuegosJSON(videojuegosJSON)

    println()
}

fun menuConversionFicheros() {
    println("======= CONVERSIÓN ENTRE FICHEROS =======\n" +
            "1. CSV -> XML\n" +
            "2. CSV -> JSON\n" +
            "3. XML -> CSV\n" +
            "4. XML -> JSON\n" +
            "5. JSON -> CSV\n" +
            "6. JSON -> XML\n" +
            "0. Volver al menú principal")
}

fun logicaMenuConversion(conversionService: ConversionService) {
    var opcion: Int

    do {
        menuConversionFicheros()
        opcion = eligeOpcion()

        when (opcion) {
            1 -> conversionService.convertirCsvAXml(rutaInicialCsv, Path.of("datos", "datos_conversion", "conversion_csv_a_xml.xml"))
            2 -> conversionService.convertirCsvAJson(rutaInicialCsv, Path.of("datos", "datos_conversion", "conversion_csv_a_json.json"))
            3 -> conversionService.convertirXmlACsv(rutaInicialXml, Path.of("datos", "datos_conversion", "conversion_xml_a_csv.csv"))
            4 -> conversionService.convertirXmlAJson(rutaInicialXml, Path.of("datos", "datos_conversion", "conversion_xml_a_json.json"))
            5 -> conversionService.convertirJsonACsv(rutaInicialJson, Path.of("datos", "datos_conversion", "conversion_json_a_csv.csv"))
            6 -> conversionService.convertirJsonAXml(rutaInicialJson, Path.of("datos", "datos_conversion", "conversion_json_a_xml.xml"))
            0 -> println("Volviendo atrás...")
            else -> println("Número incorrecto. Prueba de nuevo.")
        }
    } while (opcion != 0)
}

fun menuBinario() {
    println("===== CRUD FICHERO BIN =====\n" +
            "1. Importar información de un fichero de intercambio\n" +
            "2. Leer información del fichero binario\n" +
            "3. Añadir un registro nuevo\n" +
            "4. Modificar un registro existente (por ID)\n" +
            "5. Eliminar un registro existente (por ID)\n" +
            "0. Volver al menú principal")
}


fun logicaMenuBinario() {
    var opcion: Int

    do {
        menuBinario()
        opcion = eligeOpcion()

        when (opcion) {

            1 -> importarVideojuegosBIN()
            2 -> leerVideojuegosBIN()
            3 -> anyadirVideojuegoBIN()
            4 -> modificarNotaVideojuegoBIN()
            5 -> eliminarVideojuegoBIN()
            0 -> println("Volviendo atrás...")
            else -> println("Opción no válida.")
        }
    } while (opcion != 0)
}

fun importarVideojuegosBIN() {
    val videojuegos = repository.leerFichero(rutaInicialCsv)

    binService.importarVideojuegos(videojuegos)
}

fun leerVideojuegosBIN() {
    val videojuegos = binService.leerTodosVideojuegos()

    println("======= VIDEOJUEGOS BIN =======")

    for (videojuego in videojuegos) {
        println(videojuego)
    }
}


fun anyadirVideojuegoBIN() {
    var id: Int?
    do {
        println("Introduce el ID:")
        id = readln().toIntOrNull()

        if (id == null) {
            println("El ID debe ser un número entero.")
        } else if (binService.leerVideojuegoPorId(id) != null) {
            println("Ya existe un videojuego con ese ID.")
        }

    } while (id == null || binService.leerVideojuegoPorId(id) != null)

    println("Introduce el Título:")
    val titulo = readln()

    println("Introduce el Género:")
    val genero = readln()

    var anio: Int?
    do {
        println("Introduce el Año:")
        anio = readln().toIntOrNull()

        if (anio == null) {
            println("Año incorrecto.")
        }

    } while (anio == null)

    var nota: Double?
    do {
        println("Introduce la Nota:")
        nota = readln().toDoubleOrNull()

        if (nota == null || !nota.isFinite() || nota < 0 || nota > 10) {
            println("Nota incorrecta. Introduce un número entre 0 y 10.")
        }

    } while (nota == null || !nota.isFinite() || nota < 0 || nota > 10)

    val videojuego = Videojuego(id, titulo, genero, anio, nota)

    binService.anadirVideojuego(videojuego)
}



fun modificarNotaVideojuegoBIN() {
    var id: Int?
    do {
        println("Introduce el ID:")
        id = readln().toIntOrNull()

        if (id == null) {
            println("El ID debe ser un número entero.")
        }
    } while (id == null)

    val videojuego = binService.leerVideojuegoPorId(id)
    if (videojuego == null) {
        println("No existe ningún videojuego con ese ID.")
        return
    }

    println("Videojuego encontrado: ${videojuego.titulo}")

    var nota: Double?
    do {
        println("Introduce la nueva nota:")
        nota = readln().toDoubleOrNull()

        if (nota == null || !nota.isFinite() || nota < 0 || nota > 10) {
            println("Nota incorrecta. Introduce un número entre 0 y 10.")
        }
    } while (nota == null || !nota.isFinite() || nota < 0 || nota > 10)

    binService.modificarNotaVideojuego(id, nota)
}



fun eliminarVideojuegoBIN() {
    var id: Int?
    do {
        println("Introduce el ID:")
        id = readln().toIntOrNull()

        if (id == null) {
            println("El ID debe ser un número entero.")
        }
    } while (id == null)

    val videojuego = binService.leerVideojuegoPorId(id)
    if (videojuego == null) {
        println("No existe ningún videojuego con ese ID.")
        return
    }

    println("Videojuego encontrado: ${videojuego.titulo}")

    println("¿Estás seguro de eliminarlo? (s/n):")
    val confirmacion = readln()

    if (confirmacion.equals("s", ignoreCase = true)) {
        binService.eliminarVideojuegoPorId(id)
    } else {
        println("Eliminación cancelada.")
    }
}



fun eligeOpcion(): Int {
    println("Elige una opción: ")
    return readln().toIntOrNull() ?: -1
}