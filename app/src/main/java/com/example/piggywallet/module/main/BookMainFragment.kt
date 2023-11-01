package com.example.piggywallet.module.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piggywallet.R
import com.example.piggywallet.manager.db.datamodel.BookNote
import com.example.piggywallet.module.book.BookDetailActivity
import com.example.piggywallet.module.book.BookDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.btn_confirm
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.btn_del
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.edittext_amt
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.edittext_description
import kotlinx.android.synthetic.main.fragment_dialog_bottom_sheet.view.txt_menusID
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"

//    private var fab : FloatingActionButton? = null

/**
 * A simple [Fragment] subclass.
 * Use the [BookMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(BookMainViewModel::class.java)
    }

    private var selectIncome : Boolean = false
    private var selectOutcome: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_book, container, false)

        initInstance()

         view.fab_addItem.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
             BookDetailActivity.start(context)
        }

        view.card_income.setOnClickListener {
            if(selectIncome) {
                selectIncome = false
                bg_income.setBackgroundResource(R.drawable.bg_icon_circle_white)
                viewModel.allInOutMainBook.observe(viewLifecycleOwner , Observer {
                    it.let {
                        try {
                            book_recyclerView.layoutManager = LinearLayoutManager(context)
                            val adapter = book_recyclerView.adapter as BookMainAdapter
                            adapter.updateItems(it)
                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }
                })
                return@setOnClickListener
            }
            selectIncome = true
            selectOutcome= false
            bg_income.setBackgroundResource(R.drawable.bg_icon_circle_green)
            bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_white)

            viewModel.allIncomeMainBook.observe(viewLifecycleOwner , Observer {
                it.let {
                    try {
                        book_recyclerView.layoutManager = LinearLayoutManager(context)
                        val adapter = book_recyclerView.adapter as BookMainAdapter
                        adapter.updateItems(it)
//
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            })
        }

        view.card_outcome.setOnClickListener {
            if(selectOutcome) {
                selectOutcome = false
                bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_white)
                viewModel.allInOutMainBook.observe(viewLifecycleOwner , Observer {
                    it.let {
                        try {
                            updateDataList(it)
                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }
                })
                return@setOnClickListener
            }
            selectOutcome = true
            selectIncome = false

            bg_income.setBackgroundResource(R.drawable.bg_icon_circle_white)
            bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_green)

            viewModel.allOutcomeMainBook.observe(viewLifecycleOwner , Observer {
                it.let {
                    try {
                        updateDataList(it)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            })
        }

        return view
    }
    private fun initInstance() {


        viewModel.allInOutMainBook.observe(viewLifecycleOwner, Observer { inoutMain ->
            inoutMain?.let {
                try {
                    val list = ArrayList<BookNote>()
                    it.forEach {
                        val dataList = BookNote(
                            menuType = it.menuType,
                            menuName = it.menuName ,
                            menuID = it.menuID ,
                            des = it.des ,
                            date = it.date ,
                            total = it.total ,
                            seq = it.seq
                        )
                        list.add(dataList)
                    }
                    book_recyclerView.layoutManager = LinearLayoutManager(context)
                    book_recyclerView.adapter = BookMainAdapter(list)

                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })

        viewModel.inComeTotal.observe(viewLifecycleOwner , Observer {
            it.let {
                amt_income.text = it
            }
        })

        viewModel.outComeTotal.observe(viewLifecycleOwner , Observer {
            it.let {
                amt_outcome.text = it
            }
        })


    }

    private fun initViewModel(){

    }

    fun updateDataList( items : List<BookNote>){
        book_recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = book_recyclerView.adapter as BookMainAdapter
        adapter.updateItems(items)
    }

    fun showDialog( ){

//        val dialog = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.fragment_dialog_bottom_sheet, null)
//        view.txt_menusID.setText(itemID.menuName)
//        view.btn_del.setOnClickListener {
//            view.txt_menusID.setText("")
//            dialog.dismiss()
//        }
//        view.btn_confirm.setOnClickListener {
//            viewModel.saveData( itemID.menuID ,
//                itemID.menuName ,
//                view.edittext_amt.text.toString() ,
//                view.edittext_description.text.toString() ,
//                itemID.menuTYPE )
//            dialog.dismiss()
//        }
//        dialog.setCancelable(false)
//        dialog.setContentView(view)
//        dialog.show()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}