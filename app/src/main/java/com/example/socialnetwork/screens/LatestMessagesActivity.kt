package com.example.socialnetwork.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.ActivityLatesMessagesBinding

class LatestMessagesActivity : AppCompatActivity(R.layout.activity_lates_messages) {

    private val binding: ActivityLatesMessagesBinding by viewBinding(ActivityLatesMessagesBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnNewMessage.setOnClickListener {
            val intent = Intent(this, NewMessageActivity::class.java)
            startActivity(intent)
        }
    }
}