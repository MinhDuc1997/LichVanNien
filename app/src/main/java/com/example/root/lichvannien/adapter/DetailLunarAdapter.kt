package com.example.root.lichvannien.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.detail_list_view_lunar.view.*

class DetailLunarAdapter(val arrLables: ArrayList<String>, val arrayDatas: ArrayList<String>, val layoutXml: Int, val context: Context) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layout = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layout.inflate(layoutXml, null) //render from layoutXml
        view.lable_in_list_view_detail_lunar.text = arrLables[position]
        view.list_view_detail_lunar.text = arrayDatas[position]
        view.lable_in_list_view_detail_lunar.top = 200
        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayDatas.size
    }

}