package ru.itis.regme.presenter.calendar.customcalendar

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.itis.regme.R
import ru.itis.regme.data.AppRepository
import java.util.*
import kotlin.collections.ArrayList

class MyGridAdapter(
    context: Context,
    var dates: List<Date>,
    var currentDate: Calendar,
//    var listEvents: List<Events>
    var listEvents: List<String>
) : ArrayAdapter<Date>(context, R.layout.single_item, dates) {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    var appRepository = AppRepository(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var monthDate = dates[position]
        var dateCalendar = Calendar.getInstance()
        dateCalendar.time = monthDate
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        var displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        var displayYear = dateCalendar.get(Calendar.YEAR)
        var currentMonth = currentDate.get(Calendar.MONTH) + 1
        var currentYear = currentDate.get(Calendar.YEAR)
        var view = convertView
        if (convertView == null) view = inflater.inflate(R.layout.single_item, parent, false)
        if (!(displayMonth == currentMonth && displayYear == currentYear)) //view?.setBackgroundColor(Color.WHITE)
        /*else*/ view?.setBackgroundColor(Color.GRAY)
        var tv = view?.findViewById<TextView>(R.id.day_item)
        tv?.text = dayNo.toString()

        var eventNumber = view?.findViewById<TextView>(R.id.event_id)
//        var eventCalendar = Calendar.getInstance()
        var arrayList = ArrayList<String>()


//        for (i in listEvents.indices) {
////            eventCalendar.time = convertStringToDate(listEvents[i].day)//date
//            // view?.setBackgroundColor(Color.DKGRAY)
////            Log.e("dayNo", dayNo.toString())
////            Log.e("eventCal: ", listEvents[i].day.split("-")[2])
////            Log.e("displayMonth", displayMonth.toString())
////            Log.e("eventCal.Cal.MONTH + 2",(eventCalendar.get(Calendar.MONTH) + 2/*1*/).toString())
////            Log.e("displayYear: ",displayYear.toString())
////            Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
////
////            Log.e("dayNo", dayNo.toString())
////            Log.e("eventCal: ", listEvents[i].day.split("-")[2])
////            Log.e("displayMonth", displayMonth.toString())
////            Log.e("currentMonth", currentMonth.toString())
////
////            Log.e("eventCal.Cal.MONTH + 1",(eventCalendar.get(Calendar.MONTH) + 1/*1*/).toString())
////            Log.e("displayYear: ",displayYear.toString())
////            Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
////
////            Log.e("EVENTS", listEvents[i].toString())
//
//            if (dayNo == listEvents[i].day.split("-")[2].toInt()/*eventCalendar.get(Calendar.DAY_OF_MONTH)*/ && displayMonth == listEvents[i].day.split("-")[1].toInt()/*eventCalendar.get(Calendar.MONTH) + 1*/
//                    && displayYear == listEvents[i].year.toInt()/*eventCalendar.get(Calendar.YEAR)*/) {
////                Log.e("dayNo", dayNo.toString())
////                Log.e("eventCal: ", listEvents[i].day.split("-")[2])
////                Log.e("displayMonth", displayMonth.toString())
////                Log.e("eventCal.Cal.MONTH + 2",(eventCalendar.get(Calendar.MONTH) + 1/*1*/).toString())
////                Log.e("displayYear: ",displayYear.toString())
////                Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
//                arrayList.add(listEvents[i].day)
//                if (arrayList.size == 1) view?.setBackgroundColor(Color.CYAN)
//                if (arrayList.size > 1) view?.setBackgroundColor(Color.RED)
//                eventNumber?.text = arrayList.size.toString() + "Events"
//            }
//        }

//        var day = listEvents[0].split("-")[2].toInt()
//        var mon = listEvents[0].split("-")[1].toInt()
//        var year = listEvents[0].split("-")[0].toInt()
        for (i in listEvents.indices) {
            var day = listEvents[i].split("-")[2].toInt()
            var mon = listEvents[i].split("-")[1].toInt()
            var year = listEvents[i].split("-")[0].toInt()
//            eventCalendar.time = convertStringToDate(listEvents[i].day)//date
           // view?.setBackgroundColor(Color.DKGRAY)
//            Log.e("dayNo", dayNo.toString())
//            Log.e("eventCal: ", listEvents[i].day.split("-")[2])
//            Log.e("displayMonth", displayMonth.toString())
//            Log.e("eventCal.Cal.MONTH + 2",(eventCalendar.get(Calendar.MONTH) + 2/*1*/).toString())
//            Log.e("displayYear: ",displayYear.toString())
//            Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
//
//            Log.e("dayNo", dayNo.toString())
//            Log.e("eventCal: ", listEvents[i].day.split("-")[2])
//            Log.e("displayMonth", displayMonth.toString())
//            Log.e("currentMonth", currentMonth.toString())
//
//            Log.e("eventCal.Cal.MONTH + 1",(eventCalendar.get(Calendar.MONTH) + 1/*1*/).toString())
//            Log.e("displayYear: ",displayYear.toString())
//            Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
//
//            Log.e("EVENTS", listEvents[i].toString())

            if (dayNo == day/*eventCalendar.get(Calendar.DAY_OF_MONTH)*/ && displayMonth == mon/*eventCalendar.get(Calendar.MONTH) + 1*/
                    && displayYear == year/*eventCalendar.get(Calendar.YEAR)*/) {
//                Log.e("dayNo", dayNo.toString())
//                Log.e("eventCal: ", listEvents[i].day.split("-")[2])
//                Log.e("displayMonth", displayMonth.toString())
//                Log.e("eventCal.Cal.MONTH + 2",(eventCalendar.get(Calendar.MONTH) + 1/*1*/).toString())
//                Log.e("displayYear: ",displayYear.toString())
//                Log.e("displayYearMy: ",(eventCalendar.get(Calendar.YEAR)).toString())
                arrayList.add(listEvents[i])
                if (arrayList.size == 1) view?.setBackgroundColor(Color.CYAN)
                if (arrayList.size > 1) view?.setBackgroundColor(Color.RED)
                //eventNumber?.text = arrayList.size.toString() + "Events"
            }
        }
        return view!!
    }

//    private fun convertStringToDate(eventDate: String): Date? {
//        var format = SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH)
//        var date = null
//        try {
//            var date = format.parse(eventDate)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return date
//    }

    override fun getCount(): Int = dates.size

    override fun getPosition(item: Date?): Int = dates.indexOf(item)

    override fun getItem(position: Int): Date = dates[position]

}
