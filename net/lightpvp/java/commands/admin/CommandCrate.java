/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class CommandCrate
/*    */   implements CommandExecutor {
/*    */   private Core core;
/*    */   
/*    */   public CommandCrate(Core core) {
/* 18 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 23 */     if (!(sender instanceof Player)) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     final Player player = (Player)sender;
/*    */     
/* 29 */     ChatUtil.broadcastMessage(ChatColor.GOLD + "███" + ChatUtil.Gi + " Helicopter" + ChatColor.GRAY + " is dropping a crate in " + ChatColor.GOLD + "30" + ChatColor.GRAY + " seconds!");
/*    */     
/* 31 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run()
/*    */         {
/* 35 */           ChatUtil.broadcastMessage(ChatColor.GOLD + "███" + ChatUtil.Gi + " Helicopter" + ChatColor.GRAY + " dropped a crate with special items in it! " + ChatUtil.Gi + "GO GET IT!");
/* 36 */           CommandCrate.this.core.getCrateItemManager().dropCrate(player);
/*    */         }
/* 38 */       }).runTaskLater((Plugin)this.core, 600L);
/*    */     
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandCrate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */