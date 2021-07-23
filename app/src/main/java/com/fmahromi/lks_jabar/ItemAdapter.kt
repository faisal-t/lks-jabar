package com.fmahromi.lks_jabar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (val context: Context) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    val menuList : List<String> ?= null

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val txtMenuId : TextView? = itemView.findViewById(R.id.txt_menu_id)
        val txtMenuName : TextView? = itemView.findViewById(R.id.txt_menu_name)
        val txtMenuDescription : TextView? = itemView.findViewById(R.id.txt_menu_description)
        val txtMenuPrice : TextView? = itemView.findViewById(R.id.txt_menu_price)
        val btnDelete : ImageButton? = itemView.findViewById(R.id.btn_delete)
        val btnEdit : ImageButton? = itemView.findViewById(R.id.btn_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {
        holder.txtMenuId?.text = menuList?.get(0)?.get(position).toString()
        holder.txtMenuName?.text = menuList?.get(1)?.get(position).toString()
        holder.txtMenuDescription?.text = menuList?.get(2)?.get(position).toString()
        holder.txtMenuPrice?.text = menuList?.get(3)?.get(position).toString()

        //untuk button edit
        holder.btnEdit?.setOnClickListener {
            val intent = Intent(context,PostUpdateActivity::class.java)
            intent.putExtra("id",holder.txtMenuId?.text.toString())
            intent.putExtra("name",holder.txtMenuName?.text.toString())
            intent.putExtra("description",holder.txtMenuDescription?.text.toString())
            intent.putExtra("price",holder.txtMenuPrice?.text.toString())
            intent.putExtra("jenis","update")
            context.startActivity(intent)
        }

        //untuk button delete
        holder.btnDelete?.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = menuList?.size?:0

}