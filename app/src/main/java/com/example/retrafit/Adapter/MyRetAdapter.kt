package com.example.retrafit.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrafit.databinding.ItemRvBinding
import com.example.retrafit.models.MyTodo


class MyRetAdapter ( var rvAction: RvAction , var list: ArrayList<MyTodo>): RecyclerView.Adapter<MyRetAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBind(myTodo: MyTodo) {
            itemRvBinding.matn.text = myTodo.matn
            itemRvBinding.holat.text = myTodo.holat
            itemRvBinding.sarlavha.text = myTodo.sarlavha
            itemRvBinding.ohirgiMuddat.text = myTodo.oxirgi_muddat
            itemRvBinding.btnMenu.setOnClickListener {
                rvAction.itemClicked(itemRvBinding.btnMenu , myTodo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun itemClicked(imageView: ImageView , myTodo: MyTodo)
    }
}