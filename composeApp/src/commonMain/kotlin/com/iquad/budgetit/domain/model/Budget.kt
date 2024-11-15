package com.iquad.budgetit.domain.model

/**
 * This defines the Budget data model which represents the core business entity
 * for storing budget information.
 */
data class Budget (val amount: Double, val currency: Currency)