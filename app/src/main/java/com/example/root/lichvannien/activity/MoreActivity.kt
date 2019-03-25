package com.example.root.lichvannien.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.root.lichvannien.R
import com.example.root.lichvannien.model.RandomOn
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : AppCompatActivity() {

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        setUI()
        bottom_navigation_more.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.day_item -> {
                    val intent = Intent(this, OneDayActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.month_item -> {
                    val intent = Intent(this, MonthActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.change_day_item -> {
                    val intent = Intent(this, SelectDayActvity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
    }

    fun setUI() {
        toolbar_more.showOverflowMenu()
        setSupportActionBar(toolbar_more)
        toolbar_more.setNavigationIcon(R.drawable.ic_back)
        toolbar_more.setTitleTextColor(Color.WHITE)
        relativelayout_more.setBackgroundResource(arrayDrawer[RandomOn().random(0, 9)])
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                val i = Intent(this, OneDayActivity::class.java)
                startActivity(i)
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        //bottom_navigation_more.selectedItemId = R.id.more_item
    }
}
