package me.Bahamut.DragonEggEnchanting;

import org.bukkit.ChatColor;
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
    private static String pluginName;

    public DragonEggEnchantingLogic (DragonEggEnchantingPlugin plugin)
    {
        this.plugin = plugin;
        pluginName = BetterEnchantingColor.aqua("[Enchanter] ");
    }

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
            String itemName = BetterEnchantingMapper.materialConversionMap.get(handItem.getData().getItemType());
            sender.sendMessage(pluginName + "Attempting to enchant " + BetterEnchantingColor.green(itemName) + ".");
            List<Enchantment> availableEnchantments = getAvailableUpgrades(handItem);
            if (!availableEnchantments.isEmpty())
            {
                incrementAvailableUpgrade(sender, handItem, availableEnchantments, matName, successPenalty);
                removeRequirements (player, material, level);
            }
            else sender.sendMessage(pluginName + BetterEnchantingColor.green(itemName) + " has no enchantments or is maxed out!");
        }
        else sender.sendMessage(pluginName + "You don't have enough " + matName + "s or Levels!");
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
            if (BetterEnchantingMapper.enchantmentTable.containsKey(name) && handItem.getEnchantmentLevel(name) < BetterEnchantingMapper.enchantmentTable.get(name))
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
        String enchantmentName = BetterEnchantingMapper.enchantmentConversionMap.get(randomEnchantment);
        int enchantmentLevel = handItem.getEnchantmentLevel(randomEnchantment);

        int randomValue = random.nextInt(100) + 1;
        if (randomValue <= BetterEnchantingMapper.successTable.get(enchantmentLevel) / successPenalty)
        {
            handItem.addUnsafeEnchantment(randomEnchantment, handItem.getEnchantmentLevel(randomEnchantment) + 1);
            sender.sendMessage(pluginName + "Success! " + BetterEnchantingColor.green(enchantmentName) + " " + BetterEnchantingColor.green(enchantmentLevel + "") + " increased to " + BetterEnchantingColor.gold((enchantmentLevel + 1) + "") + "!");
        }
        else sender.sendMessage(pluginName + BetterEnchantingColor.green(enchantmentName) + " " + BetterEnchantingColor.gold(enchantmentLevel + "") + " did not increase in level.");
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
