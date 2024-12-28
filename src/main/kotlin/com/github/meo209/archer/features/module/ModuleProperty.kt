package com.github.meo209.archer.features.module

import kotlin.reflect.KProperty

class ModuleProperty<T>(
    val name: String,
    var value: T,
    var enabled: Boolean = true
) {

    operator fun getValue(m: Module, p: KProperty<*>): T {
        return value
    }

    operator fun setValue(m: Module, p: KProperty<*>, v: T) {
        value = v
    }

}