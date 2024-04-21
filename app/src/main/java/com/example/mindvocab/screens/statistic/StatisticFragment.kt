package com.example.mindvocab.screens.statistic

import android.app.Dialog
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.DialogAchievementDetailBinding
import com.example.mindvocab.databinding.DialogStatisticHelpBinding
import com.example.mindvocab.databinding.FragmentStatisticBinding
import com.example.mindvocab.model.achievement.entities.Achievement
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : BaseFragment() {

    override val viewModel by viewModels<StatisticViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticBinding.inflate(layoutInflater, container, false)

        setupMenu()

        val achievementsAdapter = AchievementAdapter(object : AchievementAdapter.Listener {
            override fun onAchievementDetail(achievement: Achievement) {
                if (!achievement.isChecked) {
                    viewModel.setAchievementAsChecked(achievement)
                }
                showAchievementDetailDialog(achievement)
            }
        })
        binding.achievementsRv.apply {
            adapter = achievementsAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        binding.statisticViewPager.adapter = StatisticPagerAdapter(this)
        binding.statisticViewPagerIndicator.attachTo(binding.statisticViewPager)

        viewModel.achievements.observe(viewLifecycleOwner) {
            with(binding) {
                achievementsRv.visibility = View.GONE
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()

                when(it) {
                    is Result.Pending -> {
                        pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.Error -> {}
                    is Result.Success -> {
                        achievementsAdapter.submitList(it.data)
                        achievementsRv.visibility = View.VISIBLE
                    }
                }
            }
        }
        return binding.root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider( object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.statistic_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.openHelpDialog -> {
                        showCalendarHelperDialog()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showCalendarHelperDialog() {
        val dialogBinding = DialogStatisticHelpBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dialogBinding.startedMarkerText.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            dialogBinding.repeatedMarkerText.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            dialogBinding.repeatedAndStartedMarkerText.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.root.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showAchievementDetailDialog(achievement: Achievement) {
        val dialogBinding = DialogAchievementDetailBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)

        dialog.setContentView(dialogBinding.root)

        dialogBinding.achievementName.text = achievement.title
        dialogBinding.achievementDescription.text = achievement.description

        if (achievement.progress >= achievement.maxProgress) {
            dialogBinding.achievementCompletedIcon.visibility = View.VISIBLE

            dialogBinding.achievementProgressText.visibility = View.GONE
        } else {
            dialogBinding.achievementCompletedIcon.visibility = View.GONE

            dialogBinding.achievementProgressText.visibility = View.VISIBLE
            dialogBinding.achievementProgressText.text = dialogBinding.root.context.getString(R.string.amount_of, achievement.progress, achievement.maxProgress)
        }

        dialogBinding.achievementProgress.max = achievement.maxProgress
        dialogBinding.achievementProgress.progress = if (achievement.progress > achievement.maxProgress) achievement.maxProgress else achievement.progress

        Glide.with(dialogBinding.achievementPhoto.context)
            .load(achievement.image)
            .circleCrop()
            .placeholder(R.drawable.ic_meditation)
            .error(R.drawable.ic_meditation)
            .into(dialogBinding.achievementPhoto)

        dialogBinding.close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
