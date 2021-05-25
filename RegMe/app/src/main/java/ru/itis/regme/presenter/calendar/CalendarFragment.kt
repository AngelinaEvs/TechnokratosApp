package ru.itis.regme.presenter.calendar

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.my_custom_calendar_layout.view.*
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.presenter.calendar.customcalendar.CalendarCallback
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.CustomCalendarView
import ru.itis.regme.presenter.calendar.customcalendar.MyGridAdapter
import ru.itis.regme.presenter.calendar.recordslist.RecordsAdapter
import javax.inject.Inject
import android.content.pm.PackageManager as PackageManager

class CalendarFragment : Fragment() {

    @Inject
    lateinit var viewModel: CalendarViewModel
    private lateinit var navController: NavController
    private var recordsAdapter: RecordsAdapter? = null
    private lateinit var calendar: CustomCalendarView
    private lateinit var dateFocus: String
    private lateinit var month: String
    var nnn = arrayListOf<String>()

    private var cc = object : CalendarCallback {
        override fun next() {
            Log.e("NEXT", "SAAAAA")
            initGetRecords()
        }

        override fun prev() {
            Log.e("PREV", "FROM FRAGMENT")
            initGetRecords()
        }

        override fun setOnItemClickListener(year: String, monthCB: String, date: String) {
            dateFocus = date
            month = monthCB
            viewModel.getRecordForDay(year, monthCB, date)
        }

        override fun currentMonth(date: String, monthCB: String) {
            Log.e("HELLO", "HELLO CURRENT MONTH")

        }

        override fun currentDay(year: String, monthCB: String, date: String) {
            Log.e("CURRENT DAY", "DAY")
//            val d = date
            dateFocus = date
            month = monthCB
            viewModel.getRecordForDay(year, monthCB, date)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        calendar = view.findViewById(R.id.cal_test)
        calendar.calendarCallback = cc
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
        viewModel.findAllPhNumbers()
        wa.setOnClickListener {
            var mess = "Hi!!!"
            var num = "79677707814"
            if (num.startsWith("8")) num = "7" + num.substring(1)
            val isInstalled = isInstalled()
            if (isInstalled) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("https://api.whatsapp.com/send/?phone=" + 79677707814 + "&text=" + mess))// + "&app_absent=0"))
                startActivity(intent)
            } else Toast.makeText(requireContext(), "Whats app не установлен на этом устройстве", Toast.LENGTH_SHORT).show()
        }
    }

    fun isInstalled(): Boolean {
        val packageManager = requireContext().packageManager
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            return true
        } catch (notFound: PackageManager.NameNotFoundException) {
            return false
        }
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
            mainPnNumbersString().observe(viewLifecycleOwner, {
                nnn.clear()
                nnn.addAll(it)
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
        plus.setOnClickListener {
            val alertDialog: AlertDialog
            val addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)

            val autoCompleteTextView = addView.findViewById<AutoCompleteTextView>(R.id.cl)
            val ad = ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1, nnn)
            autoCompleteTextView.setAdapter(ad)

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
//                addView.findViewById<EditText>(R.id.tv_eventTime).setText(hour.toString() + ":" + minutes.toString())
                time = "$hour:$minutes"
            }
            builder.setView(addView)
            alertDialog = builder.create()
            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
                var cl = addView.findViewById<AutoCompleteTextView>(R.id.cl).text.toString()
                var name = cl.split("(")[0]
                var phone = cl.split("(")[1].split(")")[0]
                viewModel.onSaveClicked(y, month, dateFocus,
                        time, Client(name, phone))
                viewModel.getRecordForDay(y, calendar.currentMonth?.text!!.split(" ")[0], dateFocus)
                viewModel.getInitRecords(y, month)
                calendar.setUpCalendar()
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }

}