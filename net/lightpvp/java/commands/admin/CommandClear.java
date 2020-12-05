/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandClear
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandClear(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player)) {
/* 26 */       return false;
/*    */     }
/* 28 */     Player player = (Player)sender;
/*    */     
/* 30 */     if (args.length == 0) {
/* 31 */       player.sendMessage(ChatColor.RED + "/clear <player>");
/* 32 */     } else if (args.length == 1) {
/* 33 */       Player target = Bukkit.getPlayer(args[0]);
/* 34 */       if (target.isOnline()) {
/* 35 */         target.getInventory().clear();
/* 36 */         target.getInventory().setArmorContents(null);
/* 37 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "sucessfully cleared " + RankUtil.getFullTag(target) + "'s inventory and armour contents!");
/*    */       } else {
/* 39 */         player.sendMessage(ChatColor.RED + args[0] + " is not online!");
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandClear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */