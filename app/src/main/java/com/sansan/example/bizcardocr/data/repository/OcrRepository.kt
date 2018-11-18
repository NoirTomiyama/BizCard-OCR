package com.sansan.example.bizcardocr.data.repository

import android.util.Base64
import com.sansan.example.bizcardocr.data.network.ApiProvider
import com.sansan.example.bizcardocr.domain.entity.googleapis.vision.Feature
import com.sansan.example.bizcardocr.domain.entity.googleapis.vision.ImagesRequest
import com.sansan.example.bizcardocr.domain.entity.googleapis.vision.ImagesResponse
import kotlinx.coroutines.experimental.async

object OcrRepository {

    suspend fun ocrRequest(rawImage: ByteArray): ImagesResponse? {
        val encodedImage = Base64.encodeToString(rawImage, Base64.NO_WRAP)
        val request = ImagesRequest.createSingleRequest(encodedImage, Feature.Type.DOCUMENT_TEXT_DETECTION)
        val response = async { ApiProvider.visionApi.images(request).execute() }.await()
        return when (response.code()){
            200 -> response.body()
            else -> null
        }
    }
}