/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandBroadcast
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandBroadcast(Core core) {
/* 19 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 24 */     if (!(sender instanceof Player)) {
/* 25 */       return false;
/*    */     }
/*    */     
/* 28 */     Player player = (Player)sender;
/*    */     
/* 30 */     if (args.length > 0) {
/* 31 */       String str; ChatColor chatColor = ChatColor.GRAY;
/* 32 */       for (int i = 0; i < args.length; i++) {
/* 33 */         str = String.valueOf(chatColor) + args[i] + " ";
/*    */       }
/* 35 */       ChatUtil.broadcastMessage(String.valueOf(ChatUtil.LPp) + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', str) + ChatColor.GRAY + " - " + RankUtil.getFullTag(player));
/*    */     } 
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandBroadcast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */