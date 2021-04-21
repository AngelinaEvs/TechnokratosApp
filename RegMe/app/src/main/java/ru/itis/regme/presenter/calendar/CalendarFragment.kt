package ru.itis.regme.presenter.calendar

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.my_custom_calendar_layout.view.*
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.CustomCalendarView
import ru.itis.regme.presenter.calendar.recordslist.RecordItem
import ru.itis.regme.presenter.calendar.recordslist.RecordsAdapter
import javax.inject.Inject

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    @Inject lateinit var viewModel: CalendarViewModel
    private lateinit var navController: NavController
    private lateinit var recordsAdapter: RecordsAdapter
    private lateinit var calendar: CustomCalendarView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        CalendarFragmentViewBinding.inflate(R.layout.calendar_fragment)
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.calendarComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
        initListeners()
        initSubscribes()
//        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        var viewCalendar = inflater.inflate(R.layout.my_custom_calendar_layout, this)
//        calendar = view.findViewById(R.id.calendarView)

//        calendar.setOnDayClickedListener {
//            viewModel.onDayClicked(position)
//        }
//        (viewModel::onDayClicked)

//        val addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
//        addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
//            Log.e("KKKKKKKKK", calendarView.currentMonth.text.split(" ")[1])
//            viewModel.onSaveClicked(calendarView.currentMonth.text.split(" ")[1], calendarView.currentMonth.text.split(" ")[0], addView.findViewById<EditText>(R.id.et_eventDate).text.toString(),
//                    addView.findViewById<EditText>(R.id.tv_eventTime).text.toString(),
//                    Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
//        }

    }

    private fun initSubscribes() {
        with (viewModel) {
            mainRecordsDay().observe(viewLifecycleOwner, {
                initRecordsAdapter(it)
            })
        }
    }

    private fun initRecordsAdapter(list: List<Pair<String, String>>) {
        recordsAdapter = RecordsAdapter(list)
        rv_records.adapter = recordsAdapter
    }

    private fun initListeners() {
        //TODO из CCV передавать сюда dateFocus
        viewModel.getRecordForDay("2021", "April", "2021-04-28")
//        plus.setOnClickListener {
//            lateinit var alertDialog: AlertDialog
//            var addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
//            var builder = AlertDialog.Builder(context)
//            builder.setCancelable(true)
//            var dateFocus = "2021-04-29"
//            addView.findViewById<EditText>(R.id.tv_eventTime).setText("14:00")
//            addView.findViewById<EditText>(R.id.et_eventDate).setText(dateFocus)
//            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
//                viewModel.onSaveClicked(calendarView.currentMonth?.text!!.split(" ")[1], calendarView.currentMonth?.text!!.split(" ")[0], addView.findViewById<EditText>(R.id.et_eventDate).text.toString(),
//                        addView.findViewById<EditText>(R.id.tv_eventTime).text.toString(),
//                        Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
//                //setUpCalendar()
//                alertDialog.dismiss()
//            }
//            builder.setView(addView)
//            alertDialog = builder.create()
//            alertDialog.show()
//        }
    }

}