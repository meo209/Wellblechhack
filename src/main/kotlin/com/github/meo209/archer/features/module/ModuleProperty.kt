package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import java.io.Serializable
import kotlin.reflect.KMutableProperty

class ModuleProperty<T : Any>(@JsonProperty("name") val name: String, @JsonProperty("value") var value: T) {

    operator fun getValue(thisRef: Module, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Module, property: KProperty<*>, value: T) {
        this.value = value
    }
}