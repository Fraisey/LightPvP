/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandStats
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandStats(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (args.length == 1) {
/* 28 */       displayStats(sender, args[0]);
/*    */     }
/* 30 */     else if (sender instanceof Player) {
/* 31 */       displayStats(sender, ((Player)sender).getName());
/*    */     } 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public void displayStats(CommandSender player, String name) {
/* 38 */     String uuidPlayer = this.core.getPlayerStatsManager().getUUIDFromName(name);
/* 39 */     if (uuidPlayer != null) {
/* 40 */       UUID uuid = UUID.fromString(uuidPlayer);
/*    */       
/* 42 */       if (this.core.getPlayerStatsManager().containsPlayer(uuid)) {
/* 43 */         User targetUser = this.core.getUser(uuid);
/*    */         
/* 45 */         int kills = this.core.getPlayerStatsManager().getInt(uuid, "kills");
/* 46 */         int deaths = this.core.getPlayerStatsManager().getInt(uuid, "deaths");
/*    */         
/* 48 */         long totalSecondsPlayed = targetUser.getTotalSecondsPlayed();
/* 49 */         long hoursPlayed = totalSecondsPlayed / 3600L;
/* 50 */         long minutesPlayed = totalSecondsPlayed % 3600L / 60L;
/* 51 */         long secondsPlayed = totalSecondsPlayed % 60L;
/* 52 */         String formattedTimePlayed = String.valueOf(hoursPlayed) + " hours, " + minutesPlayed + " minutes, " + secondsPlayed + " seconds";
/*    */         
/* 54 */         player.sendMessage(ChatColor.DARK_PURPLE + name + "'s stats:");
/* 55 */         player.sendMessage(ChatColor.GOLD + "Rank: " + RankUtil.getPrefix(uuid));
/* 56 */         player.sendMessage(ChatColor.GOLD + "Coins: " + ChatColor.GREEN + this.core.getPlayerStatsManager().getInt(uuid, "coins"));
/* 57 */         player.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.GREEN + kills);
/* 58 */         player.sendMessage(ChatColor.GOLD + "Deaths: " + ChatColor.GREEN + deaths);
/* 59 */         player.sendMessage(ChatColor.GOLD + "K/D: " + ChatColor.GREEN + ((deaths == 0) ? String.valueOf(kills) : (new DecimalFormat("#.##", this.core.decimalSymbol)).format(kills / deaths)));
/* 60 */         player.sendMessage(ChatColor.GOLD + "Killstreak: " + ChatColor.GREEN + targetUser.getKillstreak());
/* 61 */         player.sendMessage(ChatColor.GOLD + "Max Killstreak: " + ChatColor.GREEN + this.core.getPlayerStatsManager().getInt(uuid, "maxkillstreak"));
/* 62 */         player.sendMessage(ChatColor.GOLD + "1v1 Wins: " + ChatColor.GREEN + this.core.getPlayerStatsManager().getInt(uuid, "duelwins"));
/* 63 */         player.sendMessage(ChatColor.GOLD + "Play Time: " + (name.equals("Chemmic") ? (ChatColor.RED + "Hidden") : (ChatColor.GREEN + formattedTimePlayed)));
/* 64 */         player.sendMessage(ChatColor.GOLD + "Last Seen: " + (name.equals("Chemmic") ? (ChatColor.RED + "Hidden") : ((targetUser.getPlayer() != null) ? (ChatColor.GREEN + "Online") : ((targetUser.getLastSeen() == 0L) ? (ChatColor.RED + "Unknown") : (ChatColor.GREEN + (new Date(targetUser.getLastSeen())).toString())))));
/*    */       } else {
/* 66 */         player.sendMessage(ChatColor.RED + "User doesn't exist!");
/*    */       } 
/*    */     } else {
/* 69 */       player.sendMessage(ChatColor.RED + "User doesn't exist!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */