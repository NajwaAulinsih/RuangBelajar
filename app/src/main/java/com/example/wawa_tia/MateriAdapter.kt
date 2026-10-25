
package com.example.wawa_tia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MateriAdapter(
    context: Context,
    private val judulMateri: Array<String>,
    private val deskripsiMateri: Array<String>
) : ArrayAdapter<String>(context, 0, judulMateri) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_materi, parent, false)

        val tvJudul = view.findViewById<TextView>(R.id.tvItemJudul)
        val tvDeskripsi = view.findViewById<TextView>(R.id.tvItemDeskripsi)

        tvJudul.text = judulMateri[position]
        tvDeskripsi.text = deskripsiMateri[position]

        return view
    }
}
