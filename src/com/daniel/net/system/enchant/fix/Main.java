
package com.daniel.net.system.enchant.fix;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Colorable;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.command.Command;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public final Logger logger;
	protected String prefix = "��b[ ��f���� ��7]";
	protected String warnmsg = "��b�Ӽ�(��þƮ/�ξ� ��)�� �ִ� û�ݼ��� ��6������ ����/���� ������ ���� ��þƮ�� ����� �Ұ��� �մϴ�.";
	public static Command plugin;

	public Main() {
		this.logger = Logger.getLogger("Minecraft");
	}

	@Override
	public void onDisable() {
		final PluginDescriptionFile pdFile = this.getDescription();
		System.out.println(String.valueOf(pdFile.getName()) + pdFile.getVersion() + "��(��) ��Ȱ��ȭ �Ǿ����ϴ�.");
	}

	@Override
	public void onEnable() {
		final PluginDescriptionFile pdFile = this.getDescription();
		System.out.println(String.valueOf(pdFile.getName()) + pdFile.getVersion() + "��(��) Ȱ��ȭ �Ǿ����ϴ�.");
		this.reloadConfiguration();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel,
			final String[] args) {

		if (commandLabel.equalsIgnoreCase("MineEnchTableLapis") || commandLabel.equalsIgnoreCase("enchfix")) {
			if (sender.hasPermission("MineEnchTableLapis.admin") || sender.isOp()) {
				PluginDescriptionFile pdFile = this.getDescription();
				reloadConfiguration();
				sender.sendMessage("��7[" + pdFile.getName() + "��7] ��a" + pdFile.getName() + "��(��) ���ε� �Ǿ����ϴ�.");
			}

			return false;
		}

		return false;
	}

	private void reloadConfiguration() {
		PluginDescriptionFile pdFile = this.getDescription();
		File config = new File("plugins/" + pdFile.getName() + "/config.yml");
		if (config.exists()) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
			this.saveDefaultConfig();
			for (String key : cfg.getConfigurationSection("").getKeys(true)) {
				if (!this.getConfig().contains(key)) {
					this.getConfig().set(key, cfg.get(key));
				}
			}
		} else {
			this.saveDefaultConfig();
		}
		this.reloadConfig();
		this.warnmsg = this.getConfig().getString("warnmsg");
		this.prefix = this.getConfig().getString("prefix");

		this.warnmsg = (ChatColor.translateAlternateColorCodes('&', warnmsg) + "\n");
		this.prefix = (ChatColor.translateAlternateColorCodes('&', prefix) + "\n");

	}

	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent e) {

		Inventory topinv = e.getWhoClicked().getOpenInventory().getTopInventory();

		
		if (topinv instanceof EnchantingInventory) {
			if ((!e.isCancelled()) && e.getCurrentItem() != null) {
				if (e.getCurrentItem().getType().equals(Material.INK_SACK) && e.getCurrentItem().hasItemMeta()) {
					if (((Colorable) e.getCurrentItem().getData()).getColor() == DyeColor.BLUE) {
						e.setCancelled(true);
						e.getWhoClicked().sendMessage(prefix + " " + warnmsg );
					}
				}

			}

		}
	}
	
	

	

}
