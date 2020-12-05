/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HostedEvent
/*     */ {
/*     */   protected AdvancedInventory menu;
/*     */   protected UUID hostUUID;
/*  27 */   protected List<UUID> queue = new ArrayList<>();
/*  28 */   protected List<Player> players = new ArrayList<>();
/*     */   
/*  30 */   protected List<EventArena> arenas = new ArrayList<>();
/*  31 */   protected EventArena arena = null;
/*     */   
/*  33 */   private int roundChangeTime = 60;
/*     */   
/*     */   private boolean started = false;
/*     */   
/*     */   public final UUID getHostUUID() {
/*  38 */     return this.hostUUID;
/*     */   }
/*     */   
/*     */   public final void setHoster(UUID uuid) {
/*  42 */     this.hostUUID = uuid;
/*     */   }
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract String getListedName();
/*     */   
/*     */   public abstract String getDescription();
/*     */   
/*     */   public abstract Material getIcon();
/*     */   
/*     */   public abstract int getMaximumPlayers();
/*     */   
/*     */   public final Inventory getMenu() {
/*  56 */     return this.menu.construct();
/*     */   }
/*     */   
/*     */   public final EventArena getArena() {
/*  60 */     return this.arena;
/*     */   }
/*     */   
/*     */   public final void setStartTime(int time) {
/*  64 */     this.roundChangeTime = time;
/*     */   }
/*     */   
/*     */   public final int getStartTime() {
/*  68 */     return this.roundChangeTime;
/*     */   }
/*     */   
/*     */   public final boolean getStarted() {
/*  72 */     return this.started;
/*     */   }
/*     */   
/*     */   public final void setIsStarted(boolean flag) {
/*  76 */     this.started = flag;
/*     */   }
/*     */   
/*     */   public final List<UUID> getQueue() {
/*  80 */     return this.queue;
/*     */   }
/*     */   
/*     */   public final boolean addPlayerToQueue(Player player) {
/*  84 */     if (!this.queue.contains(player.getUniqueId())) {
/*  85 */       this.queue.add(player.getUniqueId());
/*  86 */       return true;
/*     */     } 
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public final boolean removePlayerFromQueue(Player player) {
/*  92 */     if (this.queue.contains(player.getUniqueId())) {
/*  93 */       this.queue.remove(player.getUniqueId());
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public void broadcastQueue(String message) {
/* 100 */     for (UUID uuid : this.queue) {
/* 101 */       Player p = Core.getNameFromUUID(uuid);
/* 102 */       if (p != null) {
/* 103 */         p.sendMessage(message);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     if (!this.queue.contains(this.hostUUID)) {
/* 108 */       Player host = Core.getNameFromUUID(this.hostUUID);
/* 109 */       if (host != null) {
/* 110 */         host.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final List<Player> getPlayers() {
/* 116 */     return this.players;
/*     */   }
/*     */   
/*     */   public final boolean containsPlayer(Player player) {
/* 120 */     return this.players.contains(player);
/*     */   }
/*     */   
/*     */   public final void removePlayer(Player player) {
/* 124 */     this.players.remove(player);
/*     */   }
/*     */   
/*     */   public void playerLeave(Player player) {
/* 128 */     broadcastEvent(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " left the " + ChatUtil.Gi + "event!" + ChatColor.GRAY + " (" + (getPlayers().size() - 1) + " left!)");
/* 129 */     removePlayer(player);
/*     */   }
/*     */   
/*     */   public void broadcastEvent(String message) {
/* 133 */     for (Player p : this.players) {
/* 134 */       if (p != null && p.isOnline()) {
/* 135 */         p.sendMessage(message);
/*     */       }
/*     */     } 
/*     */     
/* 139 */     Player host = Core.getNameFromUUID(this.hostUUID);
/* 140 */     if (host != null && 
/* 141 */       !this.players.contains(host)) {
/* 142 */       host.sendMessage(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastEventSound(Sound sound) {
/* 148 */     for (Player p : this.players) {
/* 149 */       if (p != null && p.isOnline()) {
/* 150 */         p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */     
/* 154 */     Player host = Core.getNameFromUUID(this.hostUUID);
/* 155 */     if (host != null && 
/* 156 */       !this.players.contains(host))
/* 157 */       host.playSound(host.getLocation(), sound, 1.0F, 1.0F); 
/*     */   }
/*     */   
/*     */   public abstract void start();
/*     */   
/*     */   public abstract void update();
/*     */   
/*     */   public abstract void reset();
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\HostedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */