/*    */ package net.lightpvp.java.managers.event.listeners;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CSGOEventHandler
/*    */   implements Listener
/*    */ {
/*    */   private Core core;
/*    */   private HashMap<String, Long> cooldowns;
/*    */   
/*    */   public CSGOEventHandler(Core core) {
/* 30 */     this.cooldowns = new HashMap<>();
/*    */     this.core = core;
/*    */   } @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*    */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 34 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 35 */       final Player player = evt.getPlayer();
/* 36 */       if (evt.getItem() != null && evt.getItem().getType().equals(Material.GOLD_HOE))
/*    */       {
/* 38 */         if (this.cooldowns.containsKey(player.getName())) {
/* 39 */           evt.setCancelled(true);
/*    */         } else {
/*    */           
/* 42 */           final Item item = player.getWorld().dropItem(player.getLocation().add(0.0D, 1.5D, 0.0D), new ItemStack(Material.CLAY_BALL, 1));
/* 43 */           player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 17.0F);
/*    */           
/* 45 */           item.setPickupDelay(200);
/* 46 */           item.setVelocity(player.getLocation().getDirection().normalize().multiply(2.2D));
/*    */           
/* 48 */           (new BukkitRunnable()
/*    */             {
/*    */               public void run() {
/* 51 */                 if (item != null && item.isDead()) {
/* 52 */                   cancel();
/*    */                   return;
/*    */                 } 
/* 55 */                 if (item.getVelocity().equals(item.getVelocity().zero())) {
/* 56 */                   item.remove();
/* 57 */                   cancel();
/*    */                   
/*    */                   return;
/*    */                 } 
/*    */                 
/* 62 */                 List<Entity> nearbyEntities = item.getNearbyEntities(0.6D, 0.6D, 0.6D);
/* 63 */                 for (Entity e : nearbyEntities) {
/* 64 */                   if (e instanceof Player) {
/* 65 */                     Player target = (Player)e;
/* 66 */                     if (!target.getName().equals(player.getName())) {
/* 67 */                       target.damage(8.0D, (Entity)player);
/* 68 */                       player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 2.0F);
/* 69 */                       item.remove();
/*    */                     } 
/*    */                   } 
/*    */                 } 
/*    */               }
/* 74 */             }).runTaskTimer((Plugin)this.core, 1L, 1L);
/*    */           
/* 76 */           final String name = player.getName();
/* 77 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 78 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*    */               {
/*    */                 public void run()
/*    */                 {
/* 82 */                   CSGOEventHandler.this.cooldowns.remove(name);
/*    */                 }
/*    */               }, 
/* 85 */               5L);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\listeners\CSGOEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */