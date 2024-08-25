package com.example.whashow.ui.mypage

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.DeleteMyActor
import com.example.whashow.data.FavoriteActorResDto
import com.example.whashow.data.GoodsX
import com.example.whashow.data.MyActor
import com.example.whashow.databinding.GoodsGridItemBinding
import com.example.whashow.databinding.MyActorListItemBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyActorAdapter (var list: ArrayList<FavoriteActorResDto>): RecyclerView.Adapter<MyActorAdapter.ActorViewHolder>() {

    var editClick=false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyActorAdapter.ActorViewHolder{
        return ActorViewHolder(
            MyActorListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ActorViewHolder(val binding: MyActorListItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            val profile=binding.profile
            val name=binding.name
            val delete=binding.btnDelete

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyActorAdapter.ActorViewHolder, position: Int) {
        holder.name.text=list[position].actorName
        Glide.with(holder.profile.context)
            .load(R.drawable.img_actor_like)
            .override(1500,1500)
            .placeholder(R.drawable.img_actor_like) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_actor_like) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.profile)
        holder.delete.setOnClickListener {
            removeItem(position)
            /*//선택되지 않은 경우
            val Call: Call<DeleteMyActor> =
                ApiManager.mypageService.deleteActor(
                    "Bearer " + LocalDataSource.getAccessToken(),list[position].actorId
                )
            // 비동기적으로 요청 수행
            Call.enqueue(object : Callback<DeleteMyActor> {
                override fun onResponse(
                    call: Call<DeleteMyActor>,
                    response: Response<DeleteMyActor>
                ) {
                    if (response.isSuccessful) {

                        Log.d("내 배우 서버", response.body()?.result.toString())

                    } else {
                        // 서버에서 오류 응답을 받은 경우 처리
                        Log.d("내 배우 서버", response.toString())
                    }

                }

                override fun onFailure(call: Call<DeleteMyActor>, t: Throwable) {
                    // 통신 실패 처리
                    Log.d("내 배우 서버", t.message.toString())
                }

            })*/
        }
        if (editClick){
            holder.delete.visibility= View.VISIBLE
        }
        else {
            holder.delete.visibility= View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}