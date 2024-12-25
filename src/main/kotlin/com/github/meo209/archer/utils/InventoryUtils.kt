package com.github.meo209.archer.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.SlotActionType

object InventoryUtils {

    private val client: MinecraftClient
        get() = MinecraftClient.getInstance()

    /**
     * Moves an item from the source slot to the destination slot.
     *
     * @param sourceSlot The slot to move the item from.
     * @param destSlot The slot to move the item to.
     */
    fun moveItem(sourceSlot: Int, destSlot: Int) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return

        interactionManager.clickSlot(
            player.playerScreenHandler.syncId,
            sourceSlot,
            destSlot,
            SlotActionType.SWAP,
            player
        )
    }

    /**
     * Finds the first slot containing the specified item.
     *
     * @param item The item to search for.
     * @return The slot index containing the item, or -1 if not found.
     */
    fun findItemSlot(item: Item): Int {
        val player = client.player ?: return -1
        val inventory = player.inventory

        // Search hotbar (slots 0-8) first
        for (slot in 0..8) {
            val stack = inventory.getStack(slot)
            if (stack.item == item) {
                return slot
            }
        }

        // Search main inventory (slots 9-35)
        for (slot in 9..35) {
            val stack = inventory.getStack(slot)
            if (stack.item == item) {
                return slot
            }
        }

        // Search offhand (slot 40)
        val offhandStack = inventory.getStack(40)
        if (offhandStack.item == item) {
            return 40
        }

        return -1
    }

    /**
     * Filters items in the inventory based on a predicate.
     *
     * @param predicate A function that takes an ItemStack and returns true if it matches the filter criteria.
     * @return A list of slot indices that contain items matching the predicate.
     */
    fun filterItems(predicate: (ItemStack) -> Boolean): List<Int> {
        val player = client.player ?: return emptyList()
        val inventory = player.inventory
        val matchingSlots = mutableListOf<Int>()

        // Search hotbar (slots 0-8)
        for (slot in 0..8) {
            val stack = inventory.getStack(slot)
            if (predicate(stack)) {
                matchingSlots.add(slot)
            }
        }

        // Search main inventory (slots 9-35)
        for (slot in 9..35) {
            val stack = inventory.getStack(slot)
            if (predicate(stack)) {
                matchingSlots.add(slot)
            }
        }

        // Search offhand (slot 40)
        val offhandStack = inventory.getStack(40)
        if (predicate(offhandStack)) {
            matchingSlots.add(40)
        }

        return matchingSlots
    }

    /**
     * Moves the first matching item to the specified destination slot.
     *
     * @param predicate A function that takes an ItemStack and returns true if it matches the filter criteria.
     * @param destSlot The slot to move the item to.
     * @return True if an item was moved, false otherwise.
     */
    fun moveFirstMatchingItem(predicate: (ItemStack) -> Boolean, destSlot: Int): Boolean {
        val matchingSlots = filterItems(predicate)
        if (matchingSlots.isNotEmpty()) {
            moveItem(matchingSlots.first(), destSlot)
            return true
        }
        return false
    }

    /**
     * Moves all matching items to the specified destination slot.
     *
     * @param predicate A function that takes an ItemStack and returns true if it matches the filter criteria.
     * @param destSlot The slot to move the items to.
     * @return The number of items moved.
     */
    fun moveAllMatchingItems(predicate: (ItemStack) -> Boolean, destSlot: Int): Int {
        val matchingSlots = filterItems(predicate)
        matchingSlots.forEach { moveItem(it, destSlot) }
        return matchingSlots.size
    }
}