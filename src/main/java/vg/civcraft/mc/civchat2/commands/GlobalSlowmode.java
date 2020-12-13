package vg.civcraft.mc.civchat2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import vg.civcraft.mc.civchat2.CivChat2;
import vg.civcraft.mc.civmodcore.command.CivCommand;
import vg.civcraft.mc.civmodcore.command.StandaloneCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CivCommand(id = "globalslowmode")
public class GlobalSlowmode extends StandaloneCommand {
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length < 1) {
			//if no arguments let bukkit handle explaining syntax.
			return false;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("off")) {
			CivChat2.getInstance().getCivChat2Manager().setSlowmodeTime(0);
			sender.sendMessage(ChatColor.GREEN + "Disabled Global Slowmode.");
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("get")) {
			Integer seconds = CivChat2.getInstance().getCivChat2Manager().getSlowmodeTime();
			if(seconds == 0) {
				sender.sendMessage(ChatColor.RED + "Global Slowmode is Disabled.");
				return true;
			} else {
				sender.sendMessage(ChatColor.GREEN + "Global Slowmode is set to " + ChatColor.AQUA + seconds + ChatColor.GREEN + " seconds.");
				return true;
			}
		}
		if(args.length == 2 && args[0].equalsIgnoreCase("set")) {
			try {
				//I am terrified of parseInt so now I do tryCatch.
				Integer.parseInt(args[1]);
			} catch (NumberFormatException exception) {
				sender.sendMessage(ChatColor.RED + "You need to input an amount of seconds.");
				return false;
			}
			Integer seconds = Integer.parseInt(args[1]);
			if(seconds < 0) { sender.sendMessage(ChatColor.RED + "You cannot input negative numbers."); }
			if(seconds == 0) { sender.sendMessage(ChatColor.RED + "Use /globalslowmode off."); return true; }
			CivChat2.getInstance().getCivChat2Manager().setSlowmodeTime(seconds);
			sender.sendMessage(ChatColor.GREEN + "Set slowmode to " + ChatColor.AQUA + seconds + ChatColor.GREEN +" seconds.");
			return true;
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender commandSender, String[] strings) {
		switch (strings.length) {
			case 0:
				return doTabComplete("", Arrays.asList("off", "get", "set"), false);
			case 1:
				return doTabComplete(strings[0], Arrays.asList("off", "get", "set"), false);
			default:
				return Collections.emptyList();
		}
	}
}
