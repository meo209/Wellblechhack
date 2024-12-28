package com.github.meo209.archer.ui.impl.elements

import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.features.module.specific.RangedValue
import com.github.meo209.archer.ui.impl.ClickGuiElement
import imgui.ImGui.*
import imgui.type.ImFloat
import kotlinx.atomicfu.AtomicRef

class RangedValueElement : ClickGuiElement<RangedValue> {

    override fun draw(ref: AtomicRef<RangedValue>, property: ModuleProperty<RangedValue>) {
        val float = ImFloat(ref.value.value)
        val range = ref.value.range

        if (sliderFloat(property.name, float.data, range.start, range.endInclusive)) {
            ref.value = RangedValue(float.get(), range.start..range.endInclusive)
        }
    }
}