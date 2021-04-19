package com.pokerface.novel.domain.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.pokerface.common.base.BaseFragment
import com.pokerface.novel.R
import com.pokerface.novel.databinding.FragmentHomeBinding
import com.pokerface.novel.domain.main.repository.bean.Category
import com.pokerface.novel.domain.main.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    private val viewModel by getViewModel(MainViewModel::class.java) {
        categoryLD.observe(mLifecycleOwner) {
            categoryAdapter.setList(it)
        }
    }


    override fun init() {
        binding.apply {
            rvCategoryTab.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            rvCategoryTab.adapter = categoryAdapter
        }

        viewModel.getCategory()
    }

    class CategoryAdapter : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category) {

        override fun convert(holder: BaseViewHolder, item: Category) {
            holder.setText(R.id.tvName, item.name)
        }

    }
}