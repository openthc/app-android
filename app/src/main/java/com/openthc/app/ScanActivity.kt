package com.openthc.app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.BarcodeFormat

import kotlinx.android.synthetic.main.activity_auth_scan.*
import kotlinx.android.synthetic.main.content_auth_scan.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_scan)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)


        val Scan = BarcodeDetector.Builder(applicationContext)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

    }

    /**
     * Handler for ZXing
     */
    override fun handleResult(p0: Result?) {

        if (p0 != null) {
            // Attempt Login?

            //startActivity(ScannedActivity.getScannedActivity(this, p0.text))
            //resumeCamera()
        }
    }

}
