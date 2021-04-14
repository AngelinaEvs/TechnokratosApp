package ru.itis.regme.presenter.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.calendar_fragment.*
import ru.itis.regme.App
import ru.itis.regme.R
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
    private lateinit var calendar : CustomCalendarView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        CalendarFragmentViewBinding.inflate(R.layout.calendar_fragment)
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.calendarComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
//        calendar = view.findViewById(R.id.calendarView)
//        calendar.setOnDayClickedListener { viewModel.onDayCLicked(position) }(viewModel::onDayClicked)

        initRecordsAdapter()
    }

    private fun initRecordsAdapter() {
        val list = ArrayList<RecordItem>()
        list.add(RecordItem("14:00", "Anna"))
        list.add(RecordItem("16:00", "Anna2"))
//        list.add(RecordItem("16:00", "Anna2"))
//        list.add(RecordItem("16:00", "Anna2"))
//        list.add(RecordItem("16:00", "Anna2"))
        recordsAdapter = RecordsAdapter(list)
        rv_records.adapter = recordsAdapter
    }

    private fun initListeners() {
        //viewModel.saveRecord()
    }

}