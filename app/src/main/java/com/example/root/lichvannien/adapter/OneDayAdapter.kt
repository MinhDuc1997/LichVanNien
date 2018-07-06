package com.example.root.lichvannien.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.ViewGroup
import com.example.root.lichvannien.fragment.OneDay
import java.util.*

class OneDayAdapter(fragmentManager: FragmentManager, val data: ArrayList<String>): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        //Log.d("nowPos", "$position")
        //Log.d("dataNow", data[position])
        val bundle = Bundle()
        bundle.putString("demo", data[position])
        val fragment = OneDay()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)

    }

}