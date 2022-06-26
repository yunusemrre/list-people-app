package com.sccase.listpeople.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sccase.listpeople.data.model.Person
import com.sccase.listpeople.databinding.ItemListPeopleBinding

class ListPeopleAdapter : RecyclerView.Adapter<ListPeopleAdapter.PeopleViewHolder>() {

    private var peopleDataList = arrayListOf<Person>()

    /**
     * Set new data list to the adapter
     * Check [peopleList]'s IDs whether exist in [peopleDataList]
     */
    fun setDataList(peopleList: ArrayList<Person>): Boolean {
        val newListFiltered = peopleList.filter {
            it.id !in this.peopleDataList.map { item ->
                item.id
            }
        } as ArrayList<Person>

        if (newListFiltered.size == 0)
            return false

        this.peopleDataList.addAll(newListFiltered)
        notifyDataSetChanged()
        return true
    }

    class PeopleViewHolder(private var binding: ItemListPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.mtvItemPeopleFullname.text = person.fullName
            binding.mtvItemPeopleId.text = person.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(
            ItemListPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) =
        holder.bind(peopleDataList[position])

    override fun getItemCount(): Int = peopleDataList.size
}