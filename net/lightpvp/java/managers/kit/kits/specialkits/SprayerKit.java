/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
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
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class SprayerKit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  32 */   ItemStack[] content = new ItemStack[36];
/*  33 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  35 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public SprayerKit(Core core) {
/*  38 */     this.core = core;
/*     */     
/*  40 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  42 */     ItemStack sprayBowItem = new ItemStack(Material.BOW);
/*  43 */     ItemMeta sprayMeta = sprayBowItem.getItemMeta();
/*  44 */     sprayMeta.setDisplayName(ChatColor.GREEN + "Spraying Bow " + ChatColor.GRAY + "(Right Click)");
/*  45 */     List<String> sprayLore = new ArrayList<>();
/*  46 */     sprayLore.add(ChatColor.GRAY + "Use this once, to shoot out a spray of arrows, 1 every second.");
/*  47 */     sprayLore.add(ChatColor.GRAY + "5 arrows in total will be shot out of 1 normal bow shot.");
/*  48 */     sprayMeta.setLore(sprayLore);
/*  49 */     sprayBowItem.setItemMeta(sprayMeta);
/*  50 */     sprayBowItem.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/*     */     
/*  52 */     this.content = new ItemStack[] { core.ironSwordSharpness, sprayBowItem, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, new ItemStack(Material.ARROW, 1) };
/*     */     
/*  54 */     ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
/*     */     
/*  56 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), helmet };
/*     */     
/*  58 */     for (int i = 0; i < this.armor.length; i++) {
/*  59 */       if (this.armor[i] != null) {
/*  60 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*  61 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  62 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  63 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  70 */     return "Sprayer";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  75 */     return "kit.sprayer";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  85 */     return Rareness.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  90 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  92 */     p.getInventory().setContents(this.content);
/*  93 */     p.getInventory().setArmorContents(this.armor);
/*  94 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  99 */     return Material.ARROW;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 104 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your bow normally to send a spray", ChatColor.GRAY + "of arrows(5 in total) every 1 second.", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 109 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityShootBowEvent(EntityShootBowEvent evt) {
/* 116 */     if (evt.getEntity() instanceof Player) {
/* 117 */       final Player player = (Player)evt.getEntity();
/*     */       
/* 119 */       if (this.core.getKitManager().isKit(player, (Kit)this)) {
/* 120 */         if (this.cooldowns.containsKey(player.getName())) {
/* 121 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 122 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((20000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 123 */           evt.setCancelled(true);
/*     */         } else {
/* 125 */           (new BukkitRunnable() {
/* 126 */               int count = 40;
/*     */ 
/*     */               
/*     */               public void run() {
/* 130 */                 if (this.count > 0 && player.getItemInHand() != null && player.getInventory().getItemInHand().getType().equals(Material.BOW)) {
/* 131 */                   for (int i = 0; i < 3; i++) {
/* 132 */                     SprayerKit.this.core.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 2.0F, 8.0F).setShooter((ProjectileSource)player);
/*     */                   }
/* 134 */                   this.count--;
/*     */                 } else {
/* 136 */                   cancel();
/*     */                 } 
/*     */               }
/* 139 */             }).runTaskTimer((Plugin)this.core, 2L, 2L);
/*     */           
/* 141 */           final String name = player.getName();
/* 142 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 143 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 147 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/* 148 */                   SprayerKit.this.cooldowns.remove(name);
/*     */                 }
/*     */               }, 
/* 151 */               400L);
/*     */         } 
/*     */         
/* 154 */         evt.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\SprayerKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */