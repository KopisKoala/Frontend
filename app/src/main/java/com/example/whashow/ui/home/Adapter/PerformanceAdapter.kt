//package com.example.whashow.ui.home.Adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.whashow.databinding.ListGridRecommandItemBinding
//import com.example.whashow.ui.home.Performance
//
//class PerformanceAdapter(var list: ArrayList<Performance>) : RecyclerView.Adapter<PerformanceAdapter.PerformanceHolder>(){
//
//    var PerformanceList: ArrayList<Performance> =list
//        set(value){
//            field=value
//            notifyDataSetChanged()
//        }
//
//    interface MyItemClickListener{
//        fun onDeleteClick(position: Int)
//    }
//
//    private lateinit var myItemClickListener: MyItemClickListener
//    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
//        myItemClickListener=itemClickListener
//    }
//    inner class PerformanceHolder(val binding: ListGridRecommandItemBinding) :
//        RecyclerView.ViewHolder(binding.root){
//            val img=binding.recommandPoster
//            val title=binding.performanceTitle
//            val space=binding.performanceDetail
//            val rating=binding.ratingBar
//            val review=binding.review
//    }
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): PerformanceHolder {
//        return PerformanceHolder(
//            ListGridRecommandItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//    override fun onBindViewHolder(holder: PerformanceHolder, position: Int) {
//        holder.img.setImageResource(list[position].img)
//        holder.title.text=list[position].title
//        holder.space.text=list[position].space
//        holder.rating.rating=list[position].rating
//        holder.review.text=list[position].review
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//
//
//
//}
//
//
