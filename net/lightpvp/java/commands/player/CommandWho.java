/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandWho
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandWho(Core core) {
/* 19 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 24 */     StringBuilder message = new StringBuilder();
/* 25 */     message.append(ChatColor.GRAY + "Online Players(" + ChatColor.GOLD + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "):\n");
/*    */     
/* 27 */     boolean first = true;
/*    */     
/* 29 */     message.append(ChatColor.GRAY + "[");
/* 30 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 31 */       message.append(String.valueOf(first ? "" : ", ") + RankUtil.getFullTag(p) + ChatColor.GRAY);
/* 32 */       first = false;
/*    */     } 
/* 34 */     message.append(ChatColor.GRAY + "]");
/*    */     
/* 36 */     sender.sendMessage(message.toString());
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandWho.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */