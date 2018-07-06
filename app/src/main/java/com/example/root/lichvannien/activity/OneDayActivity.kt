package com.example.root.lichvannien.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.root.lichvannien.R
import com.example.root.lichvannien.R.id.month_item
import com.example.root.lichvannien.adapter.OneDayAdapter
import com.example.root.lichvannien.modules.LunarCalendar
import kotlinx.android.synthetic.main.activity_month.*
import kotlinx.android.synthetic.main.activity_one_day.*
import kotlinx.android.synthetic.main.fragment_one_day.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.scheduleAtFixedRate


class OneDayActivity : AppCompatActivity() {
    var currentDate = Calendar.getInstance()
    lateinit var threadTime: ThreahTime

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_day)

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        start.clear()
        end.clear()

        start.set(1900, 1, 1)
        end.set(2030,12,31)
        val getArrayListDate = arrListDate(start, end)

        val wd = currentDate.get(Calendar.DAY_OF_WEEK)
        val d = currentDate.get(Calendar.DAY_OF_MONTH)
        val m = currentDate.get(Calendar.MONTH)
        val y = currentDate.get(Calendar.YEAR)
        val result = "{'weekday': '$wd' ,'day': '$d', 'month': '${m+1}', 'year': '$y'}"

        val indexFinded = getArrayListDate.indexOf(result)

        val pagerAdapter = OneDayAdapter(supportFragmentManager, getArrayListDate)
        viewpager.adapter = pagerAdapter
        viewpager.currentItem = indexFinded

        var lunarDate = LunarCalendar().convertSolar2Lunar(d, m+1, y,7f)
        var jsonObject = JSONObject(lunarDate)
        ngay_am_lich_in_one_day.text = "Ngày\n\n" + jsonObject.getString("lunarDay")
        thang_am_lich_in_one_day.text = "Tháng\n\n" + jsonObject.getString("lunarMonth")
        day_year_in_one_day.text = "${m+1}-$y"

        var lastPage = indexFinded
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                Log.d("onPageSelected", position.toString())
                jsonObject = JSONObject(getArrayListDate[position])

                day_year_in_one_day.text = "${jsonObject.getString("month").toInt()}-$y"
                lunarDate = LunarCalendar().convertSolar2Lunar(jsonObject.getString("day").toInt(),
                        jsonObject.getString("month").toInt(),
                        jsonObject.getString("year").toInt(),
                        7f)
                jsonObject = JSONObject(lunarDate)
                ngay_am_lich_in_one_day.text = "Ngày\n\n${jsonObject.getString("lunarDay")}"
                thang_am_lich_in_one_day.text = "Tháng\n\n${jsonObject.getString("lunarMonth")}"
                if(position > lastPage) {
                    //Log.d("aaaaaaaaaa", "left")
                }
                if(position < lastPage) {
                    //Log.d("aaaaaaaaaa", "right")
                }
                lastPage = position
            }

        })

        to_day_in_one_day.setOnClickListener{
            viewpager.setCurrentItem(indexFinded)
        }

        bottom_navigation_one_day.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.month_item -> {
                    val intent = Intent(this, MonthActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
    }

    private fun arrListDate(start: Calendar, end: Calendar): ArrayList<String>{
        val arList = ArrayList<String>()
        while (start <= end){
            arList.add(getDate(start))
            start.add(Calendar.DAY_OF_MONTH, 1)
        }
        return arList
    }

    private fun getDate(calendar: Calendar): String{
        val wd = calendar.get(Calendar.DAY_OF_WEEK)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val m = calendar.get(Calendar.MONTH)
        val y = calendar.get(Calendar.YEAR)
        return "{'weekday': '$wd' ,'day': '$d', 'month': '${m+1}', 'year': '$y'}"
    }

    inner class ThreahTime: Runnable{

        val thread = Thread(this)
        val timer = Timer()
        var hours = 0
        var minute = 0

        fun start(){
            thread.start()
        }

        fun cancel(){
            thread.join()
        }

        @SuppressLint("SetTextI18n")
        override fun run() {
            timer.scheduleAtFixedRate(0, 2000){
                currentDate = Calendar.getInstance()
                hours = currentDate.get(Calendar.HOUR_OF_DAY)
                minute = currentDate.get(Calendar.MINUTE)
                this@OneDayActivity.runOnUiThread {
                    if(minute<10)
                        time_now_in_one_day.text = "Giờ\n\n$hours:0$minute"
                    else
                        time_now_in_one_day.text = "Giờ\n\n$hours:$minute"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation_one_day.selectedItemId = R.id.detail_item
        threadTime = ThreahTime()
        threadTime.start()
    }

    override fun onPause() {
        super.onPause()
        threadTime.cancel()
    }
}
