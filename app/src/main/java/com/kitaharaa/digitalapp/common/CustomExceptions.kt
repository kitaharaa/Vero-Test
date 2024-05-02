package com.kitaharaa.digitalapp.common

class InvalidRemoteData: Exception("Remote data null")

class AuthorizationParameterException(msg: String): Exception(msg)