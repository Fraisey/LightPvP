/*     */ package net.lightpvp.java.managers.crate;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.FallingBlock;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class CrateItemManager
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  40 */   private Random random = new Random();
/*     */   
/*  42 */   private ItemStack[] items = new ItemStack[9];
/*     */   
/*  44 */   private List<String> looping = new ArrayList<>();
/*     */   
/*  46 */   private HashMap<String, Long> bendingLaserCooldown = new HashMap<>();
/*     */   
/*     */   public CrateItemManager(Core core) {
/*  49 */     this.core = core;
/*     */     
/*  51 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */ 
/*     */     
/*  54 */     ItemStack rocketLauncher = new ItemStack(Material.FIREBALL, 3);
/*  55 */     ItemMeta rocketMeta = rocketLauncher.getItemMeta();
/*  56 */     rocketMeta.setDisplayName(ChatColor.GREEN + "Rocket Launcher " + ChatColor.GRAY + "(Right Click)");
/*  57 */     List<String> rocketLore = new ArrayList<>();
/*  58 */     rocketLore.add(ChatColor.GRAY + "With these you can launch a " + ChatUtil.Gi + "fireball " + ChatColor.GRAY + "where you are looking");
/*  59 */     rocketLore.add(ChatColor.GRAY + "wherever they hit, players who are damaged by the fireball will");
/*  60 */     rocketLore.add(ChatColor.GRAY + "be set alight for " + ChatUtil.Gi + "5 " + ChatColor.GRAY + "seconds, dealing more damage!");
/*  61 */     rocketLore.add(" ");
/*  62 */     rocketLore.add(ChatColor.GRAY + "You have " + ChatUtil.Gi + "3 " + ChatColor.GRAY + "of these with " + ChatUtil.Gi + "no " + ChatColor.GRAY + "cooldown!");
/*  63 */     rocketMeta.setLore(rocketLore);
/*  64 */     rocketLauncher.setItemMeta(rocketMeta);
/*  65 */     this.items[0] = rocketLauncher;
/*     */     
/*  67 */     ItemStack laserBending = new ItemStack(Material.REDSTONE_BLOCK, 5);
/*  68 */     ItemMeta laserBendMeta = laserBending.getItemMeta();
/*  69 */     laserBendMeta.setDisplayName(ChatColor.GREEN + "Bending Laser Gun " + ChatColor.GRAY + "(Right Click)");
/*  70 */     List<String> laserBendLore = new ArrayList<>();
/*  71 */     laserBendLore.add(ChatColor.GRAY + "With this you can shoot  " + ChatUtil.Gi + "a laser " + ChatColor.GRAY + "that you can");
/*  72 */     laserBendLore.add(String.valueOf(ChatUtil.Gi) + "control " + ChatColor.GRAY + "by looking moving your " + ChatUtil.Gi + "head!");
/*  73 */     laserBendLore.add(" ");
/*  74 */     laserBendLore.add(ChatColor.GRAY + "You have " + ChatUtil.Gi + "5 " + ChatColor.GRAY + "laser shots with a " + ChatUtil.Gi + "10 " + ChatColor.GRAY + "second cooldown!");
/*  75 */     laserBendMeta.setLore(laserBendLore);
/*  76 */     laserBending.setItemMeta(laserBendMeta);
/*  77 */     this.items[1] = laserBending;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropCrate(Player player) {
/*  82 */     FallingBlock crate = this.core.getWorld().spawnFallingBlock(new Location(this.core.getWorld(), -643.0D, 78.0D, 857.0D), Material.PISTON_BASE, (byte)7);
/*  83 */     crate.setMetadata("crate", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/*  84 */     crate.setMetadata("owner", (MetadataValue)new FixedMetadataValue((Plugin)this.core, player.getName()));
/*     */   }
/*     */   
/*     */   public void getRandomItem(final Player player) {
/*  88 */     final Inventory inventory = Bukkit.createInventory(null, 9, "Crate Item");
/*  89 */     player.openInventory(inventory);
/*     */     
/*  91 */     this.looping.add(player.getUniqueId().toString());
/*     */     
/*  93 */     (new BukkitRunnable()
/*     */       {
/*  95 */         int scroll = 0;
/*  96 */         int randomIndex = CrateItemManager.this.random.nextInt(2);
/*     */ 
/*     */         
/*     */         public void run() {
/* 100 */           if (this.scroll <= this.randomIndex) {
/* 101 */             if (this.scroll != 0) {
/* 102 */               inventory.setItem(this.scroll - 1, new ItemStack(Material.AIR));
/*     */             } else {
/* 104 */               inventory.setItem(inventory.getSize() - 1, new ItemStack(Material.AIR));
/*     */             } 
/*     */             
/* 107 */             inventory.setItem(this.scroll, CrateItemManager.this.items[this.scroll]);
/*     */             
/* 109 */             this.scroll++;
/*     */           } else {
/* 111 */             CrateItemManager.this.looping.remove(player.getUniqueId().toString());
/* 112 */             cancel();
/*     */           } 
/*     */         }
/* 115 */       }).runTaskTimer((Plugin)this.core, 2L, 2L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClickEvent(InventoryClickEvent evt) {
/* 123 */     if (evt.getWhoClicked() instanceof Player) {
/* 124 */       Player player = (Player)evt.getWhoClicked();
/* 125 */       if (evt.getInventory().getName().equals("Crate Item")) {
/* 126 */         evt.setCancelled(this.looping.contains(player.getUniqueId().toString()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 134 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 135 */       final Player player = evt.getPlayer();
/* 136 */       if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.REDSTONE_BLOCK)) {
/* 137 */         if (this.bendingLaserCooldown.containsKey(player.getName())) {
/* 138 */           long cooldown = System.currentTimeMillis() - ((Long)this.bendingLaserCooldown.get(player.getName())).longValue();
/* 139 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((10000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 140 */           evt.setCancelled(true);
/*     */         } else {
/*     */           
/* 143 */           final Location location = player.getEyeLocation();
/*     */           
/* 145 */           (new BukkitRunnable()
/*     */             {
/* 147 */               int count = 128;
/*     */ 
/*     */               
/*     */               public void run() {
/* 151 */                 if (this.count > 0) {
/*     */                   
/* 153 */                   location.add(player.getLocation().getDirection());
/*     */                   
/* 155 */                   if (!location.getBlock().getType().equals(Material.AIR)) {
/*     */                     
/* 157 */                     CrateItemManager.this.core.getWorld().playSound(location, Sound.EXPLODE, 1.0F, 1.0F);
/*     */                     
/* 159 */                     boolean hit = false;
/*     */                     
/* 161 */                     for (Player p : Bukkit.getOnlinePlayers()) {
/* 162 */                       if (location.distanceSquared(p.getLocation()) < 12.0D) {
/* 163 */                         p.damage(32.0D, (Entity)player);
/* 164 */                         if (player.getUniqueId().equals(p.getUniqueId())) {
/* 165 */                           hit = true;
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                     
/* 170 */                     if (hit) {
/* 171 */                       player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */                     }
/*     */                     
/* 174 */                     cancel();
/*     */                   } 
/*     */                   
/* 177 */                   CrateItemManager.this.core.getWorld().playEffect(location, Effect.STEP_SOUND, Material.REDSTONE_BLOCK.getId());
/*     */                   
/* 179 */                   this.count--;
/*     */                 } else {
/*     */                   
/* 182 */                   cancel();
/*     */                 } 
/*     */               }
/* 185 */             }).runTaskTimer((Plugin)this.core, 0L, 1L);
/*     */           
/* 187 */           if (player.getItemInHand().getAmount() != 1) {
/* 188 */             player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
/*     */           } else {
/* 190 */             (new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 194 */                   player.getInventory().remove(Material.REDSTONE_BLOCK);
/*     */                 }
/* 196 */               }).runTaskLater((Plugin)this.core, 1L);
/*     */           } 
/*     */ 
/*     */           
/* 200 */           final String name = player.getName();
/* 201 */           this.bendingLaserCooldown.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 202 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 206 */                   CrateItemManager.this.bendingLaserCooldown.remove(name);
/* 207 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 210 */               200L);
/*     */         } 
/* 212 */       } else if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.FIREBALL)) {
/* 213 */         final Fireball fireball = (Fireball)player.launchProjectile(Fireball.class);
/* 214 */         fireball.setIsIncendiary(false);
/* 215 */         fireball.setYield(3.0F);
/*     */         
/* 217 */         player.setVelocity(player.getLocation().getDirection().multiply(-1));
/*     */         
/* 219 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 223 */               if (fireball == null || fireball.isDead())
/*     */               {
/*     */                 
/* 226 */                 cancel();
/*     */               }
/*     */             }
/* 229 */           }).runTaskTimer((Plugin)this.core, 0L, 2L);
/*     */         
/* 231 */         if (player.getItemInHand().getAmount() != 1) {
/* 232 */           player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
/*     */         } else {
/* 234 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 238 */                 player.getInventory().remove(Material.FIREBALL);
/*     */               }
/* 240 */             }).runTaskLater((Plugin)this.core, 1L);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 249 */     if (evt.getDamager() instanceof Fireball && evt.getEntity() instanceof Player) {
/* 250 */       Fireball fireball = (Fireball)evt.getDamager();
/* 251 */       if (((CraftFireball)fireball).getShooter() instanceof Player) {
/* 252 */         Player damaged = (Player)evt.getEntity();
/* 253 */         evt.setDamage(28.0D);
/* 254 */         damaged.setFireTicks(100);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\crate\CrateItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */