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
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwitcherKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  36 */   ItemStack snowballs = new ItemStack(Material.SNOW_BALL, 16);
/*     */   
/*  38 */   ItemStack[] content = new ItemStack[36];
/*  39 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  41 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public SwitcherKit(Core core) {
/*  44 */     this.core = core;
/*     */     
/*  46 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  48 */     ItemMeta snowballsMeta = this.snowballs.getItemMeta();
/*  49 */     snowballsMeta.setDisplayName(ChatColor.GREEN + "Switcher Ball");
/*  50 */     this.snowballs.setItemMeta(snowballsMeta);
/*  51 */     this.content = new ItemStack[] { core.diamondSwordSharpness, this.snowballs, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  52 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET) };
/*     */     
/*  54 */     for (int i = 0; i < this.armor.length; i++) {
/*  55 */       if (this.armor[i] != null) {
/*  56 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  58 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  59 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  60 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  67 */     return "Switcher";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  72 */     return "kit.switcher";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  77 */     return 58000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  82 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  84 */     p.getInventory().setContents(this.content);
/*  85 */     p.getInventory().setArmorContents(this.armor);
/*  86 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  91 */     return Material.SNOW_BALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/*  96 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your snowballs to switch places with a player", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 101 */     ItemStack related = this.snowballs.clone();
/* 102 */     related.setAmount(5);
/* 103 */     return related;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 109 */     if (evt.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && 
/* 110 */       evt.getDamager() instanceof Snowball && 
/* 111 */       evt.getEntity() instanceof Player) {
/* 112 */       Snowball snowball = (Snowball)evt.getDamager();
/* 113 */       if (snowball.getShooter() instanceof Player) {
/* 114 */         Player damager = (Player)snowball.getShooter();
/* 115 */         Player target = (Player)evt.getEntity();
/* 116 */         if (this.core.getKitManager().isKit(damager, this) && 
/* 117 */           !damager.getName().equals(target.getName())) {
/* 118 */           evt.setDamage(1.0D);
/* 119 */           Location damagerLoc = damager.getLocation();
/* 120 */           Location targetLoc = target.getLocation();
/* 121 */           damager.teleport(targetLoc);
/* 122 */           target.teleport(damagerLoc);
/* 123 */           damager.playSound(damager.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
/* 124 */           target.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 135 */     if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 136 */       evt.getItem() != null && 
/* 137 */       evt.getItem().getType().equals(Material.SNOW_BALL)) {
/* 138 */       final Player player = evt.getPlayer();
/* 139 */       if (this.core.getKitManager().isKit(player, this))
/* 140 */         if (this.cooldowns.containsKey(player.getName())) {
/* 141 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 142 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((3000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 143 */           evt.setCancelled(true);
/*     */         } else {
/* 145 */           final String name = player.getName();
/* 146 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 147 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 151 */                   SwitcherKit.this.cooldowns.remove(name);
/* 152 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 155 */               60L);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\SwitcherKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */