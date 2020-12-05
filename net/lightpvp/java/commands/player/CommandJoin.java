/*    */ package net.lightpvp.java.commands.player;
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
/*    */ public class CommandJoin
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandJoin(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player)) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     Player player = (Player)sender;
/*    */     
/* 29 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/* 30 */       if (!this.core.getHostedEventManager().getCurrentEvent().getStarted()) {
/* 31 */         if (!player.getUniqueId().equals(this.core.getHostedEventManager().getCurrentEvent().getHostUUID())) {
/* 32 */           if (this.core.getHostedEventManager().getCurrentEvent().getQueue().size() < this.core.getHostedEventManager().getCurrentEvent().getMaximumPlayers()) {
/* 33 */             if (this.core.getHostedEventManager().getCurrentEvent().addPlayerToQueue(player)) {
/* 34 */               this.core.getHostedEventManager().getCurrentEvent().broadcastQueue(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " joined the " + ChatUtil.Gi + "queue! " + ChatColor.GRAY + "(" + this.core.getHostedEventManager().getCurrentEvent().getQueue().size() + "/" + this.core.getHostedEventManager().getCurrentEvent().getMaximumPlayers() + " players)");
/* 35 */               return true;
/*    */             } 
/* 37 */             player.sendMessage(ChatColor.RED + "You are already in the queue!");
/* 38 */             return true;
/*    */           } 
/*    */           
/* 41 */           player.sendMessage(ChatColor.RED + "The event is full (" + this.core.getHostedEventManager().getCurrentEvent().getMaximumPlayers() + " players)!");
/* 42 */           return true;
/*    */         } 
/*    */         
/* 45 */         player.sendMessage(ChatColor.RED + "You already choose if you want to play or spectate!");
/* 46 */         return true;
/*    */       } 
/*    */       
/* 49 */       player.sendMessage(ChatColor.RED + "You can't join the queue because the event has already started!");
/* 50 */       return true;
/*    */     } 
/*    */     
/* 53 */     player.sendMessage(ChatColor.RED + "There is no event being hosted!");
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandJoin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */