package com.example.piggywallet.module.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.piggywallet.R
import kotlinx.android.synthetic.main.item_bookdetail.view.*

class BookDetailMenuAdapter(private val items: List<BookDetailViewModel.BookMenusItem>,
                            private val listener: Listener) : RecyclerView.Adapter<BookDetailMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookdetail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            val item = items[adapterPosition]

            itemView.txt_menu.text = item.menuName
            if(item.menuTYPE== "INCOME") {
                itemView.icon_type.setImageResource(R.drawable.item_saving)
            }
            else if(item.menuTYPE == "OUTCOME"){
                itemView.icon_type.setImageResource(R.drawable.item_outcome)
            }
 //            else if(item.mtype == "M3"){
//                itemView.icon_type.setImageResource(R.drawable.item_food_select)
//            }else{
//                itemView.icon_type.setImageResource(R.drawable.item_centercare)
//            }


        }

        private val onDetailClick = View.OnClickListener {
            val item = items[adapterPosition]
            listener.onDetailClick(adapterPosition, item)
        }

        init {
            itemView.mLayout.setOnClickListener(onDetailClick)
        }
    }

    interface Listener {
        fun onDetailClick(index: Int, item: BookDetailViewModel.BookMenusItem)

    }

}