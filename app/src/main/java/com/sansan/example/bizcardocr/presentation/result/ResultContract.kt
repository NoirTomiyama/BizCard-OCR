package com.sansan.example.bizcardocr.presentation.result

import com.sansan.example.bizcardocr.domain.model.BizCardInfo
import com.sansan.example.bizcardocr.domain.model.OcrState

interface ResultContract {
    interface View {
        fun onOcrSuccess(ocrResult: BizCardInfo)
        fun onOcrFailure(ocrState: OcrState)
    }

    interface Presenter {
        fun onStart(rawImage: ByteArray)
    }
}
