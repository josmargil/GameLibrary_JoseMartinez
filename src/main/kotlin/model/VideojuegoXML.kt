package org.example.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

data class VideojuegoXML(
    @JacksonXmlProperty(localName = "id")
    val idVideojuego: Int,
    @JacksonXmlProperty(localName = "titulo")
    val titulo: String,
    @JacksonXmlProperty(localName = "genero")
    val genero: String,
    @JacksonXmlProperty(localName = "anio")
    val anio: Int,
    @JacksonXmlProperty(localName = "nota")
    val nota: Double
)

@JacksonXmlRootElement(localName = "videojuegos")
data class VideojuegosWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "videojuego")
    val listaVideojuegos: List<VideojuegoXML> = emptyList()
)
