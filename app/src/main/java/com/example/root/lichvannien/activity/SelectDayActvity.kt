package com.example.root.lichvannien.activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.root.lichvannien.R
import com.example.root.lichvannien.modules.RandomOn
import kotlinx.android.synthetic.main.activity_month.*
import kotlinx.android.synthetic.main.activity_select_day_actvity.*

class SelectDayActvity : AppCompatActivity() {

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_day_actvity)
        setUI()

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
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
    }

    fun setUI(){
        toolbar_select_day.showOverflowMenu()
        setSupportActionBar(toolbar_select_day)
        title = "Đổi ngày âm-dương"
        toolbar_select_day.setTitleTextColor(Color.WHITE)
        relativelayout_select_day.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation_select_day.selectedItemId = R.id.change_day_item
    }
}
