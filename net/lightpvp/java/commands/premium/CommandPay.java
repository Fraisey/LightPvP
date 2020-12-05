/*    */ package net.lightpvp.java.commands.premium;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandPay
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandPay(Core core) {
/* 19 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 24 */     if (!(sender instanceof Player))
/* 25 */       return false; 
/* 26 */     Player player = (Player)sender;
/*    */     
/* 28 */     if (player.hasPermission("core.pay")) {
/* 29 */       if (args.length > 1) {
/* 30 */         Player target = Bukkit.getPlayer(args[0]);
/* 31 */         int coins = 0;
/* 32 */         if (target != null) {
/* 33 */           if (!target.getName().equals(player.getName())) {
/*    */             try {
/* 35 */               coins = Integer.parseInt(args[1]);
/* 36 */             } catch (NumberFormatException e) {
/* 37 */               player.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
/* 38 */               return false;
/*    */             } 
/* 40 */             if (coins >= 2000) {
/* 41 */               if (this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "coins") >= coins) {
/* 42 */                 this.core.getPlayerStatsManager().decrementInt(player.getUniqueId(), coins, PlayerStat.COINS);
/* 43 */                 this.core.getPlayerStatsManager().incrementInt(target.getUniqueId(), coins, PlayerStat.COINS);
/* 44 */                 player.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "Transaction complete! " + ChatColor.RESET + ChatColor.GRAY + "You gave " + RankUtil.getFullTag(target) + " " + ChatColor.GOLD + coins + " coins!");
/* 45 */                 target.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "Transaction complete! " + ChatColor.RESET + RankUtil.getFullTag(player) + ChatColor.GRAY + " gave you " + ChatColor.GOLD + coins + " coins!");
/* 46 */                 return true;
/*    */               } 
/* 48 */               player.sendMessage(ChatColor.RED + "You don't have " + coins + " coins!");
/* 49 */               return true;
/*    */             } 
/*    */             
/* 52 */             player.sendMessage(ChatColor.RED + "You can only pay 2000+ coins!");
/* 53 */             return true;
/*    */           } 
/*    */           
/* 56 */           player.sendMessage(ChatColor.RED + "You can't pay yourself coins!");
/* 57 */           return true;
/*    */         } 
/*    */         
/* 60 */         player.sendMessage(ChatColor.RED + "Player must be online!");
/* 61 */         return true;
/*    */       } 
/*    */       
/* 64 */       player.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
/* 65 */       return true;
/*    */     } 
/*    */     
/* 68 */     player.sendMessage(ChatColor.RED + "Only " + ChatColor.GREEN + "VIP" + ChatColor.RED + "(and above) users can use the /pay command!");
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\premium\CommandPay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */