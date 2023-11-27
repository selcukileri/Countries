package com.selcukileri.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.selcukileri.kotlincountries.R
import com.selcukileri.kotlincountries.databinding.FragmentCountryBinding
import com.selcukileri.kotlincountries.util.downloadFromUrl
import com.selcukileri.kotlincountries.util.placeHolderProgressBar
import com.selcukileri.kotlincountries.viewmodel.CountryVM

class CountryFragment : Fragment() {
    private lateinit var binding: FragmentCountryBinding
    private lateinit var viewModel: CountryVM
    private var countryUuid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid =
                com.selcukileri.kotlincountries.view.CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel = ViewModelProviders.of(this)[CountryVM::class.java]
        viewModel.getDataFromRoom(countryUuid)
        observeLiveData()



    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                binding.selectedCountry = it
                /*
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryRegion.text = it.countryRegion
                binding.countryLanguage.text = it.countryLanguage
                binding.countryCurrency.text = it.countryCurrency
                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                }

                 */
            }
        })
    }

}