package org.dxdrillbassx.newworld.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.dxdrillbassx.newworld.Region;

public class SelectRegionEvent implements Listener {

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event){ // Попытка пойти не через BlockBreakEvent..
        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            Region region = Region.getRegionOfAPlayer(event.getPlayer());
            if (region == null){
                region = new Region(event.getPlayer()); // Создание региона в случае если его ещё нет
            }

            region.setPos1(event.getClickedBlock().getLocation()); // Проверка на не ебанул ли игрок по воздуху
        }

        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Region region = Region.getRegionOfAPlayer(event.getPlayer());
            if (region == null){
                region = new Region(event.getPlayer()); // Создание региона в случае если его ещё нет x2
            }

            region.setPos2(event.getClickedBlock().getLocation()); // Проверка на не ебанул ли игрок по воздуху x2
        }
    }
}
