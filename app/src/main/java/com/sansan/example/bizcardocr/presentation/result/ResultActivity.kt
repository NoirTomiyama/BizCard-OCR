package com.sansan.example.bizcardocr.presentation.result

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.widget.Toast
import com.sansan.example.bizcardocr.BizCardOCRApplication
import com.sansan.example.bizcardocr.R
import com.sansan.example.bizcardocr.domain.model.BizCardInfo
import com.sansan.example.bizcardocr.domain.model.OcrState
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), ResultContract.View {

    private val presenter: ResultContract.Presenter by lazy { ResultPresenter(this) }
    private val adapter: ResultAdapter = ResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bizCardOcrApplication = application as BizCardOCRApplication
        val rawBitmap = bizCardOcrApplication.tempRawPicture!!
        presenter.onStart(rawBitmap)
        initBizCardInfoView(rawBitmap)
        initToolBar()
    }

    override fun onOcrSuccess(ocrResult: BizCardInfo) {
        adapter.updateOcrSuccess(ocrResult)
    }

    override fun onOcrFailure(ocrState: OcrState) {
        when(ocrState) {
            OcrState.REQUEST_FAILURE -> Toast.makeText(this, R.string.result_activity_toast_requesterror, Toast.LENGTH_SHORT).show()
            OcrState.NETWORK_FAILURE -> Toast.makeText(this, R.string.result_activity_toast_networkerror, Toast.LENGTH_SHORT).show()
        }
        adapter.updateOcrFailed(ocrState)
    }

    private fun initBizCardInfoView(rawBitmap: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(rawBitmap, 0, rawBitmap.size)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        bizcardInfo.addItemDecoration(itemDecoration)
        bizcardInfo.setHasFixedSize(true)
        adapter.loading(bitmap)
        bizcardInfo.adapter = adapter
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        fun createIntent(activity: Activity): Intent =
                Intent(activity, ResultActivity::class.java)
    }
}
