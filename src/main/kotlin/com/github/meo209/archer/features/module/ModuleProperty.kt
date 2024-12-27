package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import java.io.Serializable
import kotlin.reflect.KMutableProperty

class ModuleProperty<T>(val name: String, @JsonProperty("value") var value: T) :
    ReadWriteProperty<Module, T>, Serializable {
        
    lateinit var kProperty: KMutableProperty<*>

    override fun getValue(thisRef: Module, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Module, property: KProperty<*>, value: T) {
        kProperty = property as KMutableProperty<*>
        this.value = value
    }
}