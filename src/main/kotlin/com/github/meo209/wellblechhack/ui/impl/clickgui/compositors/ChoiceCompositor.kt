/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.meo209.wellblechhack.ui.impl.clickgui.compositors

import com.github.meo209.wellblechhack.features.module.config.parameter.Parameter
import com.github.meo209.wellblechhack.features.module.config.parameter.ParameterType
import com.github.meo209.wellblechhack.ui.impl.clickgui.ParameterCompositor
import imgui.ImGui.*
import imgui.type.ImInt

/*
class ChoiceCompositor : ParameterCompositor<Choice>(ParameterType.CHOICE) {

    override fun render(parameter: Parameter<Choice>) {
        val choice = parameter.value
        
        // Construct variables for ImGui
        val imIndex = ImInt(choice.options.indexOf(choice.current))
        val items = choice.options.toTypedArray()
        
        if (combo(parameter.name, imIndex, items, items.size)) {
            val new = choice.options[imIndex.get()]
            println("Setting ${parameter.name} from ${parameter.value.current} to $new")
            parameter.value.current = new
        }
    }

}
 */