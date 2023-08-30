package git.dxdrillbassx.newworld.commands;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import git.dxdrillbassx.newworld.Plugin;
import git.dxdrillbassx.newworld.Region;
import git.dxdrillbassx.newworld.Signature;

import java.util.ArrayList;
import java.util.List;

public class RegionCommands implements CommandExecutor, TabCompleter {
    public RegionCommands(Plugin plugin){
        for (Material material : Material.values()){
            if (material.isLegacy())
                continue;
            if (material.isItem())
                continue;
            Mats.add(material.getKey().toString());

        }
        plugin.getCommand("newWorld").setExecutor(this);
        plugin.getCommand("newWorld").setTabCompleter(this);
    }

    public List<String>Mats = new ArrayList<>();

    public boolean noMats(String string){
        return !Mats.contains(string);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("Только игрок может использовать эту команду!");
            return true;
        }

        Player player = (Player) sender; // Кастуем превращение sender в Player..

        Region region = Region.getRegionOfAPlayer(player);

        if (args.length == 0){
            player.sendMessage(Signature.ERROR + "Недостаточно аргументов!");
            return false;
        }
        if (args[0].equalsIgnoreCase("set")){
            if (args.length < 2){
                player.sendMessage(Signature.ERROR + "Недостаточно аргументов!");
                return true;
            }
            if (noMats(args[1])){
                player.sendMessage(Signature.ERROR + "Неизвестный блок!"); // Блока нема в Material..
                return true;
            }
            Material material = Material.getMaterial(args[1].replace("minecraft:", "").toUpperCase()); // Ищем блок по названию в Material..

            region.setBlock(material);

            player.sendMessage(Signature.MAIN + "Успешно!"); //TODO: расширить вывод
            return true;
        }else if (args[0].equalsIgnoreCase("expand")){ // /nw expand 10 arg(up, down...)
            if (args.length < 3) {
                BlockFace playerFace = player.getFacing();
                String playerFaceStringValue = playerFace.toString(); // Преобразование значений

                // Проверка в какую сторону смотрит игрок
                if (playerFaceStringValue.contains("NORTH"))
                    playerFace = BlockFace.NORTH;

                else if (playerFaceStringValue.contains("SOUTH"))
                    playerFace = BlockFace.SOUTH;

                else if (playerFaceStringValue.contains("EAST"))
                    playerFace = BlockFace.EAST;

                else if (playerFaceStringValue.contains("WEST"))
                    playerFace = BlockFace.WEST;


                int blockNum; // Без этой хуйни переменная удалится при выходе за скобки

                try {
                    blockNum = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException e){ // На случай если дцп не напишет аргументы
                    player.sendMessage(Signature.ERROR + "Использование /nw expand <number> up|down");
                    return true;
                }

                region.expand(blockNum, playerFace);
                return true;
            }
            else if (args.length == 3){
                if (args[2].equalsIgnoreCase("down")){
                    if (region.getPos1().getBlockY() < region.getPos2().getBlockY()){ // Проверка на высоту
                        region.getPos1().setY(region.getPos1().getY() - 10);
                    }
                    else{
                        region.getPos2().setY(region.getPos1().getY() - 10);
                    }
                }
                else if (args[2].equalsIgnoreCase("up")){
                    if (region.getPos1().getBlockY() > region.getPos2().getBlockY()){ // Проверка на высоту
                        region.getPos1().setY(region.getPos1().getY() + 10);
                    }
                    else{
                        region.getPos2().setY(region.getPos1().getY() + 10);
                    }
                }
            }

            player.sendMessage(Signature.MAIN + "Регион расширен на " + args[1] + " блоков!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("replace")){
            if (args.length < 3){
                player.sendMessage(Signature.ERROR + "Недостаточно аргументов!");
                return true;
            }
            if (noMats(args[1])){
                player.sendMessage(Signature.ERROR + "Неизвестный блок " + args[1]); // Нема заменяймого блока в Material..
                return true;
            }
            if (noMats(args[2])){
                player.sendMessage(Signature.ERROR + "Неизвестный блок " + args[2]); // Нема блока на который заменяем в Material..
                return true;
            }

            Material materialFrom = Material.getMaterial(args[1]); // Ищем заменяймый блок по названию в Material..
            Material materialTo = Material.getMaterial(args[2]); // Ищем блок для замены по названию в Material..

            region.replace(materialFrom, materialTo);
            player.sendMessage(Signature.MAIN + "Заменены блоки!"); //TODO: расширить вывод
            return true;
        }
        else if (args[0].equalsIgnoreCase("show")){
            region.showRegion();
            return true;
        }
        else if (args[0].equalsIgnoreCase("copy")){
            if (region.getPos1() != null && region.getPos2() != null){
                region.copy();
                player.sendMessage(Signature.MAIN + "Успех!");
            }
            else {
                player.sendMessage(Signature.ERROR + "Сначала выделите две точки!");
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("paste")){
            if (region.getClipboard() != null){
                region.paste();
                player.sendMessage(Signature.MAIN + "Успех!");
            }
            else {
                player.sendMessage(Signature.ERROR + "Нечего вставлять, скопируйте что-то!");
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("undo")){
            region.undo();
            return true;
        }
        else
        player.sendMessage(Signature.ERROR + "Неизвестная команда!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 1)
            return List.of("set", "expand", "show", "replace", "copy", "paste", "undo");

        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("expand")) {
                if (args.length == 3)
                    return List.of("up", "down");
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 2)
                    return Mats;
            }
        }
        return null;
    }
}
