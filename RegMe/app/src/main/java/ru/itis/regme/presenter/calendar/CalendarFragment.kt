package ru.itis.regme.presenter.calendar

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import ru.itis.regme.presenter.calendar.customcalendar.MyGridAdapter
import ru.itis.regme.presenter.calendar.recordslist.RecordsAdapter
import java.util.*
import javax.inject.Inject

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    @Inject lateinit var viewModel: CalendarViewModel
    private lateinit var navController: NavController
    private var recordsAdapter: RecordsAdapter? = null
    private lateinit var calendar: CustomCalendarView
    private lateinit var dateFocus: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        calendar = view.findViewById(R.id.cal_test)
        return view
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
        initGetRecords()
    }

    private fun initSubscribes() {
        with (viewModel) {
            mainRecordsDay().observe(viewLifecycleOwner, {
                initRecordsAdapter(it)
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
        with (calendar) {
            viewModel.getInitRecords(currentDate.split(" ")[1], currentDate.split(" ")[0], object : FirebaseCallback {
                override fun onCallback(list: List<Pair<String, Int>>) {
                    if (myGridAdapter != null && !myGridAdapter!!.listEvents.isEmpty()) myGridAdapter?.updateData(list)
                    else {
                        myGridAdapter = MyGridAdapter(context, dates, calendar, list as MutableList<Pair<String, Int>>, simpleDayFormat.format(calendar.time).toInt(), simpleMonthNumberFormat.format(calendar.time).toInt())
                        daysGrid.adapter = myGridAdapter
                    }
                }

                override fun onCallbackForDay(list: List<Pair<String, String>>) {
                    TODO("Not yet implemented")
                }

                override fun onCallbackForLogin(status: Boolean) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

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
                var date = simpleEventDateFormat.format(dates[i])
                dateFocus = date
                var month = simpleMonthFormat.format(dates[i])
                var year = simpleYearFormat.format(dates[i])
                viewModel.getRecordForDay(year, month, date)
            }
        }
        plus.setOnClickListener {
            var addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
            var builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            addView.findViewById<EditText>(R.id.tv_eventTime).setText("14:00")
            addView.findViewById<EditText>(R.id.et_eventDate).setText(dateFocus)
            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
                viewModel.onSaveClicked(calendar.currentMonth?.text!!.split(" ")[1], calendar.currentMonth?.text!!.split(" ")[0], addView.findViewById<EditText>(R.id.et_eventDate).text.toString(),
                        addView.findViewById<EditText>(R.id.tv_eventTime).text.toString(),
                        Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
                viewModel.getRecordForDay(calendar.currentMonth?.text!!.split(" ")[1], calendar.currentMonth?.text!!.split(" ")[0], addView.findViewById<EditText>(R.id.et_eventDate).text.toString())
                calendar.setUpCalendar()
                calendar.alertDialog.dismiss()
            }
            builder.setView(addView)
            calendar.alertDialog = builder.create()
            calendar.alertDialog.show()
        }
    }

}