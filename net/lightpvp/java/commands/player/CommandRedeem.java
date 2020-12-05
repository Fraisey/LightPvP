/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*    */ import net.lightpvp.java.managers.kit.Kit;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*    */ 
/*    */ 
/*    */ public class CommandRedeem
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandRedeem(Core core) {
/* 23 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 28 */     if (!(sender instanceof Player))
/* 29 */       return false; 
/* 30 */     Player player = (Player)sender;
/*    */     
/* 32 */     if (args.length > 0) {
/* 33 */       String code = args[0];
/* 34 */       String raw = this.core.getConfig().getString("codes." + code);
/* 35 */       if (raw != null) {
/* 36 */         String[] redeem = raw.split("-");
/* 37 */         String type = redeem[0];
/* 38 */         String value = redeem[1];
/* 39 */         this.core.getConfig().set("codes." + code, null);
/* 40 */         if (type.equalsIgnoreCase("kit")) {
/* 41 */           Kit kit = this.core.getKitManager().getKit(value);
/* 42 */           if (kit != null) {
/* 43 */             PermissionsEx.getUser(player).addPermission("kit." + value.toLowerCase());
/* 44 */             ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.YELLOW + " succesfully redeemed the code: " + code + ", and got the Kit: " + kit.getName());
/*    */           } 
/* 46 */         } else if (type.equalsIgnoreCase("coins")) {
/*    */           try {
/* 48 */             int coins = Integer.parseInt(value);
/* 49 */             this.core.getPlayerStatsManager().incrementInt(player.getUniqueId(), coins, PlayerStat.COINS);
/* 50 */             ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.YELLOW + " succesfully redeemed the code: " + code + ", and got " + coins + " Coins!");
/* 51 */           } catch (NumberFormatException e) {
/* 52 */             return true;
/*    */           } 
/* 54 */         } else if (type.equalsIgnoreCase("casinotries")) {
/*    */           try {
/* 56 */             int tries = Integer.parseInt(value);
/* 57 */             User user = this.core.getUser(player.getUniqueId());
/* 58 */             user.setSlotmachineCount(user.getSlotmachineCount() - tries);
/* 59 */             ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.YELLOW + " succesfully redeemed the code: " + code + ", and got " + tries + " Casino Tries!");
/* 60 */           } catch (NumberFormatException e) {
/* 61 */             return true;
/*    */           } 
/*    */         } 
/* 64 */         return true;
/*    */       } 
/* 66 */       player.sendMessage(ChatColor.RED + "Invalid code!");
/* 67 */       return true;
/*    */     } 
/*    */     
/* 70 */     player.sendMessage(ChatColor.RED + "Usage: /redeem <code>");
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandRedeem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */