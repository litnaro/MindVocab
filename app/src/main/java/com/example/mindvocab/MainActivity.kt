package com.example.mindvocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.mindvocab.databinding.ActivityMainBinding
import com.example.mindvocab.screens.learn.LearnWordFragmentDirections
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val topLevelDestinations = setOf(
        R.id.learn,
        R.id.repeat,
        R.id.statistic,
        R.id.browser,
        R.id.settings,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController

        setupWithNavController(binding.bottomNavigationView, navController)

        navController.addOnDestinationChangedListener(destinationListener)

        binding.actionSelectWordSet.setOnClickListener {
            navController.navigate(LearnWordFragmentDirections.actionLearnToWordSetsFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
        supportActionBar?.title = prepareTitle(destination.label, arguments)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))

        //TODO replace with when and collection maybe
        if (destination.id == R.id.learn){
            binding.actionSelectWordSet.visibility = View.VISIBLE
        } else {
            binding.actionSelectWordSet.visibility = View.GONE
        }

        if (destination.id == R.id.wordSetsFragment){
            binding.actionConfirmWordSets.visibility = View.VISIBLE
        } else {
            binding.actionConfirmWordSets.visibility = View.GONE
        }

    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {

        // code for this method has been copied from Google sources :)

        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments.getString(argName))
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        destination.parent ?: return false
        return topLevelDestinations.contains(destination.id)
    }

}