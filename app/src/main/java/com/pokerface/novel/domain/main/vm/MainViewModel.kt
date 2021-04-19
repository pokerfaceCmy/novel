package com.pokerface.novel.domain.main.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.pokerface.common.base.BaseViewModel
import com.pokerface.novel.domain.main.repository.MainRepository
import com.pokerface.novel.domain.main.repository.bean.Category

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/13 10:51
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
class MainViewModel @ViewModelInject constructor(
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