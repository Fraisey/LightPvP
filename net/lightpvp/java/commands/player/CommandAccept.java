/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandAccept
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandAccept(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (!(sender instanceof Player)) {
/* 23 */       return false;
/*    */     }
/* 25 */     Player player = (Player)sender;
/*    */     
/* 27 */     if (args.length > 0) {
/* 28 */       if (this.core.getDuelManager().isInDuelArena(player)) {
/* 29 */         Player target = Bukkit.getPlayer(args[0]);
/* 30 */         if (target != null && this.core.getDuelManager().isInDuelArena(target)) {
/* 31 */           this.core.getDuelManager().acceptDuel(player, target);
/*    */         } else {
/* 33 */           player.sendMessage(ChatColor.RED + "That player is not playing OneVsOne!");
/*    */         } 
/*    */       } 
/*    */     } else {
/* 37 */       player.sendMessage(ChatColor.RED + "Usage: /accept <player>");
/* 38 */       return false;
/*    */     } 
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandAccept.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */