package fi.shaynek.parliamentfinland.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.utils.Shared

/**
 * It defines all members of parliament in a list with their details
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 06.10.2022
 */

class AllMembersRecyclerAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val basicData: LiveData<List<MembersBasicData>>,
    private val extraData: LiveData<List<MembersExtraData>>,
    private val comments: LiveData<List<Comments>>
): RecyclerView.Adapter<AllMembersRecyclerAdapter.MemberCardHolder>() {

    inner class MemberCardHolder(private val v: View): RecyclerView.ViewHolder(v){
        val image: ImageView =v.findViewById(R.id.member_image_large)
        val comments: TextView = v.findViewById(R.id.comment_count)
        val likes: TextView = v.findViewById(R.id.likes_count)
        val dislikes: TextView = v.findViewById(R.id.dislike_count)
        var commentLayout: LinearLayout = v.findViewById(R.id.comments_count_layout)
        var likesLayout: LinearLayout = v.findViewById(R.id.likes_count_layout)
        var dislikesLayout: LinearLayout = v.findViewById(R.id.dislike_count_layout)
        val hetekaId: TextView = v.findViewById(R.id.all_members_heteka_id)
        val member_name: TextView = v.findViewById(R.id.all_members_name)
        val constituency: TextView = v.findViewById(R.id.all_members_contituency)
        val twitter: TextView = v.findViewById(R.id.all_members_twitter)
        val party: TextView = v.findViewById(R.id.all_members_party)
        val seat: TextView = v.findViewById(R.id.all_members_seatNumber)
        val minister: CheckBox = v.findViewById(R.id.all_members_minister)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberCardHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.member_card_big, parent, false)
        return MemberCardHolder(view)
    }

    override fun onBindViewHolder(holder: MemberCardHolder, position: Int) {
        observeComments(holder,position)
        obServeBasic(holder, position)
        obServeExtra(holder, position)

    }

    override fun getItemCount(): Int {
        var count = 0
        basicData.observe(lifecycleOwner, Observer{
            count = it.size
        })
        return count

    }
    private fun observeComments(holder: MemberCardHolder, position: Int) {
        comments.observe(lifecycleOwner, Observer {
            obServeBasic(holder, position)
        })
    }

    private fun obServeExtra(holder: MemberCardHolder, position: Int) {
        extraData.observe(lifecycleOwner, Observer {
            obServeBasic(holder, position)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun obServeBasic(holder: MemberCardHolder, position: Int) {
        basicData.observe(lifecycleOwner, Observer {
            holder.image.load(constructUrl(it[position].photoUrl))
            holder.comments.text = "${commentCount(it[position].hetekaId)} comments"
            holder.likes.text = "${getLikes(it[position].hetekaId)} likes"
            holder.dislikes.text = "${getDisLikes(it[position].hetekaId)} dislikes"
            holder.hetekaId.text = "HetekaId: ${it[position].hetekaId}"
            holder.party.text = "Party: ${it[position].party}"
            holder.seat.text = "Seat: ${it[position].seatNo}"
            holder.member_name.text = "Name: ${it[position].firstName} ${it[position].lastName}"
            holder.minister.isChecked = it[position].minister
            holder.twitter.text = getLatestExtraData(it[position].hetekaId)?.twitterHandle
            holder.constituency.text = "${getLatestExtraData(it[position].hetekaId)?.constituency}"
            holder.constituency.text = getLatestExtraData(it[position].hetekaId)?.let { m ->
                "Constituency: ${m.constituency}"
            }
            holder.commentLayout.setOnClickListener {_->
                val navHost =
                    (lifecycleOwner as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val navControl = navHost.navController
                val bundle = Bundle()
                bundle.putString("hetekaId", it[position].hetekaId.toString())
                navControl.navigate(R.id.commentsFragment, bundle)
            }
//            holder.dislikesLayout.setOnClickListener{_->
//                val likes = getLikes(it[position].hetekaId)
//                var dislikes = getDisLikes(it[position].hetekaId)
//            }
//
//            holder.likesLayout.setOnClickListener {_->
//                var likes = getLikes(it[position].hetekaId)
//                val dislikes = getDisLikes(it[position].hetekaId)
//            }
        })
    }

    private fun constructUrl(endPoint: String): String {
        return "${Shared.IMG_BASE_URL}$endPoint"
    }

    private fun getLikes(hetekaId: Int):Int{
//        return reactions.value?.find {
//            return@find it.hetekaId == hetekaId
//        }?.likes?:0
        return 0
    }

    private fun getDisLikes(hetekaId: Int):Int{
//        return reactions.value?.find {
//            return@find it.hetekaId == hetekaId
//        }?.dislikes?:0
        return 0
    }

    private fun getLatestExtraData(hetekaId: Int): MembersExtraData? {
        return extraData.value?.find {
            return@find it.hetekaId == hetekaId
        }
    }

    private fun commentCount(hetekaId: Int): Int {
        return comments.value?.filter {
            return@filter it.hetekaId == hetekaId
        }?.count() ?: 0
    }
}