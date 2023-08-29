package org.dxdrillbassx.newworld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private static final List<Region> regionList = new ArrayList<>();

    private Location pos1, pos2;

    private Player owner; // Уже какой-то WorldGuard а не WorldEdit...

    public void setBlock(Material material){
        // Заменяем все блоки во всем регионе
        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        Location location;

        for (int x = x1; x <= x2; x++){ //Вложенный цыкл 1..5
            for (int y = y1; y <= y2; y++){ //Вложенный цыкл 1..5
                for (int z = z1; z <= z2; z++){ //Вложенный цыкл 1..5
                    location = new Location(pos1.getWorld(), x, y, z);
                    location.getBlock().setType(material);
                }
            }
        }
    }

    public void expand(int blockNum, Side side){

    }

    public void showRegion(){

    }

    public void undo(){
        //TODO: временно не ебу как реализовать
    }

    public void copy(){
        //TODO: временно не ебу как реализовать
    }

    public void paste(){
        //TODO: временно не ебу как реализовать
    }
}
