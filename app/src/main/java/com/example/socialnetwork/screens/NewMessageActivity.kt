package com.example.socialnetwork.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.ActivityNewMessageBinding

class NewMessageActivity : AppCompatActivity(R.layout.activity_new_message) {

    private val binding: ActivityNewMessageBinding by viewBinding(ActivityNewMessageBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = GroupAdapter<ViewHolder>
        binding.rvMessage.adapter
    }
}