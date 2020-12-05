/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.managers.kit.Kit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandKits
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandKits(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (!(sender instanceof Player))
/* 23 */       return false; 
/* 24 */     Player player = (Player)sender;
/* 25 */     String kits = "";
/* 26 */     int totalKits = 0;
/* 27 */     int ownedKits = 0; byte b; int i; Kit[] arrayOfKit;
/* 28 */     for (i = (arrayOfKit = this.core.getKitManager().getKitArray()).length, b = 0; b < i; ) { Kit kit = arrayOfKit[b];
/* 29 */       boolean ownsKit = this.core.getKitManager().hasKit(player, kit);
/* 30 */       if (ownsKit)
/* 31 */         ownedKits++; 
/* 32 */       if (totalKits == 0) {
/* 33 */         kits = String.valueOf(kits) + (ownsKit ? (ChatColor.GREEN + kit.getName()) : (ChatColor.RED + kit.getName()));
/*    */       } else {
/* 35 */         kits = String.valueOf(kits) + ", " + (ownsKit ? (ChatColor.GREEN + kit.getName()) : (ChatColor.RED + kit.getName()));
/*    */       } 
/* 37 */       totalKits++; b++; }
/*    */     
/* 39 */     player.sendMessage(ChatColor.BLUE + "Kits(" + ownedKits + "/" + totalKits + "): " + kits);
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandKits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */