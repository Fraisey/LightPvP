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
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArenaBridges;
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArenaDefault;
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArenaMossy;
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArenaPillars;
/*     */ import net.lightpvp.java.managers.event.arenas.brackets.BracketsEventArenaSumo;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BracketsEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  37 */   private Random random = new Random();
/*     */   
/*  39 */   private BracketsState STATE = BracketsState.ROUND_CHANGE;
/*     */   
/*  41 */   private int round_pause_timer = 0;
/*  42 */   private final int ROUND_PAUSE_MAX = 12;
/*     */   
/*  44 */   private String player1 = "";
/*  45 */   private String player2 = "";
/*     */   
/*  47 */   private int duel_countdown_timer = 0;
/*  48 */   private final int DUEL_COUNTDOWN_MAX = 3;
/*     */   
/*  50 */   private int duel_timer = 0;
/*  51 */   private final int DUEL_TIMER_MAX = 50;
/*     */   
/*  53 */   private long last_round_change_timestamp = 0L;
/*     */   
/*     */   public BracketsEvent(HostedEventManager hostedEventManager) {
/*  56 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  58 */     this.arenas.add(new BracketsEventArenaDefault(hostedEventManager.getCore()));
/*  59 */     this.arenas.add(new BracketsEventArenaSumo(hostedEventManager.getCore()));
/*  60 */     this.arenas.add(new BracketsEventArenaPillars(hostedEventManager.getCore()));
/*  61 */     this.arenas.add(new BracketsEventArenaBridges(hostedEventManager.getCore()));
/*  62 */     this.arenas.add(new BracketsEventArenaMossy(hostedEventManager.getCore()));
/*     */     
/*  64 */     this.arena = this.arenas.get(0);
/*     */     
/*  66 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Brackets Menu");
/*     */     
/*  68 */     List<String> values = new ArrayList<>();
/*  69 */     for (EventArena arena : this.arenas) {
/*  70 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  73 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*     */     
/*  75 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  80 */     return "Brackets";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  85 */     return "event.brackets";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  90 */     return "Be the last one alive througout alot of 1v1s!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  95 */     return Material.BONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/* 100 */     return 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 108 */     for (Player p : this.players) {
/* 109 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/* 111 */       if (p.getVehicle() != null) {
/* 112 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 115 */       if (p.getPassenger() != null) {
/* 116 */         p.eject();
/*     */       }
/*     */       
/* 119 */       p.setFallDistance(0.0F);
/* 120 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 122 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 123 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 125 */       this.hostedEventManager.getCore().getKitManager().removeKit(p);
/* 126 */       Util.clearPlayer(p);
/*     */       
/* 128 */       p.updateInventory();
/*     */     } 
/*     */     
/* 131 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 135 */           for (Player p : BracketsEvent.this.players) {
/* 136 */             for (Player pO : BracketsEvent.this.players) {
/* 137 */               if (!p.getName().equals(pO.getName()) && 
/* 138 */                 p.canSee(pO)) {
/* 139 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 145 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 149 */                 for (Player p : (BracketsEvent.null.access$0(BracketsEvent.null.this)).players) {
/* 150 */                   for (Player pO : (BracketsEvent.null.access$0(BracketsEvent.null.this)).players) {
/* 151 */                     if (!p.getName().equals(pO.getName())) {
/* 152 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 158 */             }).runTaskLater((Plugin)BracketsEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 160 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 165 */     switch (this.STATE) {
/*     */       case ROUND_CHANGE:
/* 167 */         if (this.round_pause_timer >= 12) {
/* 168 */           nextDuel();
/*     */           
/* 170 */           this.round_pause_timer = 0;
/*     */           
/* 172 */           this.STATE = BracketsState.DUEL_COUNTDOWN; break;
/*     */         } 
/* 174 */         if (this.round_pause_timer == 2) {
/* 175 */           broadcastEvent(ChatColor.GRAY + "Next round starting in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/* 176 */         } else if (this.round_pause_timer + 3 >= 12) {
/* 177 */           broadcastEvent(ChatColor.GRAY + "Round is starting in " + ChatColor.GOLD + (12 - this.round_pause_timer) + ChatColor.GRAY + " seconds!");
/* 178 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         } 
/* 180 */         this.round_pause_timer++;
/*     */         break;
/*     */       
/*     */       case null:
/* 184 */         if (this.duel_countdown_timer >= 3) {
/* 185 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Fight!");
/*     */           
/* 187 */           broadcastEventSound(Sound.ANVIL_LAND);
/*     */ 
/*     */           
/* 190 */           this.duel_countdown_timer = 0;
/*     */           
/* 192 */           this.STATE = BracketsState.DUEL_WAIT; break;
/*     */         } 
/* 194 */         broadcastEvent(ChatColor.GRAY + "Duel is starting in " + ChatColor.GOLD + (3 - this.duel_countdown_timer) + ChatColor.GRAY + " seconds!");
/* 195 */         broadcastEventSound(Sound.NOTE_PLING);
/* 196 */         this.duel_countdown_timer++;
/*     */         break;
/*     */       
/*     */       case DUEL_WAIT:
/* 200 */         if (this.duel_timer >= 50) {
/* 201 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Times up, " + ChatColor.GRAY + "picking random winner!");
/* 202 */           broadcastEventSound(Sound.ANVIL_LAND);
/*     */           
/* 204 */           Player loser = Bukkit.getPlayerExact(this.random.nextBoolean() ? this.player1 : this.player2);
/*     */           
/* 206 */           loser.getLocation().getWorld().playSound(loser.getLocation(), Sound.EXPLODE, 3.0F, 1.0F);
/* 207 */           loser.damage(200.0D);
/*     */           
/* 209 */           this.duel_timer = 0; break;
/*     */         } 
/* 211 */         if (this.duel_timer == 25) {
/* 212 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Hurry up! " + ChatColor.GRAY + "Picking random " + ChatUtil.Gi + "winner" + ChatColor.GRAY + " in " + ChatColor.GOLD + (50 - this.duel_timer) + ChatColor.GRAY + " seconds!");
/* 213 */         } else if (this.duel_timer + 3 >= 50) {
/* 214 */           broadcastEvent(ChatColor.GRAY + "Duel is ending in " + ChatColor.GOLD + (50 - this.duel_timer) + ChatColor.GRAY + " seconds!");
/* 215 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         } 
/*     */         
/* 218 */         this.duel_timer++;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerLeave(Player player) {
/* 226 */     super.playerLeave(player);
/*     */     
/* 228 */     if (isArenaFight(player)) {
/* 229 */       Player winner = player.getName().equals(this.player1) ? Bukkit.getPlayerExact(this.player2) : Bukkit.getPlayerExact(this.player1);
/* 230 */       if (winner != null) {
/* 231 */         winner.setHealth(20.0D);
/* 232 */         winner.teleport(((BracketsEventArena)this.arena).getWinningRoom());
/* 233 */         Util.clearPlayer(winner);
/* 234 */         broadcastEvent(String.valueOf(RankUtil.getFullTag(winner)) + ChatColor.GRAY + " has won the " + ChatUtil.Gi + "fight!");
/*     */         
/* 236 */         this.player1 = "";
/* 237 */         this.player2 = "";
/*     */         
/* 239 */         this.duel_timer = 0;
/*     */       } else {
/* 241 */         broadcastEvent(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " has lost the " + ChatUtil.Gi + "fight!");
/*     */       } 
/*     */       
/* 244 */       if (this.players.size() <= 1) {
/* 245 */         if (this.players.size() == 1) {
/* 246 */           this.hostedEventManager.stopCurrentEvent(this.players.get(0));
/*     */         } else {
/* 248 */           this.hostedEventManager.stopCurrentEvent(null);
/*     */         } 
/*     */       } else {
/* 251 */         this.STATE = BracketsState.ROUND_CHANGE;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isArenaFight(Player player) {
/* 257 */     return !(!player.getName().equals(this.player1) && !player.getName().equals(this.player2));
/*     */   }
/*     */   
/*     */   public boolean getCanMove() {
/* 261 */     return (this.STATE != BracketsState.DUEL_COUNTDOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextDuel() {
/* 266 */     List<Player> playersInQueueRoom = new ArrayList<>();
/* 267 */     for (Player p : this.players) {
/* 268 */       if (p.getLocation().getBlockX() >= ((BracketsEventArena)this.arena).getQueueXBorder()) {
/* 269 */         playersInQueueRoom.add(p);
/*     */       }
/*     */     } 
/*     */     
/* 273 */     if (playersInQueueRoom.size() <= 1) {
/* 274 */       nextRound();
/*     */     } else {
/* 276 */       Player randomPlayer1 = playersInQueueRoom.get(this.random.nextInt(playersInQueueRoom.size()));
/* 277 */       playersInQueueRoom.remove(randomPlayer1);
/* 278 */       Player randomPlayer2 = playersInQueueRoom.get(this.random.nextInt(playersInQueueRoom.size()));
/*     */       
/* 280 */       this.player1 = randomPlayer1.getName();
/* 281 */       this.player2 = randomPlayer2.getName();
/*     */       
/* 283 */       randomPlayer1.teleport(((BracketsEventArena)this.arena).getArenaSpawn1());
/* 284 */       randomPlayer2.teleport(((BracketsEventArena)this.arena).getArenaSpawn2());
/*     */       
/* 286 */       randomPlayer1.getInventory().setContents(((BracketsEventArena)this.arena).getContent());
/* 287 */       randomPlayer2.getInventory().setContents(((BracketsEventArena)this.arena).getContent());
/*     */       
/* 289 */       randomPlayer1.getInventory().setArmorContents(((BracketsEventArena)this.arena).getArmor());
/* 290 */       randomPlayer2.getInventory().setArmorContents(((BracketsEventArena)this.arena).getArmor());
/*     */       
/* 292 */       randomPlayer1.updateInventory();
/* 293 */       randomPlayer2.updateInventory();
/*     */       
/* 295 */       broadcastEvent(String.valueOf(ChatUtil.Gi) + "Next Duel: " + RankUtil.getFullTag(randomPlayer1) + ChatUtil.Gi + " Vs " + RankUtil.getFullTag(randomPlayer2));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void nextRound() {
/* 300 */     long now = System.currentTimeMillis();
/*     */     
/* 302 */     if (this.players.size() < 2) {
/* 303 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 307 */     if (this.last_round_change_timestamp != 0L && now - this.last_round_change_timestamp <= 3000L) {
/* 308 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 312 */     this.last_round_change_timestamp = now;
/*     */     
/* 314 */     for (Player p : this.players) {
/* 315 */       if (p.getLocation().getBlockX() <= ((BracketsEventArena)this.arena).getQueueXBorder()) {
/* 316 */         p.setHealth(20.0D);
/* 317 */         p.teleport(((BracketsEventArena)this.arena).getQueueRoom());
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     broadcastEvent(ChatColor.GRAY + "Next " + ChatUtil.Gi + "round!");
/*     */     
/* 323 */     nextDuel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 328 */     this.STATE = BracketsState.ROUND_CHANGE;
/*     */     
/* 330 */     this.round_pause_timer = 0;
/*     */     
/* 332 */     this.player1 = "";
/* 333 */     this.player2 = "";
/*     */     
/* 335 */     this.duel_countdown_timer = 0;
/* 336 */     this.duel_timer = 0;
/*     */     
/* 338 */     this.last_round_change_timestamp = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 343 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 344 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 345 */       this.hostedEventManager.registerEvent(player, true, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\BracketsEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */