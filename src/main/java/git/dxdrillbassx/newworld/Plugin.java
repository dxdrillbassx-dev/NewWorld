package git.dxdrillbassx.newworld;

import git.dxdrillbassx.newworld.commands.RegionCommands;
import git.dxdrillbassx.newworld.events.SelectRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;


public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new SelectRegionEvent(), this);

        new RegionCommands(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
