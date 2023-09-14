package com.example.mindvocab.screens.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.model.caregory.Category
import com.example.mindvocab.databinding.FragmentCategoryListBinding
import com.example.mindvocab.model.utils.ErrorResult
import com.example.mindvocab.model.utils.PendingResult
import com.example.mindvocab.model.utils.SuccessResult
import com.example.mindvocab.utils.factory


class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryListViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = CategoryAdapter(object: CategoryAdapter.Listener {
            override fun onCategoryDetail(category: Category) {
                Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
            }

            override fun onCategorySelectedToggle(category: Category) {
                viewModel.toggleSelectCategory(category)
            }
        })

        binding.categoryRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.categoryList.observe(viewLifecycleOwner) { result ->
            when(result) {
                is PendingResult -> {
                    binding.progressBar.visibility = View.VISIBLE

                    binding.categoryRecyclerView.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.GONE
                }
                is ErrorResult -> {
                    binding.tryAgainButton.visibility = View.VISIBLE

                    binding.progressBar.visibility = View.GONE
                    binding.categoryRecyclerView.visibility = View.GONE
                }
                is SuccessResult -> {
                    binding.categoryRecyclerView.visibility = View.VISIBLE

                    binding.tryAgainButton.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                    categoryAdapter.submitList(result.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}