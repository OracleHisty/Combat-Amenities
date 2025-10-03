package net.hollowed.combatamenities.util;

import net.hollowed.combatamenities.CombatAmenities;

public interface AbstractSlotIdentifier {
    AbstractSlotIdentifier INSTANCE = new AbstractSlotIdentifier() {
        @Override
        public int getBeltID() {
            return 42;
        }

        @Override
        public int getBackID() {
            return 41;
        }

        @Override
        public int BeltSlotInventoryX() {
            return CombatAmenities.CONFIG.beltslotinventoryX;
        }

        @Override
        public int BeltSlotInventoryY() {
            return CombatAmenities.CONFIG.beltslotinventoryY;
        }

        @Override
        public int BackSlotInventoryX() {
            return CombatAmenities.CONFIG.backslotinventoryX;
        }

        @Override
        public int BackSlotInventoryY() {
            return CombatAmenities.CONFIG.backslotinventoryY;
        }
    };

    int getBeltID();
    int getBackID();
    int BeltSlotInventoryX();
    int BeltSlotInventoryY();
    int BackSlotInventoryX();
    int BackSlotInventoryY();
}
