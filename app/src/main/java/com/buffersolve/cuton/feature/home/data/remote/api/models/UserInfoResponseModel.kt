package com.buffersolve.cuton.feature.home.data.remote.api.models

data class UserInfoResponseModel(
    val balance: Int,
    val bonusTitle: String,
    val bonusToday: Int,
    val bonusTotal: Int,
    val firstname: String,
    val lastname: String,
    val pmFirstname: String,
    val pmLastname: String,
    val pmTelephone: String,
    val tsFirstname: String,
    val tsLastname: String,
    val tsTelephone: String,
    val userId: Int
)