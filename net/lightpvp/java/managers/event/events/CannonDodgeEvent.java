/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.cannondodge.CannonDodgeEventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.cannondodge.CannonDodgeEventArenaCartoon;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.TNTPrimed;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CannonDodgeEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  37 */   private int isProtectionTime = 15;
/*     */   
/*     */   private boolean isProtection = true;
/*  40 */   private int seconds = 1;
/*  41 */   private Random random = new Random();
/*     */   
/*  43 */   private ItemStack[] content = new ItemStack[] { new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 1) };
/*  44 */   private ItemStack[] armor = new ItemStack[] { new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
/*     */   
/*     */   public CannonDodgeEvent(HostedEventManager hostedEventManager) {
/*  47 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  49 */     this.arena = (EventArena)new CannonDodgeEventArenaCartoon();
/*     */     
/*  51 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Cannon Dodge Menu");
/*     */     
/*  53 */     List<String> values = new ArrayList<>();
/*  54 */     for (EventArena arena : this.arenas) {
/*  55 */       values.add(arena.getName());
/*     */     }
/*  57 */     this.menu.addSelector(0, ChatColor.GOLD + "Mode", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select mode!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "mode", Material.MAP, Arrays.asList(new String[] { "Spectate", "Play" }));
/*     */     
/*  59 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */     
/*  61 */     this.content[0].addEnchantment(Enchantment.ARROW_INFINITE, 1);
/*     */     
/*  63 */     for (int i = 0; i < this.armor.length; i++) {
/*  64 */       if (this.armor[i] != null) {
/*  65 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */       }
/*     */     } 
/*     */     
/*  69 */     this.armor[0].addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
/*  70 */     this.armor[3].addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return "Cannon Dodge";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  80 */     return "event.cannondodge";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  85 */     return "Dodge the arrows and be the last one standing!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  90 */     return Material.TNT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  95 */     return 25;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 103 */     for (Player p : this.players) {
/* 104 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/* 106 */       if (p.getVehicle() != null) {
/* 107 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 110 */       if (p.getPassenger() != null) {
/* 111 */         p.eject();
/*     */       }
/*     */       
/* 114 */       p.setFallDistance(0.0F);
/* 115 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 117 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 118 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 120 */       this.hostedEventManager.getCore().getKitManager().removeKit(p);
/*     */       
/* 122 */       Util.clearPlayer(p);
/*     */       
/* 124 */       p.getInventory().setContents(this.content);
/* 125 */       p.getInventory().setArmorContents(this.armor);
/*     */       
/* 127 */       p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 7));
/* 128 */       p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 1));
/* 129 */       p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2147483647, 0));
/*     */       
/* 131 */       p.updateInventory();
/*     */     } 
/*     */     
/* 134 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 138 */           for (Player p : CannonDodgeEvent.this.players) {
/* 139 */             for (Player pO : CannonDodgeEvent.this.players) {
/* 140 */               if (!p.getName().equals(pO.getName()) && 
/* 141 */                 p.canSee(pO)) {
/* 142 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 148 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 152 */                 for (Player p : (CannonDodgeEvent.null.access$0(CannonDodgeEvent.null.this)).players) {
/* 153 */                   for (Player pO : (CannonDodgeEvent.null.access$0(CannonDodgeEvent.null.this)).players) {
/* 154 */                     if (!p.getName().equals(pO.getName())) {
/* 155 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 161 */             }).runTaskLater((Plugin)CannonDodgeEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 163 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 168 */     if (this.players.size() <= 1) {
/* 169 */       if (this.players.size() == 1) {
/* 170 */         Player winner = this.players.get(0);
/* 171 */         this.hostedEventManager.stopCurrentEvent(winner);
/*     */         return;
/*     */       } 
/* 174 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 179 */     if (this.isProtection) {
/* 180 */       switch (this.isProtectionTime) {
/*     */         case 15:
/* 182 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 10:
/* 185 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 3:
/* 188 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 189 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 192 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 193 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 196 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " seconds!");
/* 197 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 200 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Fight!");
/* 201 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 202 */           this.isProtection = false; return;
/*     */       } 
/* 204 */       this
/* 205 */         .isProtectionTime = this.isProtectionTime - 1;
/*     */     } else {
/* 207 */       if (this.seconds % 20 == 0) {
/* 208 */         launchTNTinRing(this.random.nextInt(2) + 1);
/* 209 */       } else if ((this.seconds + 10) % 20 == 0) {
/* 210 */         launchTNTatPlayer(this.players.get(this.random.nextInt(this.players.size())));
/*     */       } 
/*     */       
/* 213 */       this.seconds++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getIsProtection() {
/* 218 */     return this.isProtection;
/*     */   }
/*     */   
/*     */   private void launchTNT(Vector vel, int fuseTicks) {
/* 222 */     Location launcherLocation = ((CannonDodgeEventArena)this.arena).getCannon();
/* 223 */     TNTPrimed tnt = (TNTPrimed)this.hostedEventManager.getCore().getWorld().spawn(launcherLocation, TNTPrimed.class);
/* 224 */     tnt.setVelocity(vel);
/* 225 */     tnt.setFuseTicks(fuseTicks);
/* 226 */     launcherLocation.getWorld().playSound(launcherLocation, Sound.EXPLODE, 3.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void launchTNTatPlayer(final Player player) {
/* 230 */     broadcastEvent(String.valueOf(ChatUtil.Gi) + "Cannon" + ChatColor.GRAY + " has targeted " + RankUtil.getFullTag(player) + ChatColor.GRAY + ", shooting in " + ChatColor.GOLD + "5" + ChatColor.GRAY + " seconds!");
/*     */     
/* 232 */     (new BukkitRunnable()
/*     */       {
/* 234 */         int timer = 8;
/*     */ 
/*     */         
/*     */         public void run() {
/* 238 */           if (this.timer > 0) {
/* 239 */             Location cannonLoc = ((CannonDodgeEventArena)CannonDodgeEvent.this.arena).getCannon();
/* 240 */             Location targetLoc = player.getLocation();
/* 241 */             double d = cannonLoc.distance(targetLoc);
/*     */             
/* 243 */             if (d >= 50.0D) {
/* 244 */               cancel();
/*     */             }
/*     */             
/* 247 */             double g = -0.06D;
/* 248 */             double t = d;
/* 249 */             double v_x = 0.043D * t * (targetLoc.getX() - cannonLoc.getX()) / t;
/* 250 */             double v_y = (0.3D + 0.035D * t) * (targetLoc.getY() - cannonLoc.getY()) / t - 0.4D * g * t;
/* 251 */             double v_z = 0.043D * t * (targetLoc.getZ() - cannonLoc.getZ()) / t;
/*     */             
/* 253 */             CannonDodgeEvent.this.launchTNT(new Vector(v_x, v_y, v_z), (int)t);
/*     */           } else {
/* 255 */             cancel();
/*     */           } 
/*     */           
/* 258 */           this.timer--;
/*     */         }
/* 260 */       }).runTaskTimer((Plugin)this.hostedEventManager.getCore(), 100L, 5L);
/*     */   }
/*     */   
/*     */   private void launchTNTinRing(int ring) {
/* 264 */     final float yVel, FULL_RADIAN = 6.2831855F;
/* 265 */     float TOTAL_STEPS = 16.0F;
/*     */ 
/*     */     
/* 268 */     if (ring == 1) {
/* 269 */       yVel = 1.0F;
/* 270 */       broadcastEvent(ChatColor.GRAY + "Firing a ring at the bottommost " + ChatUtil.Gi + "ring" + ChatColor.GRAY + ", in " + ChatColor.GOLD + "5" + ChatColor.GRAY + " seconds!");
/*     */     } else {
/* 272 */       yVel = 1.5F;
/* 273 */       broadcastEvent(ChatColor.GRAY + "Firing a ring at the upper " + ChatUtil.Gi + "ring" + ChatColor.GRAY + ", in " + ChatColor.GOLD + "5" + ChatColor.GRAY + " seconds!");
/*     */     } 
/*     */ 
/*     */     
/* 277 */     (new BukkitRunnable() {
/* 278 */         float radian = 0.0F;
/*     */ 
/*     */         
/*     */         public void run() {
/* 282 */           if (this.radian < 6.2831855F) {
/* 283 */             CannonDodgeEvent.this.launchTNT(new Vector(Math.cos(this.radian), yVel, Math.sin(this.radian)), 50);
/*     */           } else {
/* 285 */             cancel();
/*     */           } 
/*     */           
/* 288 */           this.radian += 0.3926991F;
/*     */         }
/* 290 */       }).runTaskTimer((Plugin)this.hostedEventManager.getCore(), 100L, 5L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 295 */     this.isProtectionTime = 15;
/* 296 */     this.isProtection = true;
/* 297 */     this.seconds = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 302 */     if (this.hostedEventManager.canRegisterEvent(player, this))
/* 303 */       this.hostedEventManager.registerEvent(player, (((Integer)data.get("mode")).intValue() == 1), this); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\CannonDodgeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */