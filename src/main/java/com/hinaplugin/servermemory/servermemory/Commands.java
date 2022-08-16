package com.hinaplugin.servermemory.servermemory;

import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Commands extends Insights implements CommandExecutor {
    public Commands(ServerMemory plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("memory")){
            if (!sender.hasPermission("server.system.command")){
                sender.sendMessage(ChatColor.RED + "You do not have permission this command!");
                return true;
            }
            if (Objects.equals(args[0], "start")){
                if (plugin.enable){
                    sender.sendMessage("すでに有効になっているため実行されませんでした．");
                    return true;
                }
                plugin.StartMemory();
                sender.sendMessage("サーバーインフォ通達システムを有効にしました．");
                plugin.enable = true;
                return true;
            }
            if (Objects.equals(args[0], "stop")){
                if (!plugin.enable){
                    sender.sendMessage("すでに無効になっているため実行されませんでした．");
                    return true;
                }
                plugin.runnable.cancel();
                plugin.task.cancel();
                sender.sendMessage("サーバーインフォ通達システムを無効にしました．");
                plugin.enable = false;
                return true;
            }
            if (Objects.equals(args[0], "get")){
                Runtime runtime = Runtime.getRuntime();
                long used = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
                long free = runtime.freeMemory() / 1048576;
                long total = runtime.totalMemory() / 1048576;

                ComponentBuilder builder = new ComponentBuilder("").append("------------------ ").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                builder.append("Server Memory").color(net.md_5.bungee.api.ChatColor.AQUA);
                builder.append(" ------------------\n").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                builder.append(org.bukkit.ChatColor.WHITE + "Memory[(free) used / total] : ( " + org.bukkit.ChatColor.GREEN + free + " MB " + org.bukkit.ChatColor.WHITE + ") " + org.bukkit.ChatColor.RED + used + " MB" + org.bukkit.ChatColor.WHITE +  " / " + org.bukkit.ChatColor.AQUA + total + "MB\n");
                builder.append("--------------------------------------------------").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                sender.spigot().sendMessage(builder.create());
                return true;
            }
            if (Objects.equals(args[0], "announce")){
                Runtime runtime = Runtime.getRuntime();
                long used = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
                long free = runtime.freeMemory() / 1048576;
                long total = runtime.totalMemory() / 1048576;
                ComponentBuilder builder = new ComponentBuilder("").append("------------------ ").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                builder.append("Server Memory").color(net.md_5.bungee.api.ChatColor.AQUA);
                builder.append(" ------------------\n").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                builder.append(org.bukkit.ChatColor.WHITE + "Memory[(free) used / total] : ( " + org.bukkit.ChatColor.GREEN + free + " MB " + org.bukkit.ChatColor.WHITE + ") " + org.bukkit.ChatColor.RED + used + " MB" + org.bukkit.ChatColor.WHITE +  " / " + org.bukkit.ChatColor.AQUA + total + "MB\n");
                builder.append("--------------------------------------------------").color(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                for (Player player : plugin.getServer().getOnlinePlayers()){
                    if (player != null){
                        if (!player.hasPermission("server.system.info")){
                            continue;
                        }
                        player.spigot().sendMessage(builder.create());
                    }
                }
                return true;
            }
            if (Objects.equals(args[0], "tps")){
                double[] tps = plugin.getServer().getTPS();
                sender.sendMessage("TPS : " + Math.round(tps[0] * 100.0D) / 100.0D);
                return true;
            }
        }
        return false;
    }
}
