package me.Bahamut.DragonEggEnchanting;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Yun on 3/6/2015.
 */
public class DragonEggEnchantingLogic
{
    private final DragonEggEnchantingPlugin plugin;
    private static HashMap<Enchantment, Integer> validEnchantments = new HashMap<Enchantment, Integer>() {{
        put (Enchantment.ARROW_DAMAGE, 10);
        put (Enchantment.DAMAGE_ALL, 10);
        put (Enchantment.DAMAGE_ARTHROPODS, 10);
        put (Enchantment.DAMAGE_UNDEAD, 10);
        put (Enchantment.DIG_SPEED, 10);
        put (Enchantment.DURABILITY, 10);
        put (Enchantment.LOOT_BONUS_BLOCKS, 10);
        put (Enchantment.LOOT_BONUS_MOBS, 10);
        put (Enchantment.LUCK, 10);
        put (Enchantment.LURE, 8);
        put (Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        put (Enchantment.PROTECTION_EXPLOSIONS, 10);
        put (Enchantment.PROTECTION_FALL, 10);
        put (Enchantment.PROTECTION_FIRE, 10);
        put (Enchantment.PROTECTION_PROJECTILE, 10);
        put (Enchantment.THORNS, 10);
    };};

    private static HashMap<Integer, Integer> successTable = new HashMap<Integer, Integer>() {{
        put (1, 100);
        put (2, 100);
        put (3, 100);
        put (4, 100);
        put (5, 100);
        put (6, 50);
        put (7, 40);
        put (8, 30);
        put (9, 20);
        put (10, 10);
    };};

    public DragonEggEnchantingLogic (DragonEggEnchantingPlugin plugin)
    {
        this.plugin = plugin;
    }

    /*
        Gives the user a chance to upgrade a random enchantment on the item
        that they are holding. The higher the enchantment level, the less likely
        the upgrade will succeed. If the item already contains maxed out enchantments
        (which is currently 10), then the upgrade will not execute.
     */
    public void upgradeItem (CommandSender sender)
    {
        Player player = ((Player) sender).getPlayer();
        if (player.getInventory().contains(Material.DRAGON_EGG) && player.getLevel() >= 50)
        {
            ItemStack handItem = player.getItemInHand();
            sender.sendMessage("[Dragon Egg] Attempting to enchant " + handItem.getType().name() + ".");
            List<Enchantment> availableEnchantments = getAvailableUpgrades(handItem);
            if (!availableEnchantments.isEmpty())
            {
                incrementAvailableUpgrade(sender, handItem, availableEnchantments);
                removeRequirements (player);
            }
            else sender.sendMessage("[Dragon Egg] " + handItem.getType().name() + " has no enchantments or is maxed out!");
        }
        else sender.sendMessage("[Dragon Egg] You don't have enough eggs or levels!");
    }

    /*
        Check the item's meta data to see the available enchantments.
        If there are remaining enchantments that are below level 10
        (some level 8), then return true; otherwise, return false.
     */
    public List<Enchantment> getAvailableUpgrades (ItemStack handItem)
    {
        List<Enchantment> availableEnchantments = new ArrayList<Enchantment>();
        Map<Enchantment, Integer> enchantments = handItem.getEnchantments();
        for (Enchantment name : enchantments.keySet())
            if (validEnchantments.containsKey(name) && handItem.getEnchantmentLevel(name) < validEnchantments.get(name))
                availableEnchantments.add(name);
        return availableEnchantments;
    }

    /*
        Go through the enchantments on the item, pick a random one,
        and give a chance to upgrade the enchantment to the next level.
        The higher the level of the enchantment, the lower the chance.
     */
    public void incrementAvailableUpgrade (CommandSender sender, ItemStack handItem, List<Enchantment> availableEnchantments)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(availableEnchantments.size());
        Enchantment randomEnchantment = availableEnchantments.get(randomIndex);
        int enchantmentLevel = handItem.getEnchantmentLevel(randomEnchantment);
        int randomValue = random.nextInt(100) + 1;
        if (randomValue <= successTable.get(enchantmentLevel))
        {
            handItem.addUnsafeEnchantment(randomEnchantment, handItem.getEnchantmentLevel(randomEnchantment) + 1);
            sender.sendMessage("[Dragon Egg] Success! " + randomEnchantment.getName() + " " + enchantmentLevel + " increased to " + (enchantmentLevel + 1) + "!");
        }
        else sender.sendMessage("[Dragon Egg] " + randomEnchantment.getName() + " " + enchantmentLevel + " did not increase in level.");
    }

    /*
        Remove a Dragon Egg and Levels as requirement.
     */
    public void removeRequirements (Player player)
    {
        int eggIndex = player.getInventory().first(Material.DRAGON_EGG);
        ItemStack eggStack = player.getInventory().getItem(eggIndex);
        int newAmount = (eggStack.getAmount() - 1 > 0) ? eggStack.getAmount() - 1 : 0;
        eggStack.setAmount(newAmount);
        player.getInventory().setItem(eggIndex, eggStack);
        player.updateInventory();
        player.setLevel(player.getLevel() - 50);
    }
}
