package com.example.piggywallet.module.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piggywallet.R
import com.example.piggywallet.manager.db.datamodel.BookMenus
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_bookdetail.*
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class BookDetailActivity : AppCompatActivity() , CoroutineScope by CoroutineScope(Dispatchers.Default)
    , BookDetailMenuAdapter.Listener  {

    private lateinit var viewModel: BookDetailViewModel // Associate ViewModel to Activity

    private lateinit var menusItem : List<BookMenus>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookdetail)

        initInstance()
        initViewModel()
    }

    private fun initInstance() {
//
        viewModel= ViewModelProviders.of(this).get(BookDetailViewModel::class.java)

//        swipe_refresh.isRefreshing = false
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = GridLayoutManager(this, 4)

        recycler_view2.layoutManager = LinearLayoutManager(this)
        recycler_view2.layoutManager = GridLayoutManager(this, 4)

        viewModel.allBookMenus.observe(this, Observer { bookMenus ->
            bookMenus?.let {
                try {
                    menusItem =  it
                    if(menusItem.size > 0){
                        viewModel.setMenusItem(it)
                    }else{
                        viewModel.initialBookMenus()
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })

//        viewModel.getData()
    }

    fun initViewModel(){
        viewModel.whenDataIncomeLoaded.observe(this , Observer {
            it.let {
                recycler_view.adapter = BookDetailMenuAdapter(it, this)
            }
        })

        viewModel.whenDataOutcomeLoaded.observe(this , Observer {
            it.let {
                recycler_view2.adapter = BookDetailMenuAdapter(it, this)
            }
        })

    }

    fun showDialog( itemID: BookDetailViewModel.BookMenusItem){
        // on below line we are creating a new bottom sheet dialog.
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.fragment_dialog_bottom_sheet, null)
        view.txt_menusID.setText(itemID.menuName)
        view.btn_del.setOnClickListener {
            view.txt_menusID.setText("")
            dialog.dismiss()
        }
        view.btn_confirm.setOnClickListener {
            viewModel.saveData( itemID.menuID ,
                itemID.menuName ,
                view.edittext_amt.text.toString() ,
                view.edittext_description.text.toString() ,
                itemID.menuTYPE )
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }
    override fun onDetailClick(index: Int, itemID: BookDetailViewModel.BookMenusItem) {
        try {
            if(itemID.menuID !="" ){
                showDialog(itemID)
            }

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    companion object{
        fun start(context: Context?) {
            val intent = Intent(context, BookDetailActivity::class.java)
            context?.startActivity(intent)
        }
    }



}