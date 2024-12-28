package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.features.module.specific.Keybinding
import com.github.meo209.archer.ui.impl.elements.*
import kotlin.reflect.KClass

object ElementRegistry {

    private val elements = mapOf<KClass<*>, ClickGuiElement<*>>(
        Boolean::class to BooleanElement(),
        Int::class to IntElement(),
        Keybinding::class to KeybindingElement(),
    )

    fun getElement(property: ModuleProperty<*>): ClickGuiElement<*>? {
        return elements[property.value::class]
    }
    
}