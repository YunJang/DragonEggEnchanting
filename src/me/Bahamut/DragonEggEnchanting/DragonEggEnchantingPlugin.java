package me.Bahamut.DragonEggEnchanting;

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
        if (cmd.getName().equalsIgnoreCase("dragonegg"))
        {
            logic.upgradeItem(sender);
            return true;
        }

        return false;
    }
}
