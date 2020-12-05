/*    */ package net.lightpvp.java.commands.admin;
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
/*    */ public class CommandSpy
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandSpy(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player))
/* 24 */       return false; 
/* 25 */     Player player = (Player)sender;
/* 26 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 28 */     if (user.isSpyMode()) {
/* 29 */       user.setSpyMode(false);
/* 30 */       player.sendMessage(ChatColor.GRAY + "Spy mode " + ChatUtil.Gi + "disabled!");
/*    */     } else {
/* 32 */       user.setSpyMode(true);
/* 33 */       player.sendMessage(ChatColor.GRAY + "Spy mode " + ChatUtil.Gi + "enabled!");
/*    */     } 
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandSpy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */