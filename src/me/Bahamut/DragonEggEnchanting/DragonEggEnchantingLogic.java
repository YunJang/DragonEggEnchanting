package me.Bahamut.DragonEggEnchanting;

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
        pluginName = yjColor.aqua("[Enchanter] ");
    }

    /*
        Show the player what upgrades are available on the item they are holding.
        Also informs the player of what command to use to upgrade their item.
     */
    public void showAvailableUpgrades (CommandSender sender)
    {
        Player player = ((Player) sender).getPlayer();
        ItemStack handItem = player.getItemInHand();
        String itemName = BetterEnchantingMapper.materialConversionMap.get(handItem.getData().getItemType());
        List<Enchantment> availableEnchantments = getAvailableUpgrades(handItem);

        if (availableEnchantments.isEmpty())
        {
            sender.sendMessage(pluginName + yjColor.green(itemName) + " has no enchantments or is already maxed out!");
            return;
        }

        sender.sendMessage(pluginName + "Your " + yjColor.green(itemName) + " has the following upgrades left:");
        for (int i = 0; i < availableEnchantments.size(); ++i)
        {
            Enchantment theEnchantment = availableEnchantments.get(i);
            sender.sendMessage(pluginName + yjColor.light_purple(Integer.toString(i)) + " - " + yjColor.gold(BetterEnchantingMapper.enchantmentConversionMap.get(theEnchantment) + " " + Integer.toString(handItem.getEnchantmentLevel(theEnchantment))));
        }
        sender.sendMessage(pluginName + "Type " + yjColor.green("/upgrade ") +  yjColor.aqua("<fodder> ") + yjColor.light_purple("<enchant number> ") + "to upgrade!");
    }

    /*
        Upgrades the player's item based on the index of the enchantment they input.
     */
    public void doUpgrade (CommandSender sender, Material material, int level, String matName, int successPenalty, int enchantIndex)
    {
        Player player = ((Player) sender).getPlayer();
        ItemStack handItem = player.getItemInHand();
        List<Enchantment> availableEnchantments = getAvailableUpgrades(handItem);
        int playerExp = convertLevelToExperience(player.getLevel());
        int requiredExp = convertLevelToExperience(level);

        if (availableEnchantments.size() > enchantIndex)
        {
            if (player.getInventory().contains(material) && playerExp >= requiredExp)
            {
                Enchantment enchantment = availableEnchantments.get(enchantIndex);
                sender.sendMessage(pluginName + "Attempting to upgrade "
                        + yjColor.green(BetterEnchantingMapper.materialConversionMap.get(handItem.getType())) + "'s "
                        + yjColor.gold(BetterEnchantingMapper.enchantmentConversionMap.get(enchantment) + " "
                        + Integer.toString(handItem.getEnchantmentLevel(enchantment))));
                incrementAvailableUpgrade(sender, handItem, successPenalty, enchantment);
                removeRequirements(player, material, playerExp, requiredExp);
            }
            else sender.sendMessage(pluginName + "You don't have enough " + yjColor.green(matName + "s") + " or " + yjColor.green("Levels") + "!");
        }
        else sender.sendMessage(pluginName + "You entered an invalid enchantment index. Please try again.");
    }

    /*
        Convert the level to flat experience amount because CraftBukkit
        is stupid and because the getTotalExperience method is broken.
     */
    public int convertLevelToExperience (int level)
    {
        if (level >= 0 && level <= 15)
            return (int) (Math.pow(level, 2) + (6 * level));
        else if (level >= 16 && level <= 30)
            return (int) ((2.5 * Math.pow(level, 2)) - (40.5 * level) + 360);
        else
            return (int) ((4.5 * Math.pow(level, 2)) - (162.5 * level) + 2220);
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
        Upgrade the item's enchantment that was chosen.
        The higher the level of the enchantment, the lower the chance.

        Depending on the material used for upgrade, a success penalty with
        be applied as some materials are easier to grind out than others.
     */
    public void incrementAvailableUpgrade (CommandSender sender, ItemStack handItem, int successPenalty, Enchantment enchantment)
    {
        Random random = new Random();
        String enchantmentName = BetterEnchantingMapper.enchantmentConversionMap.get(enchantment);
        int enchantmentLevel = handItem.getEnchantmentLevel(enchantment);

        int randomValue = random.nextInt(100) + 1;
        if (randomValue <= BetterEnchantingMapper.successTable.get(enchantmentLevel) / successPenalty)
        {
            handItem.addUnsafeEnchantment(enchantment, handItem.getEnchantmentLevel(enchantment) + 1);
            sender.sendMessage(pluginName + "Success! " + yjColor.green(enchantmentName) + " " + yjColor.green(enchantmentLevel + "") + " increased to " + yjColor.gold((enchantmentLevel + 1) + "") + "!");
        }
        else sender.sendMessage(pluginName + yjColor.green(enchantmentName) + " " + yjColor.gold(enchantmentLevel + "") + " did not increase in level.");
    }

    /*
        Remove the requested material and experience.
     */
    public void removeRequirements (Player player, Material material, int playerExp, int requiredExp)
    {
        int itemIndex = player.getInventory().first(material);
        ItemStack itemStack = player.getInventory().getItem(itemIndex);
        int newAmount = (itemStack.getAmount() - 1 > 0) ? itemStack.getAmount() - 1 : 0;
        itemStack.setAmount(newAmount);
        player.getInventory().setItem(itemIndex, itemStack);
        player.updateInventory();
        player.setLevel(0);
        player.giveExp(playerExp - requiredExp);
    }
}
