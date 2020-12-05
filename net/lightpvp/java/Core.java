/*     */ package net.lightpvp.java;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.PacketAdapter;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.events.PacketListener;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.commands.admin.CommandAddcoins;
/*     */ import net.lightpvp.java.commands.admin.CommandAdmin;
/*     */ import net.lightpvp.java.commands.admin.CommandAexec;
/*     */ import net.lightpvp.java.commands.admin.CommandBan;
/*     */ import net.lightpvp.java.commands.admin.CommandBroadcast;
/*     */ import net.lightpvp.java.commands.admin.CommandCc;
/*     */ import net.lightpvp.java.commands.admin.CommandClear;
/*     */ import net.lightpvp.java.commands.admin.CommandCode;
/*     */ import net.lightpvp.java.commands.admin.CommandCrate;
/*     */ import net.lightpvp.java.commands.admin.CommandForcestart;
/*     */ import net.lightpvp.java.commands.admin.CommandInvsee;
/*     */ import net.lightpvp.java.commands.admin.CommandIp;
/*     */ import net.lightpvp.java.commands.admin.CommandKick;
/*     */ import net.lightpvp.java.commands.admin.CommandMute;
/*     */ import net.lightpvp.java.commands.admin.CommandPeacestop;
/*     */ import net.lightpvp.java.commands.admin.CommandRecords;
/*     */ import net.lightpvp.java.commands.admin.CommandSc;
/*     */ import net.lightpvp.java.commands.admin.CommandSetrank;
/*     */ import net.lightpvp.java.commands.admin.CommandSpy;
/*     */ import net.lightpvp.java.commands.admin.CommandTempban;
/*     */ import net.lightpvp.java.commands.admin.CommandTp;
/*     */ import net.lightpvp.java.commands.admin.CommandUnban;
/*     */ import net.lightpvp.java.commands.admin.CommandUnmute;
/*     */ import net.lightpvp.java.commands.player.Command1v1;
/*     */ import net.lightpvp.java.commands.player.CommandAccept;
/*     */ import net.lightpvp.java.commands.player.CommandAccepttrade;
/*     */ import net.lightpvp.java.commands.player.CommandIgnore;
/*     */ import net.lightpvp.java.commands.player.CommandInvitetrade;
/*     */ import net.lightpvp.java.commands.player.CommandJoin;
/*     */ import net.lightpvp.java.commands.player.CommandKit;
/*     */ import net.lightpvp.java.commands.player.CommandKits;
/*     */ import net.lightpvp.java.commands.player.CommandLeaderboard;
/*     */ import net.lightpvp.java.commands.player.CommandLeave;
/*     */ import net.lightpvp.java.commands.player.CommandMsg;
/*     */ import net.lightpvp.java.commands.player.CommandPing;
/*     */ import net.lightpvp.java.commands.player.CommandRedeem;
/*     */ import net.lightpvp.java.commands.player.CommandReply;
/*     */ import net.lightpvp.java.commands.player.CommandReport;
/*     */ import net.lightpvp.java.commands.player.CommandRules;
/*     */ import net.lightpvp.java.commands.player.CommandSpawn;
/*     */ import net.lightpvp.java.commands.player.CommandStats;
/*     */ import net.lightpvp.java.commands.player.CommandVote;
/*     */ import net.lightpvp.java.commands.player.CommandWarp;
/*     */ import net.lightpvp.java.commands.player.CommandWhatsnew;
/*     */ import net.lightpvp.java.commands.player.CommandWho;
/*     */ import net.lightpvp.java.commands.premium.CommandFly;
/*     */ import net.lightpvp.java.commands.premium.CommandJoinMessage;
/*     */ import net.lightpvp.java.commands.premium.CommandPay;
/*     */ import net.lightpvp.java.commands.premium.CommandPlayers;
/*     */ import net.lightpvp.java.commands.premium.CommandTrail;
/*     */ import net.lightpvp.java.listener.CoreEntityListener;
/*     */ import net.lightpvp.java.listener.CoreInventoryListener;
/*     */ import net.lightpvp.java.listener.CorePlayerChatListener;
/*     */ import net.lightpvp.java.listener.CorePlayerListener;
/*     */ import net.lightpvp.java.listener.CoreServerPingListener;
/*     */ import net.lightpvp.java.listener.CoreVoteListener;
/*     */ import net.lightpvp.java.managers.casino.CasinoManager;
/*     */ import net.lightpvp.java.managers.chatgames.ChatGamesManager;
/*     */ import net.lightpvp.java.managers.config.ConfigManager;
/*     */ import net.lightpvp.java.managers.crate.CrateItemManager;
/*     */ import net.lightpvp.java.managers.data.SQLite;
/*     */ import net.lightpvp.java.managers.data.records.ServerRecordsManager;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStatsManager;
/*     */ import net.lightpvp.java.managers.duel.DuelManager;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.events.FootballEvent;
/*     */ import net.lightpvp.java.managers.event.events.HostedEvent;
/*     */ import net.lightpvp.java.managers.event.listeners.CSGOEventHandler;
/*     */ import net.lightpvp.java.managers.kit.KitManager;
/*     */ import net.lightpvp.java.managers.specialhead.SpecialHeadManager;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.material.Dye;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.potion.PotionType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.kitteh.tag.TagAPI;
/*     */ import ru.tehkode.permissions.PermissionGroup;
/*     */ import ru.tehkode.permissions.PermissionUser;
/*     */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Core
/*     */   extends JavaPlugin
/*     */ {
/* 138 */   private ServerLocation serverLocation = null;
/*     */   
/*     */   private World world;
/*     */   
/*     */   private Location spawn;
/* 143 */   private Random random = new Random();
/*     */   
/*     */   public DecimalFormatSymbols decimalSymbol;
/*     */   
/* 147 */   public RegionManager regionManager = null;
/*     */   
/* 149 */   public ProtocolManager protocolManager = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private Core core;
/*     */ 
/*     */ 
/*     */   
/* 157 */   public ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
/*     */   
/* 159 */   private ItemStack[] spawnContent = new ItemStack[36];
/*     */   
/* 161 */   public ItemStack inviteTool = new ItemStack(Material.DIAMOND_HOE);
/* 162 */   public ItemStack quickMatchupOffTool = new ItemStack(Material.REDSTONE_ORE);
/* 163 */   public ItemStack quickMatchupOnTool = new ItemStack(Material.EMERALD_ORE);
/*     */   
/* 165 */   public ItemStack diamondSwordSharpness = new ItemStack(Material.DIAMOND_SWORD);
/* 166 */   public ItemStack diamondOpSharpness = new ItemStack(Material.DIAMOND_SWORD);
/* 167 */   public ItemStack ironSwordSharpness = new ItemStack(Material.IRON_SWORD);
/* 168 */   public ItemStack fish = new ItemStack(Material.FISHING_ROD);
/* 169 */   public ItemStack Bow = new ItemStack(Material.BOW);
/* 170 */   public ItemStack Stick = new ItemStack(Material.STICK);
/* 171 */   public ItemStack Boots = new ItemStack(Material.DIAMOND_BOOTS);
/* 172 */   public ItemStack Leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
/* 173 */   public ItemStack Chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
/* 174 */   public ItemStack Helmet = new ItemStack(Material.DIAMOND_HELMET);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public ItemStack akItem = new ItemStack(Material.DIAMOND_HOE);
/*     */ 
/*     */   
/*     */   private SQLite database;
/*     */   
/*     */   private KitManager kitManager;
/*     */   
/*     */   private PlayerStatsManager playerStatsManager;
/*     */   
/*     */   private ServerRecordsManager serverRecordsManager;
/*     */   
/*     */   private HostedEventManager hostedEventManager;
/*     */   
/*     */   private DuelManager duelManager;
/*     */   
/*     */   private CrateItemManager crateItemsManager;
/*     */   
/*     */   private CasinoManager casinoManager;
/*     */   
/*     */   private ChatGamesManager chatGamesManager;
/*     */   
/*     */   private SpecialHeadManager specialHeadManager;
/*     */   
/* 204 */   private Map<String, User> users = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 213 */     saveDefaultConfig();
/* 214 */     getConfig().options().copyDefaults(true);
/* 215 */     saveConfig();
/*     */     
/* 217 */     this.core = this;
/*     */     
/* 219 */     ConfigManager.load((Plugin)this, "perks.yml");
/*     */     
/* 221 */     Bukkit.broadcastMessage(Bukkit.getIp());
/* 222 */     this.serverLocation = Bukkit.getIp().equals("") ? ServerLocation.EUROPEAN : ServerLocation.AUSTRALIAN;
/*     */     
/* 224 */     File statsFolder = new File(String.valueOf(getDataFolder().getAbsolutePath()) + File.separator + "data");
/* 225 */     statsFolder.mkdirs();
/*     */     
/* 227 */     File userFolder = new File(String.valueOf(getDataFolder().getAbsolutePath()) + File.separator + "users");
/* 228 */     userFolder.mkdirs();
/*     */     
/* 230 */     this.decimalSymbol = new DecimalFormatSymbols(Locale.US);
/*     */     
/* 232 */     this.protocolManager = ProtocolLibrary.getProtocolManager();
/*     */     
/* 234 */     final Field[] entityEffectFields = new Field[2];
/*     */     
/*     */     try {
/* 237 */       entityEffectFields[0] = PacketPlayOutEntityStatus.class.getDeclaredField("a");
/* 238 */       entityEffectFields[0].setAccessible(true);
/*     */       
/* 240 */       entityEffectFields[1] = PacketPlayOutEntityStatus.class.getDeclaredField("b");
/* 241 */       entityEffectFields[1].setAccessible(true);
/*     */     }
/* 243 */     catch (NoSuchFieldException|SecurityException e) {
/* 244 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 247 */     this.protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)this, new PacketType[] { PacketType.Play.Server.ENTITY_STATUS })
/*     */         {
/*     */           public void onPacketSending(PacketEvent evt)
/*     */           {
/* 251 */             if (Core.this.hostedEventManager.getCurrentEvent() != null && Core.this.hostedEventManager.getCurrentEvent() instanceof FootballEvent) {
/* 252 */               PacketPlayOutEntityStatus packet = (PacketPlayOutEntityStatus)evt.getPacket().getHandle();
/*     */               
/* 254 */               byte effect = 0;
/*     */               
/*     */               try {
/* 257 */                 effect = entityEffectFields[1].getByte(packet);
/* 258 */               } catch (IllegalArgumentException|IllegalAccessException e) {
/* 259 */                 e.printStackTrace();
/*     */               } 
/*     */               
/* 262 */               if (effect == 2) {
/* 263 */                 int entityId = 0;
/*     */                 
/*     */                 try {
/* 266 */                   entityId = entityEffectFields[0].getInt(packet);
/* 267 */                 } catch (IllegalArgumentException|IllegalAccessException e) {
/* 268 */                   e.printStackTrace();
/*     */                 } 
/*     */                 
/* 271 */                 if (((FootballEvent)Core.this.hostedEventManager.getCurrentEvent()).getBall() != null && entityId == ((FootballEvent)Core.this.hostedEventManager.getCurrentEvent()).getBall().getEntityId()) {
/* 272 */                   evt.setCancelled(true);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 280 */     initWorld();
/* 281 */     initItems();
/* 282 */     initManagers();
/*     */     
/* 284 */     (new ServerTick(this)).runTaskTimer((Plugin)this, 0L, 10L);
/*     */     
/* 286 */     this.regionManager = getWorldGuard().getRegionManager(this.world);
/*     */     
/* 288 */     registerCommands();
/* 289 */     registerListeners();
/*     */     
/* 291 */     changeNoPermissionMessage(ChatColor.RED + "You don't have permission to execute that command!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeNoPermissionMessage(final String message) {
/* 296 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 300 */           CraftServer craftServer = (CraftServer)Core.this.getServer();
/*     */           
/* 302 */           SimpleCommandMap commandMap = null;
/* 303 */           Map<String, Command> knownCommands = null;
/*     */           
/*     */           try {
/* 306 */             Field commandMapField = craftServer.getClass().getDeclaredField("commandMap");
/* 307 */             commandMapField.setAccessible(true);
/* 308 */             commandMap = (SimpleCommandMap)commandMapField.get(craftServer);
/*     */             
/* 310 */             Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
/* 311 */             knownCommandsField.setAccessible(true);
/* 312 */             knownCommands = (Map<String, Command>)knownCommandsField.get(commandMap);
/* 313 */           } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/* 314 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 317 */           Field permissionMessageField = null;
/*     */           
/*     */           try {
/* 320 */             permissionMessageField = Command.class.getDeclaredField("permissionMessage");
/* 321 */             permissionMessageField.setAccessible(true);
/* 322 */           } catch (NoSuchFieldException|SecurityException e) {
/* 323 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 326 */           for (String alias : knownCommands.keySet()) {
/* 327 */             Command command = knownCommands.get(alias);
/*     */             
/*     */             try {
/* 330 */               permissionMessageField.set(command, message);
/* 331 */             } catch (IllegalArgumentException|IllegalAccessException e) {
/* 332 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         }
/* 336 */       }).runTaskLater((Plugin)this, 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 341 */     saveConfig();
/*     */     
/* 343 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 344 */       p.kickPlayer(ChatColor.RED + "Server reloading... ");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initWorld() {
/* 353 */     this.world = Bukkit.getWorld("world");
/* 354 */     this.spawn = new Location(this.world, -712.5D, 73.0D, 934.5D, -128.0F, 0.0F);
/*     */     
/* 356 */     this.world.setAutoSave(false);
/* 357 */     this.world.setTime(6000L);
/* 358 */     this.world.setThundering(false);
/* 359 */     this.world.setStorm(false);
/*     */     
/* 361 */     for (Entity e : this.world.getEntities()) {
/* 362 */       if (e instanceof org.bukkit.entity.Minecart || e instanceof org.bukkit.entity.Item) {
/*     */         continue;
/*     */       }
/* 365 */       e.getLocation().getChunk().load();
/*     */       
/* 367 */       e.remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initItems() {
/* 372 */     ItemMeta soupMeta = this.soup.getItemMeta();
/* 373 */     soupMeta.setDisplayName(ChatColor.GREEN + "S" + ChatColor.GOLD + "o" + ChatColor.BLUE + "u" + ChatColor.DARK_PURPLE + "p");
/* 374 */     this.soup.setItemMeta(soupMeta);
/*     */ 
/*     */     
/* 377 */     ItemStack kitSelector = new ItemStack(Material.EMERALD);
/* 378 */     ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
/* 379 */     kitSelectorMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Kit Selector" + ChatColor.GRAY + " (Right Click)");
/* 380 */     kitSelector.setItemMeta(kitSelectorMeta);
/*     */     
/* 382 */     ItemStack specialKitSelector = new ItemStack(Material.COAL);
/* 383 */     ItemMeta specialKitSelectorMeta = specialKitSelector.getItemMeta();
/* 384 */     specialKitSelectorMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Special Kit Selector" + ChatColor.GRAY + " (Right Click)");
/* 385 */     specialKitSelector.setItemMeta(specialKitSelectorMeta);
/*     */     
/* 387 */     ItemStack kitShop = new ItemStack(Material.DIAMOND);
/* 388 */     ItemMeta kitShopMeta = kitShop.getItemMeta();
/* 389 */     kitShopMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Kit Shop" + ChatColor.GRAY + " (Right Click)");
/* 390 */     kitShop.setItemMeta(kitShopMeta);
/*     */     
/* 392 */     ItemStack eventSelector = new ItemStack(Material.PAPER);
/* 393 */     ItemMeta eventSelectorMeta = eventSelector.getItemMeta();
/* 394 */     eventSelectorMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Event Menu" + ChatColor.GRAY + " (Right Click)");
/* 395 */     eventSelector.setItemMeta(eventSelectorMeta);
/*     */     
/* 397 */     ItemStack duelWarp = new ItemStack(Material.EYE_OF_ENDER);
/* 398 */     ItemMeta duelWarpMeta = duelWarp.getItemMeta();
/* 399 */     duelWarpMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "1v1" + ChatColor.GRAY + " (Right Click)");
/* 400 */     duelWarp.setItemMeta(duelWarpMeta);
/*     */     
/* 402 */     this.spawnContent[0] = kitSelector;
/* 403 */     this.spawnContent[1] = kitShop;
/* 404 */     this.spawnContent[4] = eventSelector;
/* 405 */     this.spawnContent[6] = specialKitSelector;
/* 406 */     this.spawnContent[8] = duelWarp;
/*     */     
/* 408 */     ItemStack comingSoon = new ItemStack(Material.IRON_FENCE);
/* 409 */     ItemMeta comingSoonMeta = comingSoon.getItemMeta();
/* 410 */     comingSoonMeta.setDisplayName(ChatColor.GRAY + "More spawn " + ChatUtil.Gi + "buttons" + ChatColor.GRAY + " coming soon!");
/* 411 */     comingSoon.setItemMeta(comingSoonMeta);
/*     */     
/* 413 */     for (int i = 9; i < 36; i++) {
/* 414 */       this.spawnContent[i] = comingSoon;
/*     */     }
/*     */     
/* 417 */     Dye dye = new Dye();
/* 418 */     dye.setColor(DyeColor.ORANGE);
/* 419 */     ItemStack dyeFountain = dye.toItemStack();
/* 420 */     ItemMeta dyeFountainMeta = dyeFountain.getItemMeta();
/* 421 */     dyeFountainMeta.setDisplayName(ChatColor.GREEN + "Be a Dye Fountain!");
/* 422 */     dyeFountainMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Left Click", "", ChatColor.GOLD + "Spawn Fun Button" }));
/* 423 */     dyeFountain.setItemMeta(dyeFountainMeta);
/* 424 */     this.spawnContent[19] = dyeFountain;
/*     */     
/* 426 */     ItemStack spinningHeart = new ItemStack(Material.BONE);
/* 427 */     ItemMeta spinningHeartMeta = spinningHeart.getItemMeta();
/* 428 */     spinningHeartMeta.setDisplayName(ChatColor.GREEN + "Be a Spinning Heart!");
/* 429 */     spinningHeartMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Left Click", "", ChatColor.GOLD + "Spawn Fun Button" }));
/* 430 */     spinningHeart.setItemMeta(spinningHeartMeta);
/* 431 */     this.spawnContent[21] = spinningHeart;
/*     */ 
/*     */     
/* 434 */     ItemMeta inviteToolMeta = this.inviteTool.getItemMeta();
/* 435 */     inviteToolMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Invite Tool" + ChatColor.GRAY + " (Right Click Player)");
/* 436 */     this.inviteTool.setItemMeta(inviteToolMeta);
/*     */     
/* 438 */     ItemMeta quickMatchupOffToolMeta = this.quickMatchupOffTool.getItemMeta();
/* 439 */     quickMatchupOffToolMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Quick Matchup" + ChatColor.GRAY + " (Right Click)");
/* 440 */     this.quickMatchupOffTool.setItemMeta(quickMatchupOffToolMeta);
/*     */     
/* 442 */     ItemMeta quickMatchupOnToolMeta = this.quickMatchupOnTool.getItemMeta();
/* 443 */     quickMatchupOnToolMeta.setDisplayName(ChatColor.BOLD + ChatColor.GOLD + "Quick Matchup" + ChatColor.GRAY + " (Right Click)");
/* 444 */     this.quickMatchupOnTool.setItemMeta(quickMatchupOffToolMeta);
/*     */ 
/*     */     
/* 447 */     this.diamondSwordSharpness.addEnchantment(Enchantment.DAMAGE_ALL, 1);
/* 448 */     this.ironSwordSharpness.addEnchantment(Enchantment.DAMAGE_ALL, 1);
/* 449 */     this.diamondOpSharpness.addEnchantment(Enchantment.DAMAGE_ALL, 5);
/* 450 */     this.Bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
/* 451 */     this.Bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/* 452 */     this.Stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
/* 453 */     this.fish.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*     */     
/* 455 */     this.Helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
/* 456 */     this.Chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
/* 457 */     this.Leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
/* 458 */     this.Boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
/*     */ 
/*     */ 
/*     */     
/* 462 */     ItemMeta akMeta = this.akItem.getItemMeta();
/* 463 */     akMeta.setDisplayName(String.valueOf(ChatUtil.Gi) + "AK-47 Rifle");
/* 464 */     akMeta.setLore(Arrays.asList(new String[] { String.valueOf(ChatUtil.Gi) + "This " + ChatColor.GRAY + "is a " + ChatUtil.Gi + "rifle.", ChatColor.GRAY + "It is " + ChatUtil.Gi + " quite accurate!", ChatColor.RED + "Cost: $2700", ChatColor.RED + "Ammo: 30 per mag" }));
/* 465 */     this.akItem.setItemMeta(akMeta);
/*     */   }
/*     */   
/*     */   private void initManagers() {
/* 469 */     this.database = new SQLite(this, String.valueOf(getDataFolder().getAbsolutePath()) + File.separator + "data" + File.separator + "Data");
/* 470 */     this.database.execute("CREATE TABLE IF NOT EXISTS players(uuid UUID, name string, ipaddress string, coins int, kills int, deaths int, duelwins int, maxkillstreak int)");
/* 471 */     this.kitManager = new KitManager(this);
/* 472 */     this.playerStatsManager = new PlayerStatsManager(this);
/* 473 */     this.serverRecordsManager = new ServerRecordsManager(this);
/* 474 */     this.hostedEventManager = new HostedEventManager(this);
/* 475 */     this.duelManager = new DuelManager(this);
/* 476 */     this.crateItemsManager = new CrateItemManager(this);
/*     */     
/* 478 */     this.casinoManager = new CasinoManager(this);
/* 479 */     this.chatGamesManager = new ChatGamesManager(this);
/* 480 */     this.specialHeadManager = new SpecialHeadManager(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerCommands() {
/* 485 */     getCommand("peacestop").setExecutor((CommandExecutor)new CommandPeacestop(this));
/* 486 */     getCommand("setrank").setExecutor((CommandExecutor)new CommandSetrank(this));
/* 487 */     getCommand("broadcast").setExecutor((CommandExecutor)new CommandBroadcast(this));
/* 488 */     getCommand("forcestart").setExecutor((CommandExecutor)new CommandForcestart(this));
/* 489 */     getCommand("ip").setExecutor((CommandExecutor)new CommandIp(this));
/* 490 */     getCommand("aexec").setExecutor((CommandExecutor)new CommandAexec(this));
/* 491 */     getCommand("invsee").setExecutor((CommandExecutor)new CommandInvsee(this));
/* 492 */     getCommand("admin").setExecutor((CommandExecutor)new CommandAdmin(this));
/* 493 */     getCommand("sc").setExecutor((CommandExecutor)new CommandSc(this));
/* 494 */     getCommand("code").setExecutor((CommandExecutor)new CommandCode(this));
/* 495 */     getCommand("tp").setExecutor((CommandExecutor)new CommandTp(this));
/* 496 */     getCommand("mute").setExecutor((CommandExecutor)new CommandMute(this));
/* 497 */     getCommand("unmute").setExecutor((CommandExecutor)new CommandUnmute(this));
/* 498 */     getCommand("ban").setExecutor((CommandExecutor)new CommandBan(this));
/* 499 */     getCommand("tempban").setExecutor((CommandExecutor)new CommandTempban(this));
/* 500 */     getCommand("unban").setExecutor((CommandExecutor)new CommandUnban(this));
/* 501 */     getCommand("kick").setExecutor((CommandExecutor)new CommandKick(this));
/* 502 */     getCommand("spy").setExecutor((CommandExecutor)new CommandSpy(this));
/* 503 */     getCommand("records").setExecutor((CommandExecutor)new CommandRecords(this));
/* 504 */     getCommand("addcoins").setExecutor((CommandExecutor)new CommandAddcoins(this));
/* 505 */     getCommand("cc").setExecutor((CommandExecutor)new CommandCc(this));
/* 506 */     getCommand("crate").setExecutor((CommandExecutor)new CommandCrate(this));
/* 507 */     getCommand("clear").setExecutor((CommandExecutor)new CommandClear(this));
/*     */ 
/*     */     
/* 510 */     getCommand("players").setExecutor((CommandExecutor)new CommandPlayers(this));
/* 511 */     getCommand("pay").setExecutor((CommandExecutor)new CommandPay(this));
/* 512 */     getCommand("fly").setExecutor((CommandExecutor)new CommandFly(this));
/* 513 */     getCommand("joinmessage").setExecutor((CommandExecutor)new CommandJoinMessage(this));
/* 514 */     getCommand("trail").setExecutor((CommandExecutor)new CommandTrail(this));
/*     */ 
/*     */     
/* 517 */     getCommand("msg").setExecutor((CommandExecutor)new CommandMsg(this));
/* 518 */     getCommand("r").setExecutor((CommandExecutor)new CommandReply(this));
/* 519 */     getCommand("spawn").setExecutor((CommandExecutor)new CommandSpawn(this));
/* 520 */     getCommand("kits").setExecutor((CommandExecutor)new CommandKits(this));
/* 521 */     getCommand("ping").setExecutor((CommandExecutor)new CommandPing(this));
/* 522 */     getCommand("join").setExecutor((CommandExecutor)new CommandJoin(this));
/* 523 */     getCommand("leave").setExecutor((CommandExecutor)new CommandLeave(this));
/* 524 */     getCommand("redeem").setExecutor((CommandExecutor)new CommandRedeem(this));
/* 525 */     getCommand("1v1").setExecutor((CommandExecutor)new Command1v1(this));
/* 526 */     getCommand("accept").setExecutor((CommandExecutor)new CommandAccept(this));
/* 527 */     getCommand("stats").setExecutor((CommandExecutor)new CommandStats(this));
/* 528 */     getCommand("report").setExecutor((CommandExecutor)new CommandReport(this));
/* 529 */     getCommand("leaderboard").setExecutor((CommandExecutor)new CommandLeaderboard(this));
/* 530 */     getCommand("vote").setExecutor((CommandExecutor)new CommandVote(this));
/* 531 */     getCommand("who").setExecutor((CommandExecutor)new CommandWho(this));
/* 532 */     getCommand("warp").setExecutor((CommandExecutor)new CommandWarp(this));
/* 533 */     getCommand("who").setExecutor((CommandExecutor)new CommandWho(this));
/* 534 */     getCommand("ignore").setExecutor((CommandExecutor)new CommandIgnore(this));
/* 535 */     getCommand("kit").setExecutor((CommandExecutor)new CommandKit(this));
/* 536 */     getCommand("whatsnew").setExecutor((CommandExecutor)new CommandWhatsnew(this));
/* 537 */     getCommand("invitetrade").setExecutor((CommandExecutor)new CommandInvitetrade(this));
/* 538 */     getCommand("accepttrade").setExecutor((CommandExecutor)new CommandAccepttrade(this));
/* 539 */     getCommand("rules").setExecutor((CommandExecutor)new CommandRules(this));
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerListeners() {
/* 544 */     PluginManager pm = Bukkit.getPluginManager();
/* 545 */     pm.registerEvents((Listener)new CorePlayerListener(this), (Plugin)this);
/* 546 */     pm.registerEvents((Listener)new CoreEntityListener(this), (Plugin)this);
/* 547 */     pm.registerEvents((Listener)new CorePlayerChatListener(this), (Plugin)this);
/* 548 */     pm.registerEvents((Listener)new CoreInventoryListener(this), (Plugin)this);
/* 549 */     pm.registerEvents((Listener)new CoreServerPingListener(this), (Plugin)this);
/* 550 */     pm.registerEvents((Listener)new CoreVoteListener(this), (Plugin)this);
/* 551 */     pm.registerEvents((Listener)new CSGOEventHandler(this), (Plugin)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSpawnFlag(User user) {
/* 564 */     return user.getSpawn();
/*     */   }
/*     */   
/*     */   public void toSpawn(User user, boolean message) {
/* 568 */     Player player = user.getPlayer();
/*     */     
/* 570 */     player.teleport(this.spawn);
/*     */     
/* 572 */     if (message) {
/* 573 */       player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "You are now at the spawn!");
/*     */     }
/*     */     
/* 576 */     if (getHostedEventManager().getCurrentEvent() != null) {
/* 577 */       HostedEvent ev = getHostedEventManager().getCurrentEvent();
/* 578 */       if (ev.containsPlayer(player)) {
/* 579 */         ev.playerLeave(player);
/*     */       }
/*     */     } 
/*     */     
/* 583 */     if (getDuelManager().isInDuelArena(player)) {
/* 584 */       getDuelManager().removeDuelData(player);
/*     */       
/* 586 */       for (Player p : Bukkit.getOnlinePlayers()) {
/* 587 */         if (!p.getName().equals(player.getName())) {
/* 588 */           player.showPlayer(p);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 593 */     if (!user.getWarp().isEmpty()) {
/* 594 */       removeWarpFlag(user);
/*     */       
/* 596 */       if (user.getResetKit() != null) {
/* 597 */         user.setKit(user.getResetKit());
/*     */       }
/*     */       
/* 600 */       if (user.getResetContent() != null) {
/* 601 */         player.getInventory().setContents(user.getResetContent());
/*     */       }
/*     */       
/* 604 */       if (user.getResetArmour() != null) {
/* 605 */         player.getInventory().setArmorContents(user.getResetArmour());
/*     */       }
/*     */       
/* 608 */       user.resetResetKit();
/* 609 */       user.resetResetContent();
/* 610 */       user.resetResetArmour();
/*     */       
/* 612 */       for (PotionEffect pe : player.getActivePotionEffects()) {
/* 613 */         player.removePotionEffect(pe.getType());
/*     */       }
/*     */     } else {
/* 616 */       Util.clearPlayer(player);
/* 617 */       player.getInventory().setContents(this.spawnContent);
/* 618 */       getKitManager().removeKit(player);
/* 619 */       user.setKillstreak(0);
/*     */     } 
/*     */     
/* 622 */     setSpawnFlag(user, true);
/*     */     
/* 624 */     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 1));
/*     */   }
/*     */   
/*     */   public void leaveSpawn(User user) {
/* 628 */     Player player = user.getPlayer();
/*     */     
/* 630 */     player.removePotionEffect(PotionEffectType.SPEED);
/* 631 */     player.setVelocity(player.getLocation().subtract(this.spawn).multiply(0.02D).toVector().setY(0.5F));
/* 632 */     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((Entity)((CraftPlayer)player).getHandle(), 2));
/* 633 */     setSpawnFlag(user, false);
/*     */   }
/*     */   
/*     */   public void setSpawnFlag(User user, boolean flag) {
/* 637 */     user.setSpawn(flag);
/*     */   }
/*     */   
/*     */   public void toWarp(User user, String warp, Location warpLocation, boolean restore) {
/* 641 */     Player player = user.getPlayer();
/*     */     
/* 643 */     if (getHostedEventManager().getCurrentEvent() != null) {
/* 644 */       HostedEvent ev = getHostedEventManager().getCurrentEvent();
/* 645 */       if (ev.containsPlayer(player)) {
/* 646 */         ev.removePlayer(player);
/* 647 */         ev.broadcastEvent(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " left! (" + ev.getPlayers().size() + " left!)");
/*     */       } 
/*     */     } 
/*     */     
/* 651 */     if (getDuelManager().isInDuelArena(player)) {
/* 652 */       getDuelManager().removeDuelData(player);
/*     */       
/* 654 */       for (Player p : Bukkit.getOnlinePlayers()) {
/* 655 */         if (!p.getName().equals(player.getName())) {
/* 656 */           player.showPlayer(p);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 661 */     player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "You are now at the " + warp + " arena!");
/*     */     
/* 663 */     player.teleport(warpLocation);
/*     */     
/* 665 */     if (restore) {
/* 666 */       if (user.getResetKit() != null) {
/* 667 */         user.setKit(user.getResetKit());
/*     */       }
/*     */       
/* 670 */       if (user.getResetContent() != null) {
/* 671 */         player.getInventory().setContents(user.getResetContent());
/*     */       }
/*     */       
/* 674 */       if (user.getResetArmour() != null) {
/* 675 */         player.getInventory().setArmorContents(user.getResetArmour());
/*     */       }
/*     */       
/* 678 */       user.resetResetKit();
/* 679 */       user.resetResetContent();
/* 680 */       user.resetResetArmour();
/*     */     } 
/*     */     
/* 683 */     setSpawnFlag(user, false);
/* 684 */     setWarpFlag(user, warp, true);
/*     */   }
/*     */   
/*     */   public String getWarp(User user) {
/* 688 */     String warp = user.getWarp();
/* 689 */     if (warp != null) {
/* 690 */       String[] warpData = user.getWarp().split(",");
/* 691 */       if (warpData != null && warpData.length == 2) {
/* 692 */         return warpData[0];
/*     */       }
/*     */     } 
/* 695 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasWarpProtection(User user) {
/* 699 */     String warp = user.getWarp();
/* 700 */     if (warp != null) {
/* 701 */       String[] warpData = user.getWarp().split(",");
/* 702 */       if (warpData != null && warpData.length == 2) {
/* 703 */         return warpData[1].equals("PROTECTION");
/*     */       }
/*     */     } 
/* 706 */     return false;
/*     */   }
/*     */   
/*     */   public void setWarpFlag(User user, String warp, boolean protection) {
/* 710 */     user.setWarp(String.valueOf(warp) + "," + (protection ? "PROTECTION" : "PLAYING"));
/*     */   }
/*     */   
/*     */   public void removeWarpFlag(User user) {
/* 714 */     user.setWarp("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkKillstreak(final Player player, int killstreak) {
/* 722 */     if (killstreak % 5 == 0) {
/* 723 */       switch (this.random.nextInt(5)) {
/*     */         case 0:
/* 725 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is on a " + ChatUtil.Gi + "Killing Spree " + ChatColor.GRAY + "(" + ChatUtil.Gi + ChatColor.BOLD + killstreak + ChatColor.GRAY + ")!");
/*     */           break;
/*     */         case 1:
/* 728 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "Unstoppable " + ChatColor.GRAY + "(" + ChatUtil.Gi + ChatColor.BOLD + killstreak + ChatColor.GRAY + ")!");
/*     */           break;
/*     */         case 2:
/* 731 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "Godlike " + ChatColor.GRAY + "(" + ChatUtil.Gi + ChatColor.BOLD + killstreak + ChatColor.GRAY + ")!");
/*     */           break;
/*     */         case 3:
/* 734 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "Legendary " + ChatColor.GRAY + "(" + ChatUtil.Gi + ChatColor.BOLD + killstreak + ChatColor.GRAY + ")!");
/*     */           break;
/*     */         case 4:
/* 737 */           ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "Owning " + ChatColor.GRAY + "(" + ChatUtil.Gi + ChatColor.BOLD + killstreak + ChatColor.GRAY + ")!");
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 742 */     String playerWarp = getWarp(getUser(player.getUniqueId()));
/* 743 */     if (playerWarp == null || (playerWarp != null && !playerWarp.equals("EarlyHG") && !getDuelManager().isInDuelArena(player))) {
/* 744 */       switch (killstreak) {
/*     */         case 5:
/* 746 */           Util.addPotion(player, PotionType.STRENGTH, 1);
/*     */           break;
/*     */         case 10:
/* 749 */           Util.addItemStack(player, Material.IRON_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
/*     */           break;
/*     */         case 15:
/* 752 */           Util.addItemStack(player, Material.IRON_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 2);
/*     */           break;
/*     */         case 20:
/* 755 */           Util.addItemStack(player, Material.DIAMOND_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 0);
/*     */           break;
/*     */         case 25:
/* 758 */           Util.addItemStack(player, Material.DIAMOND_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 0);
/*     */           break;
/*     */         case 30:
/* 761 */           Util.addItemStack(player, Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 0);
/*     */           break;
/*     */         case 35:
/* 764 */           Util.addItemStack(player, Material.DIAMOND_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 0);
/*     */           break;
/*     */         case 40:
/* 767 */           ChatUtil.broadcastMessage(ChatColor.GOLD + "███" + ChatUtil.Gi + " Hot air balloon" + ChatColor.GRAY + " is dropping a crate in " + ChatColor.GOLD + "30" + ChatColor.GRAY + " seconds for " + RankUtil.getFullTag(player) + ChatColor.GRAY + "'s 40 killstreak!");
/*     */           
/* 769 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 773 */                 ChatUtil.broadcastMessage(ChatColor.GOLD + "███" + ChatUtil.Gi + "Hot air balloon" + ChatColor.GRAY + " dropped a crate with special items in it!" + ChatUtil.Gi + "GO GET IT!");
/* 774 */                 Core.this.crateItemsManager.dropCrate(player);
/*     */               }
/* 776 */             }).runTaskLater((Plugin)this, 600L);
/*     */           break;
/*     */         
/*     */         case 45:
/* 780 */           Util.addItemStack(player, Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 3);
/*     */           break;
/*     */         case 50:
/* 783 */           Util.addItemStack(player, Material.DIAMOND_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
/*     */           break;
/*     */         case 55:
/* 786 */           Util.addItemStack(player, Material.DIAMOND_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
/*     */           break;
/*     */         case 60:
/* 789 */           Util.addItemStack(player, Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
/*     */           break;
/*     */         case 65:
/* 792 */           Util.addItemStack(player, Material.DIAMOND_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
/*     */           break;
/*     */         case 70:
/* 795 */           Util.addItemStack(player, Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 5);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 846 */     return this.world;
/*     */   }
/*     */   
/*     */   private WorldGuardPlugin getWorldGuard() {
/* 850 */     Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
/* 851 */     return (WorldGuardPlugin)plugin;
/*     */   }
/*     */   
/*     */   public void updatePlayerDecorations(Player player) {
/* 855 */     TagAPI.refreshPlayer(player);
/* 856 */     String listName = RankUtil.getFullTag(player);
/* 857 */     if (listName.length() > 16) {
/* 858 */       listName = listName.substring(0, 16);
/*     */     }
/* 860 */     player.setPlayerListName(listName);
/*     */   }
/*     */   
/*     */   public static Player getNameFromUUID(UUID uuid) {
/* 864 */     return Bukkit.getPlayer(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRank(UUID uuid, String groupName) {
/* 880 */     if (PermissionsEx.getPermissionManager().getGroupNames().contains(groupName)) {
/* 881 */       PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
/*     */       
/* 883 */       if (!group.getOption("color").isEmpty()) {
/* 884 */         PermissionUser pu = PermissionsEx.getUser(uuid.toString());
/* 885 */         pu.setGroups(new PermissionGroup[] { group });
/*     */         
/* 887 */         Player target = getNameFromUUID(uuid);
/*     */         
/* 889 */         if (target != null) {
/* 890 */           updatePlayerDecorations(target);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public User getUser(UUID uuid) {
/* 897 */     User user = this.users.get(uuid.toString());
/* 898 */     if (user == null) {
/* 899 */       user = new User(uuid.toString());
/*     */     }
/* 901 */     return user;
/*     */   }
/*     */   
/*     */   public void loadUser(User user) {
/* 905 */     this.users.put(user.getUniqueID(), user);
/*     */   }
/*     */   
/*     */   public void saveUser(User user) {
/* 909 */     user.save();
/*     */   }
/*     */   
/*     */   public User removeUser(UUID uuid) {
/* 913 */     return this.users.remove(uuid.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLite getLocalDatabase() {
/* 921 */     return this.database;
/*     */   }
/*     */   
/*     */   public KitManager getKitManager() {
/* 925 */     return this.kitManager;
/*     */   }
/*     */   
/*     */   public PlayerStatsManager getPlayerStatsManager() {
/* 929 */     return this.playerStatsManager;
/*     */   }
/*     */   
/*     */   public ServerRecordsManager getServerRecordsManager() {
/* 933 */     return this.serverRecordsManager;
/*     */   }
/*     */   
/*     */   public HostedEventManager getHostedEventManager() {
/* 937 */     return this.hostedEventManager;
/*     */   }
/*     */   
/*     */   public DuelManager getDuelManager() {
/* 941 */     return this.duelManager;
/*     */   }
/*     */   
/*     */   public CrateItemManager getCrateItemManager() {
/* 945 */     return this.crateItemsManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CasinoManager getCasinoManager() {
/* 951 */     return this.casinoManager;
/*     */   }
/*     */   
/*     */   public ChatGamesManager getChatGamesManager() {
/* 955 */     return this.chatGamesManager;
/*     */   }
/*     */   
/*     */   public SpecialHeadManager getSpecialHeadManager() {
/* 959 */     return this.specialHeadManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\Core.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */