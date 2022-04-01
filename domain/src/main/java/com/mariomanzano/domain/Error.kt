package com.mariomanzano.domain

sealed interface Error {
    class Server(val code: Int) : Error
    object Connectivity : Error
    object NoData : Error
    class Unknown(val message: String) : Error
}