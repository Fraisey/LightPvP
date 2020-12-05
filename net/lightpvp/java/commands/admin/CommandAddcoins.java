/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandAddcoins
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandAddcoins(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/* 26 */       return false; 
/* 27 */     Player player = (Player)sender;
/*    */     
/* 29 */     if (args.length > 1) {
/* 30 */       String targetName = args[0];
/* 31 */       int amount = 0;
/*    */       try {
/* 33 */         amount = Integer.parseInt(args[1]);
/* 34 */       } catch (NumberFormatException e) {
/* 35 */         player.sendMessage(ChatColor.RED + "That's not a number!");
/* 36 */         return true;
/*    */       } 
/*    */       
/* 39 */       String targetUUID = this.core.getPlayerStatsManager().getUUIDFromName(targetName);
/*    */       
/* 41 */       if (targetUUID != null) {
/* 42 */         this.core.getPlayerStatsManager().incrementInt(UUID.fromString(targetUUID), amount, PlayerStat.COINS);
/* 43 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Succesfully added " + ChatColor.GOLD + amount + " coins" + ChatColor.GRAY + " to " + targetName + "'s account!");
/* 44 */         return true;
/*    */       } 
/* 46 */       player.sendMessage(ChatColor.RED + "That player's UUID is not found in the database!");
/* 47 */       return true;
/*    */     } 
/*    */     
/* 50 */     player.sendMessage(ChatColor.RED + "Usage: /addmoney <player> <amount>");
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandAddcoins.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */