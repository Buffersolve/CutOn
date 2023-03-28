package com.buffersolve.cuton.feature.auth.data.remote.api.models

data class LoginModel(
    val login: String,
    val password: String,
    val devman: String,
    val devmod: String,
    val devavs: String,
    val devaid: String,
)