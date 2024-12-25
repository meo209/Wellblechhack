package com.github.meo209.archer.utils

import net.minecraft.item.ItemStack

// Static storage for previous stacks 
object PreviousStackStorage {
    private val previousStacks: Array<ItemStack?> =
        arrayOfNulls(41) // 36 inventory slots + 5 armor/offhand slots

    init {
        // Initialize all elements to null
        for (i in previousStacks.indices) {
            previousStacks[i] = null
        }
    }

    fun getStack(slot: Int): ItemStack? {
        return previousStacks[slot]
    }

    fun setStack(slot: Int, stack: ItemStack?) {
        previousStacks[slot] = stack
    }
}