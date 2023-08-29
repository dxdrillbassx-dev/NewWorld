package git.dxdrillbassx.newworld.commands;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import git.dxdrillbassx.newworld.Plugin;
import git.dxdrillbassx.newworld.Region;
import git.dxdrillbassx.newworld.Signature;

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

        Region region = Region.getRegionOfAPlayer(player);

        if (args[0].equalsIgnoreCase("set")){
            Material material = Material.getMaterial(args[1]); // Ищем блок по названию в Material..

            if (material == null){
                player.sendMessage(Signature.ERROR + "Неизвестный блок!"); // Блока нема в Material..
                return true;
            }

            region.setBlock(material);

            player.sendMessage(Signature.MAIN + "Успешно!"); //TODO: расширить вывод
            return true;
        }else if (args[0].equalsIgnoreCase("expand")){ // /nw expand 10 arg(up, down...)
            if (args.length < 3) {
                BlockFace playerFace = player.getFacing();
                String playerFaceStringValue = playerFace.toString(); // Преобразование значений

                // Проверка в какую сторону смотрит игрок
                if (playerFaceStringValue.contains("NORTH")){
                    playerFace = BlockFace.NORTH;
                }
                else if (playerFaceStringValue.contains("SOUTH")){
                    playerFace = BlockFace.SOUTH;
                }
                else if (playerFaceStringValue.contains("EAST")){
                    playerFace = BlockFace.EAST;
                }
                else if (playerFaceStringValue.contains("WEST")){
                    playerFace = BlockFace.WEST;
                }

                int blockNum; // Без этой хуйни переменная удалится при выходе за скобки

                try {
                    blockNum = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException e){ // На случай если дцп не напишет аргументы
                    player.sendMessage(Signature.ERROR + "Использование /nw expand <number> up|down");
                    return true;
                }

                region.expand(blockNum, playerFace);
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
        }
        else if (args[0].equalsIgnoreCase("replace")){
            Material materialFrom = Material.getMaterial(args[1]); // Ищем заменяймый блок по названию в Material..
            Material materialTo = Material.getMaterial(args[2]); // Ищем блок для замены по названию в Material..

            if (materialFrom == null){
                player.sendMessage(Signature.ERROR + "Неизвестный блок " + args [1]); // Нема заменяймого блока в Material..
                return true;
            }

            if (materialTo == null){
                player.sendMessage(Signature.ERROR + "Неизвестный блок " + args [2]); // Нема блока на который заменяем в Material..
                return true;
            }

            region.replace(materialFrom, materialTo);
            player.sendMessage(Signature.MAIN + "Заменены блоки!"); //TODO: расширить вывод
            return true;
        }
        else if (args[0].equalsIgnoreCase("show")){
            region.showRegion();
            return true;
        }
        return false;
    }
}
