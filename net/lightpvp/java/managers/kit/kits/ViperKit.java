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
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViperKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  32 */   private Random random = new Random();
/*     */   
/*  34 */   ItemStack[] content = new ItemStack[36];
/*  35 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public ViperKit(Core core) {
/*  38 */     this.core = core;
/*     */     
/*  40 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  42 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  44 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  45 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  46 */     helmetMeta.setColor(Color.GREEN);
/*  47 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  49 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), helmet };
/*     */     
/*  51 */     for (int i = 0; i < this.armor.length; i++) {
/*  52 */       if (this.armor[i] != null) {
/*  53 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  55 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  56 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  57 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  64 */     return "Viper";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  69 */     return "kit.viper";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  74 */     return 42000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  79 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  81 */     p.getInventory().setContents(this.content);
/*  82 */     p.getInventory().setArmorContents(this.armor);
/*  83 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  88 */     return Material.POISONOUS_POTATO;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  93 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "You have a 33% chance of giving the attacked", ChatColor.GRAY + "player posion for 3 seconds", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  98 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 105 */     if (evt.getEntity() instanceof Player && 
/* 106 */       evt.getDamager() instanceof Player) {
/* 107 */       Player player = (Player)evt.getEntity();
/* 108 */       Player damager = (Player)evt.getDamager();
/* 109 */       if (this.core.getKitManager().isKit(damager, this)) {
/* 110 */         int chance = this.random.nextInt(3);
/* 111 */         if (chance == 0)
/* 112 */           player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0), true); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\ViperKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */