# GameLibrary — Catálogo de videojuegos

GameLibrary es una aplicación de consola desarrollada en Kotlin para gestionar un catálogo de videojuegos y almacenar su información en diferentes formatos de fichero.

El programa permite consultar, añadir, modificar y eliminar videojuegos en CSV; leer e integrar videojuegos desde XML y JSON; realizar conversiones entre estos tres formatos; y gestionar un fichero binario de acceso aleatorio.

## 1. Estructura de datos

El catálogo utiliza la siguiente clase principal:

```kotlin
data class Videojuego(
    val idVideojuego: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val nota: Double
)
```

Cada videojuego contiene un identificador, título, género, año de lanzamiento y nota. Para los ficheros XML y JSON se utilizan también los modelos `VideojuegoXML` y `VideojuegoJSON`, adaptados a los nombres de los campos de cada formato.

### Estructura del registro binario

Los videojuegos del fichero `.dat` se almacenan mediante registros de tamaño fijo:

| Campo | Tipo | Tamaño |
|---|---|---:|
| `idVideojuego` | `Int` | 4 bytes |
| `titulo` | Texto de longitud fija | 40 bytes |
| `genero` | Texto de longitud fija | 20 bytes |
| `anio` | `Int` | 4 bytes |
| `nota` | `Double` | 8 bytes |
| **Total** | | **76 bytes** |

Los textos se rellenan con espacios y se codifican en ISO-8859-1. Para acceder al fichero binario se utilizan `FileChannel` y `ByteBuffer`.

## 2. Instrucciones de ejecución

1. Abrir el proyecto en **IntelliJ IDEA**.
2. Disponer de **JDK 25** y una versión de Gradle compatible con ese JDK.
3. Sincronizar Gradle para descargar las dependencias del proyecto.
4. Comprobar que los ficheros de origen estén en la carpeta `datos/datos_ini/`:
   - `videojuegos.csv`
   - `videojuegos.xml`
   - `videojuegos.json`
5. Comprobar que existan las carpetas `datos/datos_fin/` y `datos/datos_conversion/`, utilizadas para guardar resultados.
6. Ejecutar la función `main()` de `Main.kt`.

También se puede comprobar la compilación desde la terminal de Windows, en la raíz del proyecto, con:

```powershell
.\gradlew.bat clean build
```

### Opciones del menú principal

- **1. Gestión CSV:** listar, buscar por ID, añadir, modificar la nota y eliminar videojuegos.
- **2. Leer catálogo XML:** mostrar sus videojuegos e incorporar al catálogo CSV los IDs que no existan.
- **3. Leer catálogo JSON:** mostrar sus videojuegos e incorporar al catálogo CSV los IDs que no existan.
- **4. Conversión entre formatos:** CSV → XML, CSV → JSON, XML → CSV, XML → JSON, JSON → CSV y JSON → XML. Los resultados se guardan en `datos/datos_conversion/`.
- **5. Gestión fichero BIN:** importar desde CSV, leer registros, añadir videojuegos, modificar su nota por ID y eliminar registros previa confirmación. El fichero se guarda en `datos/datos_fin/videojuegos.dat`.
- **0. Salir:** cerrar la aplicación.

Los cambios del catálogo CSV se guardan en `datos/datos_fin/videojuegos.csv`. Al iniciar el servicio CSV, se utiliza este fichero si existe; en caso contrario, se lee el CSV inicial.

**Importante:** la opción de importar al fichero BIN sustituye su contenido por los videojuegos del CSV inicial.

## 3. Decisiones de diseño

- He separado el proyecto en **modelos**, **repositorios** y **servicios** para organizar las responsabilidades de cada clase.
- He utilizado **CSV** como formato principal del catálogo por su sencillez para almacenar y consultar datos.
- He empleado **XML y JSON** como formatos de intercambio, permitiendo importar videojuegos nuevos y realizar conversiones.
- He reservado **76 bytes por videojuego** en el fichero binario para poder acceder a posiciones concretas mediante `FileChannel` y `ByteBuffer`.
- He utilizado un **fichero temporal** al eliminar videojuegos del binario para conservar los registros restantes.
