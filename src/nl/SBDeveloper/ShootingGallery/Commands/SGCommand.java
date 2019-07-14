package nl.SBDeveloper.ShootingGallery.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import nl.SBDeveloper.ShootingGallery.Main;

public class SGCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
        	//themepark
            return helpCommand(sender, label, args);
        } else if (args[0].equalsIgnoreCase("info") && args.length == 1) {
        	//themepark info
            return infoCommand(sender, label, args);
        } else if (args[0].equalsIgnoreCase("opengate") && args.length == 5) {
        	/*if (sender instanceof Player) {
        		Player p = (Player) sender;
        		if (!p.hasPermission("ThemeParkPlus.OpenGate")) {
        			return false;
        		}
        	}
        	//themepark opengate x y z
            return openGate(sender, label, args, 0, null);*/
        }

        return helpCommand(sender, label, args);
    }

    private boolean infoCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage("§1==================================");
        sender.sendMessage("§6ShootingGallery plugin made by §aSBDeveloper");
        sender.sendMessage("§6Version: " + Main.getInstance().getDescription().getVersion());
        sender.sendMessage("§6Type /sg help for more information!");
        sender.sendMessage("§1==================================");
        return true;
    }

    private boolean helpCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage("§8ShootingGallery commands:");
        sender.sendMessage("§6/sg info§f: Gives you information about the plugin.");
        sender.sendMessage("§6/sg help§f: Gives you this help page.");
        
        //OFFICIAL COMMANDS!
        sender.sendMessage("§6/sg opengate <World> <X> <Y> <Z>§f: Open a gate at a location.");
        return true;
    }

}
