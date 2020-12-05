/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.Util;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class CommandAdmin
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandAdmin(Core core) {
/* 23 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 28 */     if (!(sender instanceof Player))
/* 29 */       return false; 
/* 30 */     Player player = (Player)sender;
/* 31 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 33 */     if (player.getAllowFlight()) {
/* 34 */       this.core.toSpawn(user, false);
/* 35 */       player.sendMessage(ChatColor.RED + "You exited admin mode!");
/* 36 */       Bukkit.getServer().dispatchCommand((CommandSender)player, "Trail Magic");
/* 37 */       return true;
/*    */     } 
/* 39 */     Bukkit.getServer().dispatchCommand((CommandSender)player, "Trail off");
/* 40 */     user.setSpawn(false);
/* 41 */     Util.clearPlayer(player);
/* 42 */     player.setAllowFlight(true);
/* 43 */     player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0));
/* 44 */     player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2147483647, 3));
/* 45 */     player.getInventory().setItem(0, new ItemStack(Material.COMPASS));
/* 46 */     player.sendMessage(ChatColor.GRAY + "You are now in admin mode!");
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandAdmin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */