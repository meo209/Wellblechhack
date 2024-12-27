package com.github.meo209.archer.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

object InventoryUtils {

    private val client: MinecraftClient
        get() = MinecraftClient.getInstance()

    /**
     * Moves an item from the source slot to the destination slot.
     *
     * @param sourceSlot The slot to move the item from.
     * @param destSlot The slot to move the item to.
     * @param simulate If a [CloseHandledScreenC2SPacket] should be sent.
     * @param replaceAction What actions to perform if the destination slot is not empty. Default: Drop
     */
    fun moveItem(
        sourceSlot: Int, destSlot0: Int, simulate: Boolean = false, replaceAction: () -> Unit = { dropStack(destSlot0) }
    ) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return
        
        val destSlot = destSlot0 + 5

        if (!player.inventory.getStack(destSlot).isEmpty) {
            replaceAction()
        }

        interactionManager.clickSlot(
            player.playerScreenHandler.syncId, sourceSlot, 0, SlotActionType.PICKUP, player
        )

        interactionManager.clickSlot(
            player.playerScreenHandler.syncId, destSlot, 0, SlotActionType.PICKUP, player
        )

        if (simulate) player.networkHandler.sendPacket(CloseHandledScreenC2SPacket(player.playerScreenHandler.syncId))
    }

    /**
     * Drops an item stack from a slot
     * @param slot The slot to clear.
     * @param swing Should send a [HandSwingC2SPacket].
     */
    fun dropStack(slot: Int, swing: Boolean = true) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return
        val networkHandler = client.networkHandler ?: return

        if (swing)
            networkHandler.sendPacket(HandSwingC2SPacket(Hand.MAIN_HAND))
        
        interactionManager.clickSlot(
            player.playerScreenHandler.syncId,
            slot,
            0,
            SlotActionType.THROW,
            player
        )
    }

    /**
     * Drops the currently selected item
     * @param entireStack Should drop entire stack or just one.
     */
    fun dropSelectedItem(entireStack: Boolean): Boolean {
        val player = client.player ?: return false
        val networkHandler = client.networkHandler ?: return false

        val action =
            if (entireStack) PlayerActionC2SPacket.Action.DROP_ALL_ITEMS else PlayerActionC2SPacket.Action.DROP_ITEM
        val itemStack: ItemStack = player.inventory.dropSelectedItem(entireStack)
        networkHandler.sendPacket(PlayerActionC2SPacket(action, BlockPos.ORIGIN, Direction.DOWN))
        return !itemStack.isEmpty
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

        // Search hotbar (slots 36-44) first
        for (slot in PlayerInventorySlots.HOTBAR_1..PlayerInventorySlots.HOTBAR_9) {
            val stack = inventory.getStack(slot)
            if (stack.item == item) {
                return slot
            }
        }

        // Search main inventory (slots 9-35)
        for (slot in PlayerInventorySlots.INVENTORY_ROW1_COL1..PlayerInventorySlots.INVENTORY_ROW3_COL9) {
            val stack = inventory.getStack(slot)
            if (stack.item == item) {
                return slot
            }
        }

        // Search offhand (slot 45)
        val offhandStack = inventory.getStack(PlayerInventorySlots.OFFHAND)
        if (offhandStack.item == item) {
            return PlayerInventorySlots.OFFHAND
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

        for (slot in PlayerInventorySlots.HOTBAR_1..PlayerInventorySlots.HOTBAR_9) {
            val stack = inventory.getStack(slot)
            if (predicate(stack)) {
                matchingSlots.add(slot)
            }
        }

        for (slot in PlayerInventorySlots.INVENTORY_ROW1_COL1..PlayerInventorySlots.INVENTORY_ROW3_COL9) {
            val stack = inventory.getStack(slot)
            if (predicate(stack)) {
                matchingSlots.add(slot)
            }
        }

        val offhandStack = inventory.getStack(PlayerInventorySlots.OFFHAND)
        if (predicate(offhandStack)) {
            matchingSlots.add(PlayerInventorySlots.OFFHAND)
        }

        return matchingSlots
    }

    /**
     * Moves the first matching item to the specified destination slot.
     *
     * @param predicate A function that takes an ItemStack and returns true if it matches the filter criteria.
     * @param destSlot The slot to move the item to.
     * @param simulate If a [CloseHandledScreenC2SPacket] should be sent.
     * @param replaceAction What actions to perform if the destination slot is not empty. Default: Drop
     * @return True if an item was moved, false otherwise.
     */
    fun moveFirstMatchingItem(
        predicate: (ItemStack) -> Boolean,
        destSlot: Int,
        simulate: Boolean = false,
        replaceAction: () -> Unit = { dropStack(destSlot) }
    ): Boolean {
        val matchingSlots = filterItems(predicate)
        if (matchingSlots.isNotEmpty()) {
            moveItem(matchingSlots.first(), destSlot, simulate, replaceAction)
            return true
        }
        return false
    }

    /**
     * Moves all matching items to the specified destination slot.
     *
     * @param predicate A function that takes an ItemStack and returns true if it matches the filter criteria.
     * @param destSlot The slot to move the items to.
     * @param simulate If a [CloseHandledScreenC2SPacket] should be sent.
     * @param replaceAction What actions to perform if the destination slot is not empty. Default: Drop
     * @return The number of items moved.
     */
    fun moveAllMatchingItems(
        predicate: (ItemStack) -> Boolean,
        destSlot: Int,
        simulate: Boolean = false,
        replaceAction: () -> Unit = { dropStack(destSlot) }
    ): Int {
        val matchingSlots = filterItems(predicate)
        matchingSlots.forEach { moveItem(it, destSlot, simulate, replaceAction) }
        return matchingSlots.size
    }
}