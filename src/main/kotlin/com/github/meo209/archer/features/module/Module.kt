package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class Module(@JsonIgnore val name: String, @JsonIgnore val category: Category) {

    open var enabled = false

    abstract fun init()

    fun toggle() {
        enabled = !enabled
    }
    
    open fun stop() {
        ModuleIO.save(this)
    }

    fun load() {
        ModuleIO.load(this)
    }

    fun save() {
        ModuleIO.save(this)
    }
}