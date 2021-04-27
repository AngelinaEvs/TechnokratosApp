package ru.itis.regme.presenter.calendar

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.my_custom_calendar_layout.view.*
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.CustomCalendarView
import ru.itis.regme.presenter.calendar.customcalendar.MyGridAdapter
import ru.itis.regme.presenter.calendar.recordslist.RecordsAdapter
import java.util.*
import javax.inject.Inject

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    @Inject
    lateinit var viewModel: CalendarViewModel
    private lateinit var navController: NavController
    private var recordsAdapter: RecordsAdapter? = null
    private lateinit var calendar: CustomCalendarView
    private lateinit var dateFocus: String
    private lateinit var month: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        calendar = view.findViewById(R.id.cal_test)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.calendarComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
        initListeners()
        initSubscribes()
        initGetRecords()
        initToday()
    }

    private fun initToday() {
        with(calendar) {
            val d = simpleEventDateFormat.format(calendar.time)
            dateFocus = d
            month = simpleMonthFormat.format(calendar.time)
            viewModel.getRecordForDay(d.split("-")[0], simpleMonthFormat.format(calendar.time).toString(), d)
        }
    }

    private fun initSubscribes() {
        with(viewModel) {
            mainRecordsDay().observe(viewLifecycleOwner, {
                initRecordsAdapter(it)
            })
            mainRecordsCalendar().observe(viewLifecycleOwner, {
                with(calendar) {
                    if (myGridAdapter != null && !myGridAdapter!!.listEvents.isEmpty()) myGridAdapter?.updateData(it)
                    else {
                        myGridAdapter = MyGridAdapter(context, dates, calendar, it as MutableList<Pair<String, Int>>, simpleDayFormat.format(calendar.time).toInt(), simpleMonthNumberFormat.format(calendar.time).toInt())
                        daysGrid.adapter = myGridAdapter
                    }
                }
            })
        }
    }

    private fun initRecordsAdapter(list: List<Pair<String, String>>) {
        if (recordsAdapter != null) recordsAdapter?.updateData(list)
        else {
            recordsAdapter = RecordsAdapter(list as MutableList<Pair<String, String>>)
            view?.findViewById<RecyclerView>(R.id.rv_records)?.adapter = recordsAdapter
        }
    }

    private fun initGetRecords() {
        with(calendar) {
            viewModel.getInitRecords(currentDate.split(" ")[1], currentDate.split(" ")[0])
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initListeners() {
        with(calendar) {
            previousButton.setOnClickListener {
                calendar.add(Calendar.MONTH, -1)
                setUpCalendar()
                initGetRecords()
            }
            nextButton.setOnClickListener {
                calendar.add(Calendar.MONTH, 1)
                setUpCalendar()
                initGetRecords()
            }
            daysGrid?.setOnItemClickListener { adapterView, view, i, l ->
                val date = simpleEventDateFormat.format(dates[i])
                month = simpleMonthFormat.format(dates[i])
                dateFocus = date
                viewModel.getRecordForDay(date.split("-")[0], month, date)
            }
        }
        plus.setOnClickListener {
            val addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            val timePicker = addView.findViewById<TimePicker>(R.id.tp)
            timePicker.setIs24HourView(true)
            val info = dateFocus.split("-")
            val y = info[0]
            val m = info[1]
            val d = info[2]
            addView.findViewById<TextView>(R.id.info_record).text = "Запись на $d.$m.$y:"
            var time = timePicker.hour.toString() + ":" + timePicker.minute
            timePicker.setOnTimeChangedListener { _, hour, minutes ->
                addView.findViewById<EditText>(R.id.tv_eventTime).setText(hour.toString() + ":" + minutes.toString())
                time = "$hour:$minutes"
            }
            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
                viewModel.onSaveClicked(y, month, dateFocus,
                        time, Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
                viewModel.getRecordForDay(y, calendar.currentMonth?.text!!.split(" ")[0], dateFocus)
                viewModel.getInitRecords(y, month)
                calendar.setUpCalendar()
                calendar.alertDialog.dismiss()
            }
            builder.setView(addView)
            calendar.alertDialog = builder.create()
            calendar.alertDialog.show()
        }
    }

}