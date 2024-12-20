package com.github.meo209.archer.features.module.serialization

import com.github.meo209.archer.features.module.Include
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class ModuleExclusionStrategy: ExclusionStrategy {

    override fun shouldSkipField(attributes: FieldAttributes): Boolean =
        attributes.getAnnotation(Include::class.java) == null

    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
}