package com.pokerface.novel.domain.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pokerface.common.base.BaseViewModel
import com.pokerface.novel.domain.main.repository.MainRepository
import com.pokerface.novel.domain.main.repository.bean.Category

class HomeViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val categoryLD = MutableLiveData<MutableList<Category>>()

    fun getCategory() {
        enqueue({ mainRepository.getCategory() }) {
            onSuccess {
                categoryLD.value = it
            }
        }
    }
}