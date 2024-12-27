package com.github.meo209.archer.ui.impl.clickgui

import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Range
import com.github.meo209.archer.ui.impl.clickgui.renderers.*
import kotlinx.atomicfu.AtomicRef
import java.awt.Color

object SettingRendererFactory {
    
    fun getRenderer(atomicRef: AtomicRef<*>): SettingRenderer<*>? {
        return when (atomicRef.value!!::class) {
            Boolean::class -> BooleanSettingRenderer()
            Int::class -> IntSettingRenderer()
            Float::class -> FloatSettingRenderer()
            Double::class -> DoubleSettingRenderer()
            String::class -> StringSettingRenderer()
            Color::class -> ColorSettingRenderer()
            Keybind::class -> KeybindSettingRenderer()
            Range::class -> RangeSettingRenderer()
            else -> {
                null
            }
        }
    }
}