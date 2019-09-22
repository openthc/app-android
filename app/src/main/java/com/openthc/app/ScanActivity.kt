package com.openthc.app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
//import com.google.zxing.BarcodeFormat

//import kotlinx.android.synthetic.main.activity_auth_scan.*
import kotlinx.android.synthetic.main.content_auth_scan.*
//import com.google.zxing.Result
//import me.dm7.barcodescanner.zxing.ZXingScannerView

import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions

class ScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

		FirebaseApp.initializeApp(this)

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_CODE_39,
                FirebaseVisionBarcode.FORMAT_CODE_128,
                FirebaseVisionBarcode.FORMAT_QR_CODE,
                FirebaseVisionBarcode.FORMAT_PDF417
            )
            .build()

		val detector = FirebaseVision.getInstance()
			.getVisionBarcodeDetector(options)

        setContentView(R.layout.content_auth_scan)
//        setContentView(R.layout.activity_auth)
//        setSupportActionBar(toolbar)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val Scan = BarcodeDetector.Builder(applicationContext)
//            .setBarcodeFormats(Barcode.ALL_FORMATS)
//            .build()
//
////        Scan.setFormats(listOf(BarcodeFormat.QR_CODE))
//        Scan.setAutoFocus(true)
//        Scan.setLaserColor(R.color.colorAccent)
//        Scan.setMaskColor(R.color.colorAccent)
//        Scan.startCamera()
//        Scan.setResultHandler(this)

    }

    /**
     * Handler for ZXing
     */
//    override fun handleResult(p0: Result?) {
//
//        if (p0 != null) {
//            // Attempt Login?
//
//            //startActivity(ScannedActivity.getScannedActivity(this, p0.text))
//            //resumeCamera()
//        }
//    }

}
