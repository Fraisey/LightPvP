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
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class StunnerKit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  39 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  41 */   ItemStack[] content = new ItemStack[36];
/*  42 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public StunnerKit(Core core) {
/*  45 */     this.core = core;
/*     */     
/*  47 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  49 */     ItemStack stunnerBall = new ItemStack(Material.SNOW_BALL);
/*  50 */     ItemMeta stunnerMeta = stunnerBall.getItemMeta();
/*  51 */     stunnerMeta.setDisplayName(ChatColor.GREEN + "Stun Balls " + ChatColor.GRAY + "(Right Click)");
/*  52 */     List<String> stunnerLore = new ArrayList<>();
/*  53 */     stunnerLore.add(ChatColor.GRAY + "Hit players with this to stun them!");
/*  54 */     stunnerMeta.setLore(stunnerLore);
/*  55 */     stunnerBall.setItemMeta(stunnerMeta);
/*     */     
/*  57 */     ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
/*  58 */     LeatherArmorMeta legsMeta = (LeatherArmorMeta)boots.getItemMeta();
/*  59 */     legsMeta.setColor(Color.LIME);
/*  60 */     boots.setItemMeta((ItemMeta)legsMeta);
/*     */     
/*  62 */     this.content = new ItemStack[] { core.ironSwordSharpness, stunnerBall, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  63 */     this.armor = new ItemStack[] { boots, new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  65 */     for (int i = 0; i < this.armor.length; i++) {
/*  66 */       if (this.armor[i] != null) {
/*  67 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  69 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  70 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  71 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     this.armor[1].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  76 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  77 */     this.armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  78 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  83 */     return "Stunner";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  88 */     return "kit.stunner";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  93 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  98 */     return Rareness.COMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 103 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 105 */     p.getInventory().setContents(this.content);
/* 106 */     p.getInventory().setArmorContents(this.armor);
/* 107 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 112 */     return Material.SNOW_BALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 117 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Throw your snowball at a player", ChatColor.GRAY + "to stun them, making them unable to move", ChatColor.GRAY + "giving you the chance to refill and run away", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 122 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onProjectileLaunchEvent(ProjectileLaunchEvent evt) {
/* 129 */     if (evt.getEntity().getShooter() instanceof Player && evt.getEntity() instanceof Snowball) {
/* 130 */       Snowball snowball = (Snowball)evt.getEntity();
/* 131 */       final Player shooter = (Player)snowball.getShooter();
/* 132 */       if (this.core.getKitManager().isKit(shooter, (Kit)this)) {
/* 133 */         final ItemStack stunnerBall = new ItemStack(Material.SNOW_BALL);
/* 134 */         ItemMeta stunnerMeta = stunnerBall.getItemMeta();
/* 135 */         stunnerMeta.setDisplayName(ChatColor.GREEN + " " + ChatColor.GRAY + "(Right Click)");
/* 136 */         List<String> stunnerLore = new ArrayList<>();
/* 137 */         stunnerLore.add(ChatColor.GRAY + "Use this to have the ability to double jump!");
/* 138 */         stunnerMeta.setLore(stunnerLore);
/* 139 */         stunnerBall.setItemMeta(stunnerMeta);
/* 140 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run() {
/* 143 */               shooter.getInventory().addItem(new ItemStack[] { this.val$stunnerBall });
/*     */             }
/* 145 */           }).runTaskLater((Plugin)this.core, 1L);
/* 146 */         if (this.cooldowns.containsKey(shooter.getName())) {
/* 147 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(shooter.getName())).longValue();
/* 148 */           shooter.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 149 */           evt.setCancelled(true);
/*     */         } else {
/* 151 */           final String name = shooter.getName();
/* 152 */           this.cooldowns.put(shooter.getName(), Long.valueOf(System.currentTimeMillis()));
/* 153 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 157 */                   shooter.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/* 158 */                   StunnerKit.this.cooldowns.remove(name);
/*     */                 }
/*     */               }, 
/* 161 */               600L);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 169 */     if (evt.getDamager() instanceof Snowball) {
/* 170 */       Snowball snowball = (Snowball)evt.getDamager();
/* 171 */       if (evt.getEntity() instanceof Player && snowball.getShooter() instanceof Player) {
/* 172 */         Player shooter = (Player)snowball.getShooter();
/* 173 */         final Player hit = (Player)evt.getEntity();
/* 174 */         if (this.core.getKitManager().isKit(shooter, (Kit)this)) {
/*     */           
/* 176 */           final Location loc = hit.getLocation().clone();
/*     */           
/* 178 */           hit.sendMessage(String.valueOf(RankUtil.getFullTag(shooter)) + ChatColor.GRAY + " hit you with their " + ChatUtil.Gi + "Stun Snowball! " + ChatColor.GRAY + "You will be un-stunned in " + ChatUtil.Gi + "5 seconds!");
/* 179 */           shooter.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "hit " + RankUtil.getFullTag(hit) + ChatColor.GRAY + " with your " + ChatUtil.Gi + "Stun Snowball!");
/*     */           
/* 181 */           (new BukkitRunnable() {
/* 182 */               int count = 30;
/*     */ 
/*     */               
/*     */               public void run() {
/* 186 */                 if (this.count > 0 && hit.isOnline() && hit.getLocation().distanceSquared(loc) < 36.0D) {
/* 187 */                   hit.teleport(new Location(StunnerKit.this.core.getWorld(), loc.getX(), loc.getY(), loc.getZ(), hit.getLocation().getYaw(), hit.getLocation().getPitch()), PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);
/* 188 */                   this.count--;
/*     */                 } else {
/* 190 */                   cancel();
/*     */                 } 
/*     */               }
/* 193 */             }).runTaskTimer((Plugin)this.core, 0L, 3L);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\StunnerKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */