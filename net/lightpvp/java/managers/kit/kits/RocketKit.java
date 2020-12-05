/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
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
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RocketKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  40 */   ItemStack[] content = new ItemStack[36];
/*  41 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  43 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public RocketKit(Core core) {
/*  46 */     this.core = core;
/*     */     
/*  48 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  50 */     ItemStack spotPicker = new ItemStack(Material.FIREWORK);
/*  51 */     ItemMeta spotPickerMeta = spotPicker.getItemMeta();
/*  52 */     spotPickerMeta.setDisplayName(ChatColor.GREEN + "Rocket");
/*  53 */     spotPicker.setItemMeta(spotPickerMeta);
/*     */     
/*  55 */     this.content = new ItemStack[] { core.diamondSwordSharpness, spotPicker, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  57 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  58 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  59 */     helmetMeta.setColor(Color.WHITE);
/*  60 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  62 */     ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  63 */     LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
/*  64 */     chestplateMeta.setColor(Color.WHITE);
/*  65 */     chestplate.setItemMeta((ItemMeta)chestplateMeta);
/*     */     
/*  67 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_LEGGINGS), chestplate, helmet };
/*     */     
/*  69 */     for (int i = 0; i < this.armor.length; i++) {
/*  70 */       if (this.armor[i] != null) {
/*  71 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  73 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  74 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  75 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return "Rocket";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  87 */     return "kit.rocket";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  92 */     return 75000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  97 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  99 */     p.getInventory().setContents(this.content);
/* 100 */     p.getInventory().setArmorContents(this.armor);
/* 101 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 106 */     return Material.FIREWORK;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 111 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your kit item to lauch yourself", ChatColor.GRAY + "into the direction that you are looking at", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 116 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 123 */     if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 124 */       evt.getItem() != null && evt.getItem().getType().equals(Material.FIREWORK)) {
/* 125 */       final Player player = evt.getPlayer();
/* 126 */       if (this.core.getKitManager().isKit(player, this)) {
/* 127 */         if (this.cooldowns.containsKey(player.getName())) {
/* 128 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 129 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((15000.0D - cooldown) / 1000.0D) + " more seconds!");
/*     */         } else {
/* 131 */           RocketRunnable rocketRunnable = new RocketRunnable(this.core, evt.getPlayer());
/* 132 */           rocketRunnable.launch();
/* 133 */           final String name = player.getName();
/* 134 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 135 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 139 */                   RocketKit.this.cooldowns.remove(name);
/* 140 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 143 */               300L);
/*     */         } 
/* 145 */         evt.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/* 153 */     if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) && 
/* 154 */       evt.getEntity() instanceof Player) {
/* 155 */       Player player = (Player)evt.getEntity();
/* 156 */       if (this.core.getKitManager().isKit(player, this)) {
/* 157 */         evt.setDamage(evt.getDamage() / 2.0D);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent evt) {
/* 166 */     if (!evt.getPlayer().isOnGround() && 
/* 167 */       this.core.getKitManager().isKit(evt.getPlayer(), this))
/* 168 */       evt.getPlayer().removeMetadata("launch", (Plugin)this.core); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\RocketKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */