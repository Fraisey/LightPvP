/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class SoldierKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  38 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*  39 */   private HashMap<String, Long> cooldowns2 = new HashMap<>();
/*     */   
/*  41 */   ItemStack[] content = new ItemStack[36];
/*  42 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public SoldierKit(Core core) {
/*  45 */     this.core = core;
/*     */     
/*  47 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  49 */     ItemStack sniper = new ItemStack(Material.DIAMOND_BARDING);
/*  50 */     ItemMeta itemMeta1 = sniper.getItemMeta();
/*  51 */     itemMeta1.setDisplayName(ChatColor.GREEN + "Soldier's Sniper Rifle " + ChatColor.GRAY + "(Right/Left Click)");
/*  52 */     ArrayList<String> lore1 = new ArrayList<>();
/*  53 */     lore1.add(ChatColor.GRAY + "Use this to shoot out one bullet with power. Headshot");
/*  54 */     lore1.add(ChatColor.GRAY + "a player to instantly kill them! Note: Long cooldown!");
/*  55 */     itemMeta1.setLore(lore1);
/*  56 */     sniper.setItemMeta(itemMeta1);
/*     */     
/*  58 */     ItemStack pistol = new ItemStack(Material.IRON_BARDING);
/*  59 */     ItemMeta itemMeta = pistol.getItemMeta();
/*  60 */     itemMeta.setDisplayName(ChatColor.GREEN + "Soldier's Pistol " + ChatColor.GRAY + "(Right Click)");
/*  61 */     ArrayList<String> lore = new ArrayList<>();
/*  62 */     lore.add(ChatColor.GRAY + "Use this to shoot out bullets, each bullet deals");
/*  63 */     lore.add(ChatColor.GRAY + "3 hearts of damage!");
/*  64 */     itemMeta.setLore(lore);
/*  65 */     pistol.setItemMeta(itemMeta);
/*     */     
/*  67 */     this.content = new ItemStack[] { core.ironSwordSharpness, pistol, sniper, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  68 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET) };
/*     */     
/*  70 */     for (int i = 0; i < this.armor.length; i++) {
/*  71 */       if (this.armor[i] != null) {
/*  72 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  74 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  75 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  76 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  83 */     return "Soldier";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  88 */     return "kit.soldier";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  93 */     return 65000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  98 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 100 */     p.getInventory().setContents(this.content);
/* 101 */     p.getInventory().setArmorContents(this.armor);
/* 102 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 107 */     return Material.DIAMOND_BARDING;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 112 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Shoot bullets that deal damage.", ChatColor.GRAY + "Head-shot with diamond sniper is an insta kill!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 117 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 124 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 125 */       final Player player = evt.getPlayer();
/* 126 */       if (evt.getItem() != null && evt.getItem().getType().equals(Material.IRON_BARDING)) {
/* 127 */         if (this.core.getKitManager().hasKit(player, this)) {
/* 128 */           if (this.cooldowns.containsKey(player.getName())) {
/* 129 */             long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 130 */             player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((1500.0D - cooldown) / 1000.0D) + " more seconds!");
/* 131 */             evt.setCancelled(true);
/*     */           } else {
/*     */             
/* 134 */             final Item item = player.getWorld().dropItem(player.getLocation().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.IRON_BLOCK, 1));
/* 135 */             player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 17.0F);
/*     */             
/* 137 */             item.setPickupDelay(200);
/* 138 */             item.setVelocity(player.getLocation().getDirection().normalize().multiply(2.2D));
/*     */             
/* 140 */             (new BukkitRunnable()
/*     */               {
/*     */                 public void run() {
/* 143 */                   if (item != null && item.isDead()) {
/* 144 */                     cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/* 148 */                   List<Entity> nearbyEntities = item.getNearbyEntities(0.6D, 0.6D, 0.6D);
/* 149 */                   for (Entity e : nearbyEntities) {
/* 150 */                     if (e instanceof Player) {
/* 151 */                       Player target = (Player)e;
/* 152 */                       if (!target.getName().equals(player.getName())) {
/* 153 */                         target.damage(8.0D, (Entity)player);
/* 154 */                         player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 2.0F);
/* 155 */                         item.remove();
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 }
/* 160 */               }).runTaskTimer((Plugin)this.core, 1L, 1L);
/*     */             
/* 162 */             final String name = player.getName();
/* 163 */             this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 164 */             Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 168 */                     SoldierKit.this.cooldowns.remove(name);
/*     */                   }
/*     */                 }, 
/* 171 */                 30L);
/*     */           } 
/*     */         }
/* 174 */       } else if (evt.getItem() != null && evt.getItem().getType() == Material.DIAMOND_BARDING && 
/* 175 */         this.core.getKitManager().hasKit(player, this)) {
/* 176 */         if (this.cooldowns2.containsKey(player.getName())) {
/* 177 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns2.get(player.getName())).longValue();
/* 178 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 179 */           evt.setCancelled(true);
/*     */         } else {
/* 181 */           final Item item = player.getWorld().dropItem(player.getLocation().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.DIAMOND_BLOCK, 1));
/* 182 */           player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 17.0F);
/*     */           
/* 184 */           item.setPickupDelay(200);
/* 185 */           item.setVelocity(player.getLocation().getDirection().normalize().multiply(2.2D));
/*     */           
/* 187 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run() {
/* 190 */                 if (item != null && item.isDead()) {
/* 191 */                   cancel();
/*     */                   
/*     */                   return;
/*     */                 } 
/* 195 */                 List<Entity> nearbyEntities = item.getNearbyEntities(0.6D, 0.6D, 0.6D);
/* 196 */                 for (Entity e : nearbyEntities) {
/* 197 */                   if (e instanceof Player) {
/* 198 */                     Player target = (Player)e;
/* 199 */                     if (!target.getName().equals(player.getName())) {
/* 200 */                       double y = item.getLocation().getY();
/* 201 */                       double shotY = target.getLocation().getY();
/* 202 */                       boolean headshot = (y - shotY > 1.2D);
/* 203 */                       if (headshot) {
/* 204 */                         if (!target.isBlocking()) {
/* 205 */                           if (player.getLocation().distanceSquared(target.getLocation()) >= 400.0D) {
/* 206 */                             target.damage(200.0D, (Entity)player);
/* 207 */                             player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 2.0F);
/* 208 */                             item.remove(); continue;
/*     */                           } 
/* 210 */                           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "were standing to close to " + RankUtil.getFullTag(target) + "!");
/* 211 */                           item.remove();
/*     */                           continue;
/*     */                         } 
/* 214 */                         target.damage(17.0D, (Entity)player);
/* 215 */                         player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 2.0F);
/* 216 */                         item.remove();
/*     */                       }
/*     */                     
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               }
/* 223 */             }).runTaskTimer((Plugin)this.core, 1L, 1L);
/*     */           
/* 225 */           final String name = player.getName();
/* 226 */           this.cooldowns2.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 227 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 231 */                   SoldierKit.this.cooldowns2.remove(name);
/* 232 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 235 */               600L);
/*     */         }
/*     */       
/*     */       } 
/* 239 */     } else if ((evt.getAction().equals(Action.LEFT_CLICK_AIR) || evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) && 
/* 240 */       evt.getItem() != null && evt.getItem().getType().equals(Material.DIAMOND_BARDING)) {
/* 241 */       final Player player = evt.getPlayer();
/* 242 */       if (player.hasPotionEffect(PotionEffectType.SLOW)) {
/* 243 */         player.removePotionEffect(PotionEffectType.SLOW);
/* 244 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "have turned off the " + ChatUtil.Gi + "scope view!");
/*     */       } else {
/* 246 */         player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 4));
/* 247 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "have turned on the " + ChatUtil.Gi + "scope view!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\SoldierKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */