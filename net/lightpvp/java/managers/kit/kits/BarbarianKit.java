/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.InventoryUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class BarbarianKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  29 */   ItemStack[] content = new ItemStack[36];
/*  30 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public BarbarianKit(Core core) {
/*  33 */     this.core = core;
/*     */     
/*  35 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  37 */     this.content = new ItemStack[] { new ItemStack(Material.WOOD_SWORD), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  38 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
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
/*  53 */     return "Barbarian";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  58 */     return "kit.barbarian";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  63 */     return 50000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  68 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  70 */     p.getInventory().setContents(this.content);
/*  71 */     p.getInventory().setArmorContents(this.armor);
/*  72 */     user.setKit(getName());
/*     */     
/*  74 */     p.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  79 */     return Material.WOOD_SWORD;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  84 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Start with a wooden sword", "which levels up with each kill", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */   
/*     */   public ItemStack upgradeSword(Player player) {
/*  88 */     ItemStack newSword = null;
/*  89 */     if (!player.hasMetadata("level")) {
/*  90 */       return null;
/*     */     }
/*  92 */     int currentLevel = ((MetadataValue)player.getMetadata("level").get(0)).asInt();
/*  93 */     switch (currentLevel) {
/*     */       case 1:
/*  95 */         newSword = new ItemStack(Material.STONE_SWORD);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 152 */         player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1)));
/* 153 */         return newSword;case 2: newSword = new ItemStack(Material.IRON_SWORD); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 3: newSword = new ItemStack(Material.STONE_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 1); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 4: newSword = new ItemStack(Material.GOLD_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 2); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 5: newSword = new ItemStack(Material.DIAMOND_SWORD); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 6: newSword = new ItemStack(Material.IRON_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 1); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 7: newSword = new ItemStack(Material.GOLD_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 3); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 8: newSword = new ItemStack(Material.DIAMOND_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 1); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 9: newSword = new ItemStack(Material.DIAMOND_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 1); newSword.addEnchantment(Enchantment.FIRE_ASPECT, 1); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 10: newSword = new ItemStack(Material.GOLD_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 4); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 11: newSword = new ItemStack(Material.DIAMOND_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 2); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 12: newSword = new ItemStack(Material.STONE_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 4); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 13: newSword = new ItemStack(Material.WOOD_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 5); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;case 14: newSword = new ItemStack(Material.WOOD_SWORD); newSword.addEnchantment(Enchantment.DAMAGE_ALL, 5); newSword.addEnchantment(Enchantment.FIRE_ASPECT, 1); player.setMetadata("level", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(((MetadataValue)player.getMetadata("level").get(0)).asInt() + 1))); return newSword;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   public ItemStack getKitRelatedItem() {
/* 158 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDeathEvent(PlayerDeathEvent evt) {
/* 165 */     Player death = evt.getEntity();
/* 166 */     if (death.getKiller() != null) {
/* 167 */       Player player = death.getKiller();
/* 168 */       if (this.core.getKitManager().isKit(player, this) && 
/* 169 */         player.getItemInHand() != null && InventoryUtil.isSword(player.getItemInHand())) {
/* 170 */         ItemStack newSword = upgradeSword(player);
/* 171 */         if (newSword != null) {
/* 172 */           player.setItemInHand(newSword);
/* 173 */           player.sendMessage(ChatColor.GREEN + "Your sword has been upgraded!");
/*     */         } else {
/* 175 */           player.sendMessage(ChatColor.GREEN + "Your sword is already fully upgraded!");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\BarbarianKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */