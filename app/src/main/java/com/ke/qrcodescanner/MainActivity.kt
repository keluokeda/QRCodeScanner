package com.ke.qrcodescanner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ke.scanner.RxScanner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener { _ ->
            RxScanner(this)
                .startScan()
                .doOnComplete {
                    Log.d("TAGTAG","doOnComplete")
                }
                .subscribe {
                    textView.text = it.toString()
                }
        }
    }
}
