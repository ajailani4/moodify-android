package com.ajailani.moodify.util

import androidx.recyclerview.widget.DiffUtil
import com.ajailani.moodify.domain.model.MoodItem

class DiffCallback : DiffUtil.ItemCallback<MoodItem>() {
    override fun areItemsTheSame(oldItem: MoodItem, newItem: MoodItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MoodItem, newItem: MoodItem): Boolean {
        return oldItem == newItem
    }
}