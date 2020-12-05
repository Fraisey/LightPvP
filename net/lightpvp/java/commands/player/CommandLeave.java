/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandLeave
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandLeave(Core core) {
/* 16 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 21 */     if (!(sender instanceof Player)) {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     Player player = (Player)sender;
/*    */     
/* 27 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/* 28 */       if (!player.getUniqueId().equals(this.core.getHostedEventManager().getCurrentEvent().getHostUUID())) {
/* 29 */         if (this.core.getHostedEventManager().getCurrentEvent().removePlayerFromQueue(player)) {
/* 30 */           player.sendMessage(ChatColor.RED + "You left the queue!");
/* 31 */           return true;
/*    */         } 
/* 33 */         player.sendMessage(ChatColor.RED + "You are not in the queue!");
/* 34 */         return true;
/*    */       } 
/*    */       
/* 37 */       player.sendMessage(ChatColor.RED + "You already choose if you want to play or spectate!");
/*    */     } else {
/*    */       
/* 40 */       player.sendMessage(ChatColor.RED + "There is no event being hosted!");
/* 41 */       return true;
/*    */     } 
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandLeave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */