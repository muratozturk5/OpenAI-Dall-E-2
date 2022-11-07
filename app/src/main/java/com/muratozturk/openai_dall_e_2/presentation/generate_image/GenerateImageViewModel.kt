package com.muratozturk.openai_dall_e_2.presentation.generate_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.openai_dall_e_2.common.Constants.SIZE_1024
import com.muratozturk.openai_dall_e_2.common.Constants.SIZE_256
import com.muratozturk.openai_dall_e_2.common.Constants.SIZE_512
import com.muratozturk.openai_dall_e_2.common.Resource
import com.muratozturk.openai_dall_e_2.common.Sizes
import com.muratozturk.openai_dall_e_2.data.model.GeneratedImage
import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import com.muratozturk.openai_dall_e_2.domain.use_case.GenerateImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(private val generateImageUseCase: GenerateImageUseCase) :
    ViewModel() {


    private val _state = MutableStateFlow<Resource<GeneratedImage>?>(null)
    val state = _state.asStateFlow()

    fun generateImage(prompt: String, n: Int, size: Sizes) = viewModelScope.launch {

        val sizeString = when (size) {
            Sizes.SIZE_256 -> SIZE_256
            Sizes.SIZE_512 -> SIZE_512
            Sizes.SIZE_1024 -> SIZE_1024
        }

        generateImageUseCase(RequestBody(n, prompt, sizeString)).collect {
            _state.emit(it)
        }
    }


}