package com.example.android.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager
import androidx.navigation.Navigation


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener{view: View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        setHasOptionsMenu(true)

        //we are grabing the arguments we sent in gamefragments using bundles and safeargs
        /*var args = GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()*/
        return binding.root
    }

    // manages the Intent type and variables
    private fun getShareIntent(): Intent{

        //we get the arguments from the bundle
        var args = GameWonFragmentArgs.fromBundle(arguments!!)

        // we create the value through which we are sharing the data
        val shareIntent = Intent(Intent.ACTION_SEND)
            // we pass type, extra variables via putExtra which takes the type of variables we are sending, in this case EXTRA_INTENT and the values themselves
            shareIntent.setType("text/plain").putExtra(Intent.EXTRA_INTENT,getString(R.string.share_success_text,args.numCorrect,args.numQuestions))

        return shareIntent
    }

    // passes our intent to startActivity
    private fun shareSuccess(){
        // we start an intent that is associated with this fragment
        startActivity(getShareIntent())
    }

    // this function inflates the winner_menu and handles the mismatched intent type if necessary
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu,menu)

        if (getShareIntent().resolveActivity(activity!!.packageManager) == null)
        {
            // hide menu if it the type of Intent that we are sending isnt matched on the receiving end
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    // this function override calls shareSuccess if the item id matches the one of the share button and otherwise opens the other options
    // this could handle multiple buttons and intents if desired... or so I believe
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item!!.itemId){
            R.id.share-> shareSuccess()
        }

        return super.onOptionsItemSelected(item)

    }
}
