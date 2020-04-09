package com.evolutio.domain.ext

fun String?.isNotNullOrNotBlankLet(): Boolean{
    this?.let {
        return !this.isBlank()
    }
    return false
}

