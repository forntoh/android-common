package com.forntoh.common.utils

import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.ImageLoader
import coil.request.ImageRequest
import com.forntoh.common.internal.view.InsetDecoration

@BindingAdapter(value = ["setAutoCompleteItems"])
fun AutoCompleteTextView.bindAutoCompleteItems(values: List<String>?) {
    values?.let { setAdapter(ArrayAdapter(this.context, android.R.layout.simple_list_item_1, values)) }
}

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
        layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
    }
}

@BindingAdapter(value = ["insetPadding"])
fun RecyclerView.bindRecyclerViewInsetDecor(padding: Int = 0) {
    this.run { this.addItemDecoration(InsetDecoration(padding)) }
}

@BindingAdapter(value = ["setAdapter"])
fun ExpandableListView.bindExpandableListViewAdapter(adapter: ExpandableListAdapter) {
    this.run { this.setAdapter(adapter) }
}

@BindingAdapter(value = ["setImage", "setLoader"])
fun ImageView.bindImage(icon: Int, imageLoader: ImageLoader) {
    this.run { load(imageLoader, icon) }
}

@BindingAdapter(value = ["setImage", "setLoader", "setRequestBuilder"])
fun ImageView.bindImageWithHeaders(icon: String?, imageLoader: ImageLoader, request: ImageRequest.Builder?) {
    this.run {
        if (icon != null && request != null) {
            load(imageLoader, request, icon)
        }
    }
}

@BindingAdapter(value = ["setColorSchemeColors"])
fun SwipeRefreshLayout.bindColorSchemeColors(colors: Int?) {
    this.run { colors?.let { setColorSchemeColors(*context.resources.getIntArray(colors)) } }
}

@BindingAdapter(value = ["onRefresh"])
fun SwipeRefreshLayout.bindRefreshListener(action: () -> Unit) {
    this.run { setOnRefreshListener { action.invoke() } }
}