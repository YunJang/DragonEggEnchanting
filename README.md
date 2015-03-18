# DragonEggEnchanting - 1.8.x

Current Version: 1.3.0

Written for our personal Minecraft server that is running Spigot 1.8.x. Spigot 1.8.1 API was used but it should work as long as Minecraft remains on version 1.8.

Description:
- Provide a better alternative to equipment enchantment upgrades by using certain items and large number of levels to give your players a chance to upgrade their equipment beyond vanilla's max enchantment levels!

Usage:
- Type the command /upgrade(or the alias /up) <'emerald'/'dragonegg'> while holding the desired item in your hand.

Features:
- Enhance your equipment past the default enchantment levels (such as Efficiency V) by using certain materials and levels.
- Rates are modifiable. But typically you'll want lower levels to be more likely to succeed than higher levels!
- Following Enchantments are obtainable (but also modifiable):
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
- Change the name of the plugin to BetterEnchanting.
- Add configuration support to handle more material addition along with success penalties.

Changelog:
- Version 1.3.0
    - Configuration support added to allow easier addition of enchantments and modifications to value tables.
    - Commands added:
        - /upgraderates: gives base success rates.
- Version 1.2.5
    - Color context! Hooray!
    - Enchantment names are now properly shown.
    - Name of your equipment is properly shown.
- Version 1.2.0
    - Refactored code to handle adding other potential items in the future for viable upgrades.
        - Refactoring allows new items to be used, variable levels, and you can apply a success penalty to allow lower success for easier items.
    - Buffed the base success rates.
    - Added '/upgrade' and '/up' command; Removed '/dragonegg' and '/degg' command.
- Version 1.1.0
    - Plugin is published and is functional. Greater level enchantments are possible with the usage of Dragon Eggs and levels.
    - Added '/degg' command alias.
- Version 1.0.0
    - Initial Commit