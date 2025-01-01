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

package com.github.meo209.wellblechhack.ui.clickgui.compositors

import com.github.meo209.wellblechhack.config.module.Setting
import com.github.meo209.wellblechhack.config.module.SettingType
import com.github.meo209.wellblechhack.ui.clickgui.SettingCompositor
import imgui.ImGui.*
import imgui.type.ImInt

class InputIntCompositor : SettingCompositor<Int>(SettingType.INT) {

    override fun render(parameter: Setting<Int>) {
        val imInt = ImInt(parameter.value)
        if (inputInt(parameter.name, imInt))
            parameter.value = imInt.get()
    }

}