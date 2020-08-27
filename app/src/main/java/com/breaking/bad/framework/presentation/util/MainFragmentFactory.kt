package com.breaking.bad.framework.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.breaking.bad.framework.presentation.fragments.DetailsFragment
import com.breaking.bad.framework.presentation.fragments.ListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(
    private val sortUtils: SortUtils
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            ListFragment::class.java.name -> {
                ListFragment(sortUtils)
            }

            DetailsFragment::class.java.name -> {
                DetailsFragment()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}