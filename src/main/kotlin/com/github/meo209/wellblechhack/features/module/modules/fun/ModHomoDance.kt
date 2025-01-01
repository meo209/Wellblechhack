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

package com.github.meo209.wellblechhack.features.module.modules.`fun`

import com.github.meo209.wellblechhack.events.ClientTickEvent
import com.github.meo209.wellblechhack.features.module.Category
import com.github.meo209.wellblechhack.features.module.Module

object ModHomoDance : Module("HomoDance", Category.Fun) {

    private val lyrics = """
        Der Christian konnt' gut tanzen
        Und er hatte volles Haar
        Sein Gang war feminin
        Und Werfen nicht sein Ding
        Er liebte schon seit Jahren
        Das Mädchen Kolumnia
        Doch die ging mit Muskel John
        Um ihr irgendwie zu imponieren
        Erfand er den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Als Columnia mitbekam'
        Was der Homo Dance vermag
        Verließ sie Muskel John
        Und ließ den Christian ran
        Plötzlich fühlte sie sich beschützt und sicher
        In seinen schlanken Armen
        Sie gingen auf den Dance Floor
        Da waren beide so überglücklich
        Das sie sich endlich hatten
        Schuld war nur der
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        (...)
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
        Homo Dance (Homo Dance)
        Alle tanzen den Homo Dance
    """.trimIndent()
        .trim()
        .split("\n")
        .toTypedArray()

    var waitTicks = int("waitTicks")

    override fun init() {
        //EventBus.global().function<ClientTickEvent>(::onTick) { enabled && inGame }
    }

    private var ticks = 0
    private var line = 0

    private fun onTick(event: ClientTickEvent) {
        if (ticks >= waitTicks.value) {
            if (line < lyrics.size) {
                network?.sendChatMessage(lyrics[line])
                line++
            } else
                line = 0
            ticks = 0
        }
        ticks++
    }

}