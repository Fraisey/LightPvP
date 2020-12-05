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
/*     */ public class LassoKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  29 */   ItemStack[] content = new ItemStack[36];
/*  30 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public LassoKit(Core core) {
/*  33 */     this.core = core;
/*     */     
/*  35 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  37 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.fish, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
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
/*     */   
/*     */   public String getName() {
/*  54 */     return "Lasso";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  59 */     return "kit.lasso";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  64 */     return 55000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  69 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  71 */     p.getInventory().setContents(this.content);
/*  72 */     p.getInventory().setArmorContents(this.armor);
/*  73 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  78 */     return Material.LEASH;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  83 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your kit item to hook onto other player", ChatColor.GRAY + "and pull yourself", ChatColor.GRAY + "towards them", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  88 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void fishEvent(PlayerFishEvent event) {
/*  95 */     Player player = event.getPlayer();
/*  96 */     if (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY) && 
/*  97 */       event.getCaught() instanceof Player && 
/*  98 */       this.core.getKitManager().isKit(player, this)) {
/*  99 */       Player hooked = (Player)event.getCaught();
/* 100 */       if (!player.getName().equals(hooked.getName()))
/* 101 */         Util.pullEntityToLocation((Entity)player, hooked.getLocation()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\LassoKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */