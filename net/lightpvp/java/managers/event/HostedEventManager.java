/*     */ package net.lightpvp.java.managers.event;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.duel.DuelData;
/*     */ import net.lightpvp.java.managers.event.events.BracketsEvent;
/*     */ import net.lightpvp.java.managers.event.events.CannonDodgeEvent;
/*     */ import net.lightpvp.java.managers.event.events.FootballEvent;
/*     */ import net.lightpvp.java.managers.event.events.HostedEvent;
/*     */ import net.lightpvp.java.managers.event.events.LMSHostedEvent;
/*     */ import net.lightpvp.java.managers.event.events.OneManArmyEvent;
/*     */ import net.lightpvp.java.managers.event.events.RedRoverEvent;
/*     */ import net.lightpvp.java.managers.event.events.SpeedrunHostedEvent;
/*     */ import net.lightpvp.java.managers.kit.kits.SeparatorKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import net.lightpvp.java.utils.fancymessage.FancyMessage;
/*     */ import net.lightpvp.java.utils.hologram.TemporaryHologram;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HostedEventManager
/*     */ {
/*     */   private Core core;
/*  42 */   private HostedEvent[] hostedEvents = null;
/*  43 */   private HostedEvent currentEvent = null;
/*     */   
/*  45 */   private Inventory eventMenu = null;
/*     */   
/*  47 */   private int currentGameDuration = 0;
/*  48 */   private final int MAX_GAME_DURATION = 1800;
/*     */   
/*  50 */   private final int MIN_PLAYERS_TO_HOST = 6;
/*  51 */   private final int MIN_PLAYERS_TO_START = 4;
/*  52 */   private final int EVENT_COOLDOWN = 5;
/*  53 */   private long eventCooldown = 0L;
/*     */   
/*     */   public HostedEventManager(Core core) {
/*  56 */     this.core = core;
/*     */     
/*  58 */     this.hostedEvents = new HostedEvent[] { (HostedEvent)new LMSHostedEvent(this), (HostedEvent)new SpeedrunHostedEvent(this), (HostedEvent)new RedRoverEvent(this), (HostedEvent)new CannonDodgeEvent(this), (HostedEvent)new OneManArmyEvent(this), (HostedEvent)new BracketsEvent(this), (HostedEvent)new FootballEvent(this) };
/*     */     
/*  60 */     this.eventMenu = Bukkit.createInventory(null, 9, ChatColor.BLUE + "HostedEvent Menu"); byte b; int i; HostedEvent[] arrayOfHostedEvent;
/*  61 */     for (i = (arrayOfHostedEvent = this.hostedEvents).length, b = 0; b < i; ) { HostedEvent he = arrayOfHostedEvent[b];
/*  62 */       ItemStack item = new ItemStack(he.getIcon());
/*  63 */       ItemMeta meta = item.getItemMeta();
/*  64 */       meta.setDisplayName(ChatColor.GREEN + he.getName());
/*  65 */       meta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + he.getDescription() }));
/*  66 */       item.setItemMeta(meta);
/*  67 */       this.eventMenu.addItem(new ItemStack[] { item });
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public HostedEvent[] getHostedEvents() {
/*  72 */     return this.hostedEvents;
/*     */   }
/*     */   
/*     */   public HostedEvent getCurrentEvent() {
/*  76 */     return this.currentEvent;
/*     */   }
/*     */   
/*     */   public Inventory getEventMenu() {
/*  80 */     return this.eventMenu;
/*     */   }
/*     */   
/*     */   public void openSubMenu(Player player, HostedEvent hostedEvent) {
/*  84 */     player.openInventory(hostedEvent.getMenu());
/*     */   }
/*     */   
/*     */   public boolean canRegisterEvent(Player player, HostedEvent event) {
/*  88 */     long now = System.currentTimeMillis();
/*  89 */     if (player.hasPermission("core.event")) {
/*  90 */       if (player.hasPermission(event.getListedName())) {
/*  91 */         if (now > this.eventCooldown || player.hasPermission("core.bypass")) {
/*  92 */           if (this.currentEvent == null) {
/*  93 */             if (Bukkit.getOnlinePlayers().size() >= 6 || player.hasPermission("core.bypass")) {
/*  94 */               return true;
/*     */             }
/*  96 */             player.sendMessage(ChatColor.RED + "There have to be atleast " + '\006' + " players online to host an event!");
/*     */           } else {
/*     */             
/*  99 */             player.sendMessage(ChatColor.RED + "There is already an event being hosted!");
/*     */           } 
/*     */         } else {
/* 102 */           player.sendMessage(ChatColor.RED + "There can only be one event hosted per " + '\005' + " minutes! (" + ((int)(this.eventCooldown - now) / 1000 / 60 + 1) + " minutes left)");
/*     */         } 
/*     */       } else {
/* 105 */         player.sendMessage(ChatColor.RED + "You don't have enough permission to host " + ChatUtil.Gi + event.getName());
/*     */       } 
/*     */     } else {
/* 108 */       player.sendMessage(ChatColor.RED + "Only " + ChatColor.BLUE + "VETERAN" + ChatColor.RED + "(and above) users can host events!");
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public void registerEvent(Player player, boolean play, HostedEvent hostedEvent) {
/* 114 */     this.currentEvent = hostedEvent;
/*     */     
/* 116 */     if (play) {
/* 117 */       this.currentEvent.getQueue().add(player.getUniqueId());
/*     */     } else {
/* 119 */       hostedEvent.setHoster(player.getUniqueId());
/*     */     } 
/*     */     
/* 122 */     (new FancyMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is hosting " + ChatUtil.Gi + hostedEvent.getName() + "(" + hostedEvent.getArena().getName() + ")" + ChatColor.GRAY + " Type " + ChatColor.GOLD + "/join" + ChatColor.GRAY + " to join the queue, or click ")).then(ChatColor.GOLD + "here!").tooltip(ChatColor.GREEN + "Click to join the queue!").command("/join").sendAll();
/*     */   }
/*     */   
/*     */   public void startCurrentEvent(boolean force) {
/* 126 */     int playerCount = 0;
/* 127 */     for (UUID uuid : this.currentEvent.getQueue()) {
/* 128 */       Player p = Core.getNameFromUUID(uuid);
/* 129 */       if (p != null && p.isOnline() && !p.isDead()) {
/* 130 */         DuelData dd = this.core.getDuelManager().getDuelData(p);
/* 131 */         if (dd != null) {
/* 132 */           if (dd.getDuel() != null || SeparatorKit.in1v1.contains(p.getName())) {
/* 133 */             p.sendMessage(ChatColor.GRAY + "You weren't teleported to the event, because you're " + ChatUtil.Gi + "dueling!");
/*     */             
/*     */             continue;
/*     */           } 
/* 137 */           this.core.getDuelManager().removeDuelData(p);
/*     */         } 
/*     */         
/* 140 */         playerCount++;
/* 141 */         this.currentEvent.getPlayers().add(p);
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     Collections.shuffle(this.currentEvent.getPlayers());
/*     */     
/* 147 */     if (!force && 
/* 148 */       playerCount < 4) {
/* 149 */       ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " was cancelled because not enough players joined the queue! (" + '\004' + " Players)");
/* 150 */       this.eventCooldown = System.currentTimeMillis() + 180000L;
/* 151 */       stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 156 */     if (force) {
/* 157 */       ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " has been force started!");
/*     */     } else {
/* 159 */       ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " has started!");
/*     */     } 
/*     */     
/* 162 */     Player host = Core.getNameFromUUID(this.currentEvent.getHostUUID());
/* 163 */     if (host != null) {
/* 164 */       User hostUser = this.core.getUser(host.getUniqueId());
/* 165 */       if (!this.currentEvent.getQueue().contains(this.currentEvent.getHostUUID())) {
/* 166 */         host.teleport(this.currentEvent.getArena().getSpawn());
/* 167 */         this.core.setSpawnFlag(hostUser, false);
/* 168 */         this.core.removeWarpFlag(hostUser);
/* 169 */         Util.clearPlayer(host);
/* 170 */         this.core.getKitManager().removeKit(host);
/* 171 */         host.setAllowFlight(true);
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     this.currentEvent.getArena().getSpawn().getChunk().load();
/*     */     
/* 177 */     this.currentEvent.setIsStarted(true);
/* 178 */     this.currentEvent.start();
/* 179 */     this.currentEvent.getQueue().clear();
/*     */     
/* 181 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 185 */           if (HostedEventManager.this.currentEvent != null) {
/* 186 */             (new TemporaryHologram(HostedEventManager.this.core, Arrays.asList(new String[] { ChatColor.GOLD + "~" + ChatColor.BLUE + ChatColor.BOLD + "LightPvP" + ChatColor.RESET + ChatColor.GOLD + "~", ChatColor.GOLD + "- " + HostedEventManager.access$0(this.this$0).getName() + " Events -", ChatColor.DARK_RED + ChatColor.BOLD + HostedEventManager.access$0(this.this$0).getPlayers().size() + ChatColor.GOLD + ChatColor.BOLD + " Players" }))).show(HostedEventManager.this.currentEvent.getPlayers(), HostedEventManager.this.currentEvent.getArena().getHologramLocation(), 200);
/*     */           }
/*     */         }
/* 189 */       }).runTaskLater((Plugin)this.core, 20L);
/*     */   }
/*     */   
/*     */   public void updateCurrentEvent() {
/* 193 */     if (this.currentEvent != null) {
/* 194 */       if (!this.currentEvent.getStarted()) {
/* 195 */         if (this.currentEvent.getStartTime() < 1) {
/* 196 */           startCurrentEvent(false);
/* 197 */           Util.broadcastSound(Sound.ANVIL_LAND);
/*     */         } else {
/* 199 */           switch (this.currentEvent.getStartTime()) {
/*     */             case 30:
/* 201 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "30" + ChatColor.GRAY + " seconds! Type " + ChatColor.GOLD + "/join" + ChatColor.GRAY + " to join the queue!");
/*     */               break;
/*     */             case 15:
/* 204 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds! Type " + ChatColor.GOLD + "/join" + ChatColor.GRAY + " to join the queue!");
/*     */               break;
/*     */             case 10:
/* 207 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds! Type " + ChatColor.GOLD + "/join" + ChatColor.GRAY + " to join the queue!");
/*     */               break;
/*     */             case 5:
/* 210 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "5" + ChatColor.GRAY + " seconds!");
/*     */               break;
/*     */             case 4:
/* 213 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "4" + ChatColor.GRAY + " seconds!");
/*     */               break;
/*     */             case 3:
/* 216 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 217 */               Util.broadcastSound(Sound.NOTE_PLING);
/*     */               break;
/*     */             case 2:
/* 220 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 221 */               Util.broadcastSound(Sound.NOTE_PLING);
/*     */               break;
/*     */             case 1:
/* 224 */               ChatUtil.broadcastMessage(String.valueOf(ChatUtil.Gi) + this.currentEvent.getName() + ChatColor.GRAY + " is starting in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " seconds!");
/* 225 */               Util.broadcastSound(Sound.NOTE_PLING);
/*     */               break;
/*     */           } 
/* 228 */           this.currentEvent.setStartTime(this.currentEvent.getStartTime() - 1);
/*     */         }
/*     */       
/* 231 */       } else if (this.currentGameDuration < 1800) {
/* 232 */         this.currentGameDuration++;
/* 233 */         this.currentEvent.update();
/*     */       } else {
/* 235 */         this.currentEvent.broadcastEvent(ChatColor.GRAY + "Maximum game length reached, force stopping the event!");
/* 236 */         stopCurrentEvent(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopCurrentEvent(Player winner) {
/* 243 */     this.currentGameDuration = 0;
/*     */     
/* 245 */     if (winner != null) {
/* 246 */       int coins = 3000;
/*     */       
/* 248 */       boolean doubleCoins = winner.hasPermission("core.doublecoins");
/* 249 */       if (doubleCoins) {
/* 250 */         coins *= 2;
/*     */       }
/*     */       
/* 253 */       ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(winner)) + ChatColor.GRAY + " has won " + ChatUtil.Gi + this.currentEvent.getName() + ChatColor.GRAY + " and earned " + ChatColor.GOLD + coins + (doubleCoins ? "(x2)" : "") + ChatColor.GRAY + " coins!");
/* 254 */       this.core.getPlayerStatsManager().incrementInt(winner.getUniqueId(), coins, PlayerStat.COINS);
/*     */     } 
/*     */     
/* 257 */     final List<Player> toSpawnList = new ArrayList<>(this.currentEvent.getPlayers());
/* 258 */     toSpawnList.add(Core.getNameFromUUID(this.currentEvent.getHostUUID()));
/* 259 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 263 */           for (Player p : toSpawnList) {
/* 264 */             if (p != null && p.isOnline()) {
/* 265 */               User user = HostedEventManager.this.core.getUser(p.getUniqueId());
/* 266 */               if (!HostedEventManager.this.core.hasSpawnFlag(user)) {
/* 267 */                 HostedEventManager.this.core.toSpawn(user, true);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/* 272 */       }).runTaskLater((Plugin)this.core, 100L);
/*     */     
/* 274 */     this.currentEvent.setHoster(new UUID(0L, 0L));
/*     */     
/* 276 */     this.currentEvent.setIsStarted(false);
/* 277 */     this.currentEvent.setStartTime(60);
/* 278 */     this.currentEvent.getQueue().clear();
/* 279 */     this.currentEvent.getPlayers().clear();
/*     */     
/* 281 */     this.currentEvent.reset();
/*     */     
/* 283 */     this.currentEvent = null;
/*     */     
/* 285 */     this.eventCooldown = System.currentTimeMillis() + 300000L;
/*     */   }
/*     */   
/*     */   public Core getCore() {
/* 289 */     return this.core;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\HostedEventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */