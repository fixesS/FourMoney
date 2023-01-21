package com.fixess.fourmoney.common

interface EventHandler<E> {
    fun obtainEvent(event : E)
}