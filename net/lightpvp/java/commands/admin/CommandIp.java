/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.Util;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandIp
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandIp(Core core) {
/* 27 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
/* 32 */     if (args.length > 0) {
/* 33 */       final String input = args[0];
/*    */       
/* 35 */       sender.sendMessage(ChatColor.RED + input + ":");
/* 36 */       if (Util.validIP(input)) {
/* 37 */         List<String> names = this.core.getPlayerStatsManager().getNamesFromIP(input);
/* 38 */         if (names != null) {
/* 39 */           String message = ChatColor.RED + "Names: ";
/* 40 */           boolean first = true;
/* 41 */           for (String name : names) {
/* 42 */             String uuidString = this.core.getPlayerStatsManager().getUUIDFromName(name);
/* 43 */             UUID uuid = null;
/* 44 */             User user = null;
/*    */             
/* 46 */             if (uuidString != null) {
/* 47 */               uuid = UUID.fromString(uuidString);
/*    */             }
/*    */             
/* 50 */             if (uuid != null) {
/* 51 */               user = this.core.getUser(uuid);
/*    */             }
/*    */             
/* 54 */             message = String.valueOf(message) + ChatColor.GOLD + (first ? "" : ", ") + name + ChatColor.RED + " (" + ((user != null) ? (user.isBanned() ? ((user.getBanExpiry() == -1L) ? "BANNED" : "TEMP BANNED") : (ChatColor.GOLD + "NOT BANNED")) : "UNKNOWN") + ChatColor.RED + ")";
/* 55 */             first = false;
/*    */           } 
/* 57 */           sender.sendMessage(message);
/*    */           
/* 59 */           sender.sendMessage(ChatColor.RED + "Looking up from what country this ip is from...");
/* 60 */           (new BukkitRunnable()
/*    */             {
/*    */               public void run()
/*    */               {
/* 64 */                 final String country = CommandIp.this.getCountry(input);
/* 65 */                 (new BukkitRunnable()
/*    */                   {
/*    */                     public void run()
/*    */                     {
/* 69 */                       sender.sendMessage(ChatColor.RED + input + " - " + ChatColor.GOLD + country);
/*    */                     }
/* 71 */                   }).runTask((Plugin)CommandIp.this.core);
/*    */               }
/* 73 */             }).runTaskAsynchronously((Plugin)this.core);
/*    */         } else {
/* 75 */           sender.sendMessage(ChatColor.RED + "Not found!");
/*    */         } 
/*    */       } else {
/* 78 */         String ip = this.core.getPlayerStatsManager().getIPFromName(input);
/* 79 */         if (ip != null) {
/* 80 */           sender.sendMessage(ChatColor.RED + "IP: " + ip + "\n------");
/* 81 */           Bukkit.dispatchCommand(sender, "ip " + ip);
/*    */         } else {
/* 83 */           sender.sendMessage(ChatColor.RED + "Not found!");
/*    */         } 
/*    */       } 
/*    */     } else {
/* 87 */       sender.sendMessage(ChatColor.RED + "Usage: /ip <player/ip>");
/*    */     } 
/* 89 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getCountry(String ip) {
/* 94 */     return "unknown";
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */