/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*     */ import net.minecraft.server.v1_8_R3.EntityPig;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Damageable;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Pig;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class PigC4Kit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  48 */   private ItemStack pigC4 = new ItemStack(Material.PORK, 8);
/*     */   
/*  50 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  52 */   ItemStack[] content = new ItemStack[36];
/*  53 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public PigC4Kit(Core core) {
/*  56 */     this.core = core;
/*     */     
/*  58 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  60 */     ItemMeta pigC4Meta = this.pigC4.getItemMeta();
/*  61 */     pigC4Meta.setDisplayName(ChatColor.GREEN + "C4 Pig Launcher " + ChatColor.GRAY + "(Right Click)");
/*  62 */     pigC4Meta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Use this to throw a pig that stands still.", ChatColor.GRAY + "Use your detonater to explode the pig(s)!" }));
/*  63 */     this.pigC4.setItemMeta(pigC4Meta);
/*     */     
/*  65 */     ItemStack detonator = new ItemStack(Material.BREWING_STAND_ITEM);
/*  66 */     ItemMeta detonatorMeta = detonator.getItemMeta();
/*  67 */     detonatorMeta.setDisplayName(ChatColor.GREEN + "C4 Pig Detonator " + ChatColor.GRAY + "(Right Click)");
/*  68 */     detonatorMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Check the 'C4 Pig Launcher' for information about", ChatColor.GRAY + "how to use this item!" }));
/*  69 */     detonator.setItemMeta(detonatorMeta);
/*     */     
/*  71 */     ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
/*  72 */     LeatherArmorMeta legsMeta = (LeatherArmorMeta)legs.getItemMeta();
/*  73 */     legsMeta.setColor(Color.FUCHSIA);
/*  74 */     legs.setItemMeta((ItemMeta)legsMeta);
/*     */     
/*  76 */     ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  77 */     LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
/*  78 */     chestMeta.setColor(Color.FUCHSIA);
/*  79 */     chest.setItemMeta((ItemMeta)chestMeta);
/*     */     
/*  81 */     this.content = new ItemStack[] { core.ironSwordSharpness, this.pigC4, detonator, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  82 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), legs, chest, new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  84 */     for (int i = 0; i < this.armor.length; i++) {
/*  85 */       if (this.armor[i] != null) {
/*  86 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  88 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  89 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  90 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     this.armor[1].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  95 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  96 */     this.armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  97 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 102 */     return "Pig C4";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/* 107 */     return "kit.pigc4";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/* 117 */     return Rareness.EXTREMLY_RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 122 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 124 */     p.getInventory().setContents(this.content);
/* 125 */     p.getInventory().setArmorContents(this.armor);
/* 126 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 131 */     return Material.PORK;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 136 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your pork to throw out a pig.", ChatColor.GRAY + "You can have a max of 5 pigs in the map at 1 time.", ChatColor.GRAY + "Right click your detonater to make these pigs go BANG!", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 141 */     ItemStack related = this.pigC4.clone();
/* 142 */     related.setAmount(4);
/* 143 */     return related;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 150 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 151 */       final Player player = evt.getPlayer();
/* 152 */       if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.PORK)) {
/* 153 */         if (this.core.getKitManager().isKit(player, (Kit)this)) {
/* 154 */           if (this.cooldowns.containsKey(player.getName())) {
/* 155 */             long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 156 */             player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((5000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 157 */             evt.setCancelled(true);
/*     */           } else {
/* 159 */             Pig pig = (Pig)this.core.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PIG);
/* 160 */             this.core.getWorld().playSound(player.getLocation(), Sound.PIG_DEATH, 1.0F, 1.0F);
/* 161 */             pig.setVelocity(player.getLocation().getDirection().normalize().multiply(2.0D));
/*     */             
/* 163 */             EntityPig entityPig = ((CraftPig)pig).getHandle();
/*     */             
/*     */             try {
/* 166 */               Field pathfinderGoalSelectorField = EntityInsentient.class.getDeclaredField("goalSelector");
/* 167 */               pathfinderGoalSelectorField.setAccessible(true);
/* 168 */               PathfinderGoalSelector goalSelector = (PathfinderGoalSelector)pathfinderGoalSelectorField.get(entityPig);
/*     */               
/* 170 */               Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
/* 171 */               bField.setAccessible(true);
/* 172 */               List b = (List)bField.get(goalSelector);
/* 173 */               b.clear();
/* 174 */             } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/* 175 */               e.printStackTrace();
/*     */             } 
/*     */             
/* 178 */             pig.setMetadata("porkLauncher", (MetadataValue)new FixedMetadataValue((Plugin)this.core, player.getUniqueId().toString()));
/*     */             
/* 180 */             if (player.getItemInHand().getAmount() > 1) {
/* 181 */               player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
/*     */             } else {
/* 183 */               (new BukkitRunnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 187 */                     player.getInventory().remove(Material.PORK);
/*     */                   }
/* 189 */                 }).runTaskLater((Plugin)this.core, 1L);
/*     */             } 
/*     */             
/* 192 */             final String name = player.getName();
/* 193 */             this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 194 */             Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 198 */                     player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/* 199 */                     PigC4Kit.this.cooldowns.remove(name);
/*     */                   }
/*     */                 }, 
/* 202 */                 100L);
/*     */           } 
/*     */         }
/* 205 */       } else if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.BREWING_STAND_ITEM)) {
/* 206 */         for (Entity e : this.core.getWorld().getEntities()) {
/* 207 */           if (e instanceof Pig) {
/* 208 */             Pig pig = (Pig)e;
/* 209 */             if (pig.hasMetadata("porkLauncher")) {
/* 210 */               UUID uuid = UUID.fromString(((MetadataValue)pig.getMetadata("porkLauncher").get(0)).asString());
/* 211 */               Player shooter = Core.getNameFromUUID(uuid);
/* 212 */               if (shooter != null && player.getUniqueId().equals(shooter.getUniqueId())) {
/*     */                 
/* 214 */                 this.core.getWorld().playSound(pig.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
/*     */                 
/* 216 */                 boolean hit = false;
/*     */                 
/* 218 */                 List<Entity> nearbyEntities = pig.getNearbyEntities(5.0D, 5.0D, 5.0D);
/* 219 */                 for (Entity eDamageable : nearbyEntities) {
/* 220 */                   if (eDamageable instanceof Damageable) {
/* 221 */                     ((Damageable)eDamageable).damage(20.0D, (Entity)player);
/* 222 */                     if (!player.getUniqueId().equals(eDamageable.getUniqueId())) {
/* 223 */                       hit = true;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */                 
/* 228 */                 if (hit) {
/* 229 */                   player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */                 }
/*     */                 
/* 232 */                 pig.removeMetadata("porkLauncher", (Plugin)this.core);
/* 233 */                 pig.remove();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerQuitEvent(PlayerQuitEvent evt) {
/* 244 */     Player player = evt.getPlayer();
/* 245 */     if (this.core.getKitManager().isKit(player, (Kit)this)) {
/* 246 */       for (Entity e : this.core.getWorld().getEntities()) {
/* 247 */         if (e instanceof Pig) {
/* 248 */           Pig pig = (Pig)e;
/* 249 */           if (e.hasMetadata("porkLauncher")) {
/* 250 */             UUID uuid = UUID.fromString(((MetadataValue)pig.getMetadata("porkLauncher").get(0)).asString());
/* 251 */             if (player.getUniqueId().equals(uuid)) {
/* 252 */               e.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDeathEvent(PlayerDeathEvent evt) {
/* 262 */     Player player = evt.getEntity();
/* 263 */     if (this.core.getKitManager().isKit(player, (Kit)this))
/* 264 */       for (Entity e : this.core.getWorld().getEntities()) {
/* 265 */         if (e instanceof Pig) {
/* 266 */           Pig pig = (Pig)e;
/* 267 */           if (e.hasMetadata("porkLauncher")) {
/* 268 */             UUID uuid = UUID.fromString(((MetadataValue)pig.getMetadata("porkLauncher").get(0)).asString());
/* 269 */             if (player.getUniqueId().equals(uuid))
/* 270 */               e.remove(); 
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\PigC4Kit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */