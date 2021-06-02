package ru.itis.regme.presenter.calendar.customcalendar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.itis.regme.R
import java.util.*

class MyGridAdapter(
    context: Context,
    var dates: List<Date>,
    var currentDate: Calendar,
    var listEvents: MutableList<Pair<String, Int>>,
    var today: Int,
    var todayMonth: Int
) : ArrayAdapter<Date>(context, R.layout.single_item, dates) {
    var inflater: LayoutInflater = LayoutInflater.from(context)
    var pointer: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val monthDate = dates[position]
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = monthDate
        val dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        val displayYear = dateCalendar.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1
        val currentYear = currentDate.get(Calendar.YEAR)
        var view = convertView
        if (convertView == null) view = inflater.inflate(R.layout.single_item, parent, false)
        val tv = view?.findViewById<TextView>(R.id.day_item)
        tv?.text = dayNo.toString()
        if (!(displayMonth == currentMonth && displayYear == currentYear)) view?.setBackgroundResource(
            R.drawable.back_grey_item_calendar
        )
        else {
            if (pointer < listEvents.size) {
                val day = listEvents[pointer].first.split("-")[2].toInt()
                val mon = listEvents[pointer].first.split("-")[1].toInt()
                if (dayNo == day && displayMonth == mon) {
                    if (listEvents[pointer].second == -1) {
                        if (day == today && mon == todayMonth) view?.setBackgroundResource(R.drawable.back_today_green)
                        else view?.setBackgroundResource(R.drawable.back_green_item_calendar)
                        pointer++
                    } else if (listEvents[pointer].second < 3) {
                        if (day == today && mon == todayMonth) view?.setBackgroundResource(R.drawable.back_lite_item_today)
                        else view?.setBackgroundResource(R.drawable.back_lite_item)
                        pointer++
                    } else if (listEvents[pointer].second < 5) {
                        if (day == today && mon == todayMonth) view?.setBackgroundResource(R.drawable.back_today_middle)
                        else view?.setBackgroundResource(R.drawable.back_item_calendar)
                        pointer++
                    } else if (listEvents[pointer].second > 4) {
                        if (day == today && mon == todayMonth) view?.setBackgroundResource(R.drawable.back_item_hard_today)
                        else view?.setBackgroundResource(R.drawable.back_item_hard)
                        pointer++
                    }
                } else {
                    if (dayNo == today && currentMonth == todayMonth) view?.setBackgroundResource(R.drawable.back_empty_today)
                    else view?.setBackgroundResource(R.drawable.back_empty_item_calendar)
                }
            } else {
                if (!(displayMonth == currentMonth && displayYear == currentYear)) view?.setBackgroundResource(
                    R.drawable.back_grey_item_calendar
                )
                else {
                    if (dayNo == today && currentMonth == todayMonth) view?.setBackgroundResource(R.drawable.back_empty_today)
                    else view?.setBackgroundResource(R.drawable.back_empty_item_calendar)
                }
            }
        }
        return view!!
    }

    override fun getCount(): Int = dates.size

    override fun getPosition(item: Date?): Int = dates.indexOf(item)

    override fun getItem(position: Int): Date = dates[position]

    fun updateData(list: List<Pair<String, Int>>): Unit {
        listEvents.clear()
        listEvents.addAll(list)
        pointer = 0
        notifyDataSetChanged()
    }

}
