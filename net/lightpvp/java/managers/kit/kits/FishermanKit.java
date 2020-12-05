/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerFishEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FishermanKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  29 */   ItemStack[] content = new ItemStack[36];
/*  30 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public FishermanKit(Core core) {
/*  33 */     this.core = core;
/*     */     
/*  35 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  37 */     this.content = new ItemStack[] { core.diamondSwordSharpness, new ItemStack(Material.FISHING_ROD), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  38 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET) };
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
/*  53 */     return "Fisherman";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  58 */     return "kit.fisherman";
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
/*  77 */     return Material.FISHING_ROD;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  82 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your fishing rod to pull players", ChatColor.GRAY + "towards you", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
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
/*     */   public void fishEvent(PlayerFishEvent event) {
/*  94 */     Player player = event.getPlayer();
/*  95 */     if (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY) && 
/*  96 */       event.getCaught() instanceof Player && 
/*  97 */       this.core.getKitManager().isKit(player, this)) {
/*  98 */       Player hooked = (Player)event.getCaught();
/*  99 */       if (!player.getName().equals(hooked.getName())) {
/* 100 */         event.setCancelled(true);
/* 101 */         Util.pullEntityToLocation((Entity)hooked, player.getLocation());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\FishermanKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */