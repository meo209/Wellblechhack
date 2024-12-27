package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.ModuleProperty
import kotlinx.atomicfu.AtomicRef

interface ClickGuiElement<T> {
    
    fun draw(ref: AtomicRef<T>, property: ModuleProperty<T>)
    
    
    fun draw(ref: Any, property: Any) {
        draw(ref as AtomicRef<T>, property as ModuleProperty<T>)
    }
    
}