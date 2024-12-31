package com.github.meo209.archer.features.module.modules.`fun`

import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.modules.combat.ModAutoTotem
import com.github.meo209.keventbus.EventBus

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

    var keybind by keybinding("Keybind")
    var waitTicks by int("waitTicks")

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind })

        EventBus.global().function<ClientTickEvent>(::onTick) { enabled && inGame }
    }

    private var ticks = 0
    private var line = 0

    private fun onTick(event: ClientTickEvent) {
        if (ticks >= waitTicks) {
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