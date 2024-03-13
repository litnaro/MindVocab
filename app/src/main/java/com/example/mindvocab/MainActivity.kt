package com.example.mindvocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.mindvocab.databinding.ActivityMainBinding
import com.example.mindvocab.model.room.Repositories
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.screens.learn.LearnWordFragmentDirections
import com.example.mindvocab.screens.learn.wordset.WordSetsFragmentDirections
import com.example.mindvocab.screens.repeat.RepeatWordFragmentDirections
import com.example.mindvocab.screens.statistic.StatisticFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject lateinit var applicationSetting: ApplicationSettings

    private val topLevelDestinations = setOf(
        R.id.learn,
        R.id.repeat,
        R.id.statistic,
        R.id.browser,
        R.id.settings,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repositories.init(applicationContext)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("AAAAA", applicationSetting.toString())

        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController

        setupWithNavController(binding.bottomNavigationView, navController)

        navController.addOnDestinationChangedListener(destinationListener)

        binding.actionSelectWordSet.setOnClickListener {
            navController.navigate(LearnWordFragmentDirections.actionLearnToWordSetsFragment())
        }

        binding.actionNotification.setOnClickListener {
            navController.navigate(StatisticFragmentDirections.actionStatisticToNotificationsFragment())
        }

        binding.actionShowListOfRepeatingWords.setOnClickListener {
            navController.navigate(RepeatWordFragmentDirections.actionRepeatToRepeatingWordsFragment())
        }

        binding.actionConfirmWordSets.setOnClickListener {
            navController.navigate(WordSetsFragmentDirections.actionWordSetsFragmentToLearn())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

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