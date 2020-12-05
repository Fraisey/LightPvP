/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class Command1v1
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public Command1v1(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player)) {
/* 24 */       return false;
/*    */     }
/* 26 */     Player player = (Player)sender;
/* 27 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 29 */     if (user.isCombatLog() == null) {
/* 30 */       if (this.core.getDuelManager().isInDuelArena(player)) {
/* 31 */         player.sendMessage(ChatColor.GRAY + "You are already in the " + ChatUtil.Gi + "1v1" + ChatColor.GRAY + " Arena!");
/*    */       }
/* 33 */       else if ((this.core.getHostedEventManager().getCurrentEvent() != null && !this.core.getHostedEventManager().getCurrentEvent().containsPlayer(player)) || this.core.getHostedEventManager().getCurrentEvent() == null) {
/* 34 */         this.core.getDuelManager().toDuelArena(player);
/*    */       } else {
/* 36 */         player.sendMessage(ChatColor.RED + "You can't go to the 1v1 arena while playing an event!");
/*    */       } 
/*    */     } else {
/*    */       
/* 40 */       player.sendMessage(ChatColor.RED + "You can't do that while you are in combat.");
/* 41 */       return true;
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\Command1v1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */