package com.example.mindvocab.screens.statistic

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.DialogAchievementDetailBinding
import com.example.mindvocab.databinding.FragmentStatisticBinding
import com.example.mindvocab.model.achievement.Achievement
import com.github.javafaker.Faker
import kotlin.random.Random

class StatisticFragment : Fragment() {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val list = MutableList(10) {
        val id = it + 1
        Achievement(
            id = id,
            title = faker.cat().name(),
            description = faker.lorem().paragraph(1),
            icon = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            progress = 50
        )
    }

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

        achievementsAdapter.submitList(list)
        return binding.root
    }

    private fun showAchievementDetailDialog(achievement: Achievement) {
        val dialogBinding = DialogAchievementDetailBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)

        dialog.setContentView(dialogBinding.root)

        dialogBinding.achievementName.text = achievement.title
        dialogBinding.achievementDescription.text = achievement.description
        Glide.with(dialogBinding.achievementPhoto.context)
            .load(achievement.icon)
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
