package com.example.piggywallet.module.main


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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

    private var income_cal: Int? = null
    private var outcome_cal: Int? = null
    private lateinit var itemInAndOutcome : ArrayList<BookNote>

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(BookMainViewModel::class.java)
    }

    private var selectIncome : Boolean = false
    private var selectOutcome: Boolean = false
    private var filterCalendar : Boolean = false

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
        initViewModel()

         view.fab_addItem.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
             BookDetailActivity.start(context)
        }

        view.card_income.setOnClickListener {
            selectMenuInAndOut("INCOME")
        }

        view.card_outcome.setOnClickListener {
            selectMenuInAndOut("OUTCOME")
        }

        view.swipe_refresh.setOnRefreshListener {
            view.swipe_refresh.isRefreshing = false
            allInOutMainBookUpdate()
        }

        view.card_calendar.setOnClickListener {
            if(filterCalendar) {
                filterCalendar = false
                card_calendar.setBackgroundResource(R.drawable.bg_icon_circle_white)
                allInOutMainBookUpdate()
                return@setOnClickListener
            }
            filterCalendar = true
            card_calendar.setBackgroundResource(R.drawable.bg_icon_circle_green)
            bg_income.setBackgroundResource(R.drawable.bg_icon_circle_white)
            bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_white)
            showDialog()
        }

        view.edit_text_search.addTextChangedListener(textWatcher)

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
                    list.sortBy { it.date }
                    itemInAndOutcome = list

                    book_recyclerView.layoutManager = LinearLayoutManager(context)
                    book_recyclerView.adapter = BookMainAdapter(list)

                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })


        viewModel.inComeTotal.observe(viewLifecycleOwner , Observer {
            it.let {
                income_cal = it.toInt()
                amt_income.text = it
            }
        })

        viewModel.outComeTotal.observe(viewLifecycleOwner , Observer {
            it.let {
                outcome_cal = it.toInt()
                val sb = StringBuilder()
                amt_outcome.text = sb.append("-").append(it)
                if(income_cal != null && outcome_cal != null)
                 total_amt.text = ( income_cal!!.minus(outcome_cal!!)  ).toString()
            }
        })


    }

    private fun initViewModel(){

        viewModel.whenDataLoadedInOutMainBookByDate.observe(viewLifecycleOwner , Observer {
          it.let {
              updateDataList(it)
          }
        })

        viewModel.whenDataLoadedBook.observe(viewLifecycleOwner , Observer {
            it.let {
                updateDataList(it)
            }
        })


    }

    fun selectMenuInAndOut( type : String){
        if( type == "OUTCOME"){
            if(selectOutcome) {
                selectOutcome = false
                bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_white)
                allInOutMainBookUpdate()
                return
            }
            selectOutcome = true
            selectIncome = false

            bg_income.setBackgroundResource(R.drawable.bg_icon_circle_white)
            bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_green)
            card_calendar.setBackgroundResource(R.drawable.bg_icon_circle_white)

            viewModel.allOutcomeMainBook.observe(viewLifecycleOwner , Observer {
                it.let {
                    try {
                        updateDataList(it)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            })

        }else if( type == "INCOME"){
            if(selectIncome) {
                selectIncome = false
                bg_income.setBackgroundResource(R.drawable.bg_icon_circle_white)

                return
            }
            selectIncome = true
            selectOutcome= false
            bg_income.setBackgroundResource(R.drawable.bg_icon_circle_green)
            bg_outcome.setBackgroundResource(R.drawable.bg_icon_circle_white)
            card_calendar.setBackgroundResource(R.drawable.bg_icon_circle_white)

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

    }

    fun allInOutMainBookUpdate(){
        viewModel.allInOutMainBook.observe(viewLifecycleOwner , Observer {
            it.let {
                try {
                    updateDataList(it)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })
    }

    fun updateDataList( items : List<BookNote>){
        book_recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = book_recyclerView.adapter as BookMainAdapter
        adapter.updateItems(items)
    }

    fun filterCalendar( date : String){


        viewModel.InOutOnlyByDate(date ,itemInAndOutcome )
    }

    fun showDialog( ){

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
//            textView.text = sdf.format(cal.time)
            val date =  sdf.format(cal.time)
            filterCalendar(date)

        }

//        textView.setOnClickListener {
        context?.let {
            DatePickerDialog(
                it, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
//        }

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.getSearchBook(s.toString() , itemInAndOutcome)
        }

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