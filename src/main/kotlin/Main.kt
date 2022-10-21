import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.PrintWriter
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    var exit = false
    val ficheroXML = File("resources${System.getProperty("file.separator")}personajesLol.xml")
    if(ficheroXML.exists()){
        var nodoPadre: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ficheroXML)
        var nombres = nodoPadre.getElementsByTagName("name")
        while(exit == false){
            println("¿Qué deseas hacer? -1. Buscar personaje -2. Salir")
            var option = readln()
            if(option=="1"){
                var encontrado = false
                while(encontrado==false){
                println("Introduce el nombre del personaje: ")
                    var nombre = readln()
                    var letra = nombre.toCharArray()[0].toUpperCase()
                    nombre = nombre.toLowerCase().replaceFirstChar { letra }
                var i = 0
                while(i<nombres.length&&encontrado == false){
                    if(nombres.item(i).textContent==nombre){
                        println("Personaje encontrado. Generando informe...")
                        val ficheroEscritura = File("personajes${System.getProperty("file.separator")}${nombre}.txt")
                        val pw = PrintWriter(ficheroEscritura, Charsets.UTF_8)
                        var texto = ""
                        val personajes = nodoPadre.getElementsByTagName(nombre)

                        for(j in 0..personajes.length-1){
                            val nodePersonaje = personajes.item(j)
                            if(nodePersonaje.nodeType == Node.ELEMENT_NODE){
                                val personaje: Element = nodePersonaje as Element

                                val tag = personaje.getElementsByTagName("tags").item(0).textContent

                                val campos = personaje.childNodes

                                for(x in 0..campos.length-1) {
                                    val campo = campos.item(x)
                                    when(campo.nodeName){
                                        "name" -> texto+="name: ${campo.textContent}\n"
                                        "title" -> texto+="title: ${campo.textContent}\n"
                                        "blurb" -> texto+="blurb: ${campo.textContent}\n"
                                    }
                                }
                                texto+="tags: ${tag}"
                            }

                        }
                        pw.write(texto)
                        pw.close()
                        //println(texto)
                        encontrado = true
                    }
                    i+=1
                }
                    if(encontrado==false){
                        println("Personaje no encontrado. Vuelve a intentarlo...")}
                }
            }
            else if(option=="2"){
                println("Hasta pronto!")
               exit = true
            }
            else{
                println("Comando inválido")
            }
        }}
    else{
        println("No se encuentra el fichero XML.")
    }
}