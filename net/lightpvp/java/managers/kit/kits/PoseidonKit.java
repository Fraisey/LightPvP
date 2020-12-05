/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PoseidonKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  29 */   ItemStack[] content = new ItemStack[36];
/*  30 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public PoseidonKit(Core core) {
/*  33 */     this.core = core;
/*     */     
/*  35 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  37 */     this.content = new ItemStack[] { core.ironSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  39 */     ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  40 */     LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
/*  41 */     chestplateMeta.setColor(Color.BLUE);
/*  42 */     chestplate.setItemMeta((ItemMeta)chestplateMeta);
/*     */     
/*  44 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.IRON_LEGGINGS), chestplate, new ItemStack(Material.DIAMOND_HELMET) };
/*     */     
/*  46 */     for (int i = 0; i < this.armor.length; i++) {
/*  47 */       if (this.armor[i] != null) {
/*  48 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  50 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  51 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  52 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  59 */     return "Poseidon";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  64 */     return "kit.poseidon";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  69 */     return 20000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  74 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  76 */     p.getInventory().setContents(this.content);
/*  77 */     p.getInventory().setArmorContents(this.armor);
/*  78 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  83 */     return Material.WATER_BUCKET;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  88 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use the water to your advantage", ChatColor.GRAY + "as you are stronger in it!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  93 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 100 */     if (evt.getDamager() instanceof Player) {
/* 101 */       Player damager = (Player)evt.getDamager();
/* 102 */       if ((damager.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER) || damager.getLocation().getBlock().getType().equals(Material.WATER)) && 
/* 103 */         this.core.getKitManager().isKit(damager, this))
/* 104 */         evt.setDamage(evt.getDamage() * 1.3D); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\PoseidonKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */