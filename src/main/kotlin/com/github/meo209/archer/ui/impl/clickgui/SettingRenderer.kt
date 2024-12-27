package com.github.meo209.archer.ui.impl.clickgui

import com.github.meo209.archer.features.module.Module
import kotlinx.atomicfu.AtomicRef

interface SettingRenderer<T> {
    
    fun render(ref: AtomicRef<T>, name : String, module: Module, screen: ClickGuiScreen)
    
}