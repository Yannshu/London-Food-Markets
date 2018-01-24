package com.yannshu.londonfoodmarkets.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.Icon
import kotlinx.android.synthetic.main.item_icon.view.iconDescriptionTextView
import kotlinx.android.synthetic.main.item_icon.view.iconImageView

class IconsAdapter(context: Context, private val icons: List<Icon>) : RecyclerView.Adapter<IconsAdapter.IconViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IconViewHolder {
        val view = layoutInflater.inflate(R.layout.item_icon, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder?, position: Int) {
        holder?.bind(icons[position], listener)
    }

    override fun getItemCount(): Int = icons.size

    class IconViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(icon: Icon, listener: Listener?) {
            itemView.iconImageView.setImageResource(icon.icon)
            itemView.iconDescriptionTextView.setText(icon.description)
            if (listener != null) {
                itemView.setOnClickListener { listener.onClick(icon) }
            }
        }
    }

    interface Listener {
        fun onClick(icon: Icon)
    }
}