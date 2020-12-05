/*    */ package net.lightpvp.java.commands.premium;
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
/*    */ public class CommandJoinMessage
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandJoinMessage(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player))
/* 24 */       return false; 
/* 25 */     Player player = (Player)sender;
/* 26 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 28 */     player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + (user.isJoinMessage() ? "disabled" : "enabled") + ChatColor.GRAY + " join messages!");
/* 29 */     user.setJoinMessage(!user.isJoinMessage());
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\premium\CommandJoinMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */