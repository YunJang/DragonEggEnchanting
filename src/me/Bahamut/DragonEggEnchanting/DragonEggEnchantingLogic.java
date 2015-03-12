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
        put (6, 70);
        put (7, 60);
        put (8, 50);
        put (9, 40);
        put (10, 30);
    };};

    public DragonEggEnchantingLogic (DragonEggEnchantingPlugin plugin) { this.plugin = plugin; }

    /*
        Gives the user a chance to upgrade a random enchantment on the item
        that they are holding. The higher the enchantment level, the less likely
        the upgrade will succeed. If the item already contains maxed out enchantments
        (which is currently 10), then the upgrade will not execute.
     */
    public void upgradeItem (CommandSender sender, Material material, int level, String matName, int successPenalty)
    {
        Player player = ((Player) sender).getPlayer();
        if (player.getInventory().contains(material) && player.getLevel() >= level)
        {
            ItemStack handItem = player.getItemInHand();
            sender.sendMessage("[" + matName + "] Attempting to enchant " + handItem.getType().name() + ".");
            List<Enchantment> availableEnchantments = getAvailableUpgrades(handItem);
            if (!availableEnchantments.isEmpty())
            {
                incrementAvailableUpgrade(sender, handItem, availableEnchantments, matName, successPenalty);
                removeRequirements (player, material, level);
            }
            else sender.sendMessage("[" + matName + "] " + handItem.getType().name() + " has no enchantments or is maxed out!");
        }
        else sender.sendMessage("[" + matName + "] You don't have enough " + matName + "s or Levels!");
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

        Depending on the material used for upgrade, a success penalty with
        be applied as some materials are easier to grind out than others.
     */
    public void incrementAvailableUpgrade (CommandSender sender, ItemStack handItem, List<Enchantment> availableEnchantments, String matName, int successPenalty)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(availableEnchantments.size());
        Enchantment randomEnchantment = availableEnchantments.get(randomIndex);
        int enchantmentLevel = handItem.getEnchantmentLevel(randomEnchantment);
        int randomValue = random.nextInt(100) + 1;
        if (randomValue <= successTable.get(enchantmentLevel) / successPenalty)
        {
            handItem.addUnsafeEnchantment(randomEnchantment, handItem.getEnchantmentLevel(randomEnchantment) + 1);
            sender.sendMessage("[" + matName + "] Success! " + randomEnchantment.getName() + " " + enchantmentLevel + " increased to " + (enchantmentLevel + 1) + "!");
        }
        else sender.sendMessage("[" + matName + "] " + randomEnchantment.getName() + " " + enchantmentLevel + " did not increase in level.");
    }

    /*
        Remove the requested material and levels.
     */
    public void removeRequirements (Player player, Material material, int level)
    {
        int itemIndex = player.getInventory().first(material);
        ItemStack itemStack = player.getInventory().getItem(itemIndex);
        int newAmount = (itemStack.getAmount() - 1 > 0) ? itemStack.getAmount() - 1 : 0;
        itemStack.setAmount(newAmount);
        player.getInventory().setItem(itemIndex, itemStack);
        player.updateInventory();
        player.setLevel(player.getLevel() - level);
    }
}
