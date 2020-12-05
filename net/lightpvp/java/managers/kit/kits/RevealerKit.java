/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class RevealerKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  31 */   private ArrayList<String> cdTime = new ArrayList<>();
/*     */   
/*  33 */   private HashMap<String, ItemStack> helm = new HashMap<>();
/*  34 */   private HashMap<String, ItemStack> chest = new HashMap<>();
/*  35 */   private HashMap<String, ItemStack> legs = new HashMap<>();
/*  36 */   private HashMap<String, ItemStack> boots = new HashMap<>();
/*     */   
/*  38 */   ItemStack[] content = new ItemStack[36];
/*  39 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public RevealerKit(Core core) {
/*  42 */     this.core = core;
/*     */     
/*  44 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  46 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  47 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
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
/*  62 */     return "Revealer";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  67 */     return "kit.revealer";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  72 */     return 60000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  77 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  79 */     p.getInventory().setContents(this.content);
/*  80 */     p.getInventory().setArmorContents(this.armor);
/*  81 */     user.setKit(getName());
/*  82 */     this.helm.put(p.getName(), p.getInventory().getHelmet());
/*  83 */     this.chest.put(p.getName(), p.getInventory().getChestplate());
/*  84 */     this.legs.put(p.getName(), p.getInventory().getLeggings());
/*  85 */     this.boots.put(p.getName(), p.getInventory().getBoots());
/*  86 */     p.getInventory().setArmorContents(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  91 */     return Material.SKULL_ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  96 */     return Arrays.asList(new String[] { ChatColor.GREEN + "Abilities:", ChatColor.GRAY + "You have no armor until", ChatColor.GRAY + "you take damage/get hit by a player.", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 101 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/* 108 */     if (evt.getEntity() instanceof Player) {
/* 109 */       final Player player = (Player)evt.getEntity();
/* 110 */       if (!this.cdTime.contains(player.getName()) && 
/* 111 */         this.helm.containsKey(player.getName()) && this.chest.containsKey(player.getName()) && this.legs.containsKey(player.getName()) && this.boots.containsKey(player.getName()) && 
/* 112 */         this.core.getKitManager().isKit(player, this)) {
/* 113 */         player.getInventory().setHelmet(this.helm.get(player.getName()));
/* 114 */         player.getInventory().setChestplate(this.chest.get(player.getName()));
/* 115 */         player.getInventory().setLeggings(this.legs.get(player.getName()));
/* 116 */         player.getInventory().setBoots(this.boots.get(player.getName()));
/* 117 */         this.cdTime.add(player.getName());
/*     */         
/* 119 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 123 */               RevealerKit.this.cdTime.remove(player.getName());
/*     */             }
/* 125 */           }).runTaskLaterAsynchronously((Plugin)this.core, 20L);
/*     */         
/* 127 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 131 */               RevealerKit.this.helm.put(player.getName(), player.getInventory().getHelmet());
/* 132 */               RevealerKit.this.chest.put(player.getName(), player.getInventory().getChestplate());
/* 133 */               RevealerKit.this.legs.put(player.getName(), player.getInventory().getLeggings());
/* 134 */               RevealerKit.this.boots.put(player.getName(), player.getInventory().getBoots());
/* 135 */               player.getInventory().setArmorContents(null);
/*     */             }
/* 137 */           }).runTaskLaterAsynchronously((Plugin)this.core, 20L);
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (this.core.getKitManager().isKit(player, this)) {
/* 142 */         evt.setDamage(evt.getDamage() - 0.5D);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(PlayerDeathEvent evt) {
/* 149 */     Player player = evt.getEntity();
/* 150 */     if (this.core.getKitManager().hasKit(player, this)) {
/* 151 */       this.helm.remove(player.getName());
/* 152 */       this.chest.remove(player.getName());
/* 153 */       this.legs.remove(player.getName());
/* 154 */       this.boots.remove(player.getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onInventoryCloseEvent(InventoryCloseEvent evt) {
/* 160 */     Player player = (Player)evt.getPlayer();
/* 161 */     if (!this.cdTime.contains(player.getName()) && 
/* 162 */       this.core.getKitManager().isKit(player, this)) {
/* 163 */       if (player.getInventory().getHelmet() != null && !player.getInventory().getHelmet().getType().equals(Material.AIR)) {
/* 164 */         this.helm.put(player.getName(), player.getInventory().getHelmet());
/*     */       }
/* 166 */       if (player.getInventory().getChestplate() != null && !player.getInventory().getChestplate().getType().equals(Material.AIR)) {
/* 167 */         this.chest.put(player.getName(), player.getInventory().getChestplate());
/*     */       }
/* 169 */       if (player.getInventory().getLeggings() != null && !player.getInventory().getLeggings().getType().equals(Material.AIR)) {
/* 170 */         this.legs.put(player.getName(), player.getInventory().getLeggings());
/*     */       }
/* 172 */       if (player.getInventory().getBoots() != null && !player.getInventory().getBoots().getType().equals(Material.AIR)) {
/* 173 */         this.boots.put(player.getName(), player.getInventory().getBoots());
/*     */       }
/* 175 */       player.getInventory().setArmorContents(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\RevealerKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */