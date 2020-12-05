/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandVote
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandVote(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     sender.sendMessage(String.valueOf(ChatUtil.Gi) + "Vote here: " + ChatColor.GOLD + "http://bit.ly/1h0usWi " + ChatColor.GRAY + "to gain " + ChatUtil.Gi + "5000 coins! " + ChatColor.GOLD + "Thank you for your support!");
/* 23 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandVote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */