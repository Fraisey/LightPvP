/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.lms.LMSEventArenaCave;
/*     */ import net.lightpvp.java.managers.event.arenas.lms.LMSEventArenaClean;
/*     */ import net.lightpvp.java.managers.event.arenas.lms.LMSEventArenaIslands;
/*     */ import net.lightpvp.java.managers.event.arenas.lms.LMSEventArenaRuins;
/*     */ import net.lightpvp.java.managers.event.arenas.lms.LMSEventArenaSciFi;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class LMSHostedEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  32 */   private int isProtectionTime = 15;
/*     */   private boolean isProtection = true;
/*     */   
/*     */   public LMSHostedEvent(HostedEventManager hostedEventManager) {
/*  36 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  38 */     this.arenas.add(new LMSEventArenaClean());
/*  39 */     this.arenas.add(new LMSEventArenaSciFi());
/*  40 */     this.arenas.add(new LMSEventArenaRuins());
/*  41 */     this.arenas.add(new LMSEventArenaCave());
/*  42 */     this.arenas.add(new LMSEventArenaIslands());
/*     */     
/*  44 */     this.arena = this.arenas.get(0);
/*     */     
/*  46 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Last Man Standing Menu");
/*     */     
/*  48 */     List<String> values = new ArrayList<>();
/*  49 */     for (EventArena arena : this.arenas) {
/*  50 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  53 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*  54 */     this.menu.addSelector(1, ChatColor.GOLD + "Mode", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select mode!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "mode", Material.MAP, Arrays.asList(new String[] { "Spectate", "Play" }));
/*     */     
/*  56 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  61 */     return "Last Man Standing";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  66 */     return "event.lms";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  71 */     return "The Last Man standing wins the event!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  76 */     return Material.DIAMOND_SWORD;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  81 */     return 50;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  89 */     Kit pvpKit = this.hostedEventManager.getCore().getKitManager().getKit("PvP");
/*     */     
/*  91 */     for (Player p : this.players) {
/*  92 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/*  94 */       if (p.getVehicle() != null) {
/*  95 */         p.getVehicle().eject();
/*     */       }
/*     */       
/*  98 */       if (p.getPassenger() != null) {
/*  99 */         p.eject();
/*     */       }
/*     */       
/* 102 */       p.setFallDistance(0.0F);
/* 103 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 105 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 106 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 108 */       Util.clearPlayer(p);
/*     */       
/* 110 */       this.hostedEventManager.getCore().getKitManager().setKit(p, pvpKit, false);
/*     */       
/* 112 */       p.updateInventory();
/*     */     } 
/*     */     
/* 115 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 119 */           for (Player p : LMSHostedEvent.this.players) {
/* 120 */             for (Player pO : LMSHostedEvent.this.players) {
/* 121 */               if (!p.getName().equals(pO.getName()) && 
/* 122 */                 p.canSee(pO)) {
/* 123 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 129 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 133 */                 for (Player p : (LMSHostedEvent.null.access$0(LMSHostedEvent.null.this)).players) {
/* 134 */                   for (Player pO : (LMSHostedEvent.null.access$0(LMSHostedEvent.null.this)).players) {
/* 135 */                     if (!p.getName().equals(pO.getName())) {
/* 136 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 142 */             }).runTaskLater((Plugin)LMSHostedEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 144 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 149 */     if (this.players.size() <= 1) {
/* 150 */       if (this.players.size() == 1) {
/* 151 */         Player winner = this.players.get(0);
/* 152 */         if (winner != null && winner.isOnline()) {
/* 153 */           this.hostedEventManager.stopCurrentEvent(winner);
/*     */           return;
/*     */         } 
/*     */       } 
/* 157 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 161 */     if (this.isProtection) {
/* 162 */       switch (this.isProtectionTime) {
/*     */         case 15:
/* 164 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 10:
/* 167 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 3:
/* 170 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 171 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 174 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 175 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 178 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Protection" + ChatColor.GRAY + " will wear off in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 179 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 182 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Fight!");
/* 183 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 184 */           this.isProtection = false; return;
/*     */       } 
/* 186 */       this
/* 187 */         .isProtectionTime = this.isProtectionTime - 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 193 */     this.isProtectionTime = 15;
/* 194 */     this.isProtection = true;
/*     */   }
/*     */   
/*     */   public boolean getIsProtection() {
/* 198 */     return this.isProtection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 203 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 204 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 205 */       this.hostedEventManager.registerEvent(player, (((Integer)data.get("mode")).intValue() == 1), this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\LMSHostedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */