package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<Button>(R.id.btn_vote_director).setOnClickListener {
            val intent = Intent(this, VoteDirectorActivity::class.java)
            startActivity(intent)
        }
    }
}
