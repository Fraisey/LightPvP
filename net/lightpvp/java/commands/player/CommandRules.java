/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandRules
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandRules(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (!(sender instanceof Player)) {
/* 23 */       return false;
/*    */     }
/* 25 */     Player player = (Player)sender;
/*    */     
/* 27 */     player.sendMessage(String.valueOf(ChatUtil.Gi) + "Rules:\n- " + ChatColor.GRAY + "No Hacking! Hacking is " + ChatColor.RED + ChatColor.BOLD + "NOT ALLOWED AT ALL " + ChatColor.GRAY + "no matter what! Hacking = a " + ChatColor.RED + ChatColor.BOLD + "PERMANENT BAN!\n" + ChatUtil.Gi + "- " + ChatColor.GRAY + "No DDoS Threats! " + ChatColor.RED + ChatColor.BOLD + "ANY DDoS threats of ANY KIND" + "(even if it is a joke) is = to a " + ChatColor.RED + ChatColor.BOLD + "PERMANENT BAN!\n" + ChatUtil.Gi + "- " + ChatColor.GRAY + "No Advertising! Advertising(saying JOIN MY SERVER: EU.LIGHTPVP.NET || AU.LIGHTPVP.NET) = to a " + ChatColor.RED + ChatColor.BOLD + "PERMANENT BAN!\n" + ChatUtil.Gi + "- " + ChatColor.GRAY + "No Abusing Bugs! If you find a bug, please report it and DO NOT ABUSE IT. If you are caught abusing a bug you will be " + ChatColor.RED + ChatColor.BOLD + "TEMPORARILY BANNED!");
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */