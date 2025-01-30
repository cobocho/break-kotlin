package com.example.musicapp

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox

sealed class Screen(val title: String, val route: String) {

    sealed class DrawerScreen(
        val dTitle: String,
        val dRoute: String,
        @DrawableRes val icon: Int
    ): Screen(dTitle, dRoute) {
        object Account: DrawerScreen(
            "Account",
            "account-screen",
            R.drawable.baseline_person_24
        )
        object Subscription: DrawerScreen(
            "Subscription",
            "subscribe-screen",
            R.drawable.baseline_library_music_24
        )
        object AddAccount: DrawerScreen(
            "Add Screen",
            "add-screen",
            R.drawable.baseline_person_add_24
        )
    }
}