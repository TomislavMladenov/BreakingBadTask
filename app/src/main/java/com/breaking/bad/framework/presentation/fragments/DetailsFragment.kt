package com.breaking.bad.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.breaking.bad.R
import com.breaking.bad.framework.datasource.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var requestManager: RequestManager? = null // can leak memory, must be nullable

    private val args: DetailsFragmentArgs by navArgs()

    private var character: Character? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGlide()
        character = args.character

        setImageView()
        setTextVies()
        setOccupation()
        setAppearance()
    }

    private fun setTextVies(){
        character?.let { character ->
            tv_name.text = getString(R.string.tv_details_name, character.name, character.nickname)
            tv_status.text = character.status

        }
    }

    private fun setOccupation(){
        if(!character?.occupation.isNullOrEmpty()){
            val sb = StringBuilder()
            for(i in character!!.occupation.indices){
                sb.append(character!!.occupation[i])
                if(i != character!!.occupation.size -1){
                    sb.append(", ")
                }
            }
            tv_occupation.text = getString(R.string.tv_details_occupation, sb.toString())
        }
    }

    private fun setAppearance(){
        if(!character?.appearance.isNullOrEmpty()){
            val sb = StringBuilder()
            for(i in character!!.appearance.indices){
                sb.append(character!!.appearance[i])
                if(i != character!!.appearance.size -1){
                    sb.append(", ")
                }
            }
            tv_appearance.text = getString(R.string.tv_details_appearance, sb.toString())
        }
    }

    private fun setImageView(){
        character?.let {character ->
            requestManager
                ?.load(character.image)
                ?.centerCrop()
                ?.transition(DrawableTransitionOptions.withCrossFade())
                ?.into(iv_details_image)
        }
    }

    private fun setupGlide(){
        val requestOptions = RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.def_no_image)

        activity?.let {
            requestManager = Glide.with(it)
                .applyDefaultRequestOptions(requestOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // clear references (can leak memory)
        requestManager = null
    }

}