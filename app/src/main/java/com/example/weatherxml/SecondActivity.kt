package com.example.weatherxml

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherxml.utils.Constants

class SecondActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var webviewNews: WebView
    private lateinit var imageViewUrl: ImageView
    private lateinit var urlImageString: String
    private lateinit var urlNewsString: String
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        titleTextView = findViewById(R.id.titleSecond)
        webviewNews = findViewById(R.id.web_view)
        imageViewUrl = findViewById(R.id.image_view)
        backButton = findViewById(R.id.back_button)

        val data = intent.extras
        data?.let {
            titleTextView.text = it.getString(Constants.KEY_TITLE)
            urlNewsString = it.getString(Constants.KEY_URL)!!
            urlImageString = it.getString(Constants.KEY_IMAGE_URL)!!
        }

        webviewNews.settings.javaScriptEnabled = true
        webviewNews.webViewClient = WebViewClient()
        webviewNews.loadUrl(urlNewsString)

        Glide.with(this)
            .load(urlImageString)
            .error(R.drawable.ic_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewUrl)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
