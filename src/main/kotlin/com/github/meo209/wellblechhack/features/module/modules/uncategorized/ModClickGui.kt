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

package com.github.meo209.wellblechhack.features.module.modules.uncategorized

import com.github.meo209.wellblechhack.events.KeyPressEvent
import com.github.meo209.wellblechhack.features.module.Category
import com.github.meo209.wellblechhack.features.module.Module
import com.github.meo209.wellblechhack.ui.clickgui.ImClickGui
import com.github.meo209.keventbus.EventBus
import org.lwjgl.glfw.GLFW

/*
object ModClickGui : Module("ClickGui", Category.Uncategorized) {

    private val screen = ImClickGui()

    override fun init() {
        // Set the default keybind
        if (keybind == -1)
            keybind = GLFW.GLFW_KEY_RIGHT_SHIFT

        EventBus.global().handler(KeyPressEvent::class, {
            client.setScreen(screen)
        }, { it.key == keybind })
    }
}
 */