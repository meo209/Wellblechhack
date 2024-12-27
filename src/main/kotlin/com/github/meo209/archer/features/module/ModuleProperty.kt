package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import java.io.Serializable

class ModuleProperty<T>(val name: String, defaultValue: T) :
    ReadWriteProperty<Module, T>, Serializable {
    @JsonProperty("value")
    private var value: T = defaultValue

    override fun getValue(thisRef: Module, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Module, property: KProperty<*>, value: T) {
        this.value = value
    }
}