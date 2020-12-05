/*    */ package net.lightpvp.java;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Random;
/*    */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*    */ import net.lightpvp.java.managers.kit.Kit;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.potion.Potion;
/*    */ import org.bukkit.potion.PotionType;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SurpriseChest
/*    */ {
/*    */   private static final int PRICE = 50;
/*    */   private static final int COOLDOWN = 10000;
/* 25 */   private static HashMap<String, Long> cooldowns = new HashMap<>();
/* 26 */   private static Random random = new Random();
/*    */ 
/*    */   
/*    */   public static void openSupriseChest(final Player player, Core core, boolean coins) {
/* 30 */     if (cooldowns.containsKey(player.getName())) {
/* 31 */       long cooldown = System.currentTimeMillis() - ((Long)cooldowns.get(player.getName())).longValue();
/* 32 */       player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (int)((10000.0D - cooldown) / 1000.0D) + " more seconds!");
/*    */       return;
/*    */     } 
/* 35 */     if (coins) {
/* 36 */       if (core.getPlayerStatsManager().getInt(player.getUniqueId(), "coins") < 50) {
/* 37 */         player.sendMessage(ChatColor.RED + "You don't have enough coins to open a suprise chest, you need " + ChatColor.GOLD + '2' + "!");
/*    */         return;
/*    */       } 
/* 40 */       core.getPlayerStatsManager().decrementInt(player.getUniqueId(), 50, PlayerStat.COINS);
/*    */     } 
/*    */     
/* 43 */     cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/*    */     
/* 45 */     player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
/*    */     
/* 47 */     Inventory soupSuprise = Bukkit.createInventory(null, 36, "Suprise Chest");
/* 48 */     ItemStack[] soupSupriseContent = new ItemStack[36];
/* 49 */     for (int i = 0; i < soupSupriseContent.length; i++) {
/* 50 */       soupSupriseContent[i] = core.soup;
/*    */     }
/* 52 */     soupSuprise.setContents(soupSupriseContent);
/*    */     
/* 54 */     Kit playerKit = core.getKitManager().getKit(player);
/* 55 */     soupSuprise.setItem(4, (playerKit != null) ? playerKit.getKitRelatedItem() : core.soup);
/*    */     
/* 57 */     PotionType randomPotionType = null;
/* 58 */     int randomPotionNumber = random.nextInt(5);
/* 59 */     switch (randomPotionNumber) {
/*    */       case 0:
/* 61 */         randomPotionType = PotionType.SLOWNESS;
/*    */         break;
/*    */       case 1:
/* 64 */         randomPotionType = PotionType.SPEED;
/*    */         break;
/*    */       case 2:
/* 67 */         randomPotionType = PotionType.REGEN;
/*    */         break;
/*    */       case 3:
/* 70 */         randomPotionType = PotionType.INSTANT_DAMAGE;
/*    */         break;
/*    */       case 4:
/* 73 */         randomPotionType = PotionType.POISON;
/*    */         break;
/*    */     } 
/* 76 */     ItemStack randomPotion = (new Potion(randomPotionType, random.nextInt(2) + 1, true)).toItemStack(1);
/*    */     
/* 78 */     String playerWarp = core.getWarp(core.getUser(player.getUniqueId()));
/*    */     
/* 80 */     soupSuprise.setItem(31, (playerWarp == null || (playerWarp != null && playerWarp.equals("EarlyHG"))) ? randomPotion : core.soup);
/*    */     
/* 82 */     player.openInventory(soupSuprise);
/* 83 */     player.sendMessage(ChatColor.GRAY + "You opened a " + ChatUtil.Gi + "Surprise Chest" + (coins ? (ChatColor.GRAY + ", and " + ChatColor.GOLD + '2' + ChatColor.GRAY + " coins have been removed from your account!") : "!"));
/*    */     
/* 85 */     final String name = player.getName();
/* 86 */     Bukkit.getScheduler().runTaskLater((Plugin)core, new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 89 */             SurpriseChest.cooldowns.remove(name);
/* 90 */             player.sendMessage(ChatColor.GRAY + "You can now open a " + ChatUtil.Gi + "Surprise Chest" + ChatColor.GRAY + " again!");
/*    */           }
/*    */         }, 
/* 93 */         200L);
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\SurpriseChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */