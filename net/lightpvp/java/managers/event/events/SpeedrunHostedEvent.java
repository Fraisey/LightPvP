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
/*     */ import net.lightpvp.java.managers.event.arenas.speedrun.SpeedrunEventArenaMountains;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class SpeedrunHostedEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  29 */   private int canMoveTime = 15;
/*     */   private boolean canMove = false;
/*     */   
/*     */   public SpeedrunHostedEvent(HostedEventManager hostedEventManager) {
/*  33 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  35 */     this.arenas.add(new SpeedrunEventArenaMountains());
/*     */     
/*  37 */     this.arena = this.arenas.get(0);
/*     */     
/*  39 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Speedrun Menu");
/*     */     
/*  41 */     List<String> values = new ArrayList<>();
/*  42 */     for (EventArena arena : this.arenas) {
/*  43 */       values.add(arena.getName());
/*     */     }
/*  45 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*  46 */     this.menu.addSelector(1, ChatColor.GOLD + "Mode", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select mode!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "mode", Material.MAP, Arrays.asList(new String[] { "Spectate", "Play" }));
/*     */     
/*  48 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  53 */     return "Speedrun";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  58 */     return "event.speedrun";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  63 */     return "The first one to finish the track wins!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  68 */     return Material.SUGAR;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  73 */     return 50;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  81 */     for (Player p : this.players) {
/*  82 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/*  84 */       if (p.getVehicle() != null) {
/*  85 */         p.getVehicle().eject();
/*     */       }
/*     */       
/*  88 */       if (p.getPassenger() != null) {
/*  89 */         p.eject();
/*     */       }
/*     */       
/*  92 */       p.setFallDistance(0.0F);
/*  93 */       p.teleport(this.arena.getSpawn());
/*     */       
/*  95 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/*  96 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/*  98 */       this.hostedEventManager.getCore().getKitManager().removeKit(p);
/*     */       
/* 100 */       Util.clearPlayer(p);
/*     */       
/* 102 */       p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
/*     */       
/* 104 */       p.updateInventory();
/*     */     } 
/*     */     
/* 107 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 111 */           for (Player p : SpeedrunHostedEvent.this.players) {
/* 112 */             for (Player pO : SpeedrunHostedEvent.this.players) {
/* 113 */               if (!p.getName().equals(pO.getName()) && 
/* 114 */                 p.canSee(pO)) {
/* 115 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 121 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 125 */                 for (Player p : (SpeedrunHostedEvent.null.access$0(SpeedrunHostedEvent.null.this)).players) {
/* 126 */                   for (Player pO : (SpeedrunHostedEvent.null.access$0(SpeedrunHostedEvent.null.this)).players) {
/* 127 */                     if (!p.getName().equals(pO.getName())) {
/* 128 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 134 */             }).runTaskLater((Plugin)SpeedrunHostedEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 136 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 141 */     if (this.players.size() == 0) {
/* 142 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     if (!this.canMove) {
/* 147 */       switch (this.canMoveTime) {
/*     */         case 15:
/* 149 */           broadcastEvent(ChatColor.GRAY + "You can move in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 10:
/* 152 */           broadcastEvent(ChatColor.GRAY + "You can move in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 3:
/* 155 */           broadcastEvent(ChatColor.GRAY + "You can move in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 156 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 159 */           broadcastEvent(ChatColor.GRAY + "You can move in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 160 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 163 */           broadcastEvent(ChatColor.GRAY + "You can move in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 164 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 167 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Go!");
/* 168 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 169 */           this.canMove = true; return;
/*     */       } 
/* 171 */       this
/* 172 */         .canMoveTime = this.canMoveTime - 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 178 */     this.canMoveTime = 15;
/* 179 */     this.canMove = false;
/*     */   }
/*     */   
/*     */   public boolean getCanMove() {
/* 183 */     return this.canMove;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 188 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 189 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 190 */       this.hostedEventManager.registerEvent(player, (((Integer)data.get("mode")).intValue() == 1), this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\SpeedrunHostedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */