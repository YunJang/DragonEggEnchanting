package me.Bahamut.DragonEggEnchanting;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Yun on 3/6/2015.
 */
public class DragonEggEnchantingLogger
{
    public static final Logger logger = Logger.getLogger("Minecraft");
    private final DragonEggEnchantingPlugin plugin;

    public DragonEggEnchantingLogger (DragonEggEnchantingPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void info (String s)
    {
        logger.log(Level.INFO, "[" + plugin.getDescription().getName() + "] " + s);
    }
}
