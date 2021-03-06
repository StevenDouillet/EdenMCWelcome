package fr.steven.edenmcwelcome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class EdenMCWelcome extends JavaPlugin implements Listener {

    public static Map<String, Long> newPlayer = new HashMap<>();
    public static Map<String, Long> welcomeSended = new HashMap<>();
    public static List<String> welcomeMessages = Arrays.asList(
            "Bienvenue " + ChatColor.BLUE + "%player%" + ChatColor.WHITE + " sur le serveur !",
            "Bienvenue " + ChatColor.BLUE + "%player%" + ChatColor.WHITE + " sur EdenCraft :)",
            "Bienvenue " + ChatColor.BLUE + "%player%",
            "Bienvenue " + ChatColor.BLUE + "%player%" + ChatColor.WHITE + " et bon jeu",
            "Welcome " + ChatColor.BLUE + "%player%",
            "Bienvenue parmi nous " + ChatColor.BLUE + "%player%" + ChatColor.WHITE + " :)"
    );

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("b").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player playerSender = (Player) sender;

            if(!newPlayer.isEmpty()) {

                if(newPlayer.entrySet().iterator().next().getValue() < System.currentTimeMillis()){
                    newPlayer.clear();
                    welcomeSended.clear();
                    playerSender.sendMessage(ChatColor.RED + "Il n'y a pas de nouveau joueur à qui souhaiter la bienvenue :c");
                } else {

                    String newPlayerName = newPlayer.entrySet().iterator().next().getKey();

                    if(welcomeSended.containsKey(playerSender.getDisplayName())) {
                        playerSender.sendMessage(ChatColor.RED + "Vous avez déjà souhaité la bienvenue à " + newPlayerName);
                    } else {
                        welcomeSended.put(playerSender.getDisplayName(), System.currentTimeMillis());
                        if(newPlayerName.equals(playerSender.getDisplayName())) {
                            playerSender.chat("Je me souhaite la bienvenue");
                        } else {
                            Random rand = new Random();
                            playerSender.chat(welcomeMessages.get(rand.nextInt(welcomeMessages.size())).replace("%player%", newPlayerName));
                        }
                    }

                }

            } else {
                playerSender.sendMessage(ChatColor.RED + "Il n'y a pas de nouveau joueur à qui souhaiter la bienvenue :c");
            }
        }

        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            newPlayer.clear();
            welcomeSended.clear();
            newPlayer.put(event.getPlayer().getDisplayName(), System.currentTimeMillis() + (60 * 1000));
        }
    }

}
