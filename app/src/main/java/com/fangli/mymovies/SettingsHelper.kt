package com.fangli.mymovies

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import com.fangli.data.Movie

class SettingsHelper(val context: Context) {
    val sp = context.applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val TYPE = "type"
    // popular: 0, top_rated: 1
    val options = arrayOf(Movie.POPULAR, Movie.TOP_RATED)
    val titles = arrayOf(context.getString(R.string.popular), context.getString(R.string.toprated))
    
    
    fun showOptionDialog(activity: Activity, callback: SettingsCallback){
        androidx.appcompat.app.AlertDialog.Builder(activity)
            .setTitle(context.getString(R.string.order_by))
            .setSingleChoiceItems(titles, getSelectedOrderIndex()
            ) { dialogInterface, i ->
                setSelectedOrder(i)
                dialogInterface.dismiss()
                callback.OnOrderChanged(indexToOrder(i))
            }.show()
    }

    private fun indexToOrder(index: Int): Order{
        if (index==0)
            return Order.POPULAR
        else
            return Order.TOP_RATED
    }

    private fun getSelectedOrderIndex(): Int{
        return sp.getInt(TYPE, 0)
    }

    fun getSelectedOrder(): Order{
        return if (sp.getInt(TYPE, 0) == 0) Order.POPULAR else Order.TOP_RATED
    }

    fun setSelectedOrder(order: Order){
        setSelectedOrder(if (order == Order.POPULAR) 0 else 1)
    }
    private fun setSelectedOrder(orderIndex: Int) {
        sp.edit().putInt(TYPE, orderIndex).apply()
    }
}

interface SettingsCallback {
    fun OnOrderChanged(order: Order)
}

enum class Order {
    POPULAR, TOP_RATED
}