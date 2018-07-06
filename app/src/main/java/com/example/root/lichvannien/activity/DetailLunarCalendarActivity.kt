package com.example.root.lichvannien.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.root.lichvannien.R
import com.example.root.lichvannien.modules.RandomOn
import kotlinx.android.synthetic.main.activity_detail_lunar_calendar.*
import org.json.JSONObject

class DetailLunarCalendarActivity : AppCompatActivity() {

    val arrayWeekDay = mapOf("2" to "Thứ hai",
            "3" to "Thứ ba", "4" to "Thứ tư",
            "5" to "Thứ năm", "6" to "Thứ sáu",
            "7" to "Thứ Bảy", "1" to "Chủ Nhật")

    val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lunar_calendar)
        toolbar_one_day
                .showOverflowMenu()
        setSupportActionBar(toolbar_one_day)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_one_day.setNavigationIcon(R.drawable.ic_back)
        relativelayout_detail_lunar.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])

        val data = intent.getStringExtra("data")
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()

        val jsonObject = JSONObject(data)
        var wd = jsonObject.getString("weekday")
        val d = jsonObject.getString("day")
        val m = jsonObject.getString("month")
        val y = jsonObject.getString("year")
        for (i in arrayWeekDay){
            if(i.key == wd)
                wd = i.value
        }
        toolbar_title.text = "${wd}, $d/$m/$y"
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
