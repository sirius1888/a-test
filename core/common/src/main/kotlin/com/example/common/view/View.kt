package com.example.common.view

import android.view.View

public fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

public fun View.gone() {
    this.visibility = View.GONE
}
public fun View.visible() {
    this.visibility = View.VISIBLE
}