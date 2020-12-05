/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Creeper;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitanKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  41 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  43 */   private ArrayList<String> ironcreeperDone = new ArrayList<>();
/*     */   
/*  45 */   ItemStack[] content = new ItemStack[36];
/*  46 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public TitanKit(Core core) {
/*  49 */     this.core = core;
/*     */     
/*  51 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  53 */     ItemStack anvilCreep = new ItemStack(Material.ANVIL);
/*  54 */     ItemMeta anvilMeta = anvilCreep.getItemMeta();
/*  55 */     anvilMeta.setDisplayName(ChatColor.GREEN + "Titan's Exploder " + ChatColor.GRAY + "(Right Click)");
/*  56 */     List<String> lore = new ArrayList<>();
/*  57 */     lore.add(ChatColor.GRAY + "Use this to spawn a creeper that explodes and launches players around");
/*  58 */     lore.add(ChatColor.GRAY + "high into the air. Use this when launched by the creeper to explode on");
/*  59 */     lore.add(ChatColor.GRAY + "the ground at a high speed!");
/*  60 */     anvilMeta.setLore(lore);
/*  61 */     anvilCreep.setItemMeta(anvilMeta);
/*     */     
/*  63 */     this.content = new ItemStack[] { core.ironSwordSharpness, anvilCreep, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  65 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  67 */     for (int i = 0; i < this.armor.length; i++) {
/*  68 */       if (this.armor[i] != null) {
/*  69 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  71 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  72 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  73 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  80 */     return "Titan";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  85 */     return "kit.titan";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  90 */     return 40000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  95 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  97 */     p.getInventory().setContents(this.content);
/*  98 */     p.getInventory().setArmorContents(this.armor);
/*  99 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 104 */     return Material.BEACON;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 109 */     return Arrays.asList(new String[] { ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Spawn a creeper that", ChatColor.GRAY + "shoots everyone around you", ChatColor.GRAY + "in the air!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 114 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 121 */     final Player player = evt.getPlayer();
/* 122 */     if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 123 */       evt.getItem() != null && evt.getItem().getType().equals(Material.ANVIL) && 
/* 124 */       this.core.getKitManager().isKit(player, this)) {
/* 125 */       if (this.cooldowns.containsKey(player.getName()) && !this.ironcreeperDone.contains(player.getName())) {
/* 126 */         long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 127 */         player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((20000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 128 */         evt.setCancelled(true);
/* 129 */       } else if (!this.ironcreeperDone.contains(player.getName())) {
/* 130 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "creeper " + ChatColor.GRAY + "spawned!");
/* 131 */         Location loc = evt.getPlayer().getLocation();
/* 132 */         loc.setY(loc.getY() + 10.0D);
/* 133 */         final Creeper g = (Creeper)this.core.getWorld().spawnEntity(loc, EntityType.CREEPER);
/* 134 */         (new BukkitRunnable() {
/*     */             public void run() {
/* 136 */               if (g.isDead()) {
/* 137 */                 cancel();
/*     */                 return;
/*     */               } 
/* 140 */               Block b = g.getLocation().getBlock();
/* 141 */               if (!b.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
/* 142 */                 g.setHealth(0.0D);
/* 143 */                 List<Entity> nearbyEntities = g.getNearbyEntities(7.0D, 7.0D, 7.0D);
/* 144 */                 int num = 0;
/* 145 */                 for (Entity e : nearbyEntities) {
/* 146 */                   if (e instanceof Player && 
/* 147 */                     !((Player)e).getName().equals(player.getName())) {
/* 148 */                     num++;
/* 149 */                     Player t = (Player)e;
/* 150 */                     t.damage(1.0D, (Entity)player);
/* 151 */                     t.setVelocity(t.getVelocity().setY(1.8D));
/*     */                   } 
/*     */                 } 
/*     */                 
/* 155 */                 g.getWorld().createExplosion(g.getLocation(), 0.5F);
/* 156 */                 TitanKit.this.ironcreeperDone.add(player.getName());
/* 157 */                 (new BukkitRunnable() {
/*     */                     public void run() {
/* 159 */                       (TitanKit.null.access$0(TitanKit.null.this)).ironcreeperDone.remove(player.getName());
/*     */                     }
/* 161 */                   }).runTaskLater((Plugin)TitanKit.this.core, 80L);
/* 162 */                 player.setVelocity(player.getVelocity().setY(2.3D));
/* 163 */                 if (num <= 0) {
/* 164 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "Your " + ChatColor.GRAY + "creeper exploded and didn't get anyone apart from yourself!");
/*     */                 } else {
/* 166 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "You got " + ChatColor.GRAY + num + " players with your creeper!");
/*     */                 } 
/* 168 */                 cancel();
/*     */               } 
/*     */             }
/* 171 */           }).runTaskTimer((Plugin)this.core, 5L, 5L);
/*     */         
/* 173 */         final String name = player.getName();
/* 174 */         this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 175 */         Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 179 */                 player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/* 180 */                 TitanKit.this.cooldowns.remove(name);
/*     */               }
/*     */             }, 
/* 183 */             400L);
/*     */       }
/* 185 */       else if (this.ironcreeperDone.contains(player.getName())) {
/* 186 */         player.sendMessage(ChatColor.GREEN + "You shot down from the explosion of your creeper!");
/* 187 */         this.ironcreeperDone.remove(player.getName());
/* 188 */         player.setVelocity(player.getVelocity().setY(-2.0D));
/* 189 */         (new BukkitRunnable() {
/*     */             public void run() {
/* 191 */               if (player.isDead()) {
/* 192 */                 cancel();
/*     */                 return;
/*     */               } 
/* 195 */               Block b = player.getLocation().getBlock();
/* 196 */               if (!b.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
/* 197 */                 List<Entity> nearbyEntities = player.getNearbyEntities(7.0D, 7.0D, 7.0D);
/* 198 */                 int num = 0;
/* 199 */                 for (Entity e : nearbyEntities) {
/* 200 */                   if (e instanceof Player && 
/* 201 */                     !((Player)e).getName().equals(player.getName())) {
/* 202 */                     num++;
/* 203 */                     Player t = (Player)e;
/* 204 */                     t.damage(1.0D, (Entity)player);
/* 205 */                     t.setVelocity(t.getVelocity().setY(0.5D));
/*     */                   } 
/*     */                 } 
/*     */                 
/* 209 */                 player.getWorld().createExplosion(player.getLocation(), 4.8F);
/* 210 */                 if (num <= 0) {
/* 211 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "Your " + ChatColor.GRAY + "explosion didn't get anyone!");
/*     */                 } else {
/* 213 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "You got " + ChatColor.GRAY + num + " players with your explosion!");
/*     */                 } 
/* 215 */                 cancel();
/*     */               }
/*     */             
/*     */             }
/* 219 */           }).runTaskTimer((Plugin)this.core, 5L, 5L);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/* 228 */     if ((evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) || evt.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) && 
/* 229 */       evt.getEntity() instanceof Player) {
/* 230 */       Player player = (Player)evt.getEntity();
/* 231 */       if (this.cooldowns.containsKey(player.getName()) && 
/* 232 */         this.core.getKitManager().isKit(player, this)) {
/* 233 */         evt.setCancelled(true);
/* 234 */         this.ironcreeperDone.remove(player.getName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\TitanKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */