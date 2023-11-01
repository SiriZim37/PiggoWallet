package com.example.piggywallet.module.main

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.piggywallet.R
import com.example.piggywallet.manager.db.datamodel.BookNote
import kotlinx.android.synthetic.main.item_book_main_history.view.*

class BookMainAdapter(private var items: ArrayList<BookNote>) : RecyclerView.Adapter<BookMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_main_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun updateItems(data: List<BookNote>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {
            val item = items[adapterPosition]


            itemView.title.text = item.date
            itemView.type.text = item.menuName
            itemView.amount.text = item.total
            itemView.des.text = item.des
            if(item.menuType == "INCOME" ){
                itemView.type.setTextColor(Color.parseColor("#307DFA"))
                itemView.amount.setTextColor(Color.parseColor("#307DFA"))
                itemView.item_money.setImageResource(R.drawable.item_saving)
            }else{
                itemView.type.setTextColor(Color.parseColor("#EB0C1F"))
                itemView.amount.setTextColor(Color.parseColor("#EB0C1F"))
                itemView.item_money.setImageResource(R.drawable.item_outcome)
            }


        }

    }

}