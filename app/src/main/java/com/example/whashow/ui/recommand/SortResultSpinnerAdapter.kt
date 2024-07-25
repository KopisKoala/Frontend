package com.example.whashow.ui.recommand

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.example.whashow.databinding.ItemSpinnerRecommandSpaceBinding
import com.example.whashow.databinding.ItemSpinnerSortResultBinding

class SortResultSpinnerAdapter (context: Context, @LayoutRes private val resId:Int, private val resultList : List<String>):
    ArrayAdapter<String>(context,resId,resultList){
    // 드롭다운하지 않은 상태의 Spinner 항목 뷰
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemSpinnerSortResultBinding = ItemSpinnerSortResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        itemSpinnerSortResultBinding.textViewSortSpinnerItem.text = resultList[position]
        return itemSpinnerSortResultBinding.root
    }

    // 드롭다운된 항목들 리스트의 뷰
    @SuppressLint("ViewHolder")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemSpinnerSortResultBinding = ItemSpinnerSortResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        itemSpinnerSortResultBinding.textViewSortSpinnerItem.text = resultList[position]
        return itemSpinnerSortResultBinding.root
    }

    override fun getCount() = resultList.size

}