package com.github.meo209.archer.mixin;

import com.github.meo209.archer.PlayerInventorySlotChangeEvent;
import com.github.meo209.archer.utils.PreviousStackStorage;
import com.github.meo209.keventbus.EventBus;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow public float prevNauseaIntensity;

    @Shadow public float renderPitch;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Get the client player instance
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // Get the player's inventory
        PlayerInventory inventory = player.getInventory();

        // Iterate through all inventory slots
        for (int slot = 0; slot < inventory.size(); slot++) {

            ItemStack currentStack = inventory.getStack(slot);
            ItemStack previousStack = getPreviousStack(slot);

            if (currentStack == null) {
                currentStack = ItemStack.EMPTY;
            }

            if (previousStack == null) {
                previousStack = ItemStack.EMPTY;
            }
            
            // Compare the current stack with the previous stack (if any)
            if (!areStacksEqual(currentStack, previousStack)) {
                // If the stack has changed, post the event
                EventBus.Companion.global().post(new PlayerInventorySlotChangeEvent(slot, previousStack, currentStack));

                // Update the previous stack for this slot
                setPreviousStack(slot, currentStack);
            }
        }
    }

    @Unique
    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null && stack2 == null) return true;
        if (stack1 == null || stack2 == null) return false;

        return ItemStack.areEqual(stack1, stack2);
    }

    @Unique
    private ItemStack getPreviousStack(int slot) {
        return PreviousStackStorage.INSTANCE.getStack(slot);
    }

    @Unique
    private void setPreviousStack(int slot, ItemStack stack) {
        PreviousStackStorage.INSTANCE.setStack(slot, stack);
    }
}