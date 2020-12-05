/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.football.FootballEventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.football.FootballEventArenaFrostedStatium;
/*     */ import net.lightpvp.java.managers.event.arenas.football.FootballEventArenaStadium;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*     */ import net.minecraft.server.v1_8_R3.EntitySlime;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.EntityEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Slime;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FootballEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  53 */   private int maxPlayers = 22;
/*     */   
/*  55 */   private int startedTime = 15;
/*     */   
/*     */   private boolean started = false;
/*     */   
/*     */   private boolean canMove = true;
/*  60 */   private Slime ball = null;
/*  61 */   private Vector previousBallVelocity = null;
/*     */   
/*  63 */   private int pauseBetweenGoalsTime = 15;
/*     */   
/*     */   private boolean pauseBetweenGoals = false;
/*  66 */   private UUID lastKicker = null;
/*     */   
/*  68 */   private List<Player> blueTeamPlayers = new ArrayList<>();
/*  69 */   private List<Player> redTeamPlayers = new ArrayList<>();
/*     */   
/*  71 */   private int blueTeamScore = 0, redTeamScore = 0;
/*     */   
/*     */   public FootballEvent(HostedEventManager hostedEventManager) {
/*  74 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  76 */     this.arenas.add(new FootballEventArenaStadium());
/*  77 */     this.arenas.add(new FootballEventArenaFrostedStatium());
/*     */     
/*  79 */     this.arena = this.arenas.get(0);
/*     */     
/*  81 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Football Menu");
/*     */     
/*  83 */     List<String> values = new ArrayList<>();
/*  84 */     for (EventArena arena : this.arenas) {
/*  85 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  88 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*     */     
/*  90 */     this.menu.addSelector(1, ChatColor.GOLD + "Mode", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select mode!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "mode", Material.MAP, Arrays.asList(new String[] { "Spectate", "Play" }));
/*     */     
/*  92 */     this.menu.addSelector(2, ChatColor.GOLD + "Players", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select player count!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "players", Material.SIGN, Arrays.asList(new String[] { "11v11", "8v8", "5v5", "3v3", "2v2" }));
/*     */     
/*  94 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return "Football";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/* 104 */     return "event.football";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 109 */     return "There are 2 teams, first team to score wins!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/* 114 */     return Material.SLIME_BALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/* 119 */     return this.maxPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 127 */     for (Player p : this.players) {
/* 128 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/* 130 */       if (p.getVehicle() != null) {
/* 131 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 134 */       if (p.getPassenger() != null) {
/* 135 */         p.eject();
/*     */       }
/*     */       
/* 138 */       p.setFallDistance(0.0F);
/* 139 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 141 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 142 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 144 */       Util.clearPlayer(p);
/*     */       
/* 146 */       p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
/*     */       
/* 148 */       p.updateInventory();
/*     */     } 
/*     */     
/* 151 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 155 */           for (Entity e : FootballEvent.this.hostedEventManager.getCore().getWorld().getEntities()) {
/* 156 */             if (e instanceof Slime) {
/* 157 */               e.remove();
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 162 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 20L);
/*     */     
/* 164 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 168 */           for (Player p : FootballEvent.this.players) {
/* 169 */             for (Player pO : FootballEvent.this.players) {
/* 170 */               if (!p.getName().equals(pO.getName()) && 
/* 171 */                 p.canSee(pO)) {
/* 172 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 178 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 182 */                 for (Player p : (FootballEvent.null.access$0(FootballEvent.null.this)).players) {
/* 183 */                   for (Player pO : (FootballEvent.null.access$0(FootballEvent.null.this)).players) {
/* 184 */                     if (!p.getName().equals(pO.getName())) {
/* 185 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 191 */             }).runTaskLater((Plugin)FootballEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 193 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 198 */     if (this.players.size() == 1 || this.players.size() == 0) {
/* 199 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 203 */     if (this.blueTeamScore >= 5) {
/* 204 */       for (Player p : this.blueTeamPlayers) {
/* 205 */         Util.spawnFireworks(p.getLocation());
/* 206 */         this.hostedEventManager.getCore().getPlayerStatsManager().incrementInt(p.getUniqueId(), 500, PlayerStat.COINS);
/*     */       } 
/*     */       
/* 209 */       ChatUtil.broadcastMessage(ChatColor.BLUE + "BLUE" + ChatColor.GRAY + " team won the " + ChatUtil.Gi + "Football " + ChatColor.GRAY + "event and all the players on the winning team won " + ChatColor.GOLD + "500 coins!");
/* 210 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 214 */     if (this.redTeamScore >= 5) {
/* 215 */       for (Player p : this.redTeamPlayers) {
/* 216 */         Util.spawnFireworks(p.getLocation());
/* 217 */         this.hostedEventManager.getCore().getPlayerStatsManager().incrementInt(p.getUniqueId(), 500, PlayerStat.COINS);
/*     */       } 
/*     */       
/* 220 */       ChatUtil.broadcastMessage(ChatColor.RED + "RED" + ChatColor.GRAY + " team won the " + ChatUtil.Gi + "Football " + ChatColor.GRAY + "event and all the players on the winning team won " + ChatColor.GOLD + "500 coins!");
/* 221 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 225 */     if (this.pauseBetweenGoals)
/* 226 */       switch (this.pauseBetweenGoalsTime) {
/*     */         case 10:
/* 228 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Continuing" + ChatColor.GRAY + " in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */         
/*     */         case 8:
/* 231 */           positionTeams();
/*     */         
/*     */         case 7:
/* 234 */           this.ball.teleport(((FootballEventArena)this.arena).getBallSpawn());
/* 235 */           this.ball.setVelocity(new Vector(0, 0, 0));
/*     */         
/*     */         case 3:
/* 238 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Continuing" + ChatColor.GRAY + " in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 239 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 2:
/* 242 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Continuing" + ChatColor.GRAY + " in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 243 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 1:
/* 246 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Continuing" + ChatColor.GRAY + " in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 247 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 0:
/* 250 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Continue!");
/* 251 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 252 */           this.canMove = true;
/*     */           
/* 254 */           this.pauseBetweenGoals = false; break;
/*     */         default:
/* 256 */           this
/* 257 */             .pauseBetweenGoalsTime = this.pauseBetweenGoalsTime - 1;
/*     */           break;
/*     */       }  
/* 260 */     if (!this.started) {
/* 261 */       switch (this.startedTime) {
/*     */         case 15:
/* 263 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 13:
/* 266 */           selectTeams();
/*     */           break;
/*     */         case 10:
/* 269 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 8:
/* 272 */           gearTeams();
/* 273 */           positionTeams();
/*     */           break;
/*     */         case 7:
/* 276 */           spawnBall();
/*     */           break;
/*     */         case 3:
/* 279 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 280 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 283 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 284 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 287 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 288 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 291 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Play" + ChatColor.GRAY + ", first team with 5 goals wins!");
/* 292 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 293 */           this.started = true;
/* 294 */           this.canMove = true; return;
/*     */       } 
/* 296 */       this
/* 297 */         .startedTime = this.startedTime - 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectTeams() {
/* 302 */     int playerIndex = 0;
/*     */     
/* 304 */     for (Player p : this.players) {
/* 305 */       if (playerIndex % 2 == 0) {
/* 306 */         p.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " are in team: " + ChatColor.BLUE + ChatColor.BOLD + "BLUE!");
/* 307 */         this.blueTeamPlayers.add(p);
/*     */       } else {
/* 309 */         p.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " are in team: " + ChatColor.RED + ChatColor.BOLD + "RED!");
/* 310 */         this.redTeamPlayers.add(p);
/*     */       } 
/*     */       
/* 313 */       playerIndex++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void gearTeams() {
/* 318 */     ItemStack yellowBoots = new ItemStack(Material.LEATHER_BOOTS);
/* 319 */     LeatherArmorMeta yellowBootsMeta = (LeatherArmorMeta)yellowBoots.getItemMeta();
/* 320 */     yellowBootsMeta.setDisplayName(String.valueOf(ChatUtil.Gi) + "Football Boots");
/* 321 */     yellowBootsMeta.setColor(Color.YELLOW);
/* 322 */     yellowBoots.setItemMeta((ItemMeta)yellowBootsMeta);
/*     */ 
/*     */     
/* 325 */     ItemStack blueTeamChest = new ItemStack(Material.LEATHER_CHESTPLATE);
/* 326 */     LeatherArmorMeta blueTeamChestMeta = (LeatherArmorMeta)blueTeamChest.getItemMeta();
/* 327 */     blueTeamChestMeta.setDisplayName(String.valueOf(ChatUtil.Gi) + "Football T-Shirt");
/* 328 */     blueTeamChestMeta.setColor(Color.BLUE);
/* 329 */     blueTeamChest.setItemMeta((ItemMeta)blueTeamChestMeta);
/*     */     
/* 331 */     ItemStack[] blueTeamArmor = { yellowBoots, blueTeamChest };
/* 332 */     for (Player p : this.blueTeamPlayers) {
/* 333 */       p.getInventory().setArmorContents(blueTeamArmor);
/*     */     }
/*     */ 
/*     */     
/* 337 */     ItemStack redTeamChest = new ItemStack(Material.LEATHER_CHESTPLATE);
/* 338 */     LeatherArmorMeta redTeamChestMeta = (LeatherArmorMeta)redTeamChest.getItemMeta();
/* 339 */     redTeamChestMeta.setDisplayName(String.valueOf(ChatUtil.Gi) + "Football T-Shirt");
/* 340 */     redTeamChestMeta.setColor(Color.RED);
/* 341 */     redTeamChest.setItemMeta((ItemMeta)redTeamChestMeta);
/*     */     
/* 343 */     ItemStack[] redTeamArmor = { yellowBoots, redTeamChest };
/* 344 */     for (Player p : this.redTeamPlayers) {
/* 345 */       p.getInventory().setArmorContents(redTeamArmor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void positionTeams() {
/* 351 */     Location blueGoalLocation = ((FootballEventArena)this.arena).getBlueGoal();
/*     */     
/* 353 */     for (Player p : this.blueTeamPlayers) {
/* 354 */       p.teleport(blueGoalLocation);
/*     */     }
/*     */ 
/*     */     
/* 358 */     Location redGoalLocation = ((FootballEventArena)this.arena).getRedGoal();
/*     */     
/* 360 */     for (Player p : this.redTeamPlayers) {
/* 361 */       p.teleport(redGoalLocation);
/*     */     }
/*     */     
/* 364 */     this.canMove = false;
/*     */   }
/*     */   
/*     */   public void spawnBall() {
/* 368 */     if (this.ball != null && !this.ball.isDead()) {
/* 369 */       this.ball.remove();
/*     */     }
/*     */     
/* 372 */     this.ball = (Slime)this.hostedEventManager.getCore().getWorld().spawnEntity(((FootballEventArena)this.arena).getBallSpawn(), EntityType.SLIME);
/* 373 */     this.ball.setRemoveWhenFarAway(false);
/* 374 */     this.ball.setSize(1);
/* 375 */     this.ball.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, -3));
/*     */     
/* 377 */     EntitySlime entitySlime = ((CraftSlime)this.ball).getHandle();
/*     */     
/*     */     try {
/* 380 */       Field pathfinderGoalSelectorField = EntityInsentient.class.getDeclaredField("goalSelector");
/* 381 */       pathfinderGoalSelectorField.setAccessible(true);
/* 382 */       PathfinderGoalSelector goalSelector = (PathfinderGoalSelector)pathfinderGoalSelectorField.get(entitySlime);
/*     */       
/* 384 */       Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
/* 385 */       bField.setAccessible(true);
/* 386 */       List b = (List)bField.get(goalSelector);
/* 387 */       b.clear();
/* 388 */     } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/* 389 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 392 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 396 */           if (FootballEvent.this.ball != null && !FootballEvent.this.ball.isDead()) {
/* 397 */             FootballEvent.this.updateBall();
/*     */           } else {
/* 399 */             cancel();
/*     */           } 
/*     */         }
/* 402 */       }).runTaskTimer((Plugin)this.hostedEventManager.getCore(), 1L, 1L);
/*     */   }
/*     */   
/*     */   public void updateBall() {
/* 406 */     Vector previousVelocity = this.ball.getVelocity();
/*     */     
/* 408 */     if (this.previousBallVelocity != null) {
/* 409 */       previousVelocity = this.previousBallVelocity;
/*     */     }
/*     */     
/* 412 */     Vector currentVelocity = this.ball.getVelocity();
/*     */     
/* 414 */     if (currentVelocity.getX() == 0.0D) {
/* 415 */       currentVelocity.setX(-previousVelocity.getX() * 0.8D);
/* 416 */       if (Math.abs(previousVelocity.getX()) > 0.3D)
/* 417 */         this.ball.getWorld().playSound(this.ball.getLocation(), Sound.SLIME_WALK, 1.0F, 1.0F); 
/* 418 */     } else if (Math.abs(previousVelocity.getX() - currentVelocity.getX()) < 0.1D) {
/* 419 */       currentVelocity.setX(previousVelocity.getX() * 0.98D);
/*     */     } 
/*     */     
/* 422 */     if (currentVelocity.getY() == -0.0784000015258789D && previousVelocity.getY() < -0.1D) {
/* 423 */       currentVelocity.setY(-previousVelocity.getY() * 0.8D);
/* 424 */       if (Math.abs(previousVelocity.getY()) > 0.3D) {
/* 425 */         this.ball.getWorld().playSound(this.ball.getLocation(), Sound.SLIME_WALK, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */     
/* 429 */     if (currentVelocity.getZ() == 0.0D) {
/* 430 */       currentVelocity.setZ(-previousVelocity.getZ() * 0.8D);
/* 431 */       if (Math.abs(previousVelocity.getZ()) > 0.3D)
/* 432 */         this.ball.getWorld().playSound(this.ball.getLocation(), Sound.SLIME_WALK, 1.0F, 1.0F); 
/* 433 */     } else if (Math.abs(previousVelocity.getZ() - currentVelocity.getZ()) < 0.1D) {
/* 434 */       currentVelocity.setZ(previousVelocity.getZ() * 0.98D);
/*     */     } 
/*     */     
/* 437 */     this.ball.playEffect(EntityEffect.HURT);
/*     */     
/* 439 */     this.ball.setVelocity(currentVelocity);
/*     */     
/* 441 */     if (!this.pauseBetweenGoals) {
/* 442 */       if (this.ball.getLocation().toVector().isInAABB(((FootballEventArena)this.arena).getBlueGoalRegionMin(), ((FootballEventArena)this.arena).getBlueGoalRegionMax())) {
/* 443 */         scoreGoal("RED");
/* 444 */       } else if (this.ball.getLocation().toVector().isInAABB(((FootballEventArena)this.arena).getRedGoalRegionMin(), ((FootballEventArena)this.arena).getRedGoalRegionMax())) {
/* 445 */         scoreGoal("BLUE");
/*     */       } 
/*     */     }
/*     */     
/* 449 */     this.previousBallVelocity = currentVelocity;
/*     */   }
/*     */   
/*     */   public void scoreGoal(String teamScored) {
/* 453 */     final Player scored = Core.getNameFromUUID(this.lastKicker);
/*     */     
/* 455 */     boolean ownGoal = false;
/*     */     
/* 457 */     if (teamScored.equals("BLUE")) {
/* 458 */       this.blueTeamScore++;
/*     */       
/* 460 */       if (scored != null && this.redTeamPlayers.contains(scored)) {
/* 461 */         ownGoal = true;
/*     */       }
/* 463 */     } else if (teamScored.equals("RED")) {
/* 464 */       this.redTeamScore++;
/*     */       
/* 466 */       if (scored != null && this.blueTeamPlayers.contains(scored)) {
/* 467 */         ownGoal = true;
/*     */       }
/*     */     } 
/*     */     
/* 471 */     this.pauseBetweenGoalsTime = 15;
/* 472 */     this.pauseBetweenGoals = true;
/*     */     
/* 474 */     broadcastEvent(String.valueOf(teamScored.equals("BLUE") ? (ChatColor.BLUE + "BLUE") : (ChatColor.RED + "RED")) + ChatColor.GRAY + " team scored" + (ownGoal ? (" (" + ChatUtil.Gi + "Own Goal" + ChatColor.GRAY + ")") : "") + "!\n" + ChatColor.GREEN + "Goalscorer: " + ((scored != null) ? RankUtil.getFullTag(scored) : "") + "\n" + ChatColor.GREEN + "Score: " + ChatColor.BLUE + this.blueTeamScore + ChatColor.GREEN + " - " + ChatColor.RED + this.redTeamScore);
/* 475 */     broadcastEventSound(Sound.NOTE_PLING);
/*     */     
/* 477 */     if (!ownGoal) {
/* 478 */       (new BukkitRunnable()
/*     */         {
/* 480 */           int count = 10;
/*     */ 
/*     */           
/*     */           public void run() {
/* 484 */             if (this.count >= 0) {
/* 485 */               Util.spawnFireworks(scored.getLocation());
/* 486 */               this.count--;
/*     */             } else {
/* 488 */               cancel();
/*     */             } 
/*     */           }
/* 491 */         }).runTaskTimer((Plugin)this.hostedEventManager.getCore(), 0L, 6L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 497 */     this.maxPlayers = 22;
/*     */     
/* 499 */     this.startedTime = 15;
/* 500 */     this.started = false;
/*     */     
/* 502 */     this.canMove = true;
/*     */     
/* 504 */     this.ball.remove();
/* 505 */     this.ball = null;
/*     */     
/* 507 */     this.pauseBetweenGoalsTime = 15;
/* 508 */     this.pauseBetweenGoals = false;
/*     */     
/* 510 */     this.previousBallVelocity = null;
/*     */     
/* 512 */     this.blueTeamPlayers.clear();
/* 513 */     this.redTeamPlayers.clear();
/*     */     
/* 515 */     this.blueTeamScore = 0;
/* 516 */     this.redTeamScore = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerLeave(Player player) {
/* 521 */     super.playerLeave(player);
/*     */     
/* 523 */     this.blueTeamPlayers.remove(player);
/* 524 */     this.redTeamPlayers.remove(player);
/*     */   }
/*     */   
/*     */   public boolean isStarted() {
/* 528 */     return this.started;
/*     */   }
/*     */   
/*     */   public boolean getCanMove() {
/* 532 */     return this.canMove;
/*     */   }
/*     */   
/*     */   public Slime getBall() {
/* 536 */     return this.ball;
/*     */   }
/*     */   
/*     */   public boolean isPauseBetweenGoals() {
/* 540 */     return this.pauseBetweenGoals;
/*     */   }
/*     */   
/*     */   public UUID getLastKicker() {
/* 544 */     return this.lastKicker;
/*     */   }
/*     */   
/*     */   public void setLastKicker(UUID lastKicker) {
/* 548 */     this.lastKicker = lastKicker;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 553 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 554 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/*     */       
/* 556 */       switch (((Integer)data.get("players")).intValue()) {
/*     */         case 0:
/* 558 */           this.maxPlayers = 22;
/*     */           break;
/*     */         case 1:
/* 561 */           this.maxPlayers = 16;
/*     */           break;
/*     */         case 2:
/* 564 */           this.maxPlayers = 10;
/*     */           break;
/*     */         case 3:
/* 567 */           this.maxPlayers = 6;
/*     */           break;
/*     */         case 4:
/* 570 */           this.maxPlayers = 4;
/*     */           break;
/*     */       } 
/*     */       
/* 574 */       this.hostedEventManager.registerEvent(player, (((Integer)data.get("mode")).intValue() == 1), this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\FootballEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */