package com.example.root.lichvannien.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.root.lichvannien.R
import com.example.root.lichvannien.R.id.month_item
import com.example.root.lichvannien.adapter.OneDayAdapter
import com.example.root.lichvannien.modules.LunarCalendar
import com.example.root.lichvannien.modules.ThoiGianConVat
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
    var d = 0
    var m = 0
    var y = 0
    lateinit var getArrayListDate: ArrayList<String>
    lateinit var result: String
    lateinit var thoiGianConVat: ThoiGianConVat
    lateinit var ngayConVat: String
    lateinit var thangconvat: String
    var fixMonthLunar = 0
    var indexFinded = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_day)

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        start.clear()
        end.clear()

        start.set(1970, 1, 1)
        end.set(2030,12,31)
        getArrayListDate = arrListDate(start, end)

        val wd = currentDate.get(Calendar.DAY_OF_WEEK)
        d = currentDate.get(Calendar.DAY_OF_MONTH)
        m = currentDate.get(Calendar.MONTH) + 1
        y = currentDate.get(Calendar.YEAR)
        result = "{'weekday': '$wd' ,'day': '$d', 'month': '$m', 'year': '$y'}"

        thoiGianConVat = ThoiGianConVat(null)
        ngayConVat = thoiGianConVat.getNgayConVat(d, m, y)

        indexFinded = getArrayListDate.indexOf(result)

        val pagerAdapter = OneDayAdapter(supportFragmentManager, getArrayListDate)
        viewpager.adapter = pagerAdapter
        viewpager.currentItem = indexFinded

        day_year_in_one_day.text = "$m-$y"

        var lunarDate = LunarCalendar().convertSolar2Lunar(d, m, y,7f)
        var jsonObject = JSONObject(lunarDate)
        d = jsonObject.getString("lunarDay").toInt()
        m = jsonObject.getString("lunarMonth").toInt()
        fixMonthLunar = m
        y = jsonObject.getString("lunarYear").toInt()


        thangconvat = thoiGianConVat.getThangConVat(m, y) //m, y lunar

        ngay_am_lich_in_one_day.text = "Ngày\n$d\n$ngayConVat"
        thang_am_lich_in_one_day.text = "Tháng\n$m\n$thangconvat"

        var lastPage = indexFinded
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                Log.d("onPageSelected", position.toString())
                jsonObject = JSONObject(getArrayListDate[position])
                d = jsonObject.getString("day").toInt()
                m = jsonObject.getString("month").toInt()
                y = jsonObject.getString("year").toInt()
                day_year_in_one_day.text = "$m-$y"
                ngayConVat = thoiGianConVat.getNgayConVat(d, m, y)
                lunarDate = LunarCalendar().convertSolar2Lunar(jsonObject.getString("day").toInt(),
                        jsonObject.getString("month").toInt(),
                        jsonObject.getString("year").toInt(),
                        7f)
                jsonObject = JSONObject(lunarDate)
                d = jsonObject.getString("lunarDay").toInt()
                m = jsonObject.getString("lunarMonth").toInt()
                y = jsonObject.getString("lunarYear").toInt()


                thangconvat = thoiGianConVat.getThangConVat(m, y) //m, y lunar

                ngay_am_lich_in_one_day.text = "Ngày\n$d\n$ngayConVat"
                thang_am_lich_in_one_day.text = "Tháng\n$m\n$thangconvat"
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
            indexFinded = getArrayListDate.indexOf(result)
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
        val m = calendar.get(Calendar.MONTH) + 1
        val y = calendar.get(Calendar.YEAR)
        return "{'weekday': '$wd' ,'day': '$d', 'month': '$m', 'year': '$y'}"
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

        override fun run() {
            timer.scheduleAtFixedRate(0, 2000){
                val currentDatee = Calendar.getInstance()
                val hourss = currentDatee.get(Calendar.HOUR_OF_DAY)
                val minutee= currentDatee.get(Calendar.MINUTE)

                val thoiGianConVatt = ThoiGianConVat(currentDatee.timeInMillis)
                val canhgio =  thoiGianConVatt.getCanhGio(m) //m lunar

                this@OneDayActivity.runOnUiThread {
                    if(minute<10)
                        time_now_in_one_day.text = "Giờ\n$hourss:$minutee\n$canhgio"
                    else
                        time_now_in_one_day.text = "Giờ\n$hourss:$minutee\n$canhgio"
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

    public override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        val dateSet = intent.getStringExtra("DateSet")

        if(dateSet != null) {
            Toast.makeText(this@OneDayActivity, dateSet, Toast.LENGTH_SHORT).show()
            indexFinded = getArrayListDate.indexOf(dateSet)
            viewpager.currentItem = indexFinded
        }
        super.onNewIntent(intent)
    }
}
