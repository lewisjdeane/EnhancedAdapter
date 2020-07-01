package com.lewisdeane.enhancedadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

/**
 * A RecyclerView adapter with some extra features. This adapter can:
 *
 * - Automatically observe a LiveData list of data.
 * - Remove the boilerplate when dealing with RecyclerView adapters.
 * - Display a placeholder view when there are no elements in the list.
 *
 * @property context Android context - required for inflating the items in the RecyclerView.
 * @property liveItems A [LiveData] list with elements of type T.
 * @property placeholder An optional view that is shown when there are no elements in the list.
 * @property onItemClickedListener An optional listener that captures click events.
 */
abstract class EnhancedAdapter<T>(
    private val context: Context,
    private val liveItems: LiveData<List<T>>,
    private val placeholder: View? = null,
    private val onItemClickedListener: OnItemClickedListener<T>? = null
) : RecyclerView.Adapter<EnhancedAdapter<T>.ViewHolder>() {

    interface OnItemClickedListener<T> {
        fun onClick(item: T)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val observer = Observer<List<T>> { items = it }

    var items: List<T> = emptyList()
        private set(value) {
            placeholder?.isVisible = value.isEmpty()
            field = value
            notifyDataSetChanged()
        }

    init {
        placeholder?.isVisible = false
        liveItems.observeForever(observer)
    }

    /** The layout to inflate for each item in the [RecyclerView]. */
    abstract val layoutRes: Int

    /**
     * Called when an item in the list is bound to an view in the RecyclerView.
     *
     * @property view The view we are binding an item in the list to.
     * @property item The item in the list being bound.
     */
    abstract fun bind(view: View, item: T)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnhancedAdapter<T>.ViewHolder {
        val itemView = inflater.inflate(layoutRes, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        liveItems.removeObserver(observer)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal fun bind(item: T) {
            bind(itemView, item)

            if (onItemClickedListener != null) {
                itemView.isEnabled = true
                itemView.setOnClickListener { onItemClickedListener.onClick(item) }
            } else {
                itemView.isEnabled = false
                itemView.setOnClickListener(null)
            }
        }
    }
}
