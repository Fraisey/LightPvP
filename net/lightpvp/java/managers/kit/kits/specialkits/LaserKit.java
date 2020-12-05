/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Damageable;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ public class LaserKit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  39 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  41 */   ItemStack[] content = new ItemStack[36];
/*  42 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  44 */   ItemStack laser = new ItemStack(Material.REDSTONE, 5);
/*     */   
/*     */   public LaserKit(Core core) {
/*  47 */     this.core = core;
/*     */     
/*  49 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  51 */     ItemMeta laserMeta = this.laser.getItemMeta();
/*  52 */     laserMeta.setDisplayName(ChatColor.GREEN + "Laser " + ChatColor.GRAY + "(Right Click)");
/*  53 */     laserMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Use this to shoot a laser, striking anyone in", ChatColor.GRAY + "the line of your laser" }));
/*  54 */     this.laser.setItemMeta(laserMeta);
/*     */     
/*  56 */     this.content = new ItemStack[] { core.ironSwordSharpness, this.laser, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  57 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  59 */     for (int i = 0; i < this.armor.length; i++) {
/*  60 */       if (this.armor[i] != null) {
/*  61 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  63 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  64 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  65 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  70 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return "Laser";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  80 */     return "kit.laser";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  85 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  90 */     return Rareness.EXTREMLY_RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  95 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  97 */     p.getInventory().setContents(this.content);
/*  98 */     p.getInventory().setArmorContents(this.armor);
/*  99 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 104 */     return Material.REDSTONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 109 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your redstone", ChatColor.GRAY + "to fire a straight laser wherever your looking", ChatColor.GRAY + "that will damage players and set them on fire!", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 114 */     ItemStack related = this.laser.clone();
/* 115 */     related.setAmount(3);
/* 116 */     return related;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 123 */     final Player player = evt.getPlayer();
/* 124 */     if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.REDSTONE) && 
/* 125 */       this.core.getKitManager().isKit(player, (Kit)this))
/* 126 */       if (this.cooldowns.containsKey(player.getName())) {
/* 127 */         long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 128 */         player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((5000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 129 */         evt.setCancelled(true);
/*     */       } else {
/* 131 */         Vector dir = player.getLocation().getDirection();
/* 132 */         Location loc = player.getEyeLocation();
/*     */         
/* 134 */         this.core.getWorld().playSound(player.getLocation(), Sound.BAT_DEATH, 1.0F, 0.6F);
/*     */         
/* 136 */         Set<Block> blocks = new HashSet<>();
/* 137 */         for (int i = 0; i < 30; i++)
/*     */         {
/* 139 */           blocks.add(loc.getBlock());
/*     */         }
/*     */         
/* 142 */         Set<Entity> hit = new HashSet<>();
/*     */         
/* 144 */         List<Entity> nearbyEntities = player.getNearbyEntities(70.0D, 70.0D, 70.0D);
/* 145 */         for (Entity e : nearbyEntities) {
/* 146 */           if (e instanceof Damageable) {
/* 147 */             for (Block b : blocks) {
/* 148 */               if (e.getLocation().distanceSquared(b.getLocation()) < 2.0D) {
/* 149 */                 hit.add(e);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 155 */         for (Entity e : hit) {
/* 156 */           ((Damageable)e).damage(28.0D, (Entity)player);
/* 157 */           e.setFireTicks(60);
/*     */         } 
/*     */         
/* 160 */         if (hit.size() > 0) {
/* 161 */           player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 164 */         if (player.getItemInHand().getAmount() != 1) {
/* 165 */           player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
/*     */         } else {
/* 167 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 171 */                 player.getInventory().remove(Material.REDSTONE);
/*     */               }
/* 173 */             }).runTaskLater((Plugin)this.core, 1L);
/*     */         } 
/*     */ 
/*     */         
/* 177 */         final String name = player.getName();
/* 178 */         this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 179 */         Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 183 */                 LaserKit.this.cooldowns.remove(name);
/* 184 */                 player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */               }
/*     */             }, 
/* 187 */             100L);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\LaserKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */