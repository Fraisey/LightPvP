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
/*    */ public class CommandBan
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandBan(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (args.length >= 2) {
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
/* 39 */         targetUser.setBanExpiry(-1L);
/*    */         
/* 41 */         Player target = Bukkit.getPlayer(args[0]);
/* 42 */         if (target != null && target.isOnline()) {
/* 43 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " has been " + ChatUtil.Gi + "banned" + ChatColor.GRAY + " by " + ((sender instanceof Player) ? RankUtil.getFullTag((Player)sender) : (ChatColor.GRAY + sender.getName())) + "!");
/* 44 */           target.kickPlayer(ChatColor.RED + "You have been banned by " + sender.getName() + "!\nReason: " + reason);
/*    */         } else {
/* 46 */           ChatUtil.broadcastMessage(ChatColor.GRAY + args[0] + " has been " + ChatUtil.Gi + "banned" + ChatColor.GRAY + " by " + ((sender instanceof Player) ? RankUtil.getFullTag((Player)sender) : (ChatColor.GRAY + sender.getName())) + "!");
/*    */           
/* 48 */           this.core.saveUser(targetUser);
/*    */         } 
/*    */         
/* 51 */         this.core.getServerRecordsManager().addBanRecord(sender.getName(), (target != null) ? target.getName() : args[0], reason);
/*    */       } 
/* 53 */       return true;
/*    */     } 
/* 55 */     sender.sendMessage(ChatColor.RED + "Usage: /ban <player> <reason>");
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandBan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */