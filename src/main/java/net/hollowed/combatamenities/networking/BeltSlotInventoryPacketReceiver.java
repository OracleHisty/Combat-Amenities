package net.hollowed.combatamenities.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hollowed.combatamenities.util.AbstractSlotIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class BeltSlotInventoryPacketReceiver {

    public static void registerServerPacket() {
        ServerPlayNetworking.registerGlobalReceiver(BeltSlotInventoryPacketPayload.BELTSLOT_INVENTORY_PACKET_ID, ((payload, context) -> context.server().execute(() -> {
            PlayerEntity player = context.player();
            if (player == null || player.currentScreenHandler == null) {
                return;
            }

            ItemStack backSlotStack = player.getInventory().getStack(AbstractSlotIdentifier.INSTANCE.getBeltID()); // Backslot is slot AbstractSlotIdentifier.INSTANCE.getBackID()

            // Locate the hovered slot in the player's inventory screen
            int hoveredSlotIndex = payload.slot();
            if (hoveredSlotIndex < 0 || hoveredSlotIndex >= player.getInventory().size()) {
                return; // Invalid slot index
            }
            
            // Get the stack being swapped into the belt slot
            ItemStack hoveredStack = player.getInventory().getStack(hoveredSlotIndex);

            // Check if the player is in Creative Mode
            boolean isCreative = player.isCreative();

            // Swap the items, respecting the hotbar in creative mode
            if (hoveredSlotIndex < 9) {
                // Handling hotbar to backslot swap
                if (!isCreative) {
                    player.getInventory().setStack(AbstractSlotIdentifier.INSTANCE.getBeltID(), hoveredStack); // Place item in backslot
                    player.getInventory().setStack(hoveredSlotIndex, backSlotStack); // Move backslot item to hotbar
                    // If the hovered stack is not empty or the backslot stack is not empty, play a sound
                    if (!hoveredStack.isEmpty() || !backSlotStack.isEmpty()) {
                        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN.value(), SoundCategory.PLAYERS, 1F, 1F);
                    }
                }
            } else {
                // Handling general inventory slots to backslot
                if (hoveredSlotIndex < 36) {
                    player.getInventory().setStack(AbstractSlotIdentifier.INSTANCE.getBeltID(), hoveredStack); // Move item to backslot
                    player.getInventory().setStack(hoveredSlotIndex, backSlotStack); // Move backslot item to general inventory
                    // If the hovered stack is not empty or the backslot stack is not empty, play a sound
                    if (!hoveredStack.isEmpty() || !backSlotStack.isEmpty()) {
                        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN.value(), SoundCategory.PLAYERS, 1F, 1F);
                    }
                }
            }

            // Sync the inventory
            player.currentScreenHandler.sendContentUpdates();
        })));
    }
}