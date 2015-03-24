package me.Bahamut.DragonEggEnchanting;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Yun on 3/6/2015.
 */
public class DragonEggEnchantingPlugin extends JavaPlugin
{
    private static DragonEggEnchantingLogger logger;
    private DragonEggEnchantingLogic logic;

    public void onDisable ()
    {
        System.out.println(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " disabled.");
    }

    public void onEnable ()
    {
        logger = new DragonEggEnchantingLogger(this);
        logic = new DragonEggEnchantingLogic(this);
        logger.info(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " enabled.");
        loadConfigurations();
    }

    public void loadConfigurations ()
    {
        FileConfiguration config = getConfig();
        BetterEnchantingMapper.loadValidEnchantmentTable((ArrayList) config.get("enchant_table"), (ArrayList) config.get("level_table"));
        BetterEnchantingMapper.loadSuccessTable((ArrayList) config.get("success_table"));
    }

    public boolean onCommand (CommandSender sender, Command cmd, String s, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("upgrade") || cmd.getName().equalsIgnoreCase("up"))
        {
            if (args.length == 2)
            {
                try
                {
                    int index = Integer.parseInt(args[1]);
                    if (args[0].equalsIgnoreCase("emerald"))        logic.doUpgrade(sender, Material.EMERALD_BLOCK, 30, "Emerald", 2, index);
                    else if (args[0].equalsIgnoreCase("dragonegg")) logic.doUpgrade(sender, Material.DRAGON_EGG, 50, "Dragon Egg", 1, index);
                }
                catch (NumberFormatException e) { sender.sendMessage(yjColor.aqua("[Enchanter] ") + "Index value is invalid! Please enter an integer."); }
            }
            else logic.showAvailableUpgrades(sender);
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("upgraderates"))
        {
            sender.sendMessage(yjColor.aqua("[Enchanter] ") + "The current base rates are:");
            sender.sendMessage(BetterEnchantingMapper.displayRates());
            return true;
        }

        return false;
    }
}
