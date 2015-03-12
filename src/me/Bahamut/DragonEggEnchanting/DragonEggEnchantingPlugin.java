package me.Bahamut.DragonEggEnchanting;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    public boolean onCommand (CommandSender sender, Command cmd, String s, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("upgrade") || cmd.getName().equalsIgnoreCase("up"))
        {
            if (args.length > 0)
            {
                if (args[0].equalsIgnoreCase("emerald"))        logic.upgradeItem(sender, Material.EMERALD_BLOCK, 30, "Emerald", 2);
                else if (args[0].equalsIgnoreCase("dragonegg")) logic.upgradeItem(sender, Material.DRAGON_EGG, 50, "Dragon Egg", 1);
                else if (args.length == 0)                      sender.sendMessage("Usage: /" + cmd.getName().toLowerCase() + " <emerald/dragonegg>");
            }
            else sender.sendMessage("Usage: /" + cmd.getName().toLowerCase() + " <emerald/dragonegg>");
            return true;
        }

        return false;
    }
}
