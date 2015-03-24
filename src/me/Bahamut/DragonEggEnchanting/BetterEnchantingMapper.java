package me.Bahamut.DragonEggEnchanting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yunjang on 3/14/15.
 */
public class BetterEnchantingMapper
{
    public static HashMap<Integer, Integer> successTable = new HashMap<Integer, Integer>();
    public static HashMap<Enchantment, Integer> enchantmentTable = new HashMap<Enchantment, Integer>();

    public static void loadSuccessTable (ArrayList successList)
    {
        for (int i = 0; i < successList.size(); ++i)
            successTable.put(i + 1, (Integer) successList.get(i));
    }

    public static void loadValidEnchantmentTable (ArrayList enchantList, ArrayList levelList)
    {
        for (int i = 0; i < enchantList.size(); ++i)
            enchantmentTable.put(Enchantment.getByName((String) enchantList.get(i)), (Integer) levelList.get(i));
    }

    public static String displayRates ()
    {
        StringBuilder sb = new StringBuilder ();
        for (Integer level : successTable.keySet())
            sb.append(yjColor.aqua("[Enchanter] ") + yjColor.green("Level " + level.toString()) + ": " + yjColor.gold("" + successTable.get(level) + "%") + "\n");
        return sb.toString();
    }

    public static HashMap<Enchantment, String> enchantmentConversionMap = new HashMap<Enchantment, String>() {{
        put (Enchantment.ARROW_DAMAGE, "Power");
        put (Enchantment.DAMAGE_ALL, "Sharpness");
        put (Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropod");
        put (Enchantment.DAMAGE_UNDEAD, "Smite");
        put (Enchantment.DIG_SPEED, "Efficiency");
        put (Enchantment.DURABILITY, "Unbreaking");
        put (Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
        put (Enchantment.LOOT_BONUS_MOBS, "Looting");
        put (Enchantment.LUCK, "Luck of the Sea");
        put (Enchantment.LURE, "Lure");
        put (Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
        put (Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
        put (Enchantment.PROTECTION_FALL, "Feather Falling");
        put (Enchantment.PROTECTION_FIRE, "Fire Protection");
        put (Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
        put (Enchantment.THORNS, "Thorns");
    };};

    public static HashMap<Material, String> materialConversionMap = new HashMap<Material, String>() {{
        put (Material.WOOD_SWORD, "Wooden Sword");
        put (Material.STONE_SWORD, "Stone Sword");
        put (Material.IRON_SWORD, "Iron Sword");
        put (Material.GOLD_SWORD, "Gold Sword");
        put (Material.DIAMOND_SWORD, "Diamond Sword");
        put (Material.WOOD_AXE, "Wooden Axe");
        put (Material.STONE_AXE, "Stone Axe");
        put (Material.IRON_AXE, "Iron Axe");
        put (Material.GOLD_AXE, "Gold Axe");
        put (Material.DIAMOND_AXE, "Diamond Axe");
        put (Material.WOOD_SPADE, "Wooden Shovel");
        put (Material.STONE_SPADE, "Stone Shovel");
        put (Material.IRON_SPADE, "Iron Shovel");
        put (Material.GOLD_SPADE, "Gold Shovel");
        put (Material.DIAMOND_SPADE, "Diamond Shovel");
        put (Material.WOOD_PICKAXE, "Wooden Pickaxe");
        put (Material.STONE_PICKAXE, "Stone Pickaxe");
        put (Material.IRON_PICKAXE, "Iron Pickaxe");
        put (Material.GOLD_PICKAXE, "Gold Pickaxe");
        put (Material.DIAMOND_PICKAXE, "Diamond Pickaxe");
        put (Material.BOW, "Bow");
        put (Material.LEATHER_HELMET, "Leather Helmet");
        put (Material.IRON_HELMET, "Iron Helmet");
        put (Material.GOLD_HELMET, "Gold Helmet");
        put (Material.DIAMOND_HELMET, "Diamond Helmet");
        put (Material.LEATHER_CHESTPLATE, "Leather Tunic");
        put (Material.IRON_CHESTPLATE, "Iron Chestplate");
        put (Material.GOLD_CHESTPLATE, "Gold Chestplate");
        put (Material.DIAMOND_CHESTPLATE, "Diamond Chestplate");
        put (Material.LEATHER_LEGGINGS, "Leather Pants");
        put (Material.IRON_LEGGINGS, "Iron Leggings");
        put (Material.GOLD_LEGGINGS, "Gold Leggings");
        put (Material.DIAMOND_LEGGINGS, "Diamond Leggings");
        put (Material.LEATHER_BOOTS, "Leather Boots");
        put (Material.IRON_BOOTS, "Iron Boots");
        put (Material.GOLD_BOOTS, "Gold Boots");
        put (Material.DIAMOND_BOOTS, "Diamond Boots");
        put (Material.BOOK, "Book");
    };};
}
