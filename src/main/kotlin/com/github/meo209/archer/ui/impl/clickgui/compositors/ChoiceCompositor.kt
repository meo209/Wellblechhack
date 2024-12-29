/*
 * This file is part of Archer (https://github.com/meo209/Archer)
 * Copyright (c) 2024 - meo209
 *
 * Archer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Archer program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.meo209.archer.ui.impl.clickgui.compositors

import com.github.meo209.archer.features.module.config.parameter.Parameter
import com.github.meo209.archer.features.module.config.parameter.ParameterType
import com.github.meo209.archer.features.module.config.types.Choice
import com.github.meo209.archer.ui.impl.clickgui.ParameterCompositor
import imgui.ImGui.*
import imgui.type.ImInt

class ChoiceCompositor : ParameterCompositor<Choice>(ParameterType.CHOICE) {

    override fun render(parameter: Parameter<Choice>) {
        val choice = parameter.value
        
        // Construct variables for ImGui
        val imIndex = ImInt(choice.options.indexOf(choice.current))
        val items = choice.options.map { it.name }.toTypedArray()
        
        if (combo(parameter.name, imIndex, items, items.size)) {
            val new = choice.options[imIndex.get()]
            println("Setting ${parameter.name} from ${parameter.value.current.name} to ${new.name}")
            parameter.value.current = new
        }
    }

}