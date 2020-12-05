/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.managers.lag.LagManager;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandPing
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandPing(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (!(sender instanceof Player))
/* 28 */       return false; 
/* 29 */     Player player = (Player)sender;
/*    */     
/* 31 */     int senderPing = (((CraftPlayer)player).getHandle()).ping;
/*    */     
/* 33 */     double tps = LagManager.getTPS();
/* 34 */     double lag = Math.round((1.0D - tps / 20.0D) * 100.0D);
/*    */     
/* 36 */     if (args.length == 0) {
/* 37 */       player.sendMessage(ChatColor.GOLD + "Your ping to this server is " + ChatColor.GREEN + senderPing + ChatColor.GOLD + "ms");
/* 38 */       player.sendMessage(ChatColor.GOLD + "Server TPS: " + ChatUtil.Gi + tps + " tps");
/* 39 */       player.sendMessage(ChatColor.GOLD + "Lag is approximately at: " + ChatUtil.Gi + lag + "%");
/* 40 */     } else if (args.length == 1) {
/* 41 */       Player target = Bukkit.getPlayer(args[0]);
/* 42 */       if (target == null) {
/* 43 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + args[0] + ChatColor.GRAY + " is not " + ChatUtil.Gi + "online!");
/*    */       } else {
/* 45 */         int targetPing = (((CraftPlayer)target).getHandle()).ping;
/* 46 */         player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GOLD + "'s ping to this server is " + ChatColor.GREEN + targetPing + ChatColor.GOLD + "ms");
/* 47 */         player.sendMessage(ChatColor.GOLD + "Server TPS: " + ChatUtil.Gi + tps + " tps");
/* 48 */         player.sendMessage(ChatColor.GOLD + "Lag is approximately at: " + ChatUtil.Gi + lag + "%");
/*    */       } 
/* 50 */     } else if (args.length > 1) {
/* 51 */       player.sendMessage(ChatColor.RED + "Usage: /ping <player>");
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */