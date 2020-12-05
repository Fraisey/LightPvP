/*      */ package net.lightpvp.java.listener;
/*      */ 
/*      */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*      */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.lightpvp.java.Core;
/*      */ import net.lightpvp.java.SurpriseChest;
/*      */ import net.lightpvp.java.User;
/*      */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*      */ import net.lightpvp.java.managers.duel.DuelData;
/*      */ import net.lightpvp.java.managers.duel.DuelGame;
/*      */ import net.lightpvp.java.managers.event.events.BracketsEvent;
/*      */ import net.lightpvp.java.managers.event.events.FootballEvent;
/*      */ import net.lightpvp.java.managers.event.events.HostedEvent;
/*      */ import net.lightpvp.java.managers.event.events.RedRoverEvent;
/*      */ import net.lightpvp.java.managers.event.events.SpeedrunHostedEvent;
/*      */ import net.lightpvp.java.managers.kit.Kit;
/*      */ import net.lightpvp.java.managers.kit.SpecialKit;
/*      */ import net.lightpvp.java.utils.ChatUtil;
/*      */ import net.lightpvp.java.utils.InventoryUtil;
/*      */ import net.lightpvp.java.utils.RankUtil;
/*      */ import net.lightpvp.java.utils.Util;
/*      */ import net.lightpvp.java.utils.fancymessage.FancyMessage;
/*      */ import net.lightpvp.java.utils.hologram.PermanentHologram;
/*      */ import net.lightpvp.java.utils.hologram.TemporaryHologram;
/*      */ import net.minecraft.server.v1_8_R3.Entity;
/*      */ import net.minecraft.server.v1_8_R3.Packet;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Sound;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.block.Sign;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.entity.Slime;
/*      */ import org.bukkit.event.EventHandler;
/*      */ import org.bukkit.event.EventPriority;
/*      */ import org.bukkit.event.Listener;
/*      */ import org.bukkit.event.block.Action;
/*      */ import org.bukkit.event.block.BlockBreakEvent;
/*      */ import org.bukkit.event.block.BlockPlaceEvent;
/*      */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*      */ import org.bukkit.event.entity.PlayerDeathEvent;
/*      */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*      */ import org.bukkit.event.hanging.HangingBreakEvent;
/*      */ import org.bukkit.event.player.PlayerDropItemEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEvent;
/*      */ import org.bukkit.event.player.PlayerJoinEvent;
/*      */ import org.bukkit.event.player.PlayerLoginEvent;
/*      */ import org.bukkit.event.player.PlayerMoveEvent;
/*      */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*      */ import org.bukkit.event.player.PlayerQuitEvent;
/*      */ import org.bukkit.event.player.PlayerRespawnEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.metadata.FixedMetadataValue;
/*      */ import org.bukkit.metadata.MetadataValue;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.potion.PotionEffect;
/*      */ import org.bukkit.potion.PotionEffectType;
/*      */ import org.bukkit.scheduler.BukkitRunnable;
/*      */ import org.bukkit.scoreboard.DisplaySlot;
/*      */ import org.bukkit.scoreboard.Objective;
/*      */ import org.bukkit.scoreboard.Scoreboard;
/*      */ import org.bukkit.util.Vector;
/*      */ import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
/*      */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CorePlayerListener
/*      */   implements Listener
/*      */ {
/*      */   private Core core;
/*   95 */   private Random random = new Random();
/*      */ 
/*      */   
/*      */   private boolean banned = false;
/*      */ 
/*      */   
/*      */   double prevYVelocity;
/*      */ 
/*      */   
/*      */   double prevYChange;
/*      */ 
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerLoginEvent(PlayerLoginEvent evt) {
/*  109 */     Player player = evt.getPlayer();
/*      */     
/*  111 */     User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  113 */     if (user.isBanned()) {
/*      */       
/*  115 */       if (user.getBanExpiry() == -1L) {
/*  116 */         evt.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You are banned!\nYou can buy an unban at buy.lightpvp.net");
/*      */         
/*      */         return;
/*      */       } 
/*  120 */       Date banExpiryDate = new Date(user.getBanExpiry());
/*  121 */       Date currentDate = new Date();
/*  122 */       if (currentDate.before(banExpiryDate)) {
/*  123 */         evt.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You are banned until " + banExpiryDate.toString() + "\nYou can buy an unban at buy.lightpvp.net");
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  128 */     if (Bukkit.getOnlinePlayers().size() > Bukkit.getMaxPlayers() && 
/*  129 */       PermissionsEx.getUser(player.getUniqueId().toString()).getGroups()[0].getName().equals("Guest")) {
/*  130 */       evt.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.RED + "Server is full, buy a premium slot at buy.lightpvp.net!");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  135 */     user.setPlayer(player);
/*  136 */     this.core.loadUser(user);
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler(priority = EventPriority.HIGHEST)
/*      */   public void onPlayerJoinEvent(PlayerJoinEvent evt) {
/*  142 */     Bukkit.getLogger().info("Test1");
/*      */     
/*  144 */     Player player = evt.getPlayer();
/*  145 */     if (player.getName().equalsIgnoreCase("Chemmic")) {
/*  146 */       byte b; int i; SpecialKit[] arrayOfSpecialKit; for (i = (arrayOfSpecialKit = this.core.getKitManager().getSpecialKitArray()).length, b = 0; b < i; ) { SpecialKit sk = arrayOfSpecialKit[b];
/*  147 */         this.core.getKitManager().addSpecialKit(player, (Kit)sk);
/*      */         b++; }
/*      */     
/*      */     } 
/*  151 */     final User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  153 */     this.core.updatePlayerDecorations(player);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  158 */     if (Util.validIP(player.getName())) {
/*  159 */       Bukkit.getLogger().info("Test2");
/*      */       
/*  161 */       List<String> names = this.core.getPlayerStatsManager().getNamesFromIP(player.getName());
/*  162 */       if (names != null) {
/*  163 */         for (String name : names) {
/*  164 */           Bukkit.getLogger().info("Test3");
/*  165 */           String uuidString = this.core.getPlayerStatsManager().getUUIDFromName(name);
/*  166 */           UUID uuid = null;
/*  167 */           User user1 = null;
/*      */           
/*  169 */           if (uuidString != null) {
/*  170 */             uuid = UUID.fromString(uuidString);
/*  171 */             Bukkit.getLogger().info("Test4");
/*      */           } 
/*      */           
/*  174 */           if (uuid != null) {
/*  175 */             user1 = this.core.getUser(uuid);
/*  176 */             Bukkit.getLogger().info("Test5");
/*      */           } 
/*      */           
/*  179 */           if (user1.isBanned() || user1.getBanExpiry() == -1L) {
/*  180 */             this.banned = true;
/*  181 */             Bukkit.getLogger().info("Test7");
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  187 */     if (this.banned) {
/*  188 */       for (Player p : Bukkit.getOnlinePlayers()) {
/*  189 */         if (p.hasPermission("core.report")) {
/*  190 */           Bukkit.getLogger().info("Test8");
/*  191 */           p.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.RED + " has alt accounts that are banned/temp-banned! Watch out for them!");
/*      */         } 
/*      */       } 
/*  194 */       this.banned = false;
/*      */     } 
/*      */     
/*  197 */     boolean firstJoin = this.core.getPlayerStatsManager().handlePlayerJoin(player);
/*      */     
/*  199 */     if (firstJoin) {
/*  200 */       Bukkit.getLogger().info("Test10");
/*  201 */       ChatUtil.broadcastMessage(ChatColor.GRAY + "Welcome " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + ChatColor.BOLD + ", to " + ChatColor.BLUE + "LightPvP!");
/*      */     } else {
/*  203 */       String pattern = ChatColor.GOLD + "*" + ChatColor.DARK_RED + "-+-";
/*  204 */       Bukkit.getLogger().info("Test11");
/*  205 */       player.sendMessage(String.valueOf(pattern) + pattern + pattern + pattern + pattern + pattern + pattern + ChatColor.GOLD + "*" + "\n" + ChatColor.DARK_RED + " ~ " + ChatColor.GOLD + ChatColor.ITALIC + "Welcome back to LightPvP! " + ChatColor.DARK_RED + "~\n" + pattern + pattern + pattern + pattern + pattern + pattern + pattern + ChatColor.GOLD + "*");
/*      */     } 
/*      */     
/*  208 */     if (!PermissionsEx.getUser(player).getGroups()[0].getName().equals("Guest") && user.isJoinMessage()) {
/*  209 */       Bukkit.getLogger().info("Test13");
/*  210 */       ChatUtil.broadcastMessage("\n " + RankUtil.getFullTag(player) + ChatColor.GRAY + " joined the game!");
/*      */     } 
/*      */     
/*  213 */     Scoreboard playerScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
/*      */     
/*  215 */     int coins = firstJoin ? 500 : this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "coins");
/*  216 */     int kills = this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "kills");
/*  217 */     int deaths = this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "deaths");
/*  218 */     int maxkillstreak = this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "maxkillstreak");
/*  219 */     int duelwins = this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "duelwins");
/*      */     
/*  221 */     Objective pObjectiveStats = playerScoreboard.registerNewObjective("stats", "dummy");
/*  222 */     pObjectiveStats.setDisplayName(ChatColor.BLUE + ChatColor.BOLD + "LightPvP");
/*  223 */     pObjectiveStats.setDisplaySlot(DisplaySlot.SIDEBAR);
/*  224 */     pObjectiveStats.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).setScore(coins);
/*  225 */     pObjectiveStats.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Kills:")).setScore(kills);
/*  226 */     pObjectiveStats.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Deaths:")).setScore(deaths);
/*  227 */     pObjectiveStats.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Max KS:")).setScore(maxkillstreak);
/*  228 */     pObjectiveStats.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "1v1 Wins:")).setScore(duelwins);
/*      */     
/*  230 */     player.setScoreboard(playerScoreboard);
/*  231 */     Bukkit.getLogger().info("Test20");
/*  232 */     if (user == null) {
/*  233 */       player.kickPlayer(ChatColor.RED + "Relog for your own safety!");
/*  234 */       Bukkit.getLogger().info("Test15");
/*      */     } 
/*      */     
/*  237 */     if (user.getKit().isEmpty() && user.getWarp().isEmpty() && !this.core.hasSpawnFlag(user)) {
/*  238 */       (new BukkitRunnable()
/*      */         {
/*      */           public void run()
/*      */           {
/*  242 */             Bukkit.getLogger().info("Test16");
/*  243 */             CorePlayerListener.this.core.toSpawn(user, true);
/*      */           }
/*  245 */         }).runTaskLater((Plugin)this.core, 3L);
/*      */     }
/*  247 */     else if (player.getWalkSpeed() != 0.2D) {
/*  248 */       player.setWalkSpeed(0.2F);
/*      */     } 
/*      */ 
/*      */     
/*  252 */     (new FancyMessage(ChatColor.GREEN + ChatColor.BOLD + "Wanna know what's new? " + ChatColor.GOLD + ChatColor.BOLD + "/whatsnew " + ChatColor.GREEN + ChatColor.BOLD + "or click ")).then(ChatColor.GOLD + ChatColor.BOLD + "here!").tooltip(ChatColor.GREEN + "Click to see what's new!").command("/whatsnew").send(player);
/*  253 */     Bukkit.getLogger().info("Test55");
/*  254 */     Location startLocation = player.getLocation().add(player.getLocation().getDirection().multiply(10).add(new Vector(0, 2, 0)));
/*  255 */     List<Location> particleLocations = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     Bukkit.getLogger().info("Test04000");
/*      */     
/*  306 */     evt.setJoinMessage(null);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerQuitEvent(PlayerQuitEvent evt) {
/*  311 */     Player player = evt.getPlayer();
/*  312 */     User user = this.core.removeUser(player.getUniqueId());
/*      */     
/*  314 */     evt.setQuitMessage(null);
/*      */     
/*  316 */     String killerName = user.isCombatLog();
/*  317 */     if (killerName != null) {
/*  318 */       Player killer = Bukkit.getPlayerExact(killerName);
/*      */       
/*  320 */       if (killer != null && killer.isOnline()) {
/*  321 */         killer.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " earned " + ChatColor.GOLD + "200" + ChatColor.GRAY + " coins for killing " + RankUtil.getFullTag(player));
/*  322 */         this.core.getPlayerStatsManager().incrementInt(killer.getUniqueId(), 200, PlayerStat.COINS);
/*  323 */         this.core.getPlayerStatsManager().incrementInt(killer.getUniqueId(), 1, PlayerStat.KILLS);
/*  324 */         this.core.getPlayerStatsManager().incrementInt(player.getUniqueId(), 1, PlayerStat.DEATHS);
/*      */       } 
/*      */     } 
/*      */     
/*  328 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  329 */       HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  330 */       if (ev.containsPlayer(player)) {
/*  331 */         ev.playerLeave(player);
/*      */       }
/*      */     } 
/*      */     
/*  335 */     if (this.core.getDuelManager().isInDuelArena(player)) {
/*  336 */       this.core.getDuelManager().removeDuelData(player);
/*      */     }
/*      */     
/*  339 */     user.unload();
/*  340 */     this.core.saveUser(user);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.NORMAL)
/*      */   public void onPlayerDeathEvent(PlayerDeathEvent evt) {
/*  345 */     final Player player = evt.getEntity();
/*  346 */     Player killer = player.getKiller();
/*      */     
/*  348 */     User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  350 */     user.setWarp("");
/*      */     
/*  352 */     User killerUser = null;
/*      */     
/*  354 */     player.eject();
/*      */     
/*  356 */     user.untag();
/*      */     
/*  358 */     this.core.getKitManager().removeKit(player);
/*  359 */     evt.setDroppedExp(0);
/*  360 */     evt.getDrops().clear();
/*  361 */     evt.setDeathMessage(null);
/*      */ 
/*      */     
/*  364 */     (new BukkitRunnable()
/*      */       {
/*      */         public void run()
/*      */         {
/*  368 */           (((CraftPlayer)player).getHandle()).playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
/*      */         }
/*  378 */       }).runTaskLater((Plugin)this.core, 4L);
/*      */ 
/*      */     
/*  381 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  382 */       HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  383 */       if (ev.containsPlayer(player)) {
/*  384 */         ev.playerLeave(player);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  389 */     this.core.getPlayerStatsManager().incrementInt(player.getUniqueId(), 1, PlayerStat.DEATHS);
/*      */     
/*  391 */     player.removeMetadata("perk", (Plugin)this.core);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  407 */     if (killer != null) {
/*  408 */       this.core.getPlayerStatsManager().incrementInt(killer.getUniqueId(), 1, PlayerStat.KILLS);
/*      */     }
/*      */ 
/*      */     
/*  412 */     int playerKillstreak = user.getKillstreak();
/*  413 */     int killerKillstreak = 0;
/*      */     
/*  415 */     if (killer != null && killer.isOnline() && !player.getName().equals(killer.getName())) {
/*  416 */       killerUser = this.core.getUser(killer.getUniqueId());
/*  417 */       killerKillstreak = killerUser.getKillstreak();
/*      */       
/*  419 */       if (playerKillstreak >= 3) {
/*  420 */         ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(killer)) + ChatColor.GRAY + " has ended " + RankUtil.getFullTag(player) + ChatColor.GRAY + "'s killstreak of " + ChatColor.GOLD + playerKillstreak);
/*      */       }
/*      */       
/*  423 */       killerKillstreak = killerUser.incrementKillstreakAndGet();
/*  424 */       killer.setLevel(killerKillstreak);
/*      */       
/*  426 */       this.core.checkKillstreak(killer, killerKillstreak);
/*      */       
/*  428 */       if (killerKillstreak > this.core.getPlayerStatsManager().getInt(killer.getUniqueId(), "maxkillstreak")) {
/*  429 */         this.core.getPlayerStatsManager().setInt(killer.getUniqueId(), PlayerStat.MAXKILLSTREAK, killerKillstreak);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  434 */     if (this.core.getDuelManager().isInDuelArena(player)) {
/*  435 */       DuelGame dg = this.core.getDuelManager().isDueling(player);
/*  436 */       if (dg != null) {
/*  437 */         this.core.getDuelManager().stopDuel(dg, player.getName().equals(dg.getPlayer1().getName()) ? dg.getPlayer2() : dg.getPlayer1());
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  443 */     int coins = Math.min(playerKillstreak * 15 + killerKillstreak * 15 + 60, 400);
/*  444 */     if (killer != null && killer instanceof Player && 
/*  445 */       !player.getName().equals(killer.getName())) {
/*  446 */       killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
/*      */       
/*  448 */       boolean doubleCoins = killer.hasPermission("core.doublecoins");
/*  449 */       if (doubleCoins) {
/*  450 */         coins *= 2;
/*      */       }
/*      */       
/*  453 */       this.core.getPlayerStatsManager().incrementInt(killer.getUniqueId(), coins, PlayerStat.COINS);
/*  454 */       killer.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " earned " + ChatColor.GOLD + coins + (doubleCoins ? "(x2)" : "") + ChatColor.GRAY + " coins for killing " + RankUtil.getFullTag(player));
/*      */       
/*  456 */       Kit kitKiller = this.core.getKitManager().getKit(killer);
/*  457 */       player.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " were killed by " + RankUtil.getFullTag(killer) + " " + ChatColor.GRAY + ((kitKiller != null) ? ("(" + ChatUtil.Gi + kitKiller.getName() + ChatColor.GRAY + ")") : ""));
/*      */       
/*  459 */       Kit kitPlayer = this.core.getKitManager().getKit(player);
/*      */       
/*  461 */       int soupsPlayer = 0;
/*  462 */       for (int j = 0; j < 36; j++) {
/*  463 */         ItemStack cStack = player.getInventory().getItem(j);
/*  464 */         if (cStack != null && cStack.getType().equals(Material.MUSHROOM_SOUP)) {
/*  465 */           soupsPlayer++;
/*      */         }
/*      */       } 
/*      */       
/*  469 */       (new TemporaryHologram(this.core, Arrays.asList(new String[] { ChatColor.GOLD + "~-+-~-+-~-+-~-+-~-+-~", ChatColor.GOLD + ChatColor.BOLD + "Kill Confirmed - " + RankUtil.getFullTag(player), ChatColor.GREEN + "Killstreak: " + ChatColor.GOLD + playerKillstreak, ChatColor.GREEN + "Kit: " + ChatColor.GOLD + ((kitPlayer != null) ? kitPlayer.getName() : "None") + ChatColor.GREEN + ", Soups: " + ChatColor.GOLD + soupsPlayer, ChatColor.GOLD + "~-+-~-+-~-+-~-+-~-+-~" }))).show(Arrays.asList(new Player[] { killer }, ), player.getEyeLocation(), 100);
/*      */     } 
/*      */     
/*  472 */     (new PermanentHologram(this.core, Arrays.asList(new String[] { ChatColor.GOLD + "~-+-~-+-~-+-~-+-~-+-~", ChatColor.DARK_RED + ChatColor.BOLD + "Update on sunday! ", ChatColor.GREEN + "You can now pay via" + ChatColor.GOLD + "Paysafecard ", ChatColor.GOLD + "Just message Chemmic", ChatColor.GOLD + "~-+-~-+-~-+-~-+-~-+-~" }))).show(Arrays.asList(new Player[] { killer }, ), player.getWorld().getSpawnLocation().add(5.0D, 3.0D, 0.0D));
/*      */     
/*  474 */     ItemStack randomArmorPiece = player.getInventory().getArmorContents()[this.random.nextInt(4)];
/*  475 */     ItemStack randomArmorPiece2 = player.getInventory().getArmorContents()[this.random.nextInt(4)];
/*  476 */     if (randomArmorPiece != null && !randomArmorPiece.getType().equals(Material.AIR)) {
/*  477 */       evt.getDrops().add(randomArmorPiece);
/*      */     }
/*  479 */     if (randomArmorPiece2 != null && !randomArmorPiece2.getType().equals(Material.AIR) && !randomArmorPiece2.getType().equals(randomArmorPiece.getType())) {
/*  480 */       evt.getDrops().add(randomArmorPiece2);
/*      */     }
/*      */     
/*  483 */     for (int i = 0; i < Math.min(Math.max(user.getKillstreak() * 2, 4), 12); i++) {
/*  484 */       evt.getDrops().add(this.core.soup);
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerRespawnEvent(PlayerRespawnEvent evt) {
/*  490 */     final Player player = evt.getPlayer();
/*  491 */     final User user = this.core.getUser(player.getUniqueId());
/*      */ 
/*      */     
/*  494 */     if (this.core.getDuelManager().isInDuelArena(player)) {
/*  495 */       (new BukkitRunnable()
/*      */         {
/*      */           public void run() {
/*  498 */             CorePlayerListener.this.core.getDuelManager().toDuelArena(player);
/*      */           }
/*  500 */         }).runTaskLater((Plugin)this.core, 2L);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  506 */     (new BukkitRunnable()
/*      */       {
/*      */         public void run()
/*      */         {
/*  510 */           CorePlayerListener.this.core.toSpawn(user, true);
/*      */         }
/*  512 */       }).runTaskLater((Plugin)this.core, 2L);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/*  517 */     final Player player = evt.getPlayer();
/*  518 */     User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  520 */     evt.setCancelled(false);
/*      */ 
/*      */ 
/*      */     
/*  524 */     if (evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
/*  525 */       Block clickedAbove = evt.getClickedBlock().getRelative(BlockFace.UP);
/*  526 */       if (clickedAbove.getType().equals(Material.FIRE)) {
/*  527 */         evt.setCancelled(true);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  532 */     if (evt.getAction() == Action.RIGHT_CLICK_BLOCK)
/*      */     {
/*  534 */       if (evt.getClickedBlock().getState() instanceof Sign) {
/*  535 */         Sign s = (Sign)evt.getClickedBlock().getState();
/*      */         
/*  537 */         if (s.getLine(0).equalsIgnoreCase("~Right click~") && 
/*  538 */           s.getLine(1).equalsIgnoreCase("To claim reward")) {
/*      */           
/*  540 */           player.sendMessage(ChatColor.GREEN + "You earned 3000 coins for doing the parkour!");
/*  541 */           this.core.toSpawn(user, true);
/*  542 */           this.core.getPlayerStatsManager().incrementInt(player.getUniqueId(), 3000, PlayerStat.COINS);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/*  553 */       if (player.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
/*  554 */         if (!player.hasMetadata("cantSoup") && (
/*  555 */           (CraftPlayer)player).getHealth() <= 19.0D && ((CraftPlayer)player).getHealth() > 0.0D) {
/*  556 */           double newHealth = ((CraftPlayer)player).getHealth() + 7.0D;
/*  557 */           player.setHealth((newHealth > 20.0D) ? 20.0D : newHealth);
/*  558 */           player.getItemInHand().setType(Material.BOWL);
/*  559 */           player.playSound(player.getEyeLocation(), Sound.DRINK, 0.3F, 1.2F);
/*      */         }
/*      */       
/*  562 */       } else if (player.getItemInHand().getType().equals(Material.EMERALD)) {
/*  563 */         player.openInventory(this.core.getKitManager().getKitSelectorInventory(player));
/*  564 */       } else if (player.getItemInHand().getType().equals(Material.DIAMOND)) {
/*  565 */         player.openInventory(this.core.getKitManager().getKitShopInventory(player));
/*  566 */       } else if (player.getItemInHand().getType().equals(Material.PAPER)) {
/*  567 */         player.openInventory(this.core.getHostedEventManager().getEventMenu());
/*  568 */       } else if (player.getItemInHand().getType().equals(Material.COAL)) {
/*  569 */         player.openInventory(this.core.getKitManager().getSpecialKitSelectorInventory(player));
/*  570 */         player.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "Special Kits:\n \n" + ChatUtil.Gi + "What is a Special Kit: " + ChatColor.GRAY + "A special kit is a kit in which you can only gain from a trade(/invitetrade <player>), the marketplace(coming soon, will be /warp marketplace), or the casino(/warp casino).\n\n" + ChatUtil.Gi + "What does common/rare etc.. mean: " + ChatColor.GRAY + "This means how rare a kit is, so for example if your kit said 'common', this means that the kit you gained is a common kit and isn't worth a lot. But if you got a kit that said 'Extremely Rare' that means it is worth a lot and is very rare(the rarest).\n\n" + ChatUtil.Gi + "Could you give me a list of the rareness's of kits: " + ChatColor.GRAY + 
/*  571 */             "Common, Rare, Very Rare, Extremely Rare, Impossibly Rare (Curious?).");
/*  572 */       } else if (player.getItemInHand().getType().equals(Material.EYE_OF_ENDER)) {
/*  573 */         this.core.getDuelManager().toDuelArena(player);
/*  574 */       } else if ((player.getItemInHand().getType().equals(Material.REDSTONE_ORE) || player.getItemInHand().getType().equals(Material.EMERALD_ORE)) && 
/*  575 */         this.core.getDuelManager().isInDuelArena(player)) {
/*  576 */         this.core.getDuelManager().quickMatchup(player);
/*      */       } 
/*      */     }
/*      */     
/*  580 */     if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/*  581 */       Block targetBlock = evt.getClickedBlock();
/*  582 */       if (targetBlock != null) {
/*  583 */         if (targetBlock.getType().equals(Material.TRAPPED_CHEST)) {
/*  584 */           evt.setCancelled(true);
/*  585 */           SurpriseChest.openSupriseChest(player, this.core, !targetBlock.getRelative(BlockFace.DOWN).getType().equals(Material.STAINED_GLASS));
/*  586 */         } else if (evt.getClickedBlock().getType().equals(Material.PISTON_BASE)) {
/*  587 */           final Block crate = evt.getClickedBlock();
/*  588 */           if (crate.hasMetadata("crate")) {
/*  589 */             String owner = ((MetadataValue)crate.getMetadata("owner").get(0)).asString();
/*  590 */             if (player.getName().equals(owner)) {
/*  591 */               crate.removeMetadata("crate", (Plugin)this.core);
/*  592 */               crate.removeMetadata("owner", (Plugin)this.core);
/*  593 */               crate.removeMetadata("users", (Plugin)this.core);
/*      */               
/*  595 */               crate.setType(Material.AIR);
/*      */               
/*  597 */               this.core.getCrateItemManager().getRandomItem(player);
/*      */               
/*  599 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "Opened " + ChatColor.GRAY + "the " + ChatUtil.Gi + "special item " + ChatColor.GRAY + "crate!");
/*      */             } else {
/*  601 */               if (!crate.hasMetadata("users")) {
/*  602 */                 crate.setMetadata("users", (MetadataValue)new FixedMetadataValue((Plugin)this.core, ""));
/*      */               }
/*      */               
/*  605 */               String users = ((MetadataValue)crate.getMetadata("users").get(0)).asString();
/*      */               
/*  607 */               if (!users.contains("-" + player.getName())) {
/*      */                 
/*  609 */                 crate.setMetadata("users", (MetadataValue)new FixedMetadataValue((Plugin)this.core, users.concat("-" + player.getName())));
/*      */                 
/*  611 */                 (new BukkitRunnable()
/*      */                   {
/*  613 */                     int count = 9;
/*      */ 
/*      */                     
/*      */                     public void run() {
/*  617 */                       if (crate.getType().equals(Material.PISTON_BASE)) {
/*  618 */                         if (this.count <= 0) {
/*  619 */                           open();
/*      */                         }
/*  621 */                         else if (player.getLocation().distance(crate.getLocation()) <= 5.0D) {
/*  622 */                           if (player.isBlocking()) {
/*  623 */                             player.sendMessage(ChatColor.GRAY + "Unlocking percentage: " + ChatUtil.Gi + ((10 - this.count) * 10) + "%");
/*  624 */                             this.count--;
/*      */                           } else {
/*  626 */                             player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "have to be " + ChatUtil.Gi + "blocking " + ChatColor.GRAY + "while in the " + ChatUtil.Gi + "5 block radius " + ChatColor.GRAY + "of the " + ChatUtil.Gi + "special item " + ChatColor.GRAY + "crate to keep " + ChatUtil.Gi + "unlocking " + ChatColor.GRAY + "it!");
/*  627 */                             stop();
/*      */                           } 
/*      */                         } else {
/*  630 */                           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "went out of the " + ChatUtil.Gi + "5 block radius " + ChatColor.GRAY + "of the " + ChatUtil.Gi + "special item " + ChatColor.GRAY + "crate!");
/*  631 */                           stop();
/*      */                         } 
/*      */                       } else {
/*      */                         
/*  635 */                         stop();
/*      */                       } 
/*      */                     }
/*      */ 
/*      */                     
/*      */                     private void open() {
/*  641 */                       crate.removeMetadata("crate", (Plugin)CorePlayerListener.this.core);
/*  642 */                       crate.removeMetadata("owner", (Plugin)CorePlayerListener.this.core);
/*  643 */                       crate.removeMetadata("users", (Plugin)CorePlayerListener.this.core);
/*      */                       
/*  645 */                       crate.setType(Material.AIR);
/*      */                       
/*  647 */                       CorePlayerListener.this.core.getCrateItemManager().getRandomItem(player);
/*      */                       
/*  649 */                       player.sendMessage(String.valueOf(ChatUtil.Gi) + "Opened " + ChatColor.GRAY + "the " + ChatUtil.Gi + "special item " + ChatColor.GRAY + "crate!");
/*      */                     }
/*      */                     
/*      */                     private void stop() {
/*  653 */                       if (crate.hasMetadata("users")) {
/*  654 */                         String users = ((MetadataValue)crate.getMetadata("users").get(0)).asString();
/*  655 */                         crate.setMetadata("users", (MetadataValue)new FixedMetadataValue((Plugin)CorePlayerListener.this.core, users.replace("-" + player.getName(), "")));
/*      */                       } 
/*      */                       
/*  658 */                       cancel();
/*      */                     }
/*  660 */                   }).runTaskTimer((Plugin)this.core, 20L, 20L);
/*      */                 
/*  662 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "Keep blocking! " + ChatColor.GRAY + "You " + ChatUtil.Gi + "must " + ChatColor.GRAY + "keep blocking or you will stop " + ChatUtil.Gi + "unlocking " + ChatColor.GRAY + "the " + ChatUtil.Gi + "special item " + ChatColor.GRAY + "crate!");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*  668 */     } else if (evt.getAction().equals(Action.PHYSICAL)) {
/*  669 */       Block trigger = evt.getClickedBlock();
/*  670 */       if (trigger.getType().equals(Material.STONE_PLATE) && 
/*  671 */         this.core.getHostedEventManager().getCurrentEvent() != null && 
/*  672 */         this.core.getHostedEventManager().getCurrentEvent() instanceof SpeedrunHostedEvent && 
/*  673 */         this.core.getHostedEventManager().getCurrentEvent().containsPlayer(player)) {
/*  674 */         this.core.getHostedEventManager().stopCurrentEvent(player);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     if (this.core.hasSpawnFlag(user)) {
/*  682 */       evt.setCancelled(true);
/*      */     }
/*      */     
/*  685 */     if (this.core.hasWarpProtection(user)) {
/*  686 */       evt.setCancelled(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
/*  693 */     if (evt.getRightClicked() instanceof Player) {
/*  694 */       Player player = evt.getPlayer();
/*  695 */       User user = this.core.getUser(player.getUniqueId());
/*      */       
/*  697 */       Player clicked = (Player)evt.getRightClicked();
/*  698 */       if (player.getItemInHand().getType().equals(Material.DIAMOND_HOE) && 
/*  699 */         this.core.getDuelManager().isInDuelArena(player) && this.core.getDuelManager().isInDuelArena(clicked)) {
/*  700 */         DuelData ddClicked = this.core.getDuelManager().getDuelData(clicked);
/*  701 */         if (ddClicked.getInvited().equals(player.getName()) && ddClicked.isActive()) {
/*  702 */           if (this.core.getHostedEventManager().getCurrentEvent() != null && 
/*  703 */             this.core.getHostedEventManager().getCurrentEvent().getQueue().contains(player.getName())) {
/*  704 */             player.sendMessage(ChatColor.GRAY + "You can't accept 1v1 invites, because you are in the " + ChatUtil.Gi + "Event Queue!");
/*  705 */             evt.setCancelled(true);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*  710 */           this.core.getDuelManager().acceptDuel(player, clicked);
/*      */         } else {
/*  712 */           if (this.core.getHostedEventManager().getCurrentEvent() != null && 
/*  713 */             this.core.getHostedEventManager().getCurrentEvent().getQueue().contains(player.getName())) {
/*  714 */             player.sendMessage(ChatColor.GRAY + "You can't invite players, because you are in the " + ChatUtil.Gi + "Event Queue!");
/*  715 */             evt.setCancelled(true);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*  720 */           this.core.getDuelManager().openMenu(player, clicked);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  725 */       if (this.core.hasSpawnFlag(user)) {
/*  726 */         evt.setCancelled(true);
/*      */       }
/*  728 */     } else if (evt.getRightClicked() instanceof Slime) {
/*  729 */       Player player = evt.getPlayer();
/*  730 */       if (this.core.getHostedEventManager().getCurrentEvent() != null && this.core.getHostedEventManager().getCurrentEvent() instanceof FootballEvent) {
/*  731 */         FootballEvent evFootball = (FootballEvent)this.core.getHostedEventManager().getCurrentEvent();
/*      */         
/*  733 */         if (this.core.getHostedEventManager().getCurrentEvent().containsPlayer(evt.getPlayer()) && !evFootball.isPauseBetweenGoals()) {
/*  734 */           Slime slime = (Slime)evt.getRightClicked();
/*  735 */           if (slime.getVelocity().getY() < 0.6D && 
/*  736 */             slime.getUniqueId().equals(evFootball.getBall().getUniqueId())) {
/*  737 */             slime.setVelocity(slime.getVelocity().add((new Vector(0.0F, 0.4F, 0.0F)).add(evt.getPlayer().getLocation().getDirection().normalize().multiply(1.0D).setY(0))));
/*  738 */             slime.getWorld().playSound(slime.getLocation(), Sound.SLIME_WALK, 1.0F, 1.0F);
/*      */             
/*  740 */             evFootball.setLastKicker(player.getUniqueId());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onFoodLevelChangeEvent(FoodLevelChangeEvent evt) {
/*  750 */     evt.setCancelled(true);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onBlockPlaceEvent(BlockPlaceEvent evt) {
/*  755 */     evt.setCancelled(!evt.getPlayer().getGameMode().equals(GameMode.CREATIVE));
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onBlockBreakEvent(BlockBreakEvent evt) {
/*  760 */     evt.setCancelled(!evt.getPlayer().getGameMode().equals(GameMode.CREATIVE));
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent evt) {
/*  765 */     evt.setCancelled(true);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onHangingBreakEvent(HangingBreakEvent evt) {
/*  770 */     evt.setCancelled(true);
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerPickupItemEvent(PlayerPickupItemEvent evt) {
/*  775 */     if (evt.getPlayer().getAllowFlight()) {
/*  776 */       evt.setCancelled(true);
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerDropItemEvent(PlayerDropItemEvent evt) {
/*  783 */     Player player = evt.getPlayer();
/*  784 */     User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  786 */     if (this.core.hasSpawnFlag(user)) {
/*  787 */       evt.setCancelled(true);
/*      */       
/*      */       return;
/*      */     } 
/*  791 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  792 */       HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  793 */       if ((ev instanceof RedRoverEvent || ev instanceof net.lightpvp.java.managers.event.events.CannonDodgeEvent) && 
/*  794 */         ev.containsPlayer(player)) {
/*  795 */         evt.setCancelled(true);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  800 */     if (InventoryUtil.isSword(evt.getItemDrop().getItemStack())) {
/*  801 */       evt.setCancelled(true);
/*      */       
/*      */       return;
/*      */     } 
/*  805 */     if (this.core.getDuelManager().isInDuelArena(player)) {
/*  806 */       if (this.core.getDuelManager().getDuelData(player).getDuel() == null) {
/*  807 */         evt.setCancelled(true);
/*      */         return;
/*      */       } 
/*  810 */       evt.getItemDrop().remove();
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   public CorePlayerListener(Core core) {
/*  816 */     this.prevYVelocity = 0.0D;
/*  817 */     this.prevYChange = 0.0D;
/*      */     this.core = core;
/*      */   } @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerMoveEvent(PlayerMoveEvent evt) {
/*  821 */     Player player = evt.getPlayer();
/*      */     
/*  823 */     if (evt.getFrom().getBlockX() == evt.getTo().getBlockX() && evt.getFrom().getBlockY() == evt.getTo().getBlockY() && evt.getFrom().getBlockZ() == evt.getTo().getBlockZ()) {
/*      */       return;
/*      */     }
/*      */     
/*  827 */     User user = this.core.getUser(player.getUniqueId());
/*      */     
/*  829 */     user.getTrail();
/*      */ 
/*      */ 
/*      */     
/*  833 */     Block b = evt.getTo().getBlock().getRelative(BlockFace.DOWN);
/*  834 */     if (b.getType().equals(Material.ENDER_STONE)) {
/*  835 */       int push = 1;
/*      */       
/*  837 */       while (b.getRelative(BlockFace.DOWN, push).getType().equals(Material.ENDER_STONE))
/*      */       {
/*  839 */         push++;
/*      */       }
/*      */ 
/*      */       
/*  843 */       int burst = push;
/*      */       
/*  845 */       player.setVelocity(new Vector(player.getVelocity().getX(), (burst * 0.4F), player.getVelocity().getZ()));
/*      */       
/*  847 */       (new BukkitRunnable(burst)
/*      */         {
/*      */           int count;
/*      */ 
/*      */           
/*      */           public void run() {
/*  853 */             if (this.count <= 0) {
/*  854 */               Bukkit.broadcastMessage("Line 8242");
/*  855 */               cancel();
/*      */             } else {
/*      */               
/*  858 */               this.count--;
/*      */             } 
/*      */           }
/*  861 */         }).runTaskTimer((Plugin)this.core, 0L, 4L);
/*      */     } 
/*      */     
/*  864 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  865 */       HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  866 */       if (ev instanceof SpeedrunHostedEvent) {
/*  867 */         SpeedrunHostedEvent evSpeedRun = (SpeedrunHostedEvent)ev;
/*  868 */         if (!evSpeedRun.getCanMove() && 
/*  869 */           evSpeedRun.containsPlayer(player)) {
/*  870 */           evt.setTo(evt.getFrom());
/*      */         }
/*      */       }
/*  873 */       else if (ev instanceof BracketsEvent) {
/*  874 */         BracketsEvent evBrackets = (BracketsEvent)ev;
/*  875 */         if (!evBrackets.getCanMove() && 
/*  876 */           evBrackets.isArenaFight(player)) {
/*  877 */           evt.setTo(evt.getFrom());
/*      */         }
/*      */       }
/*  880 */       else if (ev instanceof FootballEvent) {
/*  881 */         FootballEvent evFootball = (FootballEvent)ev;
/*  882 */         if (!evFootball.getCanMove() && 
/*  883 */           evFootball.containsPlayer(player)) {
/*  884 */           evt.setTo(evt.getFrom());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  890 */     if (this.core.getDuelManager().isInDuelArena(player)) {
/*  891 */       DuelGame dg = this.core.getDuelManager().isDueling(player);
/*  892 */       if (dg != null && 
/*  893 */         !dg.canMove()) {
/*  894 */         evt.setTo(evt.getFrom());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  899 */     if (player.getAllowFlight() && 
/*  900 */       player.getLocation().getY() >= 100.0D && !player.hasPermission("core.bypassfly")) {
/*  901 */       evt.setTo(evt.getFrom().subtract(0.0D, 1.0D, 0.0D));
/*  902 */       player.sendMessage(ChatColor.RED + "You can't fly that high!");
/*      */     } 
/*      */ 
/*      */     
/*  906 */     ApplicableRegionSet regions = this.core.regionManager.getApplicableRegions(evt.getTo());
/*  907 */     for (ProtectedRegion pr : regions) {
/*  908 */       if (pr.getId().equals("spawn")) {
/*  909 */         if (this.core.hasSpawnFlag(user))
/*  910 */           this.core.leaveSpawn(user);  continue;
/*      */       } 
/*  912 */       if (pr.getId().equals("parkourenter")) {
/*  913 */         player.removePotionEffect(PotionEffectType.SPEED);
/*  914 */         player.setAllowFlight(false);
/*  915 */         this.core.getKitManager().removeKit(player); continue;
/*  916 */       }  if (pr.getId().equals("parkourexit")) {
/*  917 */         boolean isTrigger = false;
/*  918 */         this.core.getKitManager().removeKit(player);
/*  919 */         ApplicableRegionSet regionsFrom = this.core.regionManager.getApplicableRegions(evt.getFrom());
/*  920 */         for (ProtectedRegion prFrom : regionsFrom) {
/*  921 */           if (prFrom.getId().equals("parkourenter")) {
/*  922 */             isTrigger = true;
/*      */           }
/*      */         } 
/*      */         
/*  926 */         if (isTrigger)
/*  927 */           this.core.toSpawn(this.core.getUser(player.getUniqueId()), true);  continue;
/*      */       } 
/*  929 */       if (pr.getId().equals("parkourlava")) {
/*  930 */         player.teleport(new Location(this.core.getWorld(), -705.5D, 72.0D, 961.5D, 0.0F, 0.0F)); continue;
/*  931 */       }  if (pr.getId().equals("tp1")) {
/*  932 */         Location location = player.getLocation();
/*  933 */         boolean isTrigger = false;
/*  934 */         ApplicableRegionSet regionsFrom = this.core.regionManager.getApplicableRegions(evt.getFrom());
/*  935 */         for (ProtectedRegion prFrom : regionsFrom) {
/*  936 */           if (prFrom.getId().equals("tp2")) {
/*  937 */             isTrigger = true;
/*      */           }
/*      */         } 
/*  940 */         if (isTrigger) {
/*  941 */           if (location.getBlockY() > 62) {
/*  942 */             Vector vector = player.getVelocity().clone();
/*  943 */             player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 4));
/*  944 */             (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((Entity)((CraftPlayer)player).getHandle(), 2));
/*  945 */             player.teleport(player.getLocation().subtract(0.5D, 15.0D, 0.0D));
/*  946 */             player.setVelocity(vector.multiply(2.0D)); continue;
/*      */           } 
/*  948 */           Vector speed = player.getVelocity().clone();
/*  949 */           player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 4));
/*  950 */           (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((Entity)((CraftPlayer)player).getHandle(), 2));
/*  951 */           player.teleport(player.getLocation().add(-0.5D, 15.0D, 0.0D));
/*  952 */           player.setVelocity(speed.multiply(2.0D));
/*      */         } 
/*      */         continue;
/*      */       } 
/*  956 */       if (pr.getId().equals("sp1cp")) {
/*  957 */         if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  958 */           HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  959 */           if (ev instanceof SpeedrunHostedEvent && 
/*  960 */             ev.containsPlayer(player))
/*  961 */             player.teleport(new Location(this.core.getWorld(), -100.0D, 62.0D, 1309.0D, 90.0F, 0.0F)); 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  965 */       if (pr.getId().equals("br1")) {
/*  966 */         player.damage(200.0D); continue;
/*  967 */       }  if (pr.getId().startsWith("wp")) {
/*  968 */         if (this.core.hasWarpProtection(user) && 
/*  969 */           !player.getAllowFlight()) {
/*      */           
/*  971 */           this.core.setWarpFlag(user, this.core.getWarp(user), false);
/*      */           
/*  973 */           (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((Entity)((CraftPlayer)player).getHandle(), 2));
/*      */           
/*  975 */           if (!this.core.getKitManager().choseKit(player))
/*  976 */             this.core.getKitManager().setKit(player, this.core.getKitManager().getKit(user.getLastKit()), true); 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  980 */       if (pr.getId().startsWith("rr") && 
/*  981 */         this.core.getHostedEventManager().getCurrentEvent() != null) {
/*  982 */         HostedEvent ev = this.core.getHostedEventManager().getCurrentEvent();
/*  983 */         if (ev instanceof RedRoverEvent && 
/*  984 */           ev.containsPlayer(player)) {
/*  985 */           RedRoverEvent evRedRover = (RedRoverEvent)this.core.getHostedEventManager().getCurrentEvent();
/*  986 */           evRedRover.handleMovement(pr.getId(), player);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerTeleportEvent(PlayerTeleportEvent evt) {
/* 1002 */     if (!evt.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
/* 1003 */       evt.getPlayer().closeInventory();
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler(priority = EventPriority.LOW)
/*      */   public void onPlayerReceiveNameTagEvent(AsyncPlayerReceiveNameTagEvent evt) {
/* 1009 */     String tagName = RankUtil.getFullTag(evt.getNamedPlayer());
/* 1010 */     if (tagName.length() > 16) {
/* 1011 */       tagName = tagName.substring(0, 16);
/*      */     }
/* 1013 */     evt.setTag(tagName);
/*      */   }
/*      */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\listener\CorePlayerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */