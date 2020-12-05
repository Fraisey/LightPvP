/*    */ package net.lightpvp.java.commands.premium;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.Util;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ public class CommandFly
/*    */   implements CommandExecutor, Listener
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandFly(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/* 26 */       return false; 
/* 27 */     Player player = (Player)sender;
/* 28 */     User user = this.core.getUser(player.getUniqueId());
/* 29 */     if (user.getWarp().isEmpty()) {
/* 30 */       if (this.core.getHostedEventManager().getCurrentEvent() == null || (this.core.getHostedEventManager().getCurrentEvent() != null && !this.core.getHostedEventManager().getCurrentEvent().containsPlayer(player))) {
/* 31 */         if (this.core.getDuelManager().isDueling(player) == null) {
/* 32 */           if (user.isCombatLog() == null) {
/* 33 */             if (player.getAllowFlight()) {
/* 34 */               this.core.toSpawn(user, false);
/* 35 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "are now out of " + ChatUtil.Gi + "fly mode!");
/*    */             }
/* 37 */             else if (player.getLocation().getBlockY() <= 100) {
/* 38 */               Util.clearPlayer(player);
/* 39 */               this.core.setSpawnFlag(user, false);
/* 40 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "are now in " + ChatUtil.Gi + "fly mode!");
/* 41 */               player.setAllowFlight(true);
/*    */             } else {
/* 43 */               player.sendMessage(ChatColor.RED + "You can't fly above Y:100");
/*    */             } 
/*    */           } else {
/*    */             
/* 47 */             player.sendMessage(ChatColor.RED + "You can't do that while you are in combat.");
/*    */           } 
/*    */         } else {
/* 50 */           player.sendMessage(ChatColor.RED + "You can't fly in a duel!");
/*    */         } 
/*    */       } else {
/* 53 */         player.sendMessage(ChatColor.RED + "You can't fly in an event!");
/*    */       } 
/*    */     } else {
/* 56 */       player.sendMessage(ChatColor.RED + "You can't fly in a warp!");
/*    */     } 
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\premium\CommandFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */