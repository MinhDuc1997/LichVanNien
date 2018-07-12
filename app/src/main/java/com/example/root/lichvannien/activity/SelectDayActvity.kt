package com.example.root.lichvannien.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.DatePicker
import com.example.root.lichvannien.R
import com.example.root.lichvannien.modules.LunarCalendar
import com.example.root.lichvannien.modules.RandomOn
import kotlinx.android.synthetic.main.activity_select_day_actvity.*
import org.json.JSONObject
import java.util.*

class SelectDayActvity : AppCompatActivity() {

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    private var wd = 0
    private var d = 0
    private var m = 0
    private var y = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_day_actvity)
        setUI()
        setDate()

        bottom_navigation_select_day.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.day_item -> {
                    val intent = Intent(this, OneDayActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.month_item ->{
                    val intent = Intent(this, MonthActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.more_item ->{
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
        val cal = Calendar.getInstance()
        date_picker_top.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), ListenerDatePicker1())
        date_picker_down.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), ListenerDatePicker2())
        go_to_day.setOnClickListener{
            val result = "{'weekday': '$wd' ,'day': '$d', 'month': '$m', 'year': '$y'}"
            Log.d("result", result)
            val intent = Intent(this, OneDayActivity::class.java)
            intent.putExtra("DateSet", result)
            startActivity(intent)
        }
    }

    private fun setUI(){
        toolbar_select_day.showOverflowMenu()
        setSupportActionBar(toolbar_select_day)
        title = "Đổi ngày âm-dương"
        toolbar_select_day.setTitleTextColor(Color.WHITE)
        relativelayout_select_day.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])
    }

    private fun setDate(){
        val cal = Calendar.getInstance()
        wd = cal.get(Calendar.DAY_OF_WEEK)
        d = cal.get(Calendar.DAY_OF_MONTH)
        m = cal.get(Calendar.MONTH) + 1
        y = cal.get(Calendar.YEAR)
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation_select_day.selectedItemId = R.id.change_day_item
    }

    inner class ListenerDatePicker1: DatePicker.OnDateChangedListener {
        override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            Log.d("solar", "$year ${monthOfYear+1} $dayOfMonth")
            val newCal = Calendar.getInstance()
            newCal.set(year, monthOfYear, dayOfMonth)
            val dayOfWeek = newCal.get(Calendar.DAY_OF_WEEK)
            y = year; m = monthOfYear+1; d = dayOfMonth; wd = dayOfWeek; Log.d("wd", dayOfWeek.toString())
            val lunar = LunarCalendar().convertSolar2Lunar(dayOfMonth, monthOfYear+1, year, 7f)
            val jsonObj = JSONObject(lunar)
            date_picker_down.updateDate(jsonObj.getString("lunarYear").toInt(), jsonObj.getString("lunarMonth").toInt()-1, jsonObj.getString("lunarDay").toInt())
        }
    }

    inner class ListenerDatePicker2: DatePicker.OnDateChangedListener {
        override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            //Log.d("lunar", "$year $monthOfYear $dayOfMonth")
        }

    }
}
