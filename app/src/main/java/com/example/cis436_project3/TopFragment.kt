package com.example.cis436_project3

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private val viewModel: TopViewModel by viewModels()
    private val sharedViewModel: SharedCatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.catSpinner)

        viewModel.catBreeds.observe(viewLifecycleOwner) { breeds ->
            sharedViewModel.setBreeds(breeds)
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, breeds)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ){
                val selected = parent.getItemAtPosition(position) as CatBreed
                sharedViewModel.setSelectedBreed(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val catService = CatService(requireContext())
        catService.getBreeds(
            onSuccess = { breeds -> viewModel.setBreeds(breeds) },
            onError = { error -> viewModel.setError(error) }
        )
    }
}