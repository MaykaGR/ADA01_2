import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.PrintWriter
import javax.xml.parsers.DocumentBuilderFactory
fun nodes(nodeList: NodeList, nombre: String):NodeList{
    var nuevaList = nodeList
    if(nodeList.length>1){
        for(i in 0..nodeList.length-1){
            val campo = nuevaList.item(i) as Element
            if(campo.tagName==nombre){
                nuevaList = nodeList.item(i).childNodes
            }
        }
    }
    else{
        val elemento = nodeList.item(0)
        val e = elemento as Element
        if(e.tagName == nombre){
            nuevaList = nodeList
        }
        else{
            nuevaList = nodeList.item(0).childNodes
        }
    }
    //println(nuevaList.item(5).childNodes.item(0).textContent)
    return nuevaList
}


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
                    var nodos = nodoPadre.childNodes
                var i = 0
                while(i<nombres.length&&encontrado == false){
                    if(nombres.item(i).textContent==nombre){
                        println("Personaje encontrado. Generando informe...")
                        //val nodo = nombres.item(i).getRootNode()
                        val ficheroEscritura = File("personajes${System.getProperty("file.separator")}${nombre}.txt")
                        val pw = PrintWriter(ficheroEscritura, Charsets.UTF_8)
                        var texto = ""
                        /*var over = false
                        var x = 0
                        var etiquetas = nodoPadre.childNodes
                        while(x<nodoPadre.childNodes.length&&over==false){
                            var nodos = etiquetas.item(x).childNodes
                            if(nodos.length>1){
                                for(n in 0..nodos.length-1){
                                    val et = nodos.item(i) as Element
                                    if(et.tagName == nombre){
                                        etiquetas = nodos.item(i).childNodes
                                        over = true
                                    }
                                }
                            }
                            x += 1
                        }*/
                        //val personaje = nodoPadre.getElementsByTagName(nombre)
                        //val etiquetas = personaje.item(0).childNodes
                        //texto = personaje.item(0).textContent
                        val etiquetas = nodes(nodos,nombre)
                        for(j in 0..etiquetas.length-1){
                            //println(etiquetas.item(j))
                            val campo = etiquetas.item(j).nodeName
                            //println("${etiquetas.item(j).nodeName}${etiquetas.item(j).childNodes.item(0).textContent}")
                            //if(campo.tagName=="name"){println(campo.textContent)}
                            when(campo){
                                "name" -> texto+="name: ${etiquetas.item(j).childNodes.item(0).textContent}"
                                "title" -> texto+="title: ${etiquetas.item(j).childNodes.item(0).textContent}"
                                "blurb" -> texto+="blurb: ${etiquetas.item(j).childNodes.item(0).textContent}"
                                "tags" -> texto+="tags: ${etiquetas.item(j).childNodes.item(0).textContent}"
                            }
                            println(texto)
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