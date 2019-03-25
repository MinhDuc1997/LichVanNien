package com.example.root.lichvannien.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.root.lichvannien.R
import com.example.root.lichvannien.model.DrawLableForDate
import com.example.root.lichvannien.model.LunarCalendar
import com.example.root.lichvannien.model.RandomOn
import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.activity_month.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap


@Suppress("DEPRECATION")
class MonthActivity : AppCompatActivity() {

    private val start = Calendar.getInstance()
    private val end = Calendar.getInstance()

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    private val seted = HashMap<String, String>()

    private val calendar = Calendar.getInstance()
    val d = calendar.get(Calendar.DAY_OF_MONTH)
    val m = calendar.get(Calendar.MONTH)
    val y = calendar.get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        setUI()

        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        start.set(y, 0, 1)
        end.set(y, 11, 31)
        setLunar(start, end)
        seted[y.toString()] = ""

        calendarView.setOnDateChangedListener(DateSelectedListener())
        calendarView.setOnMonthChangedListener(MonthChangeListener())

        bottom_navigation_month.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.day_item -> {
                    val intent = Intent(this, OneDayActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.change_day_item -> {
                    val intent = Intent(this, SelectDayActvity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.more_item ->{
//                    val intent = Intent(this, MoreActivity::class.java)
//                    startActivity(intent)
//                    return@setOnNavigationItemSelectedListener true
//                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
    }

    private fun setUI() {
        toolbar_month.showOverflowMenu()
        setSupportActionBar(toolbar_month)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_month.setNavigationIcon(R.drawable.ic_back)
        relativelayout_month.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])

        val maxDate = Calendar.getInstance()
        maxDate.set(2070, 12, 31)
        val minDate = Calendar.getInstance()
        minDate.set(1930, 1, 1)

        calendarView.state().edit()
                .setMinimumDate(minDate)
                .setMaximumDate(maxDate)
                .commit()

        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        calendarView.setDateTextAppearance(R.style.DateTextAppearance)
        calendarView.setWeekDayTextAppearance(R.style.WeekDayTextAppearance)
        calendarView.addDecorators(SundayDecorator())
        calendarView.addDecorators(TodayDecorator())
    }

    private fun setLunar(start: Calendar, end: Calendar) {

        while (start <= end) {
            val calendarDay = CalendarDay(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH))
            val lunarDate = LunarCalendar().convertSolar2Lunar(start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH) + 1, start.get(Calendar.YEAR), 7f)
            val jsonObject = JSONObject(lunarDate)
            calendarView.addDecorator(LunarDecorator(calendarDay, jsonObject.getString("lunarDay").toInt(), jsonObject.getString("lunarMonth").toInt()))
            start.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.month_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation_month.selectedItemId = R.id.month_item
    }

    inner class SundayDecorator : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar)
            val sunDay = calendar.get(Calendar.DAY_OF_WEEK)
            return sunDay == Calendar.SUNDAY
        }

        override fun decorate(view: DayViewFacade) {
            view.setBackgroundDrawable(resources.getDrawable(R.drawable.sunday))
        }
    }

    inner class TodayDecorator : DayViewDecorator {

        val calendar2 = Calendar.getInstance()

        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar2)
            val dd = calendar2.get(Calendar.DAY_OF_MONTH)
            val mm = calendar2.get(Calendar.MONTH) + 1
            val yy = calendar2.get(Calendar.YEAR)
            return dd == d && mm == m + 1 && yy == y
        }

        override fun decorate(view: DayViewFacade) {
            view.setBackgroundDrawable(resources.getDrawable(R.drawable.current_day))
        }
    }

    inner class LunarDecorator(val dates: CalendarDay, val lunarDay: Int, val lunarMonth: Int) : DayViewDecorator {

        val calendar3 = Calendar.getInstance()

        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar3)
            return dates == day
        }

        override fun decorate(view: DayViewFacade) {
            if (lunarDay > 9)
                view.addSpan(DrawLableForDate(0f, Color.WHITE, "$lunarDay/$lunarMonth"))
            else
                view.addSpan(DrawLableForDate(0f, Color.WHITE, "0$lunarDay/$lunarMonth"))
        }
    }

    inner class DateSelectedListener : OnDateSelectedListener {

        override fun onDateSelected(p0: MaterialCalendarView, p1: CalendarDay, p2: Boolean) {
            val cal = p1.calendar
            val wd = cal.get(Calendar.DAY_OF_WEEK)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val m = cal.get(Calendar.MONTH) + 1
            val y = cal.get(Calendar.YEAR)
            val result = "{'weekday': '$wd' ,'day': '$d', 'month': '$m', 'year': '$y'}"
            val intent = Intent(this@MonthActivity, OneDayActivity::class.java)
            intent.putExtra("DateSet", result)
            startActivity(intent)
        }
    }

    inner class MonthChangeListener : OnMonthChangedListener {

        override fun onMonthChanged(p0: MaterialCalendarView?, p1: CalendarDay) {
            val year = p1.year
            val month = p1.month
            val day = p1.day

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            val setKey = "$year"

            if (!seted.containsKey(setKey) && year != y) {
                start.set(year, 0, 1)
                end.set(year, 11, 31)
                setLunar(start, end)
                seted[setKey] = ""
            }
        }
    }

//    inner class ThreahCal: Runnable{
//
//        val Thread = Thread(this)
//
//        override fun run() {
//            start.add(Calendar.MONTH, -2)
//            end.add(Calendar.MONTH, 2)
//            this@MonthActivity.runOnUiThread{
//                Toast.makeText(this@MonthActivity, "dsd", Toast.LENGTH_SHORT).show()
//                //setLunar(start, end)
//            }
//        }
//    }
}
