package com.example.bizomapplication.adapters
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bizomapplication.R
import com.example.bizomapplication.model.AddedClaims
/**
 * This Adapter is responsible to display added claims though click on button
 * @see ClaimsListAdapter.ButtonViewHolder.button
 *This Adapter is calling and updating from
 * @See ClaimsListAdapter
 * */
class AddedClaimsAdapter() : RecyclerView.Adapter<AddedClaimsAdapter.MyViewHolder>() {
    private var list: ArrayList<AddedClaims> = arrayListOf()

    constructor(list: ArrayList<AddedClaims>) : this() {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_add_claim_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.claimTypeText.text = list[position].claimType
        holder.claimDateText.text = list[position].claimDate
        holder.remarkText.text = list[position].remark
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var claimTypeText: TextView = itemView.findViewById(R.id.claim_type_text)
        var claimDateText: TextView = itemView.findViewById(R.id.claim_date_text)
        var remarkText: TextView = itemView.findViewById(R.id.remark_text)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * This method is responsible to update recycler view with new list
     * This method is called when user submitted the new claims
     * @param updatedList is Based on user submitted data it will change
     * this method refresh the entire adapter with new updated list
     * */
    fun updateList(updatedList: ArrayList<AddedClaims>){
        this.list = updatedList
        notifyDataSetChanged()
    }
}