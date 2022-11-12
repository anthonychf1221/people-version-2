package com.anthonychaufrias.people.ui.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.model.Person
import kotlinx.android.synthetic.main.row_person.view.*

class PersonListAdapter(val onPersonClick: (Person) -> Unit): RecyclerView.Adapter<PersonListAdapter.SearchViewHolder>() {
    var peopleList: List<Person> = emptyList()
    fun setData(list: List<Person>){
        peopleList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_person, parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val person = peopleList[position]
        holder.itemView.txtFullName.text = person.fullName
        holder.itemView.txtCountry.text = person.countryName
        holder.itemView.setOnClickListener { onPersonClick(person) }
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}