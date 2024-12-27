package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.ModuleProperty
import kotlin.reflect.KClass

object ElementRegistry {

    private val elements = mapOf<KClass<*>, ClickGuiElement<*>>(
        Boolean::class to BooleanElement(),
    )

    fun getElement(property: ModuleProperty<*>): ClickGuiElement<*>? {
        return elements[property.value!!::class]
    }
    
}