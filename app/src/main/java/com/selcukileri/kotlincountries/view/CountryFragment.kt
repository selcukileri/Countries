package com.selcukileri.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.selcukileri.kotlincountries.databinding.FragmentCountryBinding
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
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[CountryVM::class.java]
        viewModel.getDataFromRoom()
        observeLiveData()


        arguments?.let {
            countryUuid =
                com.selcukileri.kotlincountries.view.CountryFragmentArgs.fromBundle(it).countryUuid
        }
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryRegion.text = it.countryRegion
                binding.countryLanguage.text = it.countryLanguage
                binding.countryCurrency.text = it.countryCurrency
            }
        })
    }

}