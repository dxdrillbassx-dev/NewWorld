package git.dxdrillbassx.newworld.events;

import git.dxdrillbassx.newworld.Region;
import git.dxdrillbassx.newworld.Signature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectRegionEvent implements Listener {

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event){ // Попытка пойти не через BlockBreakEvent..
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Region.wandItem) {
            event.setCancelled(true);

            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Region region = Region.getRegionOfAPlayer(event.getPlayer());
                if (region == null) {
                    region = new Region(event.getPlayer()); // Создание региона в случае если его ещё нет
                }

                region.setPos1(event.getClickedBlock().getLocation()); // Проверка на не ебанул ли игрок по воздуху
                event.getPlayer().sendMessage(Signature.MAIN + "Установлена первая точка!");
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Region region = Region.getRegionOfAPlayer(event.getPlayer());
                if (region == null) {
                    region = new Region(event.getPlayer()); // Создание региона в случае если его ещё нет x2
                }

                event.getPlayer().sendMessage(Signature.MAIN + "Установлена вторая точка!");
            }
        }
    }
}
