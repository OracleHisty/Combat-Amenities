package net.hollowed.combatamenities.compat;

import com.terraformersmc.modmenu.util.mod.Mod;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;



public class TrinketsCompat {
    private static final String MOD_ID = TrinketsCompatHeader.MOD_ID;
    private static boolean intialized = false;
    private static boolean enabled = false;

    public static boolean init() {
        if (intialized) {
            return enabled;
        }
        intialized = true;
        enabled = FabricLoader.getInstance().isModLoaded(MOD_ID);
        if(!enabled) {
            return enabled;
        }
    }  //TODO: Finish Compatibiliity

}