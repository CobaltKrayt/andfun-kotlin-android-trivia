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

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayoutvar: DrawerLayout
    private lateinit var appBarConfigurationvar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // we setup drawerLayoutvar to get the drawer layout from the binding val that gets activity_main
        // its the name of the layout in activity_main
        drawerLayoutvar = binding.drawerLayoutxmlvar

        val navController = this.findNavController(R.id.myNavHostFragment)
        // added the drawerLayoutvar as the third variable for the actionbar component
        // we implement the options menu in the title fragment
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayoutvar)

        // used the appbarconfigurationvar and setupwithnavcontroller to connect the graph with the menu
        // the nav controller is used to help the layout navigate through menus
        // AppBarConfiguration manages the behavior of the Navigation button in the upper-left
        // corner of your app's display area
        appBarConfigurationvar = AppBarConfiguration(navController.graph, drawerLayoutvar)
        NavigationUI.setupWithNavController(binding.navView, navController)

        // receives a callback whenever the destination changes
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            //you can check the current position in the graph by calling a navcontroller.graph.wanteddestination
            if (nd.id == nc.graph.startDestination) {
                drawerLayoutvar.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayoutvar.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    //we use this to implement the up key.
    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController((R.id.myNavHostFragment))
        // this sets up the drawerlayout with the navcontroller instead of the up button in the titlefragment
        return NavigationUI.navigateUp(navController, drawerLayoutvar)

    }
}
