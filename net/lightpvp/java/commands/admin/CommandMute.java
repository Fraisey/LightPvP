/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
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
/*    */ public class CommandMute
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandMute(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (args.length >= 1) {
/* 26 */       Player target = Bukkit.getPlayer(args[0]);
/* 27 */       if (target != null) {
/* 28 */         if (!sender.getName().equals(target.getName())) {
/* 29 */           User targetUser = this.core.getUser(target.getUniqueId());
/* 30 */           if (args.length == 2) {
/* 31 */             int minutes = 60;
/*    */             try {
/* 33 */               minutes = Integer.parseInt(args[1]);
/* 34 */             } catch (NumberFormatException numberFormatException) {}
/*    */             
/* 36 */             if (minutes > 240) {
/* 37 */               minutes = 240;
/*    */             }
/* 39 */             targetUser.setMuted(System.currentTimeMillis() + (minutes * 60 * 1000));
/* 40 */             ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " has been " + ChatUtil.Gi + "muted" + ChatColor.GRAY + " by " + ((sender instanceof Player) ? RankUtil.getFullTag((Player)sender) : (ChatColor.GRAY + sender.getName())) + ChatColor.GRAY + " for " + ChatUtil.Gi + minutes + ChatColor.GRAY + " minutes!");
/*    */             
/* 42 */             this.core.getServerRecordsManager().addMuteRecord(sender.getName(), target.getName());
/* 43 */             return true;
/*    */           } 
/* 45 */           sender.sendMessage(ChatColor.RED + "Usage: /mute <player> <minutes (max 240)>");
/* 46 */           return true;
/*    */         } 
/*    */         
/* 49 */         sender.sendMessage(ChatColor.RED + "You can't mute yourself!");
/* 50 */         return true;
/*    */       } 
/*    */       
/* 53 */       sender.sendMessage(ChatColor.RED + "Player must be online!");
/* 54 */       return true;
/*    */     } 
/*    */     
/* 57 */     sender.sendMessage(ChatColor.RED + "Usage: /mute <player> <minutes (max 240)>");
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandMute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */