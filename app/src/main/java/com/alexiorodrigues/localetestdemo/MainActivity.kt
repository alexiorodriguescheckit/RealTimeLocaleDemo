package com.alexiorodrigues.localetestdemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        changeLangPT.setOnClickListener {
            LocaleHelper.updateToLanguage(this, "pt")

        }

        changeLangUK.setOnClickListener {
            LocaleHelper.updateToLanguage(this, "en")
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }

}
