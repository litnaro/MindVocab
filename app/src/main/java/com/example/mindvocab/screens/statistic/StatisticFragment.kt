package com.example.mindvocab.screens.statistic

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.DialogAchievementDetailBinding
import com.example.mindvocab.databinding.FragmentStatisticBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.achievement.entities.Achievement

class StatisticFragment : BaseFragment() {

    override val viewModel: StatisticViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticBinding.inflate(layoutInflater, container, false)
        val achievementsAdapter = AchievementAdapter(object : AchievementAdapter.Listener {
            override fun onAchievementDetail(achievement: Achievement) {
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
            when(it) {
                is PendingResult -> {}
                is ErrorResult -> {}
                is SuccessResult -> {
                    achievementsAdapter.submitList(it.data)
                }
            }
        }
        return binding.root
    }

    private fun showAchievementDetailDialog(achievement: Achievement) {
        val dialogBinding = DialogAchievementDetailBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)

        dialog.setContentView(dialogBinding.root)

        dialogBinding.achievementName.text = achievement.title
        dialogBinding.achievementDescription.text = achievement.description

        if (achievement.progress == achievement.maxProgress) {
            dialogBinding.achievementCompletedIcon.visibility = View.VISIBLE
        } else {
            dialogBinding.achievementCompletedIcon.visibility = View.GONE
        }

        dialogBinding.achievementProgress.max = achievement.maxProgress
        dialogBinding.achievementProgress.progress = achievement.progress

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
