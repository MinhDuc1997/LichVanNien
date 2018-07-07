package com.example.root.lichvannien.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.root.lichvannien.R
import kotlinx.android.synthetic.main.fragment_lunar_date_bar.*
import kotlinx.android.synthetic.main.fragment_one_day.*
import org.json.JSONObject

class LunarDateBar : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lunar_date_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val data = bundle!!.getString("lunar_date")
        val jsonObject = JSONObject(data)
        val lunarD = jsonObject.getString("lunarDay")
        val lunarM = jsonObject.getString("lunarMonth")
    }
}