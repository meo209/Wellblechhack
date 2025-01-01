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

package com.github.meo209.wellblechhack.ui.clickgui

import com.github.meo209.wellblechhack.config.module.SettingType
import com.github.meo209.wellblechhack.ui.clickgui.compositors.*

enum class ECompositor(private val type: SettingType, private val compositor: SettingCompositor<*>) {

    //KEYBINDING(SettingType.KEYBINDING, KeybindingCompositor()),
    CHECKBOX(SettingType.BOOLEAN, CheckboxCompositor()),
    //STRING(SettingType.STRING, InputTextCompositor()),
    //CHOICE(SettingType.CHOICE, ChoiceCompositor()),
    //DOUBLE(SettingType.DOUBLE, InputDoubleCompositor()),
    //FLOAT(SettingType.FLOAT, InputFloatCompositor()),
    Int(SettingType.INT, InputIntCompositor());
    
    companion object {
        fun get(parameterType: SettingType): SettingCompositor<*> =
            entries
                .first { it.type == parameterType }
                .compositor
    }

}