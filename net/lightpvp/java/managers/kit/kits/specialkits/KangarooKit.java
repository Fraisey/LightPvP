/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class KangarooKit
/*     */   implements Listener, SpecialKit {
/*     */   private Core core;
/*  31 */   private List<String> cooldowns = new ArrayList<>();
/*     */   
/*  33 */   ItemStack[] content = new ItemStack[36];
/*  34 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public KangarooKit(Core core) {
/*  37 */     this.core = core;
/*     */     
/*  39 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  41 */     ItemStack kangarooRocket = new ItemStack(Material.FIREWORK);
/*  42 */     ItemMeta rocketMeta = kangarooRocket.getItemMeta();
/*  43 */     rocketMeta.setDisplayName(ChatColor.GREEN + "Kangaroo's Jumping Rocket " + ChatColor.GRAY + "(Right Click)");
/*  44 */     List<String> rocketLore = new ArrayList<>();
/*  45 */     rocketLore.add(ChatColor.GRAY + "Use this to have the ability to double jump!");
/*  46 */     rocketMeta.setLore(rocketLore);
/*  47 */     kangarooRocket.setItemMeta(rocketMeta);
/*     */     
/*  49 */     this.content = new ItemStack[] { core.ironSwordSharpness, kangarooRocket, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  50 */     this.armor = new ItemStack[] { new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  52 */     for (int i = 0; i < this.armor.length; i++) {
/*  53 */       if (this.armor[i] != null) {
/*  54 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  56 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  57 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  58 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     this.armor[0].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  63 */     this.armor[0].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  68 */     return "Kangaroo";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  73 */     return "kit.kangaroo";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  83 */     return Rareness.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  88 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  90 */     p.getInventory().setContents(this.content);
/*  91 */     p.getInventory().setArmorContents(this.armor);
/*  92 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  97 */     return Material.FIREWORK;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 102 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your firework rocket", ChatColor.GRAY + "to have the ability to jump two times", ChatColor.GRAY + "(right click two times for the double jump)", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 107 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 114 */     Player player = evt.getPlayer();
/* 115 */     if (evt.getItem() != null && evt.getItem().getType().equals(Material.FIREWORK)) {
/* 116 */       if (this.core.getKitManager().isKit(player, (Kit)this) && 
/* 117 */         !this.cooldowns.contains(player.getName())) {
/* 118 */         if (player.hasMetadata("kangaroo")) {
/* 119 */           kangarooJump(player);
/*     */           
/* 121 */           this.cooldowns.add(player.getName());
/* 122 */           final String name = player.getName();
/* 123 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 127 */                   KangarooKit.this.cooldowns.remove(name);
/*     */                 }
/*     */               }, 
/* 130 */               40L);
/*     */           
/* 132 */           player.removeMetadata("kangaroo", (Plugin)this.core);
/*     */         } else {
/* 134 */           player.setMetadata("kangaroo", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(1)));
/* 135 */           kangarooJump(player);
/*     */         } 
/*     */       }
/*     */       
/* 139 */       evt.setCancelled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kangarooJump(Player player) {
/* 144 */     if (!player.isSneaking()) {
/* 145 */       player.setFallDistance(-6.0F);
/* 146 */       Vector vector = player.getEyeLocation().getDirection();
/* 147 */       vector.multiply(0.8F);
/* 148 */       vector.setY(0.8F);
/* 149 */       player.setVelocity(vector);
/*     */     } else {
/* 151 */       player.setFallDistance(-6.0F);
/* 152 */       Vector vector = player.getEyeLocation().getDirection();
/* 153 */       vector.multiply(0.8F);
/* 154 */       vector.setY(0.6D);
/* 155 */       player.setVelocity(vector);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\KangarooKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */