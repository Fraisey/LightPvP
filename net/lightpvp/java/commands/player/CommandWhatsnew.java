/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandWhatsnew
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandWhatsnew(Core core) {
/* 16 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 21 */     sender.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "New:\n" + ChatColor.GOLD + ChatColor.BOLD + "- Fly-hack Protection\n- /Stats now shows when the player was last seen\n- Support for trails\n- Rewrote macro detection");
/* 22 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandWhatsnew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */