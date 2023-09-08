package dev.hossam.expensetracker.core.enums

import java.util.Arrays
import java.util.stream.Collectors

enum class CategoryEnum(val value: String) {

    HOUSING("Housing"),
    TRANSPORTATION("Transportation"),
    FOOD("Food"),
    UTILITIES("Utilities"),
    INSURANCE("Insurance"),
    HEALTHCARE("Healthcare"),
    SAVING_DEBTS("Saving & Debts"),
    PERSONAL_SPENDING("Personal Spending"),
    ENTERTAINMENT("Entertainment"),
    MISCELLANEOUS("Miscellaneous");

    fun getAllCategoryEnums(): List<String> {
        return Arrays.stream(CategoryEnum.values())
            .map { type -> type.value }
            .collect(Collectors.toList())
    }

}