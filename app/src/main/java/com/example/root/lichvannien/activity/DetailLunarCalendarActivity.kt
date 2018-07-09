package com.example.root.lichvannien.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.root.lichvannien.R
import com.example.root.lichvannien.adapter.DetailLunarAdapter
import com.example.root.lichvannien.modules.LunarCalendar
import com.example.root.lichvannien.modules.RandomOn
import com.example.root.lichvannien.modules.ThoiGianConVat
import kotlinx.android.synthetic.main.activity_detail_lunar_calendar.*
import org.json.JSONObject
import java.util.*

class DetailLunarCalendarActivity : AppCompatActivity() {

    private val arrayWeekDay = mapOf("2" to "Thứ hai",
            "3" to "Thứ ba", "4" to "Thứ tư",
            "5" to "Thứ năm", "6" to "Thứ sáu",
            "7" to "Thứ Bảy", "1" to "Chủ Nhật")

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    private lateinit var arrHoangDao: ArrayList<String>

    private val calendar = Calendar.getInstance()
    private lateinit var jsonObject: JSONObject
    private lateinit var wd: String
    private var d = 0
    private var m = 0
    private var y = 0
    private var lunarDay = 0
    private var lunarMonth = 0
    private var lunarYear = 0
    private lateinit var gio: String
    private lateinit var thang: String
    private lateinit var nam: String
    private lateinit var ngay: String
    private lateinit var ngayHoangDao: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lunar_calendar)
        setUI()
        val data = intent.getStringExtra("data")
        setDate(data)
        setLunar()
        updateUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        val thongTinChung = "Ngay am: Ngay $lunarDay, thang $lunarMonth, nam $lunarYear\n" +
                "Ngay: $ngayHoangDao\n" +
                "Gio $gio, ngay $ngay, thang $thang, nam $nam"

        toolbar_title.text = "$wd, $d/$m/$y"
        val arrDatas = arrayListOf(thongTinChung, gioHoangDao(ngay))
        val arrLables = arrayListOf("Giờ hoàng đạo", "Tiết khí", "Trực ngày", "Việc nên làm", "Việc không nên làm", "Thông tin ngày theo Nhị Thập Bát Tú")
        list_view_lunar.adapter = DetailLunarAdapter(arrLables, arrDatas, R.layout.detail_list_view_lunar, this)
    }

    private fun setUI(){
        toolbar_one_day
                .showOverflowMenu()
        setSupportActionBar(toolbar_one_day)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_one_day.setNavigationIcon(R.drawable.ic_back)
        relativelayout_detail_lunar.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])
        list_view_lunar.top = toolbar_one_day.height
    }

    private fun setDate(data: String){
        jsonObject = JSONObject(data)
        wd = jsonObject.getString("weekday")
        d = jsonObject.getString("day").toInt()
        m = jsonObject.getString("month").toInt()
        y = jsonObject.getString("year").toInt()
        for (i in arrayWeekDay){
            if(i.key == wd)
                wd = i.value
        }
    }

    private fun setLunar(){
        val lunar = LunarCalendar().convertSolar2Lunar(d, m, y, 7f)
        jsonObject = JSONObject(lunar)
        lunarDay = jsonObject.getString("lunarDay").toInt()
        lunarMonth = jsonObject.getString("lunarMonth").toInt()
        lunarYear = jsonObject.getString("lunarYear").toInt()
        val thoiGianConVat = ThoiGianConVat(calendar.timeInMillis)
        gio = thoiGianConVat.getCanhGio(lunarMonth)
        ngay = thoiGianConVat.getNgayConVat(d, m, y)
        thang = thoiGianConVat.getThangConVat(lunarMonth, lunarYear)
        nam = thoiGianConVat.getNamConVat()
        ngayHoangDao = ngayHoangDao(lunarMonth, ngay)
    }

    private fun ngayHoangDao(month: Int, day: String): String{
        when(month){
            1, 7 -> arrHoangDao = arrayListOf("Tý", "Sửu", "Tỵ", "Mùi")
            2, 8 -> arrHoangDao = arrayListOf("Dần", "Mão", "Mùi", "Dậu")
            3, 9 -> arrHoangDao = arrayListOf("Thìn", "Tỵ", "Dậu", "Hợi")
            4, 10 -> arrHoangDao = arrayListOf("Ngọ", "Mùi", "Sửu", "Dậu")
            5, 11 -> arrHoangDao = arrayListOf("Thân", "Dậu", "Sửu", "Mão")
            6, 12 -> arrHoangDao = arrayListOf("Tuất", "Hợi", "Mão", "Tị")
        }
        val dateSub = day.substring(day.indexOf(" ")+1, day.length)
        for(i in arrHoangDao){
            if(i.compareTo(dateSub) == 0) {
                Log.d("dateSub", dateSub)
                return "Hoàng đạo"
            }
        }

        return "Hắc đạo"
    }

    private fun gioHoangDao(day: String): String{
        val dateSub = day.substring(day.indexOf(" ")+1, day.length)
        when(dateSub){
            "Tý" -> return "Tý, Sửu, Mão, Ngọ, Thân, Dậu"
            "Sửu" -> return "Dần, Mão, Tỵ, Thân, Tuất, Hợi"
            "Dần" -> return "Tý, Sửu, Thìn, Tỵ, Mùi, Tuất"
            "Mão" -> return "Tý, Dần, Mão, Ngọ, Mùi, Dậu"
            "Thìn" -> return "Dần, Thìn, Tỵ, Thân, Dậu, Hợi"
            "Tị" -> return "Sửu, Thìn, Ngọ, Mùi, Tuất, Hợi"
            "Ngọ" -> return "Tý, Sửu, mão, Ngọ, Thân, Dậu"
            "Mùi" -> return "Dần, Mão, Tỵ, Thân, Tuất, Hợi"
            "Thân" -> return "Tý, Sửu, Thìn, Tỵ, Mùi, Tuất"
            "Dậu" -> return "Tý, Dần, Mão, Ngọ, Mùi, Dậu"
            "Tuất" -> return "Dần, Thìn, Tỵ, Thân, Dậu, Hợi"
            "Hợi" -> return "Sửu, Thìn, Ngọ, Mùi, Tuất, Hợi"
            else -> return ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lunar_toolbar_menu, menu)
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

}
