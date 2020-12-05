/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.redrover.RedRoverEventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.redrover.RedRoverEventArenaRedChamber;
/*     */ import net.lightpvp.java.managers.event.arenas.redrover.RedRoverEventArenaWoodHouse;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class RedRoverEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  36 */   private int side = 2;
/*     */   
/*  38 */   private RedRoverState STATE = RedRoverState.ROUND_PAUSE;
/*     */   
/*  40 */   private int round_pause_timer = 0;
/*  41 */   private final int ROUND_PAUSE_MAX = 12;
/*     */   
/*  43 */   private int round_timer = 0;
/*  44 */   private int round_max = 42;
/*     */   
/*  46 */   private UUID roverUUID = null;
/*  47 */   private List<String> reached = new ArrayList<>();
/*     */   
/*  49 */   private ItemStack limeWool = new ItemStack(Material.WOOL, 1, (short)5);
/*  50 */   private ItemStack redWool = new ItemStack(Material.WOOL, 1, (short)14);
/*     */   
/*  52 */   private ItemStack[] limeWoolHotBar = new ItemStack[] { this.limeWool, this.limeWool, this.limeWool, this.limeWool, this.limeWool, this.limeWool, this.limeWool, this.limeWool, this.limeWool };
/*  53 */   private ItemStack[] redWoolHotBar = new ItemStack[] { this.redWool, this.redWool, this.redWool, this.redWool, this.redWool, this.redWool, this.redWool, this.redWool, this.redWool };
/*     */   
/*     */   public RedRoverEvent(HostedEventManager hostedEventManager) {
/*  56 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  58 */     this.arenas.add(new RedRoverEventArenaRedChamber());
/*  59 */     this.arenas.add(new RedRoverEventArenaWoodHouse());
/*     */     
/*  61 */     this.arena = this.arenas.get(0);
/*     */     
/*  63 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Red Rover Menu");
/*     */     
/*  65 */     List<String> values = new ArrayList<>();
/*  66 */     for (EventArena arena : this.arenas) {
/*  67 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  70 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*     */     
/*  72 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  77 */     return "Red Rover";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  82 */     return "event.redrover";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  87 */     return "Get to the other side, without getting slained by the host!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  92 */     return Material.FLINT_AND_STEEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  97 */     return 25;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastQueue(String message) {
/* 104 */     for (UUID uuid : this.queue) {
/* 105 */       Player p = Core.getNameFromUUID(uuid);
/* 106 */       if (p != null) {
/* 107 */         p.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 115 */     for (Player p : this.players) {
/* 116 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/* 118 */       if (p.getVehicle() != null) {
/* 119 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 122 */       if (p.getPassenger() != null) {
/* 123 */         p.eject();
/*     */       }
/*     */       
/* 126 */       p.setFallDistance(0.0F);
/* 127 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 129 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 130 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 132 */       this.hostedEventManager.getCore().getKitManager().removeKit(p);
/*     */       
/* 134 */       Util.clearPlayer(p);
/*     */       
/* 136 */       setHotbar(p, this.redWoolHotBar);
/*     */       
/* 138 */       p.updateInventory();
/*     */     } 
/*     */     
/* 141 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 145 */           for (Player p : RedRoverEvent.this.players) {
/* 146 */             for (Player pO : RedRoverEvent.this.players) {
/* 147 */               if (!p.getName().equals(pO.getName()) && 
/* 148 */                 p.canSee(pO)) {
/* 149 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 155 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 159 */                 for (Player p : (RedRoverEvent.null.access$0(RedRoverEvent.null.this)).players) {
/* 160 */                   for (Player pO : (RedRoverEvent.null.access$0(RedRoverEvent.null.this)).players) {
/* 161 */                     if (!p.getName().equals(pO.getName())) {
/* 162 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 168 */             }).runTaskLater((Plugin)RedRoverEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 170 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastEvent(String message) {
/* 175 */     for (Player p : this.players) {
/* 176 */       if (p != null && p.isOnline()) {
/* 177 */         p.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastEventSound(Sound sound) {
/* 184 */     for (Player p : this.players) {
/* 185 */       if (p != null && p.isOnline()) {
/* 186 */         p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 193 */     if (this.players.size() <= 2) {
/* 194 */       if (this.players.size() == 2) {
/* 195 */         Player winner = null;
/* 196 */         int winnerIndex = 0;
/* 197 */         if (((Player)this.players.get(winnerIndex)).getUniqueId().equals(this.roverUUID)) {
/* 198 */           winnerIndex = 1;
/*     */         }
/* 200 */         winner = this.players.get(winnerIndex);
/* 201 */         this.hostedEventManager.stopCurrentEvent(winner);
/*     */       } else {
/* 203 */         this.hostedEventManager.stopCurrentEvent(null);
/*     */       } 
/*     */     }
/*     */     
/* 207 */     switch (this.STATE) {
/*     */       case null:
/* 209 */         if (this.round_pause_timer >= 12) {
/* 210 */           this.STATE = RedRoverState.ROUND_PLAY;
/* 211 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Go!");
/* 212 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 213 */           for (Player p : this.players) {
/* 214 */             if (!p.getUniqueId().equals(this.roverUUID))
/* 215 */               setHotbar(p, this.limeWoolHotBar); 
/*     */           } 
/*     */           break;
/*     */         } 
/* 219 */         if (this.round_pause_timer == 2) {
/* 220 */           broadcastEvent(ChatColor.GRAY + "Next round starting in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/* 221 */         } else if (this.round_pause_timer == 4) {
/* 222 */           broadcastEvent(ChatColor.GRAY + "Round time limit: " + ChatColor.GOLD + this.round_max + ChatColor.GRAY + " seconds");
/* 223 */         } else if (this.round_pause_timer == 6) {
/* 224 */           if (this.roverUUID == null) {
/* 225 */             pickRover();
/*     */           } else {
/* 227 */             Player roverPlayer = Core.getNameFromUUID(this.roverUUID);
/* 228 */             if (roverPlayer == null || !this.players.contains(roverPlayer)) {
/* 229 */               pickRover();
/*     */             }
/*     */           } 
/* 232 */         } else if (this.round_pause_timer >= 9) {
/* 233 */           broadcastEvent(ChatColor.GRAY + "Round is starting in " + ChatColor.GOLD + (12 - this.round_pause_timer) + ChatColor.GRAY + " seconds!");
/* 234 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         } 
/* 236 */         this.round_pause_timer++;
/*     */         break;
/*     */       
/*     */       case ROUND_PLAY:
/* 240 */         if (this.round_timer >= this.round_max) {
/* 241 */           endRound(); break;
/*     */         } 
/* 243 */         if (this.round_timer + 3 >= this.round_max) {
/* 244 */           broadcastEvent(ChatColor.GRAY + "Round is ending in " + ChatColor.GOLD + (this.round_max - this.round_timer) + ChatColor.GRAY + " seconds!");
/*     */         }
/* 246 */         this.round_timer++;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void pickRover() {
/* 253 */     Player roverPlayer = null;
/* 254 */     int triesLeft = 10;
/*     */     do {
/* 256 */       roverPlayer = this.players.get((new Random()).nextInt(this.players.size()));
/* 257 */       triesLeft--;
/* 258 */       if (triesLeft > 0)
/* 259 */         continue;  this.hostedEventManager.stopCurrentEvent(null);
/*     */     }
/* 261 */     while (roverPlayer == null);
/*     */     
/* 263 */     this.roverUUID = roverPlayer.getUniqueId();
/* 264 */     RedRoverEventArena cArena = (RedRoverEventArena)getArena();
/* 265 */     roverPlayer.teleport(cArena.getRoverSpot());
/* 266 */     roverPlayer.getInventory().clear();
/* 267 */     ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
/* 268 */     sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
/* 269 */     ItemStack stick = new ItemStack(Material.STICK);
/* 270 */     stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
/* 271 */     roverPlayer.getInventory().addItem(new ItemStack[] { sword });
/* 272 */     roverPlayer.getInventory().addItem(new ItemStack[] { stick });
/* 273 */     broadcastEvent(String.valueOf(RankUtil.getFullTag(roverPlayer)) + ChatColor.GRAY + " has been selected to be the " + ChatUtil.Gi + "Rover");
/*     */   }
/*     */   
/*     */   public void endRound() {
/* 277 */     String caught = ChatColor.GRAY + "These players didn't make it to the other side in time: " + ChatColor.GOLD;
/* 278 */     boolean first = true;
/* 279 */     for (int i = 0; i < this.players.size(); i++) {
/* 280 */       Player p = this.players.get(i);
/* 281 */       if (!this.reached.contains(p.getName()))
/* 282 */         if (!p.getUniqueId().equals(this.roverUUID)) {
/* 283 */           if (first) {
/* 284 */             caught = String.valueOf(caught) + p.getName();
/*     */           } else {
/* 286 */             caught = String.valueOf(caught) + ", " + p.getName();
/*     */           } 
/* 288 */           first = false;
/*     */           
/* 290 */           p.sendMessage(ChatColor.GRAY + "You didn't make it to the other " + ChatUtil.Gi + "side" + ChatColor.GRAY + " in time!");
/* 291 */           p.damage(20.0D);
/*     */         } else {
/*     */           
/* 294 */           p.setHealth(20.0D);
/*     */         }  
/*     */     } 
/* 297 */     if (first) {
/* 298 */       caught = String.valueOf(caught) + "None!";
/*     */     }
/*     */     
/* 301 */     broadcastEvent(caught);
/* 302 */     broadcastEventSound(Sound.NOTE_PLING);
/* 303 */     this.reached.clear();
/*     */     
/* 305 */     this.round_timer = 0;
/* 306 */     if (this.round_max > 10) {
/* 307 */       this.round_max -= 8;
/*     */     }
/*     */     
/* 310 */     this.side = (this.side == 1) ? 2 : 1;
/*     */     
/* 312 */     this.round_pause_timer = 0;
/* 313 */     this.STATE = RedRoverState.ROUND_PAUSE;
/*     */   }
/*     */   public void handleMovement(String regionID, Player player) {
/*     */     String str;
/* 317 */     switch ((str = regionID).hashCode()) { case -924392024: if (!str.equals("rr1ph1")) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 329 */         if (this.roverUUID.equals(player.getUniqueId()))
/* 330 */           player.teleport(player.getLocation().subtract(-1.0D, 0.0D, 0.0D));  break;
/*     */       case -924392023:
/*     */         if (!str.equals("rr1ph2"))
/*     */           break; 
/* 334 */         if (this.roverUUID.equals(player.getUniqueId()))
/* 335 */           player.teleport(player.getLocation().subtract(1.0D, 0.0D, 0.0D));  break;case 108728178: if (!str.equals("rr1p1")) break;  if (this.reached.contains(player.getName()) || this.STATE.equals(RedRoverState.ROUND_PAUSE))
/*     */           player.teleport(player.getLocation().subtract(1.0D, 0.0D, 0.0D));  break;case 108728179: if (!str.equals("rr1p2"))
/*     */           break;  if (this.reached.contains(player.getName()) || this.STATE.equals(RedRoverState.ROUND_PAUSE))
/*     */           player.teleport(player.getLocation().subtract(-1.0D, 0.0D, 0.0D));  break;case 108728271: if (!str.equals("rr1s1"))
/* 339 */           break;  if (this.side == 1 && this.STATE.equals(RedRoverState.ROUND_PLAY) && 
/* 340 */           !this.reached.contains(player.getName())) {
/* 341 */           this.reached.add(player.getName());
/* 342 */           player.sendMessage(ChatColor.GRAY + "You got " + ChatUtil.Gi + "lucky" + ChatColor.GRAY + " this time!");
/* 343 */           player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
/* 344 */           setHotbar(player, this.redWoolHotBar);
/* 345 */           player.setHealth(20.0D);
/*     */           
/* 347 */           if (this.reached.size() >= this.players.size() - 1)
/* 348 */             endRound(); 
/*     */         } 
/*     */         break;
/*     */       case 108728272:
/*     */         if (!str.equals("rr1s2"))
/*     */           break; 
/* 354 */         if (this.side == 2 && this.STATE.equals(RedRoverState.ROUND_PLAY) && 
/* 355 */           !this.reached.contains(player.getName())) {
/* 356 */           this.reached.add(player.getName());
/* 357 */           player.sendMessage(ChatColor.GRAY + "You got " + ChatUtil.Gi + "lucky" + ChatColor.GRAY + " this time!");
/* 358 */           player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
/* 359 */           setHotbar(player, this.redWoolHotBar);
/* 360 */           player.setHealth(20.0D);
/*     */           
/* 362 */           if (this.reached.size() >= this.players.size() - 1) {
/* 363 */             endRound();
/*     */           }
/*     */         } 
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHotbar(Player player, ItemStack[] itemStackArray) {
/* 372 */     player.getInventory().setContents((ItemStack[])itemStackArray.clone());
/*     */   }
/*     */   
/*     */   public UUID getRover() {
/* 376 */     return this.roverUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 381 */     this.side = 2;
/*     */     
/* 383 */     this.STATE = RedRoverState.ROUND_PAUSE;
/* 384 */     this.round_pause_timer = 0;
/*     */     
/* 386 */     this.round_timer = 0;
/* 387 */     this.round_max = 42;
/*     */     
/* 389 */     this.roverUUID = null;
/*     */     
/* 391 */     this.reached.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 396 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 397 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 398 */       this.hostedEventManager.registerEvent(player, true, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\RedRoverEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */