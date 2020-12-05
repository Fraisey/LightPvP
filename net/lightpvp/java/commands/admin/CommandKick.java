/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandKick
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandKick(Core core) {
/* 19 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 24 */     if (args.length > 1) {
/* 25 */       Player target = Bukkit.getPlayer(args[0]);
/* 26 */       if (target != null) {
/* 27 */         if (!sender.getName().equals(target.getName())) {
/* 28 */           StringBuilder builder = new StringBuilder();
/* 29 */           for (int i = 1; i < args.length; i++) {
/* 30 */             builder.append(String.valueOf(args[i]) + " ");
/*    */           }
/*    */           
/* 33 */           String reason = builder.toString();
/*    */           
/* 35 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " has been " + ChatUtil.Gi + "kicked" + ChatColor.GRAY + " by " + ((sender instanceof Player) ? RankUtil.getFullTag((Player)sender) : (ChatColor.GRAY + sender.getName())));
/* 36 */           target.kickPlayer(ChatColor.RED + "You were kicked by " + sender.getName() + "\nReason: " + reason);
/*    */           
/* 38 */           this.core.getServerRecordsManager().addKickRecord(sender.getName(), target.getName(), reason);
/* 39 */           return true;
/*    */         } 
/* 41 */         sender.sendMessage(ChatColor.RED + "You can't kick yourself!");
/* 42 */         return true;
/*    */       } 
/*    */       
/* 45 */       sender.sendMessage(ChatColor.RED + "That player is not online!");
/* 46 */       return true;
/*    */     } 
/*    */     
/* 49 */     sender.sendMessage(ChatColor.RED + "Usage: /kick <player> <reason>");
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandKick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */