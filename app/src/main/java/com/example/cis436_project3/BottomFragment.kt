package com.example.cis436_project3

import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.widget.TextView


class BottomFragment : Fragment() {

    companion object {
        fun newInstance() = BottomFragment()
    }

    private val sharedViewModel: SharedCatViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val breedNameTextView = view.findViewById<TextView>(R.id.breedNameTextView)
        val breedDetailsTextView = view.findViewById<TextView>(R.id.breedDetailsTextView)
        val breedImageView = view.findViewById<ImageView>(R.id.breedImageView)

        sharedViewModel.selectedBreed.observe(viewLifecycleOwner, Observer { breed ->
            breed?.let {
                breedNameTextView.text = it.name
                breedDetailsTextView.text = it.details

                val catService = CatService(requireContext())
                catService.getBreedImage(it.id, {imageUrl ->
                    loadImage(imageUrl, breedImageView)
                }, {errorMessage ->
                    breedImageView.setImageResource(R.drawable.placeholder_image)
                })
            }
        })
    }

    //function to load image from url into 'ImageView'

    private fun loadImage(imageUrl: String, imageView: ImageView){
        val queue = Volley.newRequestQueue(requireContext())
        val imageRequest = ImageRequest(imageUrl,
            { response ->
                imageView.setImageBitmap(response)
            },
            0,0, null, null,
            {error ->
                imageView.setImageResource(R.drawable.placeholder_image)
            })

        queue.add(imageRequest)
    }
}