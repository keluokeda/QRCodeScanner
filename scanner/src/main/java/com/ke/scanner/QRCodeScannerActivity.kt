package com.ke.scanner

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_qrcode_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRCodeScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    override fun handleResult(p0: Result) {

        val intent = Intent().apply {
            putExtra("qr_code_text", p0.text)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)


        back.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onResume() {
        super.onResume()
        scanner_view.setResultHandler(this)
        scanner_view.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scanner_view.stopCamera()
    }
}
