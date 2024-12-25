package com.github.meo209.archer.events

import com.github.meo209.keventbus.Event
import net.minecraft.item.ItemStack

class PlayerInventorySlotChangeEvent(val slot: Int, val old: ItemStack, val new: ItemStack): Event