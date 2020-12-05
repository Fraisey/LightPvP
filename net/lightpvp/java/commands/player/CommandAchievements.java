/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandAchievements
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandAchievements(Core core) {
/* 16 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 21 */     if (!(sender instanceof Player)) {
/* 22 */       return false;
/*    */     }
/* 24 */     Player player = (Player)sender;
/*    */     
/* 26 */     if (args.length == 1) {
/* 27 */       player.sendMessage(ChatColor.DARK_PURPLE + "Achievements:");
/*    */     }
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandAchievements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */