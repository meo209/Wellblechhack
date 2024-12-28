package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.ModuleProperty
import kotlinx.atomicfu.AtomicRef
import java.util.concurrent.atomic.AtomicReference

interface ClickGuiElement<T : Any> {
    
    fun draw(ref: AtomicRef<T>, property: ModuleProperty<T>)
    
    // We can't call draw directly because <T> is star projected.
    fun draw_(ref: Any, property: Any) {
        draw(ref as AtomicRef<T>, property as ModuleProperty<T>)
    }
    
}