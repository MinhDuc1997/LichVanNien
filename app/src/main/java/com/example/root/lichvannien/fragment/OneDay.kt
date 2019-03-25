package com.example.root.lichvannien.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.root.lichvannien.R
import com.example.root.lichvannien.activity.DetailLunarCalendarActivity
import com.example.root.lichvannien.model.RandomOn
import kotlinx.android.synthetic.main.fragment_one_day.*
import org.json.JSONObject

class OneDay : Fragment() {

    private lateinit var arrStringFromXml: Array<String>

    private val arrayWeekDay = mapOf("2" to "Thứ hai",
            "3" to "Thứ ba", "4" to "Thứ tư",
            "5" to "Thứ năm", "6" to "Thứ sáu",
            "7" to "Thứ Bảy", "1" to "Chủ Nhật")

    private val arrayDrawer = listOf(R.drawable.nen, R.drawable.nen1,
            R.drawable.nen2, R.drawable.nen5,
            R.drawable.nen6, R.drawable.nen7,
            R.drawable.nen8, R.drawable.nen9,
            R.drawable.nen10)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val random = RandomOn()
        relativelayoutfragmentoneday.background = ContextCompat.getDrawable(activity!!, arrayDrawer[random.random(0, 5)])
        arrStringFromXml = resources.getStringArray(R.array.quotes)
        quotes.text = arrStringFromXml[random.random(0, arrStringFromXml.size)]
        val bundle = arguments
        val data = bundle!!.getString("demo")
        val jsonObject = JSONObject(data)
        var wd = jsonObject.getString("weekday")
        val d = jsonObject.getString("day")

        day.text = d
        for (i in arrayWeekDay) {
            if (i.key == wd)
                wd = i.value
        }
        weekdayString.text = wd

        relativelayoutfragmentoneday.setOnClickListener {
            val intent = Intent(context, DetailLunarCalendarActivity::class.java)
            intent.putExtra("data", data)
            context!!.startActivity(intent)
        }
    }
}
