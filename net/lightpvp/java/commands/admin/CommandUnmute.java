/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandUnmute
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandUnmute(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (args.length == 1) {
/* 24 */       Player target = Bukkit.getPlayer(args[0]);
/* 25 */       if (target != null) {
/* 26 */         User targetUser = this.core.getUser(target.getUniqueId());
/* 27 */         if (targetUser.isMuted()) {
/* 28 */           targetUser.setMuted(0L);
/* 29 */           sender.sendMessage(ChatColor.RED + "You unmuted " + target.getName());
/* 30 */           target.sendMessage(ChatColor.GRAY + "You have been unmuted by " + sender.getName());
/*    */           
/* 32 */           this.core.getServerRecordsManager().addUnmuteRecord(sender.getName(), target.getName());
/* 33 */           return true;
/*    */         } 
/* 35 */         sender.sendMessage(ChatColor.RED + "That player is not muted!");
/* 36 */         return true;
/*    */       } 
/*    */       
/* 39 */       sender.sendMessage(ChatColor.RED + "Player must be online!");
/* 40 */       return true;
/*    */     } 
/*    */     
/* 43 */     sender.sendMessage(ChatColor.RED + "Usage: /unmute <player>");
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandUnmute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */