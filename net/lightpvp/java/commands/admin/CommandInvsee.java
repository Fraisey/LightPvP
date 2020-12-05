/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CommandInvsee
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandInvsee(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player))
/* 24 */       return false; 
/* 25 */     Player player = (Player)sender;
/*    */     
/* 27 */     if (args.length > 0) {
/* 28 */       Player target = Bukkit.getPlayer(args[0]);
/* 29 */       if (target != null) {
/* 30 */         player.openInventory((Inventory)target.getInventory());
/* 31 */         return true;
/*    */       } 
/* 33 */       player.sendMessage(ChatColor.RED + "That player is not online!");
/* 34 */       return true;
/*    */     } 
/*    */     
/* 37 */     player.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandInvsee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */