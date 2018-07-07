package com.example.root.lichvannien.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.root.lichvannien.R
import com.example.root.lichvannien.modules.RandomOn
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kotlinx.android.synthetic.main.activity_month.*
import java.util.*


class MonthActivity : AppCompatActivity() {

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    private val calendar = Calendar.getInstance()
    val d = calendar.get(Calendar.DAY_OF_MONTH)
    val m = calendar.get(Calendar.MONTH) + 1
    val y = calendar.get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        toolbar_month.showOverflowMenu()
        setSupportActionBar(toolbar_month)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_month.setNavigationIcon(R.drawable.ic_back)
        relativelayout_month.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])

        bottom_navigation_month.menu.findItem(R.id.detail_item).title = "Lịch ngày"

        bottom_navigation_month.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.detail_item -> {
                    val intent = Intent(this, OneDayActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }

        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        calendarView.setDateTextAppearance(R.style.DateTextAppearance)
        calendarView.setWeekDayTextAppearance(R.style.WeekDayTextAppearance)
        calendarView.addDecorators(SundayDecorator())
        calendarView.addDecorators(TodayDecorator())
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
            Log.d("day", day.toString())
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

            Log.d("day", "$d $m $y")
            val dd = calendar2.get(Calendar.DAY_OF_MONTH)
            val mm= calendar2.get(Calendar.MONTH) + 1
            val yy = calendar2.get(Calendar.YEAR)
            return dd == d && mm == m && yy == y
        }

        override fun decorate(view: DayViewFacade) {
            view.setBackgroundDrawable(resources.getDrawable(R.drawable.current_day))
        }
    }
}
