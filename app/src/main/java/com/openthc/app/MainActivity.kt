package com.openthc.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat.startActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var host = "";

    private val REQUEST_AUTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        webView.webViewClient = MainWebViewClient(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true

        // Start the AuthActivity
        val intent = android.content.Intent(this, AuthActivity::class.java).apply {
            // putExtra(EXTRA_MESSAGE, message)
        }

        startActivityForResult(intent, REQUEST_AUTH)

    }

    /**
     * The Launched Intent can bass back data here
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_AUTH -> {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
//                    val returnValue = data!!.getStringExtra("username")
                    host  = data!!.getStringExtra("hostname")
                    webView.loadUrl(host + "/auth/open")
                }
            }
        }
    }



    private class MainWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

            if (Uri.parse(url).scheme == "zxing") {

                //val i0 = Intent()
                val intent = Intent(activity, ScanActivity::class.java).apply {
                    // Empty
                }
                activity.startActivity(intent)
                return true

//        if (Uri.parse(url).host == "www.example.com") {
//            // This is my web site, so do not override; let my WebView load the page
//            return false
//        }
//        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//            startActivity(this)
//        }

            }

            return false
        }
    }

}
