/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.onemanarmy.OneManArmyEventArenaGreen;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class OneManArmyEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private HostedEventManager hostedEventManager;
/*  38 */   private UUID armyUUID = null;
/*     */   
/*  40 */   private int isProtectionTime = 15;
/*     */   private boolean isProtection = true;
/*     */   
/*     */   public OneManArmyEvent(HostedEventManager hostedEventManager) {
/*  44 */     this.hostedEventManager = hostedEventManager;
/*     */     
/*  46 */     this.arenas.add(new OneManArmyEventArenaGreen());
/*     */     
/*  48 */     this.arena = this.arenas.get(0);
/*     */     
/*  50 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "One Man Army Menu");
/*     */     
/*  52 */     List<String> values = new ArrayList<>();
/*  53 */     for (EventArena arena : this.arenas) {
/*  54 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  57 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*     */     
/*  59 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  64 */     return "One Man Army";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  69 */     return "event.oma";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  74 */     return "One player will get OP stuff and has to kill all the others!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  79 */     return Material.IRON_SWORD;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/*  84 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  92 */     Kit pvpKit = this.hostedEventManager.getCore().getKitManager().getKit("PvP");
/*     */     
/*  94 */     for (Player p : this.players) {
/*  95 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/*  97 */       if (p.getVehicle() != null) {
/*  98 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 101 */       if (p.getPassenger() != null) {
/* 102 */         p.eject();
/*     */       }
/*     */       
/* 105 */       p.setFallDistance(0.0F);
/* 106 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 108 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 109 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 111 */       Util.clearPlayer(p);
/*     */       
/* 113 */       this.hostedEventManager.getCore().getKitManager().setKit(p, pvpKit, false);
/*     */       
/* 115 */       p.updateInventory();
/*     */     } 
/*     */     
/* 118 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 122 */           for (Player p : OneManArmyEvent.this.players) {
/* 123 */             for (Player pO : OneManArmyEvent.this.players) {
/* 124 */               if (!p.getName().equals(pO.getName()) && 
/* 125 */                 p.canSee(pO)) {
/* 126 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 132 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 136 */                 for (Player p : (OneManArmyEvent.null.access$0(OneManArmyEvent.null.this)).players) {
/* 137 */                   for (Player pO : (OneManArmyEvent.null.access$0(OneManArmyEvent.null.this)).players) {
/* 138 */                     if (!p.getName().equals(pO.getName())) {
/* 139 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 145 */             }).runTaskLater((Plugin)OneManArmyEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 147 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 152 */     if (this.players.size() == 0) {
/* 153 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */     }
/*     */     
/* 156 */     if (this.isProtection) {
/* 157 */       switch (this.isProtectionTime) {
/*     */         case 15:
/* 159 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 10:
/* 162 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds!");
/*     */           break;
/*     */         case 3:
/* 165 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/* 166 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 169 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds!");
/* 170 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 173 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Starting" + ChatColor.GRAY + " in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second!");
/* 174 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 177 */           pickRandomArmy();
/* 178 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 179 */           this.isProtection = false; return;
/*     */       } 
/* 181 */       this
/* 182 */         .isProtectionTime = this.isProtectionTime - 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void pickRandomArmy() {
/* 187 */     Player armyPlayer = null;
/* 188 */     int triesLeft = 10;
/*     */     do {
/* 190 */       armyPlayer = this.players.get((new Random()).nextInt(this.players.size()));
/* 191 */       triesLeft--;
/* 192 */       if (triesLeft > 0)
/* 193 */         continue;  this.hostedEventManager.stopCurrentEvent(null);
/*     */     }
/* 195 */     while (armyPlayer == null);
/*     */     
/* 197 */     this.armyUUID = armyPlayer.getUniqueId(); byte b; int i;
/*     */     ItemStack[] arrayOfItemStack;
/* 199 */     for (i = (arrayOfItemStack = armyPlayer.getInventory().getArmorContents()).length, b = 0; b < i; ) { ItemStack is = arrayOfItemStack[b];
/* 200 */       if (is != null) {
/* 201 */         is.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/* 202 */         is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
/*     */       } 
/*     */       b++; }
/*     */     
/* 206 */     armyPlayer.getInventory().getArmorContents()[3].setType(Material.DIAMOND_HELMET);
/*     */     
/* 208 */     ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
/* 209 */     sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
/* 210 */     armyPlayer.getInventory().setItem(0, sword);
/*     */     
/* 212 */     armyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 1));
/*     */     
/* 214 */     broadcastEvent(String.valueOf(RankUtil.getFullTag(armyPlayer)) + ChatColor.GRAY + " has been selected to be the " + ChatUtil.Gi + "One Man Army!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 219 */     this.armyUUID = null;
/*     */     
/* 221 */     this.isProtectionTime = 15;
/* 222 */     this.isProtection = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerLeave(Player player) {
/* 227 */     super.playerLeave(player);
/*     */     
/* 229 */     if (player.getUniqueId().equals(this.armyUUID)) {
/* 230 */       ChatUtil.broadcastMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatUtil.Gi + " died " + ChatColor.GRAY + "as the " + ChatUtil.Gi + "One Man Army " + ChatColor.GRAY + ", the remaining players received " + ChatColor.GOLD + "800 coins!");
/*     */       
/* 232 */       for (Player p : this.players) {
/* 233 */         this.hostedEventManager.getCore().getPlayerStatsManager().incrementInt(p.getUniqueId(), 800, PlayerStat.COINS);
/*     */       }
/*     */       
/* 236 */       this.hostedEventManager.stopCurrentEvent(null);
/* 237 */     } else if (this.players.size() == 1) {
/* 238 */       Player army = Core.getNameFromUUID(this.armyUUID);
/* 239 */       if (army != null) {
/* 240 */         this.hostedEventManager.stopCurrentEvent(army);
/*     */       } else {
/* 242 */         this.hostedEventManager.stopCurrentEvent(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getIsProtection() {
/* 248 */     return this.isProtection;
/*     */   }
/*     */   
/*     */   public UUID getArmy() {
/* 252 */     return this.armyUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 257 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 258 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 259 */       this.hostedEventManager.registerEvent(player, true, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\OneManArmyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */