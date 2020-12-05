/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerToggleSneakEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SonicKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  31 */   ItemStack[] content = new ItemStack[36];
/*  32 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public SonicKit(Core core) {
/*  35 */     this.core = core;
/*     */     
/*  37 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  39 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  41 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  42 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  43 */     helmetMeta.setColor(Color.BLUE);
/*  44 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  46 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), helmet };
/*     */     
/*  48 */     for (int i = 0; i < this.armor.length; i++) {
/*  49 */       if (this.armor[i] != null) {
/*  50 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  52 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  53 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  54 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  61 */     return "Sonic";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  66 */     return "kit.sonic";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  71 */     return 60000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  76 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  78 */     p.getInventory().setContents(this.content);
/*  79 */     p.getInventory().setArmorContents(this.armor);
/*  80 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  85 */     return Material.LAPIS_BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  90 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Block and shift to charge", ChatColor.GRAY + "your super speed!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/*  95 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent evt) {
/* 102 */     final Player player = evt.getPlayer();
/* 103 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 105 */     if (evt.isSneaking() && evt.getPlayer().isBlocking() && 
/* 106 */       this.core.getKitManager().isKit(player, this) && 
/* 107 */       !this.core.hasSpawnFlag(user) && 
/* 108 */       player.getWalkSpeed() == 0.2F) {
/*     */       
/* 110 */       player.setWalkSpeed(0.15F);
/*     */       
/* 112 */       (new BukkitRunnable()
/*     */         {
/* 114 */           int charge = 0;
/*     */ 
/*     */           
/*     */           public void run() {
/* 118 */             if (this.charge >= 10) {
/* 119 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "Go!");
/* 120 */               player.setWalkSpeed(0.8F);
/* 121 */               (new BukkitRunnable()
/*     */                 {
/* 123 */                   int count = 0;
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void run() {
/* 128 */                     if (player.getWalkSpeed() > 0.2D) {
/* 129 */                       if (this.count % 2 == 0) {
/* 130 */                         player.setWalkSpeed(player.getWalkSpeed() - 0.04F);
/*     */                       }
/*     */ 
/*     */ 
/*     */                       
/* 135 */                       this.count++;
/*     */                     } else {
/* 137 */                       player.setWalkSpeed(0.2F);
/* 138 */                       cancel();
/*     */                     } 
/*     */                   }
/* 141 */                 }).runTaskTimer((Plugin)SonicKit.this.core, 4L, 4L);
/*     */               
/* 143 */               cancel();
/*     */             }
/* 145 */             else if (player.isSneaking() && player.isBlocking()) {
/* 146 */               this.charge++;
/* 147 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "Charging" + ChatColor.GRAY + " at " + ChatColor.GOLD + (this.charge * 10) + "%");
/*     */             } else {
/* 149 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "Charging" + ChatColor.GRAY + " stopped!");
/* 150 */               player.setWalkSpeed(0.2F);
/* 151 */               cancel();
/*     */             }
/*     */           
/*     */           }
/* 155 */         }).runTaskTimer((Plugin)this.core, 5L, 5L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\SonicKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */