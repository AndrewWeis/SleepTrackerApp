package com.example.android.sleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sleeptracker.R
import com.example.android.sleeptracker.convertDurationToFormatted
import com.example.android.sleeptracker.convertNumericQualityToString
import com.example.android.sleeptracker.database.SleepNight


/**
 * An adapter that provides a list of [SleepNight] to a RecyclerView
 */
class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    /**
     * Part of RecyclerView adapter, called when RecyclerView needs to show an item
     *
     * The ViewHolder passed may be recycled, so make sure that this sets any properties that
     * may have been set previously
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    /**
     * Part of RecyclerView adapter, called when RecyclerView need a new [ViewHolder],
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as when on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val res = itemView.context.resources!!

        private val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        private val quality: TextView = itemView.findViewById(R.id.quality_string)
        private val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        fun bind(item: SleepNight) {
            sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_sleep_night, parent, false)

                return ViewHolder(view)
            }
        }

    }

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }
    }
}