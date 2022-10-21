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
                val nombre = readln()
                var i = 0
                while(i<nombres.length&&encontrado == false){
                    if(nombres.item(i).textContent==nombre){
                        println("Personaje encontrado. Generando informe...")
                        var personajes= nodoPadre.childNodes
                        val ficheroEscritura = File("personajes${System.getProperty("file.separator")}${nombre}.txt")
                        val pw = PrintWriter(ficheroEscritura, Charsets.UTF_8)
                        var texto = ""
                        var x = 0
                        var over = false
                        while(x<personajes.length&&over==false){
                            var personaje = personajes.item(x) as Element
                            if(personaje.nodeType==Node.ELEMENT_NODE&&personaje.tagName != nombre) {
                                personajes = personaje.childNodes
                            }
                            else if(personaje.nodeType==Node.ELEMENT_NODE&&personaje.tagName==nombre){
                                val etiquetas = personaje.childNodes
                                for(j in 0..etiquetas.length-1){
                                    //println(etiquetas.item(j))
                                    val campo: Element = etiquetas.item(j) as Element
                                    //println("${campo.tagName}${campo.textContent}")
                                    //if(campo.tagName=="name"){println(campo.textContent)}
                                    when(campo.tagName){
                                        "name" -> texto+="name: ${campo.textContent}"
                                        "title" -> texto+="title: ${campo.textContent}"
                                        "blurb" -> texto+="blurb: ${campo.textContent}"
                                        "tags" -> texto+="tags: ${campo.textContent}"
                                    }
                                }
                            over = true
                            }
                            //println(personajes.length)
                            x+=1
                        }
                        pw.write(texto)
                        pw.close()
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