package com.example.cis436_project3

import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breedNameTextView = view.findViewById<TextView>(R.id.breedNameTextView)
        val breedDetailsTextView = view.findViewById<TextView>(R.id.breedDetailsTextView)
        val breedImageView = view.findViewById<ImageView>(R.id.breedImageView)

        sharedViewModel.selectedBreed.observe(viewLifecycleOwner) { breed ->
            if (breed != null) {
                breedNameTextView.text = breed.name
                breedDetailsTextView.text = breed.details
                breedImageView.visibility = View.VISIBLE

                val catService = CatService(requireContext())
                catService.getBreedImage(breed.id, { imageUrl ->
                    loadImage(imageUrl, breedImageView)
                }, {
                    breedImageView.setImageResource(R.drawable.placeholder_image)
                })
            } else {
                // Clear everything
                breedNameTextView.text = ""
                breedDetailsTextView.text = ""
                breedImageView.setImageDrawable(null)
                breedImageView.visibility = View.GONE
            }
        }

    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        val queue = Volley.newRequestQueue(requireContext())
        val imageRequest = ImageRequest(imageUrl,
            { response ->
                imageView.setImageBitmap(response)
            },
            0, 0, null, null,
            {
                imageView.setImageResource(R.drawable.placeholder_image)
            })
        queue.add(imageRequest)
    }
}

