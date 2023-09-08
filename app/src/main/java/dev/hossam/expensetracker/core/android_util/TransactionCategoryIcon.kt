package dev.hossam.expensetracker.core.android_util

import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.enums.CategoryEnum.*

object TransactionCategoryIcon {

    val categoryIconMap: Map<String, Int> by lazy {
        hashMapOf(
            HOUSING.value to R.drawable.home,
            TRANSPORTATION.value to R.drawable.truck,
            FOOD.value to R.drawable.shopping_cart,
            UTILITIES.value to R.drawable.zap,
            INSURANCE.value to R.drawable.shield,
            HEALTHCARE.value to R.drawable.activity,
            SAVING_DEBTS.value to R.drawable.pocket,
            PERSONAL_SPENDING.value to R.drawable.user,
            ENTERTAINMENT.value to R.drawable.tv,
            MISCELLANEOUS.value to R.drawable.feather,
        )
    }

}