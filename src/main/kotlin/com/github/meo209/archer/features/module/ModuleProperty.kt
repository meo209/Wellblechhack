package com.github.meo209.archer.features.module

import kotlin.reflect.KProperty

class ModuleProperty<T: Any>(
    val name: String,
    var value: Any, // Should be <T>, however, this works so imma leave it.
    var enabled: Boolean = true
) {
    operator fun getValue(m: Module, p: KProperty<*>): T {
        return value as T
    }

    operator fun setValue(m: Module, p: KProperty<*>, v: T) {
        value = v
    }

}