package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PairDetailResDto
import com.example.whashow.databinding.ListGridPairingBinding
class SearchPairAdapter(var list: ArrayList<PairDetailResDto>): RecyclerView.Adapter<SearchPairAdapter.SearchPairViewHolder>() {

    interface MyItemClickListener{
        fun onDeleteClick(position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPairViewHolder {
        return SearchPairViewHolder(
            ListGridPairingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class SearchPairViewHolder(val binding: ListGridPairingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img1 = binding.paringImg1
        val img2 = binding.paringImg2
        val name = binding.namePairing
        val performance = binding.namePerformance
        val hashtag1 = binding.paringFeature
        val hashtag2 = binding.paringFeature2
        val reviewNum = binding.reviewNum
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchPairViewHolder, position: Int) {
        Log.d("인기 페어 조회 서버", list.toString())

        Glide.with(holder.itemView.context)
            .load(list[position].actor1Profile)
            .override(1500, 1500)
            .placeholder(R.drawable.img_actor_like)
            .error(R.drawable.img_actor_like)
            .into(holder.img1)

        Glide.with(holder.itemView.context)
            .load(list[position].actor2Profile)
            .override(1500, 1500)
            .placeholder(R.drawable.img_actor_like)
            .error(R.drawable.img_actor_like)
            .into(holder.img2)

        holder.name.text = "${list[position].actor1Name}         ${list[position].actor2Name}"
        holder.performance.text = list[position].title
        holder.hashtag1.text = "# ${list[position].hashtag1}"
        holder.hashtag2.text = "# ${list[position].hashtag2}"
        holder.reviewNum.text = list[position].reviewCount.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updatePairs(newList: List<PairDetailResDto>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
