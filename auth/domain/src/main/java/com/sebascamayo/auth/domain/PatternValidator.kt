package com.sebascamayo.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}