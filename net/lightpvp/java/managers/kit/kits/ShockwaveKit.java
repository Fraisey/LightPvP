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
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShockwaveKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  37 */   ItemStack[] content = new ItemStack[36];
/*  38 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  40 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public ShockwaveKit(Core core) {
/*  43 */     this.core = core;
/*     */     
/*  45 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  47 */     ItemStack mightyHammer = new ItemStack(Material.DIAMOND_AXE);
/*  48 */     mightyHammer.addEnchantment(Enchantment.DURABILITY, 3);
/*  49 */     ItemMeta mightyHammerMeta = mightyHammer.getItemMeta();
/*  50 */     mightyHammerMeta.setDisplayName(ChatColor.GREEN + "Mighty Hammer");
/*  51 */     mightyHammer.setItemMeta(mightyHammerMeta);
/*     */     
/*  53 */     this.content = new ItemStack[] { core.diamondSwordSharpness, mightyHammer, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  55 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  56 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  57 */     helmetMeta.setColor(Color.BLACK);
/*  58 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  60 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), helmet };
/*     */     
/*  62 */     for (int i = 0; i < this.armor.length; i++) {
/*  63 */       if (this.armor[i] != null) {
/*  64 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  66 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  67 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  68 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return "Shockwave";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  80 */     return "kit.shockwave";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  85 */     return 20000;
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
/*  99 */     return Material.DIAMOND_AXE;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 104 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your axe to create a shockwave", ChatColor.GRAY + "and knock everyone around you away", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 109 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 117 */     if (evt.getAction().equals(Action.LEFT_CLICK_BLOCK) && 
/* 118 */       evt.getItem() != null && evt.getItem().getType().equals(Material.DIAMOND_AXE)) {
/* 119 */       final Player player = evt.getPlayer();
/* 120 */       if (this.core.getKitManager().isKit(player, this) && 
/* 121 */         player.isOnGround())
/* 122 */         if (this.cooldowns.containsKey(player.getName())) {
/* 123 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 124 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((15000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 125 */           evt.setCancelled(true);
/*     */         } else {
/* 127 */           Location shockLocation = player.getLocation();
/* 128 */           shockLocation.getWorld().playSound(shockLocation, Sound.EXPLODE, 0.2F, 0.7F);
/*     */ 
/*     */           
/* 131 */           for (Entity e : player.getNearbyEntities(4.0D, 2.0D, 4.0D)) {
/* 132 */             if (e instanceof Player) {
/* 133 */               Player target = (Player)e;
/* 134 */               target.damage(12.0D, (Entity)player);
/* 135 */               target.setVelocity(target.getLocation().subtract(shockLocation).multiply(0.5D).toVector().setY(0.2F));
/*     */             } 
/*     */           } 
/* 138 */           evt.setCancelled(true);
/* 139 */           final String name = player.getName();
/* 140 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 141 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 145 */                   ShockwaveKit.this.cooldowns.remove(name);
/* 146 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 149 */               300L);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\ShockwaveKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */