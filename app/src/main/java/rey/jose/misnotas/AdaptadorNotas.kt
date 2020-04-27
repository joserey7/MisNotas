package rey.jose.misnotas

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File
import java.lang.Exception

class AdaptadorNotas: BaseAdapter{
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[p0]

        vista.tv_titulo_det.text = nota.titulo
        vista.tv_contenido_det.text = nota.contenido

        vista.btn_borrar.setOnClickListener {
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }

    public fun eliminar(titulo:String){
        if (titulo == ""){
            Toast.makeText(context, "Error, título vacío", Toast.LENGTH_SHORT).show()
        }else{
            try {
                //elimina el archivo con el títutlo seleccionado
                val archivo = File(ubicacion(), titulo + ".txt")
                archivo.delete()

                Toast.makeText(context,"se eliminó el archivo",Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String{
        val carpeta = File(Environment.getExternalStorageDirectory(), "notas")
        if (!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }

}