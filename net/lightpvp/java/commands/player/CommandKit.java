/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.managers.kit.Kit;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandKit
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandKit(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player)) {
/* 26 */       return false;
/*    */     }
/* 28 */     Player player = (Player)sender;
/*    */     
/* 30 */     if (args.length > 0) {
/* 31 */       Player target = Bukkit.getPlayer(args[0]);
/* 32 */       if (target != null) {
/* 33 */         Kit kit = this.core.getKitManager().getKit(target);
/* 34 */         if (kit != null) {
/* 35 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " is using the kit: " + ChatUtil.Gi + kit.getName() + "!");
/*    */         } else {
/* 37 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " is not using a kit!");
/*    */         } 
/*    */       } else {
/* 40 */         player.sendMessage(ChatColor.RED + "That player is not online!");
/*    */       } 
/*    */     } else {
/* 43 */       player.sendMessage(ChatColor.RED + "Usage: /kit <player>");
/*    */     } 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */