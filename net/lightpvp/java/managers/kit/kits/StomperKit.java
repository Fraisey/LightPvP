/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StomperKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  29 */   ItemStack[] content = new ItemStack[36];
/*  30 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public StomperKit(Core core) {
/*  33 */     this.core = core;
/*     */     
/*  35 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  37 */     this.content = new ItemStack[] { core.ironSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  38 */     this.armor = new ItemStack[] { new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
/*     */     
/*  40 */     for (int i = 0; i < this.armor.length; i++) {
/*  41 */       if (this.armor[i] != null) {
/*  42 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  44 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  45 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  46 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  53 */     return "Stomper";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  58 */     return "kit.stomper";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  63 */     return 55000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  68 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  70 */     p.getInventory().setContents(this.content);
/*  71 */     p.getInventory().setArmorContents(this.armor);
/*  72 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  77 */     return Material.IRON_BOOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  82 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Jump from a high place and stomp the players", ChatColor.GRAY + "and take a max of 2 hearts damage", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  87 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/*  94 */     if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) && 
/*  95 */       evt.getEntity() instanceof Player) {
/*  96 */       Player player = (Player)evt.getEntity();
/*  97 */       if (this.core.getKitManager().isKit(player, this)) {
/*  98 */         for (Entity e : player.getNearbyEntities(5.0D, 2.0D, 5.0D)) {
/*  99 */           if (e instanceof Player) {
/* 100 */             Player target = (Player)e;
/* 101 */             if (target.isSneaking()) {
/* 102 */               target.damage(evt.getDamage() / 5.0D, (Entity)player); continue;
/*     */             } 
/* 104 */             target.damage(evt.getDamage() / 1.4D, (Entity)player);
/*     */           } 
/*     */         } 
/*     */         
/* 108 */         evt.setDamage((evt.getDamage() > 5.0D) ? 5.0D : evt.getDamage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\StomperKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */