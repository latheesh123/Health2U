package com.example.health2u.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health2u.R
import com.example.health2u.model.Centers
import com.example.health2u.model.Sources
import com.example.health2u.utils.Utils
import com.squareup.picasso.Picasso

class TestingCenterListAdapter(
    private val list: ArrayList<Centers>,
    private val selectedListItem: (Centers) -> Unit
) : RecyclerView.Adapter<TestingCenterListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestingCenterListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.testing_center_view, parent, false)
        return TestingCenterListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestingCenterListAdapter.ViewHolder, position: Int) {
        list.get(position).let { holder.bindView(it, selectedListItem, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SetTextI18n")
        fun bindView(item: Centers, selectedListItem: (Centers) -> Unit, position: Int) {
            itemView.apply {

                findViewById<AppCompatTextView>(R.id.title_name).text = item.title?.substringAfter(":")
                findViewById<AppCompatTextView>(R.id.address_one).text = item.address?.street+","+item.address?.city
                findViewById<AppCompatTextView>(R.id.address_two).text = item.address?.stateCode+","+item.address?.countryName
                findViewById<AppCompatTextView>(R.id.distance).text =
                    item.distance?.let { Utils.getMiles(it).toString() }+" miles"

            }
            itemView.setOnClickListener { selectedListItem(item) }
        }

    }



}