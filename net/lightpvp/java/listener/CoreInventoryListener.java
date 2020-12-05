/*     */ package net.lightpvp.java.listener;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.event.events.FootballEvent;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryAction;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.material.Dye;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreInventoryListener
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  51 */   private Random random = new Random();
/*     */   
/*  53 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public CoreInventoryListener(Core core) {
/*  56 */     this.core = core;
/*     */   }
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent e) {
/*  60 */     Player p = e.getPlayer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onInventoryClickEvent(InventoryClickEvent evt) {
/*  67 */     if (evt.getWhoClicked() instanceof Player) {
/*  68 */       final Player player = (Player)evt.getWhoClicked();
/*  69 */       User user = this.core.getUser(player.getUniqueId());
/*     */       String str;
/*  71 */       switch ((str = ChatColor.stripColor(evt.getInventory().getTitle())).hashCode()) { case -1578407264: if (!str.equals("Player Stats")) {
/*     */             break;
/*     */           }
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
/* 241 */           if (evt.getRawSlot() < 36 && 
/* 242 */             evt.getCurrentItem() != null) {
/* 243 */             if (evt.getCurrentItem().getType().equals(Material.CARPET)) {
/* 244 */               int page = Integer.parseInt(((String)evt.getCurrentItem().getItemMeta().getLore().get(1)).split(" ")[1]) + ((evt.getRawSlot() == 0) ? -1 : 1);
/* 245 */               ItemStack[] content = this.core.getPlayerStatsManager().getAllPlayersInventory(page);
/* 246 */               if (content != null) {
/* 247 */                 evt.getInventory().setContents(content);
/*     */               }
/* 249 */             } else if (evt.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
/* 250 */               ItemMeta meta = evt.getCurrentItem().getItemMeta();
/* 251 */               final String name = meta.getDisplayName();
/* 252 */               UUID uuid = UUID.fromString(this.core.getPlayerStatsManager().getUUIDFromName(name));
/* 253 */               meta.setLore(Arrays.asList(new String[] { "Coins: " + this.core.getPlayerStatsManager().getInt(uuid, "coins"), "Kills: " + this.core.getPlayerStatsManager().getInt(uuid, "kills"), "Deaths: " + this.core.getPlayerStatsManager().getInt(uuid, "deaths"), "Max Killstreak: " + this.core.getPlayerStatsManager().getInt(uuid, "maxkillstreak") }));
/* 254 */               evt.getCurrentItem().setItemMeta(meta);
/*     */             } 
/*     */           }
/*     */           
/* 258 */           evt.setCancelled(true); break;case -1184310090: if (!str.equals("Leaderboards")) break;  evt.setCancelled(true); break;case -779451636: if (!str.equals("HostedEvent Menu")) break;  if (evt.getRawSlot() > (this.core.getHostedEventManager().getHostedEvents()).length - 1) { evt.setCancelled(true); return; }  if (evt.getCurrentItem() != null) this.core.getHostedEventManager().openSubMenu(player, this.core.getHostedEventManager().getHostedEvents()[evt.getRawSlot()]);  evt.setCancelled(true); break;case -678543831: if (!str.equals("Kit Selector")) break;  if (evt.getRawSlot() < (this.core.getKitManager().getKitArray()).length && evt.getCurrentItem() != null) { this.core.getKitManager().setKit(player, evt.getSlot()); player.closeInventory(); }  evt.setCancelled(true); break;case -381494720: if (!str.equals("Kit Shop")) break;  if (evt.getRawSlot() < (this.core.getKitManager().getKitArray()).length && evt.getCurrentItem() != null) { Kit kit = this.core.getKitManager().getKitArray()[evt.getSlot()]; if (!this.core.getKitManager().hasKit(player, kit)) { if (this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "coins") >= kit.getPrice()) { this.core.getPlayerStatsManager().decrementInt(player.getUniqueId(), kit.getPrice(), PlayerStat.COINS); player.sendMessage(ChatColor.GRAY + "You succesfully bought the kit: " + ChatUtil.Gi + kit.getName() + ChatColor.GRAY + ", and " + ChatColor.GOLD + kit.getPrice() + " coins" + ChatColor.GRAY + " were subtracted from your account!"); PermissionsEx.getUser(player).addPermission(kit.getListedName()); } else { player.sendMessage(ChatColor.RED + "You don't have enough coins to buy this kit, you need " + ChatColor.GOLD + kit.getPrice() + "!"); }  } else { player.sendMessage(ChatColor.RED + "You already own this kit!"); }  player.closeInventory(); }  evt.setCancelled(true); break;case -361322353: if (!str.equals("container.crafting")) break;  if (this.core.hasSpawnFlag(user)) { if (evt.getRawSlot() == 19) { if (this.cooldowns.containsKey(player.getName())) { long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue(); player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!"); evt.setCancelled(true); player.closeInventory(); break; }  if (player.hasPermission("core.spawnbuttons")) { player.sendMessage(ChatColor.GRAY + "You are now a " + ChatColor.GREEN + "Dye Fountain!"); (new BukkitRunnable() { final int COUNT_MAX = (DyeColor.values()).length * 6; int count = 0; public void run() { if (this.count >= this.COUNT_MAX) { cancel(); } else { Dye is = new Dye(); is.setColor(DyeColor.values()[this.count & (DyeColor.values()).length - 1]); Item item = player.getWorld().dropItemNaturally(player.getEyeLocation().subtract(0.5D, 0.0D, 0.5D), is.toItemStack()); item.setVelocity(new Vector((CoreInventoryListener.this.random.nextDouble() - 0.5D) / 3.0D, 0.4000000059604645D, (CoreInventoryListener.this.random.nextDouble() - 0.5D) / 3.0D)); item.setPickupDelay(100); this.count++; }  } }
/*     */                   ).runTaskTimer((Plugin)this.core, 1L, 1L); final String name = player.getName(); this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis())); Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable() { public void run() { CoreInventoryListener.this.cooldowns.remove(name); }
/*     */                     },  600L); evt.setCancelled(true); player.closeInventory(); break; }  player.sendMessage(ChatColor.RED + "Only " + ChatColor.GREEN + "ELITE" + ChatColor.RED + "(and above) users can use the " + ChatColor.GREEN + "Spawn Fun Buttons!"); evt.setCancelled(true); player.closeInventory(); break; }  if (evt.getRawSlot() == 21) { if (this.cooldowns.containsKey(player.getName())) { long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue(); player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!"); evt.setCancelled(true); player.closeInventory(); break; }  if (player.hasPermission("core.spawnbuttons")) { player.sendMessage(ChatColor.GRAY + "You are now a " + ChatColor.GREEN + "Spinning Heart!"); (new BukkitRunnable() { final double FULL_RADIAN = 6.283185307179586D; int count = 128; double point = 0.0D; public void run() { if (this.count > 0) { if (this.point >= 6.283185307179586D) this.point = 0.0D;  this.point += 0.39269908169872414D; if (this.count % 4 == 0) { float pitch = (this.count / 2 | 0x24) / 36.0F; player.getWorld().playSound(player.getEyeLocation(), Sound.NOTE_PIANO, 2.0F, pitch); }  if (this.count % 32 == 0) player.getWorld().playSound(player.getEyeLocation(), Sound.NOTE_BASS_DRUM, 2.0F, 0.6F);  Location loc = player.getEyeLocation().add(Math.cos(this.point), 0.0D, Math.sin(this.point)); this.count--; } else { cancel(); }  } }
/* 261 */                   ).runTaskTimer((Plugin)this.core, 0L, 1L); final String name = player.getName(); this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis())); Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable() { public void run() { CoreInventoryListener.this.cooldowns.remove(name); } }, 600L); evt.setCancelled(true); player.closeInventory(); break; }  player.sendMessage(ChatColor.RED + "Only " + ChatColor.GREEN + "ELITE" + ChatColor.RED + "(and above) users can use the " + ChatColor.GREEN + "Spawn Fun Buttons!"); evt.setCancelled(true); player.closeInventory(); }  }  break;case 1230628415: if (!str.equals("Special Kit Trader")) break;  if (evt.getRawSlot() <= 53 && evt.getAction().equals(InventoryAction.PICKUP_ALL)) {
/* 262 */             ItemStack coreItem = evt.getInventory().getItem(40);
/* 263 */             ItemMeta coreMeta = coreItem.getItemMeta();
/* 264 */             List<String> lore = coreMeta.getLore();
/*     */             
/* 266 */             String traderLeftRaw = lore.get(4);
/* 267 */             String traderRightRaw = lore.get(5);
/*     */             
/* 269 */             String traderLeft = ChatColor.stripColor(traderLeftRaw);
/* 270 */             String traderRight = ChatColor.stripColor(traderRightRaw);
/*     */             
/* 272 */             Player traderLeftPlayer = Bukkit.getPlayerExact(traderLeft);
/* 273 */             Player traderRightPlayer = Bukkit.getPlayerExact(traderRight);
/*     */             
/* 275 */             boolean traderLeftReady = traderLeftRaw.substring(1, 2).equals((new StringBuilder(String.valueOf(ChatColor.GREEN.getChar()))).toString());
/* 276 */             boolean traderRightReady = traderRightRaw.substring(1, 2).equals((new StringBuilder(String.valueOf(ChatColor.GREEN.getChar()))).toString());
/*     */             
/* 278 */             int remainder = evt.getRawSlot() % 9;
/*     */             
/* 280 */             boolean tradeChanged = false;
/*     */             
/* 282 */             if (evt.getRawSlot() == 40) {
/* 283 */               if (traderLeftPlayer == null && traderRightPlayer != null) {
/* 284 */                 traderRightPlayer.closeInventory(); return;
/*     */               } 
/* 286 */               if (traderRightPlayer == null && traderLeftPlayer != null) {
/* 287 */                 traderLeftPlayer.closeInventory();
/*     */                 
/*     */                 return;
/*     */               } 
/* 291 */               if (player.getName().equals(traderLeft)) {
/* 292 */                 lore.set(4, ChatColor.GREEN + traderLeft);
/*     */                 
/* 294 */                 traderLeftReady = true;
/*     */                 
/* 296 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "are ready!");
/*     */                 
/* 298 */                 if (traderRightPlayer != null) {
/* 299 */                   traderRightPlayer.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is ready!");
/*     */                 }
/* 301 */               } else if (player.getName().equals(traderRight)) {
/* 302 */                 lore.set(5, ChatColor.GREEN + traderRight);
/*     */                 
/* 304 */                 traderRightReady = true;
/*     */                 
/* 306 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "are ready!");
/*     */                 
/* 308 */                 if (traderLeftPlayer != null) {
/* 309 */                   traderLeftPlayer.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " is ready!");
/*     */                 }
/*     */               } 
/* 312 */               coreMeta.setLore(lore);
/* 313 */               coreItem.setItemMeta(coreMeta);
/*     */               
/* 315 */               if (traderLeftReady && traderRightReady) {
/* 316 */                 List<String> traderLeftKits = new ArrayList<>();
/* 317 */                 List<String> traderRightKits = new ArrayList<>();
/*     */                 int slot;
/* 319 */                 for (slot = 45; slot <= 48; slot++) {
/* 320 */                   ItemStack item = evt.getInventory().getItem(slot);
/*     */                   
/* 322 */                   if (item != null && item.hasItemMeta()) {
/*     */ 
/*     */                     
/* 325 */                     final String name = ChatColor.stripColor(item.getItemMeta().getDisplayName()); byte b; int i;
/*     */                     SpecialKit[] arrayOfSpecialKit;
/* 327 */                     for (i = (arrayOfSpecialKit = this.core.getKitManager().getSpecialKitArray()).length, b = 0; b < i; ) { SpecialKit kit = arrayOfSpecialKit[b];
/* 328 */                       if (kit.getName().equals(name))
/* 329 */                         for (int j = 0; j < item.getAmount(); j++) {
/* 330 */                           traderLeftKits.add(kit.getListedName());
/*     */                         } 
/*     */                       b++; }
/*     */                   
/*     */                   } 
/*     */                 } 
/* 336 */                 for (slot = 50; slot <= 53; slot++) {
/* 337 */                   ItemStack item = evt.getInventory().getItem(slot);
/*     */                   
/* 339 */                   if (item != null && item.hasItemMeta()) {
/*     */ 
/*     */                     
/* 342 */                     final String name = ChatColor.stripColor(item.getItemMeta().getDisplayName()); byte b; int i;
/*     */                     SpecialKit[] arrayOfSpecialKit;
/* 344 */                     for (i = (arrayOfSpecialKit = this.core.getKitManager().getSpecialKitArray()).length, b = 0; b < i; ) { SpecialKit kit = arrayOfSpecialKit[b];
/* 345 */                       if (kit.getName().equals(name))
/* 346 */                         for (int j = 0; j < item.getAmount(); j++) {
/* 347 */                           traderRightKits.add(kit.getListedName());
/*     */                         } 
/*     */                       b++; }
/*     */                   
/*     */                   } 
/*     */                 } 
/* 353 */                 User traderLeftUser = null;
/* 354 */                 User traderRightUser = null;
/*     */                 
/* 356 */                 if (player.getName().equals(traderLeft)) {
/* 357 */                   traderLeftUser = user;
/* 358 */                   traderRightUser = this.core.getUser(traderRightPlayer.getUniqueId());
/*     */                 } else {
/* 360 */                   traderLeftUser = this.core.getUser(traderLeftPlayer.getUniqueId());
/* 361 */                   traderRightUser = user;
/*     */                 } 
/*     */                 
/* 364 */                 if (traderLeftUser.getSpecialKits().containsAll(traderLeftKits) && traderRightUser.getSpecialKits().containsAll(traderRightKits)) {
/* 365 */                   for (String listedKitName : traderLeftKits) {
/* 366 */                     traderLeftUser.getSpecialKits().remove(listedKitName);
/*     */                   }
/* 368 */                   traderLeftUser.getSpecialKits().addAll(traderRightKits);
/*     */                   
/* 370 */                   for (String listedKitName : traderRightKits) {
/* 371 */                     traderRightUser.getSpecialKits().remove(listedKitName);
/*     */                   }
/* 373 */                   traderRightUser.getSpecialKits().addAll(traderLeftKits);
/*     */                   
/* 375 */                   evt.getInventory().getItem(40).setType(Material.EMERALD);
/* 376 */                   traderLeftPlayer.closeInventory();
/* 377 */                   traderRightPlayer.closeInventory();
/*     */                   
/* 379 */                   traderLeftPlayer.sendMessage(String.valueOf(ChatUtil.Gi) + "Trade" + ChatColor.GRAY + " complete!");
/* 380 */                   traderRightPlayer.sendMessage(String.valueOf(ChatUtil.Gi) + "Trade" + ChatColor.GRAY + " complete!");
/*     */                 } else {
/* 382 */                   evt.getInventory().getItem(40).setType(Material.EMERALD);
/* 383 */                   traderLeftPlayer.closeInventory();
/* 384 */                   traderRightPlayer.closeInventory();
/*     */                   
/* 386 */                   traderLeftPlayer.sendMessage(ChatColor.RED + "Something went wrong, cancelling trading!");
/* 387 */                   traderRightPlayer.sendMessage(ChatColor.RED + "Something went wrong, cancelling trading!");
/*     */                 }
/*     */               
/*     */               }
/*     */             
/* 392 */             } else if (evt.getCurrentItem() != null && !evt.getCurrentItem().getType().equals(Material.IRON_FENCE)) {
/* 393 */               if (remainder < 4) {
/* 394 */                 if (player.getName().equals(traderLeft)) {
/* 395 */                   if (!traderLeftReady) {
/* 396 */                     if (evt.getRawSlot() <= 35) {
/* 397 */                       ItemStack clone = evt.getCurrentItem().clone();
/* 398 */                       clone.setAmount(1);
/*     */                       
/* 400 */                       boolean succesful = false;
/* 401 */                       for (int slot = 45; slot <= 48; slot++) {
/* 402 */                         ItemStack scan = evt.getInventory().getItem(slot);
/*     */                         
/* 404 */                         if (scan == null) {
/* 405 */                           evt.getInventory().setItem(slot, clone);
/* 406 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/* 410 */                         if (scan.getType().equals(clone.getType())) {
/* 411 */                           scan.setAmount(scan.getAmount() + 1);
/* 412 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/* 416 */                         if (scan.getType().equals(Material.AIR)) {
/* 417 */                           evt.getInventory().setItem(slot, clone);
/* 418 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/*     */                       } 
/* 423 */                       if (succesful) {
/* 424 */                         tradeChanged = true;
/* 425 */                         if (evt.getCurrentItem().getAmount() > 1) {
/* 426 */                           evt.getCurrentItem().setAmount(evt.getCurrentItem().getAmount() - 1);
/*     */                         } else {
/* 428 */                           evt.getInventory().setItem(evt.getRawSlot(), new ItemStack(Material.AIR));
/*     */                         } 
/*     */                       } else {
/* 431 */                         player.sendMessage(ChatColor.RED + "Not enough space in your trading section!");
/*     */                       } 
/* 433 */                     } else if (evt.getRawSlot() >= 45 && evt.getRawSlot() <= 53) {
/* 434 */                       ItemStack clone = evt.getCurrentItem().clone();
/* 435 */                       clone.setAmount(1);
/*     */                       
/* 437 */                       boolean succesful = false; int slotY;
/* 438 */                       label316: for (slotY = 0; slotY < 4; slotY++) {
/* 439 */                         for (int slotX = 0; slotX < 4; slotX++) {
/* 440 */                           int slot = slotY * 9 + slotX;
/* 441 */                           ItemStack scan = evt.getInventory().getItem(slot);
/*     */                           
/* 443 */                           if (scan == null) {
/* 444 */                             evt.getInventory().setItem(slot, clone);
/* 445 */                             succesful = true;
/*     */                             
/*     */                             break label316;
/*     */                           } 
/* 449 */                           if (scan.getType().equals(clone.getType())) {
/* 450 */                             scan.setAmount(scan.getAmount() + 1);
/* 451 */                             succesful = true;
/*     */                             
/*     */                             break label316;
/*     */                           } 
/* 455 */                           if (scan.getType().equals(Material.AIR)) {
/* 456 */                             evt.getInventory().setItem(slot, clone);
/* 457 */                             succesful = true;
/*     */                             
/*     */                             break label316;
/*     */                           } 
/*     */                         } 
/*     */                       } 
/* 463 */                       if (succesful) {
/* 464 */                         tradeChanged = true;
/* 465 */                         if (evt.getCurrentItem().getAmount() > 1) {
/* 466 */                           evt.getCurrentItem().setAmount(evt.getCurrentItem().getAmount() - 1);
/*     */                         } else {
/* 468 */                           evt.getInventory().setItem(evt.getRawSlot(), new ItemStack(Material.AIR));
/*     */                         } 
/*     */                       } else {
/* 471 */                         player.sendMessage(ChatColor.RED + "Not enough space in your trading section!");
/*     */                       } 
/*     */                     } 
/*     */                   } else {
/* 475 */                     player.sendMessage(ChatColor.RED + "You can't change the Special Kits as you have already clicked ready!");
/*     */                   } 
/*     */                 } else {
/* 478 */                   player.sendMessage(ChatColor.RED + "That's not your trading side!");
/*     */                 } 
/* 480 */               } else if (remainder > 4) {
/* 481 */                 if (player.getName().equals(traderRight)) {
/* 482 */                   if (!traderRightReady) {
/* 483 */                     if (evt.getRawSlot() <= 35) {
/* 484 */                       ItemStack clone = evt.getCurrentItem().clone();
/* 485 */                       clone.setAmount(1);
/*     */                       
/* 487 */                       boolean succesful = false;
/* 488 */                       for (int slot = 50; slot <= 53; slot++) {
/* 489 */                         ItemStack scan = evt.getInventory().getItem(slot);
/*     */                         
/* 491 */                         if (scan == null) {
/* 492 */                           evt.getInventory().setItem(slot, clone);
/* 493 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/* 497 */                         if (scan.getType().equals(clone.getType())) {
/* 498 */                           scan.setAmount(scan.getAmount() + 1);
/* 499 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/* 503 */                         if (scan.getType().equals(Material.AIR)) {
/* 504 */                           evt.getInventory().setItem(slot, clone);
/* 505 */                           succesful = true;
/*     */                           
/*     */                           break;
/*     */                         } 
/*     */                       } 
/* 510 */                       if (succesful) {
/* 511 */                         tradeChanged = true;
/* 512 */                         if (evt.getCurrentItem().getAmount() > 1) {
/* 513 */                           evt.getCurrentItem().setAmount(evt.getCurrentItem().getAmount() - 1);
/*     */                         } else {
/* 515 */                           evt.getInventory().setItem(evt.getRawSlot(), new ItemStack(Material.AIR));
/*     */                         } 
/*     */                       } else {
/* 518 */                         player.sendMessage(ChatColor.RED + "Not enough space in your trading section!");
/*     */                       } 
/* 520 */                     } else if (evt.getRawSlot() >= 45 && evt.getRawSlot() <= 53) {
/* 521 */                       ItemStack clone = evt.getCurrentItem().clone();
/* 522 */                       clone.setAmount(1);
/*     */                       
/* 524 */                       boolean succesful = false; int slotY;
/* 525 */                       label317: for (slotY = 0; slotY < 4; slotY++) {
/* 526 */                         for (int slotX = 0; slotX < 4; slotX++) {
/* 527 */                           int slot = slotY * 9 + slotX + 5;
/* 528 */                           ItemStack scan = evt.getInventory().getItem(slot);
/*     */                           
/* 530 */                           if (scan == null) {
/* 531 */                             evt.getInventory().setItem(slot, clone);
/* 532 */                             succesful = true;
/*     */                             
/*     */                             break label317;
/*     */                           } 
/* 536 */                           if (scan.getType().equals(clone.getType())) {
/* 537 */                             scan.setAmount(scan.getAmount() + 1);
/* 538 */                             succesful = true;
/*     */                             
/*     */                             break label317;
/*     */                           } 
/* 542 */                           if (scan.getType().equals(Material.AIR)) {
/* 543 */                             evt.getInventory().setItem(slot, clone);
/* 544 */                             succesful = true;
/*     */                             
/*     */                             break label317;
/*     */                           } 
/*     */                         } 
/*     */                       } 
/* 550 */                       if (succesful) {
/* 551 */                         tradeChanged = true;
/* 552 */                         if (evt.getCurrentItem().getAmount() > 1) {
/* 553 */                           evt.getCurrentItem().setAmount(evt.getCurrentItem().getAmount() - 1);
/*     */                         } else {
/* 555 */                           evt.getInventory().setItem(evt.getRawSlot(), new ItemStack(Material.AIR));
/*     */                         } 
/*     */                       } else {
/* 558 */                         player.sendMessage(ChatColor.RED + "Not enough space in your trading section!");
/*     */                       } 
/*     */                     } 
/*     */                   } else {
/* 562 */                     player.sendMessage(ChatColor.RED + "You can't change the Special Kits as you have already clicked ready!");
/*     */                   } 
/*     */                 } else {
/* 565 */                   player.sendMessage(ChatColor.RED + "That's not your trading side!");
/*     */                 } 
/*     */               } 
/*     */               
/* 569 */               if (tradeChanged && (
/* 570 */                 traderLeftReady || traderRightReady)) {
/* 571 */                 traderLeftPlayer.sendMessage(ChatColor.GRAY + "The " + ChatUtil.Gi + "trade" + ChatColor.GRAY + " changed, both traders are now " + ChatUtil.Gi + "unready!");
/* 572 */                 traderRightPlayer.sendMessage(ChatColor.GRAY + "The " + ChatUtil.Gi + "trade" + ChatColor.GRAY + " changed, both traders are now " + ChatUtil.Gi + "unready!");
/*     */                 
/* 574 */                 lore.set(4, ChatColor.RED + traderLeft);
/* 575 */                 lore.set(5, ChatColor.RED + traderRight);
/*     */                 
/* 577 */                 coreMeta.setLore(lore);
/* 578 */                 coreItem.setItemMeta(coreMeta);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 585 */           evt.setCancelled(true); break;
/*     */         case 1438234192:
/*     */           if (!str.equals("Special Kit Selector"))
/*     */             break;  if (evt.getRawSlot() < (this.core.getKitManager().getSpecialKitArray()).length && evt.getCurrentItem() != null && !evt.getCurrentItem().getType().equals(Material.IRON_FENCE)) { this.core.getKitManager().setSpecialKit(player, evt.getSlot()); player.closeInventory(); }  evt.setCancelled(true); break; }
/* 589 */        if (this.core.getHostedEventManager().getCurrentEvent() != null && this.core.getHostedEventManager().getCurrentEvent() instanceof FootballEvent) {
/* 590 */         FootballEvent evFootball = (FootballEvent)this.core.getHostedEventManager().getCurrentEvent();
/*     */         
/* 592 */         if (evFootball.containsPlayer(player)) {
/* 593 */           evt.setCancelled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onInventoryCloseEvent(InventoryCloseEvent evt) {
/* 601 */     if (evt.getPlayer() instanceof Player) {
/* 602 */       Player player = (Player)evt.getPlayer();
/*     */       String str;
/* 604 */       switch ((str = ChatColor.stripColor(evt.getInventory().getTitle())).hashCode()) { case 1230628415: if (!str.equals("Special Kit Trader"))
/*     */             break; 
/* 606 */           if (evt.getInventory().getViewers().size() == 2) {
/* 607 */             Player other = player.getName().equals(((HumanEntity)evt.getInventory().getViewers().get(0)).getName()) ? (Player)evt.getInventory().getViewers().get(1) : (Player)evt.getInventory().getViewers().get(0);
/* 608 */             ItemStack coreItem = evt.getInventory().getItem(40);
/* 609 */             if (coreItem.getType().equals(Material.GOLD_NUGGET)) {
/* 610 */               player.sendMessage(ChatColor.GRAY + "You succesfully stopped the " + ChatUtil.Gi + "trading!");
/* 611 */               other.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " stopped the " + ChatUtil.Gi + "trading!");
/* 612 */               coreItem.setType(Material.EMERALD);
/* 613 */               other.closeInventory();
/*     */             } 
/*     */           } 
/*     */           break; }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\listener\CoreInventoryListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */