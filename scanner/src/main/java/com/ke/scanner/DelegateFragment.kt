package com.ke.scanner


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import io.reactivex.subjects.PublishSubject


class DelegateFragment : Fragment() {


    lateinit var scanResultSubject : PublishSubject<ScanResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun start() {

        scanResultSubject = PublishSubject.create()

        val permission = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startScanner()
        } else {
            requestCameraPermission()
        }
    }


    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val grantResult = grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (grantResult) {
            startScanner()
        } else {
            scanResultSubject.onNext(ScanResult(status = ScanResultStatus.NoCameraPermission))
            scanResultSubject.onComplete()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                val text = data?.getStringExtra("qr_code_text")
                scanResultSubject.onNext(ScanResult(status = ScanResultStatus.Success, text = text))
                scanResultSubject.onComplete()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                scanResultSubject.onNext(ScanResult(status = ScanResultStatus.Cancel))
                scanResultSubject.onComplete()
            }
        }
    }


    private fun startScanner() {
        val intent = Intent(context!!, QRCodeScannerActivity::class.java)
        startActivityForResult(intent, 101)
    }

}
