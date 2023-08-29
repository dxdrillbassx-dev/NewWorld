package org.dxdrillbassx.newworld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dxdrillbassx.newworld.events.SelectRegionEvent;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new SelectRegionEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
