package net.hollowed.combatamenities.mixin.slots;

import com.mojang.datafixers.util.Pair;
import net.hollowed.combatamenities.CombatAmenities;
import net.hollowed.combatamenities.util.AbstractSlotIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class SlotsMixin extends AbstractRecipeScreenHandler {

    protected SlotsMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V", at = @At("RETURN"))
    private void addBackSlot(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        // Determine slot position based on context
        int xPos = CombatAmenities.CONFIG.backslotinventoryX + 1;
        int yPos = CombatAmenities.CONFIG.backslotinventoryY + 1;

        int xPos1 = CombatAmenities.CONFIG.beltslotinventoryX + 1; //aligns with configurable slot position
        int yPos1 = CombatAmenities.CONFIG.beltslotinventoryY + 1; //this makes faux-compatibility just by moving slot position in Combat Amenities.

        // Add BackSlot at determined position
        this.addSlot(new Slot(inventory, AbstractSlotIdentifier.INSTANCE.getBackID(), xPos, yPos) {
            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return new Pair<>(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("item/backslot_overlay"));
            }

            @Override
            public ItemStack takeStack(int amount) {
                ItemStack stack = super.takeStack(amount);
                if (stack.isEmpty()) {
                    this.setStack(ItemStack.EMPTY);
                }
                return stack;
            }
        });

        // Add Belt Slot at determined position
        this.addSlot(new Slot(inventory, AbstractSlotIdentifier.INSTANCE.getBeltID(), xPos1, yPos1) {
            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return new Pair<>(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("item/beltslot_overlay"));
            }

            @Override
            public ItemStack takeStack(int amount) {
                ItemStack stack = super.takeStack(amount);
                if (stack.isEmpty()) {
                    this.setStack(ItemStack.EMPTY);
                }
                return stack;
            }
        });
    }
}

