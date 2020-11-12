/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false)
        setHasOptionsMenu(true)
        binding.playButton.setOnClickListener { view: View -> view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment()) }

        // lambda variant
        // binding.playButton.setOnClickListener{view: View -> view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)}

        return binding.root
    }

    // The use of these two functions is how to make the about menu work
    // this inflates the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //apparently we can just inflate menus
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    // this connects our options menu to the navigation ui
    // its called when you select an item from the options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //the menu item had a destination and this attempts to navigate to that destination using the navcontroller we set out in navigation xml and iin main activity
        return NavigationUI.onNavDestinationSelected(item!!,view!!.findNavController())||super.onOptionsItemSelected(item)
    }
}
