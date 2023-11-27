package com.selcukileri.kotlincountries.adapter

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.selcukileri.kotlincountries.R
import com.selcukileri.kotlincountries.databinding.ItemCountryBinding
import com.selcukileri.kotlincountries.model.Country
import com.selcukileri.kotlincountries.util.downloadFromUrl
import com.selcukileri.kotlincountries.util.placeHolderProgressBar
import com.selcukileri.kotlincountries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country,parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country = countryList[position]
        holder.binding.listener = this


        /*holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion
        holder.binding.imageView.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            action.countryUuid = countryList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.itemView.context))

         */

    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val binding = DataBindingUtil.getBinding<ItemCountryBinding>(v)
        val uuid = binding?.countryUuidText?.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
        action.countryUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}