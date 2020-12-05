/*     */ package net.lightpvp.java.managers.casino;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class CasinoManager
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  25 */   private HashMap<String, SlotMachine> slotMachines = new HashMap<>();
/*     */   
/*  27 */   private final int PRICE = 600;
/*     */   
/*     */   public CasinoManager(Core core) {
/*  30 */     this.core = core;
/*     */     
/*  32 */     this.slotMachines.put((new Location(core.getWorld(), -353.0D, 74.0D, 1829.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-352, 76, 1831)));
/*  33 */     this.slotMachines.put((new Location(core.getWorld(), -347.0D, 74.0D, 1829.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-346, 76, 1831)));
/*  34 */     this.slotMachines.put((new Location(core.getWorld(), -341.0D, 74.0D, 1829.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-340, 76, 1831)));
/*  35 */     this.slotMachines.put((new Location(core.getWorld(), -335.0D, 74.0D, 1829.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-334, 76, 1831)));
/*  36 */     this.slotMachines.put((new Location(core.getWorld(), -329.0D, 74.0D, 1829.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-328, 76, 1831)));
/*     */     
/*  38 */     this.slotMachines.put((new Location(core.getWorld(), -353.0D, 74.0D, 1841.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-352, 76, 1843)));
/*  39 */     this.slotMachines.put((new Location(core.getWorld(), -347.0D, 74.0D, 1841.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-346, 76, 1843)));
/*  40 */     this.slotMachines.put((new Location(core.getWorld(), -341.0D, 74.0D, 1841.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-340, 76, 1843)));
/*  41 */     this.slotMachines.put((new Location(core.getWorld(), -335.0D, 74.0D, 1841.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-334, 76, 1843)));
/*  42 */     this.slotMachines.put((new Location(core.getWorld(), -329.0D, 74.0D, 1841.0D, 0.0F, 0.0F)).toVector().toString(), new SlotMachine(this, core.getWorld().getBlockAt(-328, 76, 1843)));
/*     */     
/*  44 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */   }
/*     */   
/*     */   public Core getCore() {
/*  48 */     return this.core;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/*  53 */     Player player = evt.getPlayer();
/*     */     
/*  55 */     if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/*  56 */       Block block = evt.getClickedBlock();
/*     */       
/*  58 */       if (block != null && block.getType().equals(Material.STONE_BUTTON) && 
/*  59 */         this.slotMachines.containsKey(block.getLocation().toVector().toString())) {
/*  60 */         SlotMachine slotMachine = this.slotMachines.get(block.getLocation().toVector().toString());
/*  61 */         if (slotMachine.isRunning().isEmpty()) {
/*  62 */           boolean playing = false;
/*     */           
/*  64 */           for (String key : this.slotMachines.keySet()) {
/*  65 */             SlotMachine sm = this.slotMachines.get(key);
/*     */             
/*  67 */             if (sm.isRunning().equals(player.getName())) {
/*  68 */               playing = true;
/*     */             }
/*     */           } 
/*     */           
/*  72 */           if (!playing) {
/*  73 */             int coins = this.core.getPlayerStatsManager().getInt(player.getUniqueId(), "coins");
/*  74 */             if (coins >= 600) {
/*  75 */               User user = this.core.getUser(player.getUniqueId());
/*     */               
/*  77 */               if (System.currentTimeMillis() >= user.getResetSlotmachineCount() || user.getResetSlotmachineCount() == 0L) {
/*  78 */                 user.setResetSlotmachineCount(System.currentTimeMillis() + 3600000L);
/*  79 */                 user.setSlotmachineCount(0);
/*     */               } 
/*     */               
/*  82 */               int maxSlotmachineCount = 25;
/*     */               
/*  84 */               if (player.hasPermission("core.casinopro")) {
/*  85 */                 maxSlotmachineCount = 60;
/*     */               }
/*     */               
/*  88 */               if (user.getSlotmachineCount() < maxSlotmachineCount) {
/*  89 */                 user.incrementSlotmachineCount();
/*  90 */                 player.sendMessage(ChatColor.GRAY + "The " + ChatUtil.Gi + "slotmachine " + ChatColor.GRAY + "is now active and " + ChatUtil.Gi + 'ɘ' + " coins" + ChatColor.GRAY + " were removed from your account! (" + ChatUtil.Gi + (maxSlotmachineCount - user.getSlotmachineCount()) + ChatColor.GRAY + " times remaining this " + ChatUtil.Gi + "hour" + ChatColor.GRAY + ")");
/*  91 */                 this.core.getPlayerStatsManager().decrementInt(player.getUniqueId(), 600, PlayerStat.COINS);
/*  92 */                 slotMachine.play(player);
/*     */               } else {
/*  94 */                 player.sendMessage(ChatColor.RED + "You already played " + maxSlotmachineCount + " times this hour, " + getTimeDifference(System.currentTimeMillis(), user.getResetSlotmachineCount()) + " left until you can play again!" + ChatColor.GRAY + " (Go to buy.lightpvp.net to play more times an hour!!)");
/*     */               } 
/*     */             } else {
/*  97 */               player.sendMessage(ChatColor.RED + "You don't have enough coins to do that!");
/*     */             } 
/*     */           } else {
/* 100 */             player.sendMessage(ChatColor.RED + "You can't use more than 1 slotmachine at a time!");
/*     */           } 
/*     */         } else {
/* 103 */           player.sendMessage(ChatColor.RED + "This slotmachine is already in use!");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTimeDifference(long start, long end) {
/* 111 */     long diff = end - start;
/*     */     
/* 113 */     long diffMinutes = diff / 60000L % 60L;
/*     */     
/* 115 */     return String.valueOf(diffMinutes) + " minutes";
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\casino\CasinoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */