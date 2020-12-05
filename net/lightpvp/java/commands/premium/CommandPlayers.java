/*    */ package net.lightpvp.java.commands.premium;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CommandPlayers
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandPlayers(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (!(sender instanceof Player))
/* 23 */       return false; 
/* 24 */     Player player = (Player)sender;
/* 25 */     Inventory inv = Bukkit.createInventory(null, 36, "Player Stats");
/* 26 */     inv.setContents(this.core.getPlayerStatsManager().getAllPlayersInventory(1));
/* 27 */     player.openInventory(inv);
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\premium\CommandPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */