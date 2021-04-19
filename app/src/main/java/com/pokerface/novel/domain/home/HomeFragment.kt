package com.pokerface.novel.domain.home

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.pokerface.common.base.BaseFragment
import com.pokerface.novel.R
import com.pokerface.novel.databinding.FragmentHomeBinding
import com.pokerface.novel.domain.main.repository.bean.Category
import com.pokerface.novel.domain.main.repository.bean.CategoryDetailData
import com.pokerface.novel.domain.main.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    private val categoryDetailAdapter by lazy {
        CategoryDetailAdapter()
    }
    private val viewModel by getViewModel(MainViewModel::class.java) {
        categoryLD.observe(mLifecycleOwner) {
            categoryAdapter.setList(it)
            binding.rvCategoryTab.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
        }

        categoryDetailLD.observe(mLifecycleOwner) {
            categoryDetailAdapter.setList(it)
        }
    }

    override fun init() {
        binding.apply {
            rvCategoryTab.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            rvCategoryTab.adapter = categoryAdapter

            rvCategoryDetail.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            rvCategoryDetail.adapter = categoryDetailAdapter
        }

        viewModel.getCategory()
    }

    inner class CategoryAdapter :
        BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category) {
        override fun convert(holder: BaseViewHolder, item: Category) {
            holder.setText(R.id.tvName, item.name)
            holder.getView<TextView>(R.id.tvName).setOnClickListener {
                viewModel.getCategoryDetail(item.id)
            }
        }
    }

    class CategoryDetailAdapter :
        BaseQuickAdapter<CategoryDetailData, BaseViewHolder>(R.layout.item_category_detail) {

        override fun convert(holder: BaseViewHolder, item: CategoryDetailData) {
            holder.setText(R.id.tvName, item.name)
            holder.getView<ShapeableImageView>(R.id.sbImgCover).load(item.cover_path)
        }

    }
}