/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ public class HulkKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  39 */   ItemStack[] content = new ItemStack[36];
/*  40 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  42 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  44 */   private HashMap<Player, Float> name = new HashMap<>();
/*  45 */   private HashMap<Player, Float> namep = new HashMap<>();
/*     */   
/*     */   public HulkKit(Core core) {
/*  48 */     this.core = core;
/*     */     
/*  50 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  52 */     ItemStack slimeball = new ItemStack(Material.SLIME_BALL);
/*  53 */     ItemMeta slimeballMeta = slimeball.getItemMeta();
/*  54 */     slimeballMeta.setDisplayName(ChatColor.GREEN + "Ride Tool");
/*  55 */     slimeball.setItemMeta(slimeballMeta);
/*     */     
/*  57 */     this.content = new ItemStack[] { core.diamondSwordSharpness, slimeball, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  59 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  60 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  61 */     helmetMeta.setColor(Color.GREEN);
/*  62 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  64 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), helmet };
/*     */     
/*  66 */     for (int i = 0; i < this.armor.length; i++) {
/*  67 */       if (this.armor[i] != null) {
/*  68 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  70 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  71 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  72 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  79 */     return "Hulk";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  84 */     return "kit.hulk";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  89 */     return 20000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  94 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  96 */     p.getInventory().setContents(this.content);
/*  97 */     p.getInventory().setArmorContents(this.armor);
/*  98 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 103 */     return Material.SLIME_BALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 108 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your kit item and right click players", ChatColor.GRAY + "to make them ride on you", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 113 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
/* 120 */     if (evt.getRightClicked() instanceof Player && 
/* 121 */       this.core.getKitManager().isKit(evt.getPlayer(), this)) {
/* 122 */       final Player player = evt.getPlayer();
/* 123 */       if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.SLIME_BALL) && player.getVehicle() == null) {
/* 124 */         Player clicked = (Player)evt.getRightClicked();
/*     */         
/* 126 */         if (this.cooldowns.containsKey(player.getName())) {
/* 127 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 128 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((5000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 129 */           evt.setCancelled(true);
/*     */         }
/* 131 */         else if (player.getVehicle() == null) {
/* 132 */           player.setPassenger((Entity)clicked);
/* 133 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "hulked " + RankUtil.getFullTag(clicked));
/* 134 */           clicked.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatUtil.Gi + " hulked you! " + ChatColor.GRAY + "Press " + ChatUtil.Gi + "SHIFT " + ChatColor.GRAY + "to stop riding them!");
/*     */ 
/*     */           
/* 137 */           final String name = player.getName();
/* 138 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 139 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 143 */                   HulkKit.this.cooldowns.remove(name);
/* 144 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 147 */               100L);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onVehicleExitEvent(VehicleExitEvent evt) {
/* 157 */     if (evt.getExited() instanceof Player) {
/* 158 */       Player player = (Player)evt.getExited();
/* 159 */       if (player.getVehicle() != null) {
/* 160 */         player.getVehicle().eject();
/* 161 */         player.setVelocity(new Vector(0, 1, 0));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 168 */     if (evt.getAction().equals(Action.LEFT_CLICK_AIR) || evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
/* 169 */       Player player = evt.getPlayer();
/* 170 */       if (this.core.getKitManager().isKit(player, this) && 
/* 171 */         player.getPassenger() != null && 
/* 172 */         player.getPassenger() instanceof Player) {
/* 173 */         Player pass = (Player)player.getPassenger();
/* 174 */         this.name.put(pass, Float.valueOf(pass.getLocation().getYaw()));
/* 175 */         this.namep.put(pass, Float.valueOf(pass.getLocation().getPitch()));
/* 176 */         pass.eject();
/* 177 */         pass.leaveVehicle();
/* 178 */         double x = player.getLocation().getX();
/* 179 */         double y = player.getLocation().getY();
/* 180 */         double z = player.getLocation().getZ();
/* 181 */         float direction1 = ((Float)this.name.get(pass)).floatValue();
/* 182 */         float pitch1 = ((Float)this.namep.get(pass)).floatValue();
/* 183 */         Location loc1 = new Location(this.core.getWorld(), x, y, z, direction1, pitch1);
/* 184 */         loc1.setY(y + 3.0D);
/* 185 */         pass.teleport(loc1);
/* 186 */         Vector vector = player.getEyeLocation().getDirection();
/* 187 */         vector.multiply(0.7F);
/* 188 */         pass.setVelocity(vector);
/* 189 */         pass.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " threw you out of their hands");
/* 190 */         this.name.remove(pass);
/* 191 */         this.namep.remove(pass);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\HulkKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */