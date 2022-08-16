package com.hinaplugin.servermemory.servermemory;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class ServerMemory extends JavaPlugin {
    public static ServerMemory plugin;
    public boolean enable = true;
    public BukkitRunnable runnable;
    public BukkitTask task;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getCommand("memory").setExecutor(new Commands(plugin));
        //new ChatInfoSystem(plugin).runTaskTimer(this, 0L, 200L);
        StartMemory();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (enable){
            runnable.cancel();
            task.cancel();
        }
    }

    public static ServerMemory getPlugin(){ return plugin; }

    public void StartMemory(){
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                long used = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
                long free = runtime.freeMemory() / 1048576;
                long total = runtime.totalMemory() / 1048576;
                ComponentBuilder builder = new ComponentBuilder("").append("------------------ ").color(ChatColor.DARK_GRAY);
                builder.append("Server Memory").color(ChatColor.AQUA);
                builder.append(" ------------------\n").color(ChatColor.DARK_GRAY);
                builder.append(org.bukkit.ChatColor.WHITE + "Memory[(free) used / total] : ( " + org.bukkit.ChatColor.GREEN + free + " MB " + org.bukkit.ChatColor.WHITE + ") " + org.bukkit.ChatColor.RED + used + " MB" + org.bukkit.ChatColor.WHITE +  " / " + org.bukkit.ChatColor.AQUA + total + "MB\n");
                builder.append("--------------------------------------------------").color(ChatColor.DARK_GRAY);
                for (Player player : plugin.getServer().getOnlinePlayers()){
                    if (player != null){
                        if (!player.hasPermission("server.system.info")){
                            continue;
                        }
                        player.spigot().sendMessage(builder.create());
                    }
                }
            }
        };
        task = runnable.runTaskTimer(this, 0L, 18000L);
    }
}
