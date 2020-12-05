/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import net.lightpvp.java.utils.fancymessage.FancyMessage;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandInvitetrade
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandInvitetrade(Core core) {
/* 21 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 26 */     if (!(sender instanceof Player)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     Player player = (Player)sender;
/* 31 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 33 */     if (args.length > 0) {
/* 34 */       Player target = Bukkit.getPlayer(args[0]);
/*    */       
/* 36 */       if (target != null && !player.getName().equals(target.getName())) {
/* 37 */         user.setTradeInvite(target.getName());
/* 38 */         (new FancyMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " invited you to " + ChatUtil.Gi + "trade" + ChatColor.GRAY + ", to " + ChatUtil.Gi + "accept" + ChatColor.GRAY + " the invite " + ChatColor.GRAY + "click ")).then(ChatColor.GOLD + "here").tooltip(ChatColor.GREEN + "Click here to accept the invite!").command("/accepttrade " + player.getName()).send(target);
/* 39 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "succesfully invited " + RankUtil.getFullTag(target) + ChatColor.GRAY + " to " + ChatUtil.Gi + "trade!");
/* 40 */         return true;
/*    */       } 
/* 42 */       player.sendMessage(ChatColor.RED + "That player is not online!");
/* 43 */       return true;
/*    */     } 
/*    */     
/* 46 */     player.sendMessage(ChatColor.RED + "Usage: /invitetrade <player>");
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandInvitetrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */