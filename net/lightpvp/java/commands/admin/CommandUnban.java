/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandUnban
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandUnban(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (args.length == 1) {
/* 24 */       String targetName = args[0];
/* 25 */       String uuidString = this.core.getPlayerStatsManager().getUUIDFromName(targetName);
/* 26 */       if (uuidString != null) {
/* 27 */         User targetUser = this.core.getUser(UUID.fromString(uuidString));
/* 28 */         if (targetUser.isBanned()) {
/* 29 */           targetUser.setBanExpiry(System.currentTimeMillis());
/* 30 */           sender.sendMessage(ChatColor.RED + "You unbanned " + targetName + "!");
/* 31 */           this.core.saveUser(targetUser);
/*    */           
/* 33 */           this.core.getServerRecordsManager().addUnbanRecord(sender.getName(), targetName);
/* 34 */           return true;
/*    */         } 
/* 36 */         sender.sendMessage(ChatColor.RED + "That player is not banned!");
/*    */       } else {
/*    */         
/* 39 */         sender.sendMessage(ChatColor.RED + "UUID not found!");
/*    */       } 
/* 41 */       return true;
/*    */     } 
/* 43 */     sender.sendMessage(ChatColor.RED + "Usage: /unban <player>");
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandUnban.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */