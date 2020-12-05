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
/*    */ 
/*    */ public class CommandSc
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandSc(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/* 26 */       return false; 
/* 27 */     Player player = (Player)sender;
/*    */     
/* 29 */     StringBuilder message = new StringBuilder();
/* 30 */     for (int i = 0; i < args.length; i++) {
/* 31 */       message.append(String.valueOf(args[i]) + " ");
/*    */     }
/*    */     
/* 34 */     String staffMessage = message.toString();
/*    */     
/* 36 */     if (!staffMessage.toString().isEmpty()) {
/* 37 */       for (Player p : Bukkit.getOnlinePlayers()) {
/* 38 */         if (p.hasPermission("core.staffchat")) {
/* 39 */           p.sendMessage(ChatColor.GRAY + "(" + ChatUtil.Gi + "Staff" + ChatColor.GRAY + ") " + RankUtil.getFullTag(player) + ChatColor.WHITE + ": " + ChatColor.RED + staffMessage);
/*    */         }
/*    */       } 
/*    */     } else {
/* 43 */       player.sendMessage(ChatColor.RED + "Usage: /sc <message>");
/*    */     } 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandSc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */