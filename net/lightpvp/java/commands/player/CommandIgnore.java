/*    */ package net.lightpvp.java.commands.player;
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
/*    */ public class CommandIgnore
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandIgnore(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     Player player = (Player)sender;
/*    */     
/* 31 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 33 */     if (args.length > 0) {
/* 34 */       Player target = Bukkit.getPlayer(args[0]);
/*    */       
/* 36 */       if (target != null) {
/* 37 */         if (user.getIgnorePlayers().size() < 20) {
/* 38 */           user.ignorePlayer(target);
/* 39 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "Succesfully" + ChatColor.GRAY + (user.ignoresPlayer(target) ? " added " : " removed ") + RankUtil.getFullTag(target) + ChatColor.GRAY + (user.ignoresPlayer(target) ? " to " : " from ") + "your ignore list!");
/*    */         } else {
/* 41 */           player.sendMessage(ChatColor.RED + "Your ignore list is full! (20 players)");
/*    */         } 
/*    */       }
/*    */     } else {
/* 45 */       player.sendMessage(ChatColor.RED + "Usage: /ignore <player>");
/*    */     } 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandIgnore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */