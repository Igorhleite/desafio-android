package com.picpay.desafio.android.domain

interface UseCase {
    interface Empty<Type> {
        suspend operator fun invoke(): Type
    }

    interface Params<Type, in Params> {
        suspend operator fun invoke(params: Params): Type
    }
}