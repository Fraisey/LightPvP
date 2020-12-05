/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.minecraft.server.v1_8_R3.EntityCreature;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*     */ import net.minecraft.server.v1_8_R3.EntityZombie;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoal;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class SummonerKit
/*     */   implements Listener, Kit {
/*     */   private Core core;
/*  55 */   private Random random = new Random();
/*     */   
/*  57 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  59 */   ItemStack[] content = new ItemStack[36];
/*  60 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public SummonerKit(Core core) {
/*  63 */     this.core = core;
/*     */     
/*  65 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  67 */     ItemStack summonTool = new ItemStack(Material.MONSTER_EGG, 1);
/*     */     
/*  69 */     this.content = new ItemStack[] { core.ironSwordSharpness, summonTool, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  70 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET) };
/*     */     
/*  72 */     for (int i = 0; i < this.armor.length; i++) {
/*  73 */       if (this.armor[i] != null) {
/*  74 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  76 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  77 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  78 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  85 */     return "Summoner";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  90 */     return "kit.summoner";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  95 */     return 65000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 100 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 102 */     p.getInventory().setContents(this.content);
/* 103 */     p.getInventory().setArmorContents(this.armor);
/* 104 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 109 */     return Material.MONSTER_EGG;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 114 */     return Arrays.asList(new String[] { ChatColor.GREEN + "Abilities:", ChatColor.GRAY + "Spawn a creature which is", ChatColor.GRAY + "your personal minion.", ChatColor.GRAY + "It will fight for you if", ChatColor.GRAY + "there are players around you!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 119 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEntityEvent(PlayerInteractEvent evt) {
/* 126 */     if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && 
/* 127 */       evt.getPlayer().getItemInHand().getType().equals(Material.MONSTER_EGG)) {
/* 128 */       final Player player = evt.getPlayer();
/* 129 */       if (this.core.getKitManager().isKit(player, this)) {
/* 130 */         if (this.cooldowns.containsKey(player.getName())) {
/* 131 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 132 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((15000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 133 */           evt.setCancelled(true);
/*     */         } else {
/* 135 */           int spawned = 0;
/*     */           
/* 137 */           for (Entity e : this.core.getWorld().getEntities()) {
/* 138 */             if (e instanceof Zombie) {
/* 139 */               Zombie zombie = (Zombie)e;
/* 140 */               if (zombie.hasMetadata("owner")) {
/* 141 */                 UUID ownerUUID = UUID.fromString(((MetadataValue)zombie.getMetadata("owner").get(0)).asString());
/* 142 */                 if (player.getUniqueId().equals(ownerUUID)) {
/* 143 */                   spawned++;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 149 */           if (spawned >= 2) {
/* 150 */             player.sendMessage(String.valueOf(ChatUtil.Gi) + "There" + ChatColor.GRAY + " are already 2 of your " + ChatUtil.Gi + "minions" + ChatColor.GRAY + " spawned that are still alive!");
/*     */             
/*     */             return;
/*     */           } 
/* 154 */           Zombie minion = (Zombie)this.core.getWorld().spawnEntity(evt.getClickedBlock().getLocation().add(0.0D, 1.0D, 0.0D), EntityType.ZOMBIE);
/*     */           
/* 156 */           minion.setCustomName(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + "'s minion");
/* 157 */           minion.setCustomNameVisible(true);
/*     */           
/* 159 */           minion.setMaxHealth(60.0D);
/* 160 */           minion.setHealth(60.0D);
/*     */           
/* 162 */           minion.setMetadata("owner", (MetadataValue)new FixedMetadataValue((Plugin)this.core, player.getUniqueId().toString()));
/*     */           
/* 164 */           minion.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 0));
/* 165 */           minion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 0));
/*     */           
/* 167 */           minion.setBaby(true);
/*     */           
/* 169 */           EntityZombie entityZombie = ((CraftZombie)minion).getHandle();
/* 170 */           entityZombie.setEquipment(0, CraftItemStack.asNMSCopy(new ItemStack(Material.WOOD_SWORD)));
/* 171 */           entityZombie.setEquipment(4, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_HELMET)));
/*     */           
/*     */           try {
/* 174 */             Field pathfinderGoalSelectorField = EntityInsentient.class.getDeclaredField("goalSelector");
/* 175 */             pathfinderGoalSelectorField.setAccessible(true);
/* 176 */             PathfinderGoalSelector goalSelector = (PathfinderGoalSelector)pathfinderGoalSelectorField.get(entityZombie);
/*     */             
/* 178 */             Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
/* 179 */             bField.setAccessible(true);
/* 180 */             List b = (List)bField.get(goalSelector);
/* 181 */             b.clear();
/*     */             
/* 183 */             goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)entityZombie));
/* 184 */             goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)entityZombie, EntityHuman.class, 1.0D, false));
/* 185 */           } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/* 186 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 189 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "Summoned" + ChatColor.GRAY + " minion!");
/*     */           
/* 191 */           final String name = player.getName();
/* 192 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 193 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 197 */                   SummonerKit.this.cooldowns.remove(name);
/* 198 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 201 */               300L);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 210 */     if (evt.getDamager() instanceof Zombie && evt.getEntity() instanceof Player) {
/* 211 */       Zombie damager = (Zombie)evt.getDamager();
/* 212 */       if (damager.isBaby() && 
/* 213 */         damager.hasMetadata("owner")) {
/* 214 */         String ownerUUID = ((MetadataValue)damager.getMetadata("owner").get(0)).asString();
/* 215 */         Player owner = Core.getNameFromUUID(UUID.fromString(ownerUUID));
/* 216 */         Player damaged = (Player)evt.getEntity();
/* 217 */         if (owner != null && 
/* 218 */           this.core.getKitManager().isKit(owner, this)) {
/* 219 */           if (!owner.getUniqueId().equals(damaged.getUniqueId())) {
/* 220 */             damaged.damage(10.0D, (Entity)owner);
/* 221 */             evt.setCancelled(true);
/* 222 */             if (((CraftPlayer)damaged).getHealth() <= 0.0D) {
/* 223 */               damager.removeMetadata("owner", (Plugin)this.core);
/* 224 */               damager.getWorld().playSound(damager.getLocation(), Sound.WITHER_HURT, 1.0F, 1.0F);
/*     */               
/* 226 */               damager.remove();
/*     */             } 
/*     */           } else {
/* 229 */             damager.setTarget(null);
/* 230 */             evt.setCancelled(true);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDeathEvent(EntityDeathEvent evt) {
/* 242 */     if (evt.getEntity() instanceof Zombie) {
/* 243 */       Zombie zombie = (Zombie)evt.getEntity();
/* 244 */       if (zombie.hasMetadata("owner")) {
/* 245 */         zombie.removeMetadata("owner", (Plugin)this.core);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent evt) {
/* 252 */     if (evt.getEntity() instanceof Zombie) {
/* 253 */       Zombie zombie = (Zombie)evt.getEntity();
/* 254 */       if (zombie.hasMetadata("owner")) {
/* 255 */         String ownerUUID = ((MetadataValue)zombie.getMetadata("owner").get(0)).asString();
/* 256 */         Player owner = Core.getNameFromUUID(UUID.fromString(ownerUUID));
/* 257 */         if (owner != null && evt.getTarget().getUniqueId().equals(owner.getUniqueId())) {
/* 258 */           Player target = null;
/*     */           
/* 260 */           if (this.random.nextInt(10) == 0) {
/* 261 */             List<Entity> nearbyEntities = owner.getNearbyEntities(10.0D, 10.0D, 10.0D);
/* 262 */             for (Entity e : nearbyEntities) {
/* 263 */               if (e instanceof Player) {
/* 264 */                 target = (Player)e;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 270 */           if (target != null) {
/* 271 */             evt.setTarget((Entity)target);
/*     */           } else {
/* 273 */             evt.setCancelled(true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\SummonerKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */