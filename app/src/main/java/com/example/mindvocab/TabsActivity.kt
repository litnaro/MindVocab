package com.example.mindvocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.mindvocab.databinding.ActivityTabsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class TabsActivity : AppCompatActivity() {

    private var _binding: ActivityTabsBinding? = null
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
        _binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
//
//        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
//        navController = navHost.navController
//
//        setupWithNavController(binding.bottomNavigationView, navController)

//        navController.addOnDestinationChangedListener(destinationListener)
//
//        binding.actionSelectWordSet.setOnClickListener {
//            navController.navigate(LearnWordFragmentDirections.actionLearnToWordSetsFragment())
//        }
//
//        binding.actionNotification.setOnClickListener {
//            navController.navigate(StatisticFragmentDirections.actionStatisticToNotificationsFragment())
//        }
//
//        binding.actionShowListOfRepeatingWords.setOnClickListener {
//            navController.navigate(RepeatWordFragmentDirections.actionRepeatToRepeatingWordsFragment())
//        }
//
//        binding.actionConfirmWordSets.setOnClickListener {
//            navController.navigate(WordSetsFragmentDirections.actionWordSetsFragmentToLearn())
//        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
        supportActionBar?.title = prepareTitle(destination.label, arguments)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))

        binding.actionButtonsContainer.children.forEach {
            it.visibility = View.GONE
        }

        when(destination.id) {
            R.id.learn -> {
                binding.actionSelectWordSet.visibility = View.VISIBLE
            }
            R.id.repeat -> {
                binding.actionShowListOfRepeatingWords.visibility = View.VISIBLE
            }
            R.id.statistic -> {
                binding.actionNotification.visibility = View.VISIBLE
            }
            R.id.wordSetsFragment -> {
                binding.actionConfirmWordSets.visibility = View.VISIBLE
            }
        }

    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {
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