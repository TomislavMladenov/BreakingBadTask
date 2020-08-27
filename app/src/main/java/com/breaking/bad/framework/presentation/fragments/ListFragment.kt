package com.breaking.bad.framework.presentation.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.breaking.bad.R
import com.breaking.bad.business.state.DataState
import com.breaking.bad.framework.datasource.model.Character
import com.breaking.bad.framework.presentation.CharactersListAdapter
import com.breaking.bad.framework.presentation.Interaction
import com.breaking.bad.framework.presentation.MainStateEvent
import com.breaking.bad.framework.presentation.MainViewModel
import com.breaking.bad.framework.presentation.util.SortUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skoumal.fragmentback.BackFragment


@AndroidEntryPoint
@ExperimentalCoroutinesApi
open class ListFragment
constructor(
    private val sortUtils: SortUtils
) : Fragment(R.layout.fragment_list), Interaction, BackFragment {

    private lateinit var listAdapter: CharactersListAdapter
    private var requestManager: RequestManager? = null // can leak memory, must be nullable

    private var originalList: List<Character>? = null

    private lateinit var searchView: SearchView

    private var bottomDrawerBehavior: BottomSheetBehavior<View>? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBottomDrawer()
        setupGlide()
        initRecyclerView()
        setupNavigationListener()

        subscribeObservers()

        viewModel.setStateEvent(MainStateEvent.GetCharactersEvent)
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

    private fun initRecyclerView(){
        characters_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            listAdapter = CharactersListAdapter(
                requestManager as RequestManager,
                this@ListFragment
            )
            adapter = listAdapter
        }
    }

    private fun subscribeObservers(){
        viewModel.getDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Character>> -> {
                    displayProgressBar(false)
                    displayCharacters(dataState.data)
                    originalList = dataState.data
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackbar(dataState.exception.message.toString())
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayCharacters(list: List<Character>?){
        list?.let {
            listAdapter.preloadGlideImages(requestManager as RequestManager, list)
            listAdapter.setCharacters(it)
        }
    }

    private fun showSnackbar(text: CharSequence) {
        Snackbar.make(list_container, text, Snackbar.LENGTH_SHORT)
            .setAnchorView(bar)
            .show()
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onItemSelected(position: Int, item: Character) {
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(
            character = item
        )
        findNavController().navigate(action)
    }

    private fun setupNavigationListener(){
        navigation_view.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_item_season_one -> {
                    sortBySeason(1)
                }
                R.id.menu_item_season_two -> {
                    sortBySeason(2)
                }
                R.id.menu_item_season_three -> {
                    sortBySeason(3)
                }
                R.id.menu_item_season_four -> {
                    sortBySeason(4)
                }
                R.id.menu_item_season_five -> {
                    sortBySeason(5)
                }
            }
            bottomDrawerBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            false
        }
    }

    private fun setUpBottomDrawer() {
        bottomDrawerBehavior = BottomSheetBehavior.from(bottom_drawer)
        bottomDrawerBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        bar.setNavigationOnClickListener {
            bottomDrawerBehavior!!.setState(
                BottomSheetBehavior.STATE_HALF_EXPANDED
            )
        }
        bar.setNavigationIcon(R.drawable.ic_baseline_format_list_bulleted_24)
        bar.replaceMenu(R.menu.menu_seach)

        initMenu(bar.menu)
    }

    private fun initMenu(menu: Menu){
        activity?.apply {
            val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.item_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                filterByName(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isNotEmpty()){
                    filterByName(query)
                }
                searchView.clearFocus()
                // task HERE
                return false
            }
        })
    }

    private fun sortBySeason(season: Int){
        originalList?.let {
            listAdapter.setCharacters(sortUtils.filterBySeasonAppearance(season, it))
        }?: showSnackbar(getString(R.string.error_empty_list))

    }

    private fun filterByName(name: String){
        originalList?.let {
            listAdapter.setCharacters(sortUtils.searchByName(name, it))
        }?: showSnackbar(getString(R.string.error_empty_list))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // clear references (can leak memory)
        characters_recycler_view.adapter = null
        requestManager = null
    }

    override fun onBackPressed(): Boolean {
        return if(bottomDrawerBehavior!!.state != BottomSheetBehavior.STATE_HIDDEN){
            bottomDrawerBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            true
        } else {
            false
        }
    }

    override fun getBackPriority(): Int {
        return 1
    }

}