/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandTempban
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandTempban(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (args.length >= 3) {
/* 28 */       String uuid = this.core.getPlayerStatsManager().getUUIDFromName(args[0]);
/* 29 */       if (uuid != null) {
/* 30 */         User targetUser = this.core.getUser(UUID.fromString(uuid));
/*    */         
/* 32 */         StringBuilder builder = new StringBuilder();
/* 33 */         for (int i = 1; i < args.length; i++) {
/* 34 */           builder.append(String.valueOf(args[i]) + " ");
/*    */         }
/*    */         
/* 37 */         String reason = builder.toString();
/*    */         
/* 39 */         int hours = 1;
/*    */         try {
/* 41 */           hours = Integer.parseInt(args[args.length - 1]);
/* 42 */         } catch (NumberFormatException numberFormatException) {}
/*    */ 
/*    */         
/* 45 */         if (hours > 96) {
/* 46 */           hours = 96;
/*    */         }
/*    */         
/* 49 */         long milli = System.currentTimeMillis() + (hours * 1000 * 60 * 60);
/* 50 */         targetUser.setBanExpiry(milli);
/*    */         
/* 52 */         Player target = Bukkit.getPlayer(args[0]);
/* 53 */         if (target != null && target.isOnline()) {
/* 54 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " has been " + ChatUtil.Gi + "tempbanned" + ChatColor.GRAY + " by " + ((sender instanceof Player) ? RankUtil.getFullTag((Player)sender) : (ChatColor.GRAY + sender.getName())) + ChatColor.GRAY + " for " + ChatUtil.Gi + hours + ChatColor.GRAY + " hours!");
/* 55 */           target.kickPlayer(ChatColor.RED + "You have been banned by " + sender.getName() + " for " + hours + " hour" + ((hours != 1) ? "s" : "") + "!\nReason: " + reason);
/*    */         } else {
/* 57 */           this.core.saveUser(targetUser);
/*    */         } 
/*    */         
/* 60 */         this.core.getServerRecordsManager().addTempBanRecord(sender.getName(), (target != null) ? target.getName() : args[0], reason);
/*    */       } 
/* 62 */       return true;
/*    */     } 
/* 64 */     sender.sendMessage(ChatColor.RED + "Usage: /tempban <player> <reason> <hours (max 96)>");
/* 65 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandTempban.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */