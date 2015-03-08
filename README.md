# DragonEggEnchanting - 1.8.x

Current Version: 1.1.0

Written for our personal Minecraft server that is running Spigot 1.8.x. Spigot 1.8.1 API was used but it should work as long as Minecraft remains on version 1.8.

Description:
- Provide a better alternative to equipment enchantment upgrades by using Dragon Eggs and large number of levels to give your players a chance to upgrade their equipment beyond vanilla's max enchantment levels! This is more suitable for servers that have respawning Ender Dragons -- giving it a semi-RPG aspect to the feature without completely breaking balance in the game.

Usage:
- Type the command /dragonegg (or the alias /degg) while holding the desired item in your hand.

Features:
- Enhance your equipment past the default enchantment levels (such as Efficiency V) by using a Dragon Egg and large number of levels (default is 50).
- The higher the level of your current enchantment, the lower the chance of success!
	- Level 1 ~ 5: 100%
	- Level 6: 50%
	- Level 7: 40%
	- Level 8: 30%
	- Level 9: 20%
	- Level 10: 10%
- Following Enchantments are obtainable:
	- Power X
	- Sharpness X
	- Bane of Arthopods X
	- Smite X
	- Efficiency X
	- Unbreaking X
	- Fortune X
	- Looting X
	- Luck of the Sea X
	- Lure VIII (otherwise, the fishing rod fails to fish)
	- Protection X
	- Blast Protection X
	- Feather Falling X
	- Fire Protection X
	- Projectile Protection X
	- Thorns X

To-Do List:
- Map the enchantment names from the CraftBukkit's built in names to the original names.
- Add color to the plugin messages to make it more legible.
- Add support for more enchantments after making sure it doesn't break the game.
- Add support for different items as enchantment fodders.
- Add a configuration file support.

Changelog:
- Version 1.1.0
    - Plugin is published and is functional. Greater level enchantments are possible with the usage of Dragon Eggs and levels.
    - Added '/degg' command alias.
- Version 1.0.0
    - Initial Commit