package com.example.cis436_project3

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import android.widget.AdapterView

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

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

        sharedViewModel.breeds.observe(viewLifecycleOwner) { breeds ->
            val catBreedsWithPlaceholder = mutableListOf(CatBreed(id = "", name = "Select a Cat", temperament = null, origin = null))
            catBreedsWithPlaceholder.addAll(breeds)
            val adapter = BreedArrayAdapter(requireContext(), catBreedsWithPlaceholder)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(0)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position) as? CatBreed
                if (selected?.id?.isNotEmpty() == true) {
                    sharedViewModel.setSelectedBreed(selected)
                } else {
                    // This is the "Select a Cat" placeholder option
                    sharedViewModel.setSelectedBreed(null)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Fetch the cat breeds from the service
        val catService = CatService(requireContext())
        catService.getBreeds(
            onSuccess = { breeds -> sharedViewModel.setBreeds(breeds) },
            onError = { error ->  "Error"}
        )
    }
}
