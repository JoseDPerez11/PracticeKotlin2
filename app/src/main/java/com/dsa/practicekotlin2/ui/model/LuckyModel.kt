package com.dsa.practicekotlin2.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LuckyModel (
    @DrawableRes val image: Int,
    @StringRes val text: Int
)