package org.dxdrillbassx.newworld.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dxdrillbassx.newworld.Plugin;
import org.dxdrillbassx.newworld.Signature;

public class RegionCommands implements CommandExecutor {
    public RegionCommands(Plugin plugin){
        plugin.getCommand("newWorld").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("Только игрок может использовать эту команду!");
            return true;
        }

        Player player = (Player) sender; // Кастуем превращение sender в Player..

        if (args[0].equalsIgnoreCase("set")){
            Material material = Material.getMaterial(args[1]); // Ищем блок по названию в Material..

            if (material == null){
                player.sendMessage(Signature.ERROR + "Неизвестный блок!"); // Блока нема в Material..
                return true;
            }



        }
        return false;
    }
}
