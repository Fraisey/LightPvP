/*     */ package net.lightpvp.java.listener;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.event.events.BracketsEvent;
/*     */ import net.lightpvp.java.managers.event.events.CannonDodgeEvent;
/*     */ import net.lightpvp.java.managers.event.events.FootballEvent;
/*     */ import net.lightpvp.java.managers.event.events.HostedEvent;
/*     */ import net.lightpvp.java.managers.event.events.LMSHostedEvent;
/*     */ import net.lightpvp.java.managers.event.events.OneManArmyEvent;
/*     */ import net.lightpvp.java.managers.event.events.RedRoverEvent;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.InventoryUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Slime;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.entity.ItemDespawnEvent;
/*     */ import org.bukkit.event.entity.ItemSpawnEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreEntityListener
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  57 */   private Random random = new Random();
/*     */   
/*     */   public CoreEntityListener(Core core) {
/*  60 */     this.core = core;
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/*  65 */     if (evt.getEntity() instanceof Player) {
/*  66 */       Player player = (Player)evt.getEntity();
/*     */       
/*  68 */       if (player.getAllowFlight() && !player.hasPotionEffect(PotionEffectType.INVISIBILITY) && !evt.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
/*  69 */         evt.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*  73 */       if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
/*  74 */         String warpName = this.core.getWarp(this.core.getUser(player.getUniqueId()));
/*  75 */         if (warpName != null && warpName.equals("FisherFight")) {
/*  76 */           evt.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  81 */       if (evt.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
/*  82 */         evt.setDamage(100.0D);
/*     */       }
/*     */       
/*  85 */       if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  86 */         HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  87 */         if (ev instanceof net.lightpvp.java.managers.event.events.SpeedrunHostedEvent) {
/*  88 */           if (ev.containsPlayer(player)) {
/*  89 */             evt.setCancelled(true);
/*     */             return;
/*     */           } 
/*  92 */         } else if (ev instanceof CannonDodgeEvent) {
/*  93 */           if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) && 
/*  94 */             ev.containsPlayer(player)) {
/*  95 */             evt.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/*  99 */         } else if (ev instanceof BracketsEvent) {
/* 100 */           if (ev.containsPlayer(player)) {
/* 101 */             BracketsEvent evBrackets = (BracketsEvent)ev;
/* 102 */             if (!evBrackets.isArenaFight(player)) {
/* 103 */               evt.setCancelled(true);
/*     */               return;
/*     */             } 
/*     */           } 
/* 107 */         } else if (ev instanceof FootballEvent && 
/* 108 */           ev.containsPlayer(player)) {
/* 109 */           evt.setDamage(0.0D);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 114 */         if (player.getUniqueId().equals(ev.getHostUUID())) {
/* 115 */           evt.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 120 */       if (this.core.getDuelManager().isInDuelArena(player) && 
/* 121 */         this.core.getDuelManager().isDueling(player) == null) {
/* 122 */         evt.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 127 */       User user = this.core.getUser(player.getUniqueId());
/* 128 */       if (this.core.hasSpawnFlag(user)) {
/* 129 */         evt.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 133 */       if (this.core.hasWarpProtection(user)) {
/* 134 */         evt.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 138 */       if (evt.getEntity() instanceof org.bukkit.entity.ItemFrame || evt.getEntity() instanceof org.bukkit.entity.EnderCrystal || evt.getEntity() instanceof org.bukkit.entity.Pig) {
/* 139 */         evt.setCancelled(true); return;
/*     */       } 
/* 141 */       if (evt.getEntity() instanceof Slime)
/* 142 */         evt.setCancelled(true); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 148 */     if (evt.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
/* 149 */       if (evt.getEntity() instanceof Player) {
/* 150 */         if (evt.getDamager() instanceof Player) {
/* 151 */           Player player = (Player)evt.getEntity();
/* 152 */           Player damager = (Player)evt.getDamager();
/*     */           
/* 154 */           User user = this.core.getUser(player.getUniqueId());
/* 155 */           User damagerUser = this.core.getUser(damager.getUniqueId());
/*     */           
/* 157 */           Block b = damager.getLocation().getBlock();
/* 158 */           if (b.getType().equals(Material.AIR)) {
/* 159 */             evt.setDamage(evt.getDamage() - 1.0D);
/*     */           }
/* 161 */           if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
/* 162 */             evt.setDamage(evt.getDamage() - 7.0D);
/*     */           }
/* 164 */           ItemStack item = damager.getItemInHand();
/* 165 */           if (InventoryUtil.isSword(item)) {
/* 166 */             item.setDurability((short)1);
/*     */           }
/*     */           
/* 169 */           if (damager.getAllowFlight()) {
/* 170 */             evt.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 174 */           if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/* 175 */             HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/* 176 */             if (ev instanceof LMSHostedEvent) {
/* 177 */               LMSHostedEvent evLMS = (LMSHostedEvent)ev;
/* 178 */               if (evLMS.getStarted() && 
/* 179 */                 evLMS.getIsProtection() && 
/* 180 */                 evLMS.containsPlayer(player)) {
/* 181 */                 damager.sendMessage(ChatColor.RED + "Wait until the " + ChatUtil.Gi + "protection" + ChatColor.RED + " wears off!");
/* 182 */                 evt.setCancelled(true);
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/* 187 */             } else if (ev instanceof OneManArmyEvent) {
/* 188 */               OneManArmyEvent evOneManArmy = (OneManArmyEvent)ev;
/* 189 */               if (evOneManArmy.getStarted()) {
/* 190 */                 if (evOneManArmy.getIsProtection()) {
/* 191 */                   if (evOneManArmy.containsPlayer(player)) {
/* 192 */                     damager.sendMessage(ChatColor.RED + "Wait until the " + ChatUtil.Gi + "protection" + ChatColor.RED + " wears off!");
/* 193 */                     evt.setCancelled(true);
/*     */                     
/*     */                     return;
/*     */                   } 
/* 197 */                 } else if (evOneManArmy.containsPlayer(player) && 
/* 198 */                   !evOneManArmy.getArmy().equals(damager.getUniqueId()) && !evOneManArmy.getArmy().equals(player.getUniqueId())) {
/* 199 */                   Player army = Core.getNameFromUUID(evOneManArmy.getArmy());
/* 200 */                   if (army != null) {
/* 201 */                     damager.sendMessage(ChatColor.GRAY + "Only attack " + RankUtil.getFullTag(army));
/*     */                   }
/* 203 */                   evt.setCancelled(true);
/*     */ 
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */               }
/* 209 */             } else if (ev instanceof RedRoverEvent) {
/* 210 */               if (ev.containsPlayer(player)) {
/* 211 */                 RedRoverEvent evRedRover = (RedRoverEvent)ev;
/* 212 */                 if (!damager.getUniqueId().equals(evRedRover.getRover())) {
/* 213 */                   evt.setCancelled(true);
/*     */                   return;
/*     */                 } 
/*     */               } 
/* 217 */             } else if (ev instanceof CannonDodgeEvent) {
/* 218 */               if (ev.containsPlayer(player)) {
/* 219 */                 evt.setCancelled(true);
/*     */               }
/* 221 */             } else if (ev instanceof FootballEvent && 
/* 222 */               player.getNoDamageTicks() == 0 && 
/* 223 */               ev.containsPlayer(player)) {
/* 224 */               FootballEvent evFootball = (FootballEvent)ev;
/*     */               
/* 226 */               if (evFootball.getBall() != null && !evFootball.isPauseBetweenGoals()) {
/* 227 */                 evt.setCancelled(!(player.getLocation().distanceSquared(evFootball.getBall().getLocation()) < 36.0D));
/*     */               } else {
/* 229 */                 evt.setCancelled(true);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 236 */           if (this.core.hasSpawnFlag(damagerUser)) {
/* 237 */             if (this.core.hasSpawnFlag(user)) {
/* 238 */               damager.sendMessage(ChatColor.RED + "This player has spawn protection!");
/*     */             } else {
/* 240 */               damager.sendMessage(ChatColor.RED + "You can't hit players while you have spawn protection!");
/*     */             } 
/* 242 */             evt.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 246 */           if (this.core.hasWarpProtection(damagerUser)) {
/* 247 */             if (this.core.hasWarpProtection(user)) {
/* 248 */               damager.sendMessage(ChatColor.RED + "This player has warp protection!");
/*     */             } else {
/* 250 */               damager.sendMessage(ChatColor.RED + "You can't hit players while you have warp protection!");
/*     */             } 
/* 252 */             evt.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 256 */           if (!damager.getName().equals(player.getName())) {
/* 257 */             user.tag(damager);
/*     */           }
/*     */         } 
/* 260 */       } else if (evt.getEntity() instanceof Slime && evt.getDamager() instanceof Player) {
/* 261 */         Player damager = (Player)evt.getDamager();
/* 262 */         if (this.core.getHostedEventManager().getCurrentEvent() != null && this.core.getHostedEventManager().getCurrentEvent() instanceof FootballEvent) {
/* 263 */           FootballEvent evFootball = (FootballEvent)this.core.getHostedEventManager().getCurrentEvent();
/*     */           
/* 265 */           if (evFootball.getBall() != null) {
/* 266 */             Slime ball = evFootball.getBall();
/*     */             
/* 268 */             if (evt.getEntity().getUniqueId().equals(ball.getUniqueId()) && ball.getNoDamageTicks() == 0) {
/* 269 */               if (evFootball.containsPlayer(damager) && !evFootball.isPauseBetweenGoals()) {
/* 270 */                 ball.setVelocity(ball.getVelocity().add((new Vector(0, 1, 0)).add(damager.getLocation().getDirection().normalize().multiply(1.4D).setY(0))));
/* 271 */                 ball.getWorld().playSound(ball.getLocation(), Sound.SLIME_WALK, 1.0F, 1.0F);
/*     */                 
/* 273 */                 ball.setNoDamageTicks(ball.getMaximumNoDamageTicks());
/*     */                 
/* 275 */                 evFootball.setLastKicker(damager.getUniqueId());
/*     */               } 
/* 277 */               evt.setCancelled(true);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 282 */     } else if (evt.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && 
/* 283 */       evt.getEntity() instanceof Player && 
/* 284 */       evt.getDamager() instanceof Arrow && (
/* 285 */       (Arrow)evt.getDamager()).getShooter() instanceof Player) {
/* 286 */       Player player = (Player)evt.getEntity();
/* 287 */       Player damager = (Player)((Arrow)evt.getDamager()).getShooter();
/*     */       
/* 289 */       User user = this.core.getUser(player.getUniqueId());
/* 290 */       User damagerUser = this.core.getUser(damager.getUniqueId());
/*     */       
/* 292 */       if (this.core.hasSpawnFlag(damagerUser)) {
/* 293 */         if (this.core.hasSpawnFlag(user)) {
/* 294 */           damager.sendMessage(ChatColor.RED + "This player has spawn protection!");
/*     */         } else {
/* 296 */           damager.sendMessage(ChatColor.RED + "You can't hit players while you have spawn protection!");
/*     */         } 
/* 298 */         evt.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 302 */       if (this.core.hasWarpProtection(damagerUser)) {
/* 303 */         if (this.core.hasWarpProtection(user)) {
/* 304 */           damager.sendMessage(ChatColor.RED + "This player has warp protection!");
/*     */         } else {
/* 306 */           damager.sendMessage(ChatColor.RED + "You can't hit players while you have warp protection!");
/*     */         } 
/* 308 */         evt.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 312 */       if (!damager.getName().equals(player.getName())) {
/* 313 */         user.tag(damager);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onEntityChangeBlockEvent(EntityChangeBlockEvent evt) {
/* 324 */     if (evt.getEntity().hasMetadata("crate")) {
/* 325 */       evt.setCancelled(true);
/* 326 */       Block crate = evt.getEntity().getLocation().getBlock();
/* 327 */       crate.setType(Material.PISTON_BASE);
/* 328 */       crate.setData((byte)6);
/* 329 */       crate.setMetadata("crate", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/* 330 */       crate.setMetadata("owner", (MetadataValue)new FixedMetadataValue((Plugin)this.core, ((MetadataValue)evt.getEntity().getMetadata("owner").get(0)).asString()));
/* 331 */       evt.getEntity().remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onItemSpawnEvent(ItemSpawnEvent evt) {
/* 337 */     Util.setOldItem(evt.getEntity(), this.random.nextInt(8) + 3);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onItemDespawnEvent(ItemDespawnEvent evt) {}
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onProjectileLaunchEvent(ProjectileLaunchEvent evt) {
/* 347 */     if (this.core.getHostedEventManager().getCurrentEvent() != null && 
/* 348 */       this.core.getHostedEventManager().getCurrentEvent() instanceof CannonDodgeEvent && 
/* 349 */       evt.getEntity() instanceof Arrow && evt.getEntity().getShooter() instanceof Player && 
/* 350 */       this.core.getHostedEventManager().getCurrentEvent().containsPlayer((Player)evt.getEntity().getShooter()) && (
/* 351 */       (CannonDodgeEvent)this.core.getHostedEventManager().getCurrentEvent()).getIsProtection()) {
/* 352 */       evt.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onProjectileHitEvent(ProjectileHitEvent evt) {
/* 362 */     if (this.core.getHostedEventManager().getCurrentEvent() != null && 
/* 363 */       this.core.getHostedEventManager().getCurrentEvent() instanceof CannonDodgeEvent && 
/* 364 */       evt.getEntity() instanceof Arrow && evt.getEntity().getShooter() instanceof Player && 
/* 365 */       this.core.getHostedEventManager().getCurrentEvent().containsPlayer((Player)evt.getEntity().getShooter())) {
/* 366 */       evt.getEntity().getWorld().createExplosion(evt.getEntity().getLocation(), 2.5F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (evt.getEntity() instanceof Arrow) {
/* 373 */       evt.getEntity().remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageEvent e) {
/* 380 */     if (e.getEntity() instanceof Player) {
/*     */       
/* 382 */       Player p = (Player)e.getEntity();
/* 383 */       Location spawn = new Location(Bukkit.getWorld("world"), 0.0D, 59.0D, 0.0D);
/* 384 */       if (p.getLocation().distance(spawn) < 10.0D) {
/*     */         
/* 386 */         e.setCancelled(true);
/*     */ 
/*     */       
/*     */       }
/* 390 */       else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
/*     */         
/* 392 */         Block b = p.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
/* 393 */         if (b.getType() == Material.HAY_BLOCK) {
/*     */           
/* 395 */           e.setCancelled(true);
/* 396 */           p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 170);
/* 397 */           Vector vnew = p.getVelocity().setY(0.2D);
/* 398 */           p.setVelocity(vnew);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onEntityExplodeEvent(EntityExplodeEvent evt) {
/* 407 */     evt.blockList().clear();
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onEntityDeathEvent(EntityDeathEvent evt) {
/* 412 */     if (!(evt.getEntity() instanceof Player)) {
/* 413 */       evt.getDrops().clear();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent evt) {
/* 419 */     if (evt.getEntity() instanceof org.bukkit.entity.ItemFrame)
/* 420 */       evt.setCancelled(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\listener\CoreEntityListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */