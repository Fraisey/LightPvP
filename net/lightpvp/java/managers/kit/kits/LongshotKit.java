/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongshotKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  30 */   ItemStack[] content = new ItemStack[36];
/*  31 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  33 */   Random random = new Random();
/*     */   
/*     */   public LongshotKit(Core core) {
/*  36 */     this.core = core;
/*     */     
/*  38 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  40 */     ItemStack longshotBow = new ItemStack(Material.BOW);
/*  41 */     ItemMeta longshotBowMeta = longshotBow.getItemMeta();
/*  42 */     longshotBowMeta.setDisplayName(ChatColor.GREEN + "Longshot Bow");
/*  43 */     longshotBowMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Longshot I" }));
/*  44 */     longshotBow.setItemMeta(longshotBowMeta);
/*     */     
/*  46 */     this.content = new ItemStack[] { core.ironSwordSharpness, longshotBow, new ItemStack(Material.ARROW, 20), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  47 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.CHAINMAIL_HELMET) };
/*     */     
/*  49 */     for (int i = 0; i < this.armor.length; i++) {
/*  50 */       if (this.armor[i] != null) {
/*  51 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  53 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  54 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  55 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  62 */     return "Longshot";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  67 */     return "kit.longshot";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  72 */     return 25000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  77 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  79 */     p.getInventory().setContents(this.content);
/*  80 */     p.getInventory().setArmorContents(this.armor);
/*  81 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  86 */     return Material.BOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  91 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your bow skills to kill players from a distance", ChatColor.GRAY + "The longer your arrow travells", ChatColor.GRAY + "the more damage it deals", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  96 */     return new ItemStack(Material.ARROW, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 103 */     if (evt.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
/* 104 */       if (evt.getEntity() instanceof Player && evt.getDamager() instanceof Arrow) {
/* 105 */         Arrow arrow = (Arrow)evt.getDamager();
/* 106 */         Player player = (Player)evt.getEntity();
/* 107 */         if (arrow.getShooter() instanceof Player) {
/* 108 */           Player damager = (Player)arrow.getShooter();
/* 109 */           if (this.core.getKitManager().isKit(damager, this)) {
/* 110 */             double distance = player.getLocation().distance(damager.getLocation()) * 0.5D;
/* 111 */             double damage = distance + evt.getDamage();
/* 112 */             if (damage > 50.0D) {
/* 113 */               damage = 50.0D;
/*     */             }
/* 115 */             evt.setDamage(damage);
/* 116 */             damager.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
/*     */           } 
/*     */         } 
/*     */       } 
/* 120 */     } else if (evt.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && 
/* 121 */       evt.getDamager() instanceof Player) {
/* 122 */       Player damager = (Player)evt.getDamager();
/* 123 */       if (evt.getEntity() instanceof Player && 
/* 124 */         this.core.getKitManager().isKit(damager, this) && 
/* 125 */         !damager.getName().equals(((Player)evt.getEntity()).getName())) {
/* 126 */         int chance = this.random.nextInt(10);
/* 127 */         if (chance == 0)
/* 128 */           damager.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) }); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\LongshotKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */