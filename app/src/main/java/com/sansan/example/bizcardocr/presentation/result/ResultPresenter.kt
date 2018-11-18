package com.sansan.example.bizcardocr.presentation.result

import com.sansan.example.bizcardocr.domain.model.OcrState
import com.sansan.example.bizcardocr.domain.usecase.OcrUseCase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class ResultPresenter(
        private val resultView: ResultContract.View
): ResultContract.Presenter {

    override fun onStart(rawImage: ByteArray) {
        request(rawImage)
    }

    private fun request(rawImage: ByteArray) {
        launch(CommonPool) {
            val ocrResult = OcrUseCase.bizCardOcr(rawImage)
            launch(UI) {
                when(ocrResult) {
                    is OcrUseCase.OcrResult.Success -> resultView.onOcrSuccess(ocrResult.bizCardInfo)
                    is OcrUseCase.OcrResult.RequestError -> resultView.onOcrFailure(OcrState.REQUEST_FAILURE)
                    is OcrUseCase.OcrResult.NetworkError -> resultView.onOcrFailure(OcrState.NETWORK_FAILURE)
                }
            }
        }
    }
}
