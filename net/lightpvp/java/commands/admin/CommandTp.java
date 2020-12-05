/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandTp
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandTp(Core core) {
/* 19 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 24 */     if (args.length > 0) {
/* 25 */       if (!(sender instanceof Player))
/* 26 */         return false; 
/* 27 */       Player player = (Player)sender;
/*    */       
/* 29 */       Player target = Bukkit.getPlayer(args[0]);
/* 30 */       if (target != null) {
/* 31 */         player.teleport((Entity)target);
/* 32 */         player.sendMessage(ChatColor.RED + "You teleported to " + RankUtil.getFullTag(target));
/* 33 */         return true;
/*    */       } 
/* 35 */       player.sendMessage(ChatColor.RED + "That player is not online!");
/* 36 */       return true;
/*    */     } 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandTp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */