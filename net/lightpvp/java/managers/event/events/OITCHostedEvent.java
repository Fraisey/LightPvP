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
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class OITCHostedEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  32 */   private int isProtectionTime = 15;
/*     */   private boolean isProtection = true;
/*     */   
/*     */   public OITCHostedEvent(HostedEventManager hostedEventManager) {
/*  36 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  38 */     this.arenas.add(new LMSEventArenaSciFi());
/*  39 */     this.arenas.add(new LMSEventArenaRuins());
/*  40 */     this.arenas.add(new LMSEventArenaCave());
/*  41 */     this.arenas.add(new LMSEventArenaIslands());
/*     */     
/*  43 */     this.arena = this.arenas.get(0);
/*     */     
/*  45 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "One In The Chamber Menu");
/*     */     
/*  47 */     List<String> values = new ArrayList<>();
/*  48 */     for (EventArena arena : this.arenas) {
/*  49 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  52 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*     */     
/*  54 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  59 */     return "One In The Chamber";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  64 */     return "event.oitc";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  69 */     return "You have three lifes, last man standing wins!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  74 */     return Material.BOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  79 */     return 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  86 */     Kit pvpKit = this.hostedEventManager.getCore().getKitManager().getKit("PvP");
/*     */     
/*  88 */     for (Player p : this.players) {
/*  89 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/*  91 */       if (p.getVehicle() != null) {
/*  92 */         p.getVehicle().eject();
/*     */       }
/*     */       
/*  95 */       if (p.getPassenger() != null) {
/*  96 */         p.eject();
/*     */       }
/*     */       
/*  99 */       p.setFallDistance(0.0F);
/* 100 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 102 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/*     */       
/* 104 */       Util.clearPlayer(p);
/*     */       
/* 106 */       this.hostedEventManager.getCore().getKitManager().setKit(p, pvpKit, false);
/*     */       
/* 108 */       p.updateInventory();
/*     */     } 
/*     */     
/* 111 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 115 */           for (Player p : OITCHostedEvent.this.players) {
/* 116 */             for (Player pO : OITCHostedEvent.this.players) {
/* 117 */               if (!p.getName().equals(pO.getName()) && 
/* 118 */                 p.canSee(pO)) {
/* 119 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 125 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 129 */                 for (Player p : (OITCHostedEvent.null.access$0(OITCHostedEvent.null.this)).players) {
/* 130 */                   for (Player pO : (OITCHostedEvent.null.access$0(OITCHostedEvent.null.this)).players) {
/* 131 */                     if (!p.getName().equals(pO.getName())) {
/* 132 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 138 */             }).runTaskLater((Plugin)OITCHostedEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 140 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 145 */     if (this.players.size() <= 1) {
/* 146 */       if (this.players.size() == 1) {
/* 147 */         Player winner = this.players.get(0);
/* 148 */         if (winner != null && winner.isOnline()) {
/* 149 */           this.hostedEventManager.stopCurrentEvent(winner);
/*     */           return;
/*     */         } 
/*     */       } 
/* 153 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 157 */     if (this.isProtection)
/* 158 */       switch (this.isProtectionTime) {
/*     */         case 15:
/* 160 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "OITC" + ChatColor.GRAY + " will begin in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */         
/*     */         case 10:
/* 163 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "OITC" + ChatColor.GRAY + " will begin in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */         
/*     */         case 3:
/* 166 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "OITC" + ChatColor.GRAY + " will begin in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 167 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 2:
/* 170 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "OITC" + ChatColor.GRAY + " will begin in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 171 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 1:
/* 174 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "OITC" + ChatColor.GRAY + " will begin in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 175 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */         
/*     */         case 0:
/* 178 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Fight!");
/* 179 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 180 */           this.isProtection = false; break;
/*     */         default:
/* 182 */           this
/* 183 */             .isProtectionTime = this.isProtectionTime - 1;
/*     */           break;
/*     */       }  
/* 186 */     oitcStart();
/*     */   }
/*     */ 
/*     */   
/*     */   private void oitcStart() {
/* 191 */     for (Player p : this.players) {
/* 192 */       p.getInventory().clear();
/* 193 */       p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
/* 194 */       p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BOW) });
/* 195 */       p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
/* 196 */       p.sendMessage(ChatColor.GRAY + "Shoot other " + ChatUtil.Gi + "players " + ChatColor.GRAY + "with your bow and you will one shot them. You get an extra arrow per kill.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 202 */     this.isProtectionTime = 15;
/* 203 */     this.isProtection = true;
/*     */   }
/*     */   
/*     */   public boolean getIsProtection() {
/* 207 */     return this.isProtection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 212 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 213 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 214 */       this.hostedEventManager.registerEvent(player, true, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\OITCHostedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */