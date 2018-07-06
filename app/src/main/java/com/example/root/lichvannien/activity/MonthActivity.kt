package com.example.root.lichvannien.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import com.example.root.lichvannien.R
import com.example.root.lichvannien.modules.RandomOn
import kotlinx.android.synthetic.main.activity_detail_lunar_calendar.*
import kotlinx.android.synthetic.main.activity_month.*
import kotlinx.android.synthetic.main.activity_one_day.*
import java.util.*

class MonthActivity : AppCompatActivity() {

    val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

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
}
