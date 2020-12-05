/*     */ package net.lightpvp.java.managers.event.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.event.HostedEventManager;
/*     */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*     */ import net.lightpvp.java.managers.event.arenas.csgo.CSGOEventArenaDustII;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
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
/*     */ public class CSGOEvent
/*     */   extends HostedEvent
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*  41 */   ItemStack[] content = new ItemStack[36];
/*  42 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  44 */   private ArrayList<Player> terroistPlayers = new ArrayList<>();
/*  45 */   private ArrayList<Player> cterroistPlayers = new ArrayList<>();
/*     */   
/*  47 */   private int cterroistScore = 0, terroistScore = 0;
/*     */   
/*  49 */   private int roundStart = 13;
/*     */   
/*     */   private boolean isRoundStart = true;
/*     */   private boolean firstRun = true;
/*  53 */   private int roundNumber = 10;
/*     */   
/*     */   private HostedEventManager hostedEventManager;
/*     */   
/*     */   public CSGOEvent(HostedEventManager hostedEventManager) {
/*  58 */     this.hostedEventManager = hostedEventManager;
/*  59 */     this.content = new ItemStack[36];
/*  60 */     this.armor = new ItemStack[] { new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
/*     */     
/*  62 */     for (int i = 0; i < this.armor.length; i++) {
/*  63 */       if (this.armor[i] != null) {
/*  64 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  66 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  67 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  68 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     this.arenas.add(new CSGOEventArenaDustII());
/*     */     
/*  74 */     this.arena = this.arenas.get(0);
/*     */     
/*  76 */     this.menu = new AdvancedInventory(hostedEventManager.getCore(), this, 9, "Counter Strike Menu");
/*     */     
/*  78 */     List<String> values = new ArrayList<>();
/*  79 */     for (EventArena arena : this.arenas) {
/*  80 */       values.add(arena.getName());
/*     */     }
/*     */     
/*  83 */     this.menu.addSelector(0, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, values);
/*  84 */     this.menu.addSelector(1, ChatColor.GOLD + "Mode", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select mode!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "mode", Material.MAP, Arrays.asList(new String[] { "Spectate", "Play" }));
/*     */     
/*  86 */     this.menu.addButton(8, ChatColor.GOLD + "Host", Arrays.asList(new String[] { "", ChatColor.GOLD + ChatColor.BOLD + "Click to host this event, with the selected settings" }, ), "start", Material.ENDER_PEARL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return "Counter Strike";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  96 */     return "event.csgo";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 101 */     return "First to five wins!";
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/* 106 */     return Material.DIAMOND_HOE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumPlayers() {
/* 111 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 119 */     Kit pvpKit = this.hostedEventManager.getCore().getKitManager().getKit("PvP");
/*     */     
/* 121 */     for (Player p : this.players) {
/* 122 */       User user = this.hostedEventManager.getCore().getUser(p.getUniqueId());
/*     */       
/* 124 */       if (p.getVehicle() != null) {
/* 125 */         p.getVehicle().eject();
/*     */       }
/*     */       
/* 128 */       if (p.getPassenger() != null) {
/* 129 */         p.eject();
/*     */       }
/*     */       
/* 132 */       p.setFallDistance(0.0F);
/* 133 */       p.teleport(this.arena.getSpawn());
/*     */       
/* 135 */       this.hostedEventManager.getCore().setSpawnFlag(user, false);
/* 136 */       this.hostedEventManager.getCore().removeWarpFlag(user);
/*     */       
/* 138 */       Util.clearPlayer(p);
/*     */       
/* 140 */       this.hostedEventManager.getCore().getKitManager().setKit(p, pvpKit, false);
/*     */       
/* 142 */       p.updateInventory();
/*     */     } 
/*     */     
/* 145 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 149 */           for (Player p : CSGOEvent.this.players) {
/* 150 */             for (Player pO : CSGOEvent.this.players) {
/* 151 */               if (!p.getName().equals(pO.getName()) && 
/* 152 */                 p.canSee(pO)) {
/* 153 */                 p.hidePlayer(pO);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 159 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 163 */                 for (Player p : (CSGOEvent.null.access$0(CSGOEvent.null.this)).players) {
/* 164 */                   for (Player pO : (CSGOEvent.null.access$0(CSGOEvent.null.this)).players) {
/* 165 */                     if (!p.getName().equals(pO.getName())) {
/* 166 */                       p.showPlayer(pO);
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               }
/* 172 */             }).runTaskLater((Plugin)CSGOEvent.this.hostedEventManager.getCore(), 20L);
/*     */         }
/* 174 */       }).runTaskLater((Plugin)this.hostedEventManager.getCore(), 40L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 179 */     if (this.players.size() == 1 || this.players.size() == 0) {
/* 180 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 184 */     if (this.cterroistScore >= 5) {
/* 185 */       for (Player p : this.cterroistPlayers) {
/* 186 */         Util.spawnFireworks(p.getLocation());
/* 187 */         this.hostedEventManager.getCore().getPlayerStatsManager().incrementInt(p.getUniqueId(), 500, PlayerStat.COINS);
/*     */       } 
/*     */       
/* 190 */       ChatUtil.broadcastMessage(ChatColor.BLUE + "COUNTER TERRORIST" + ChatColor.GRAY + " team won the " + ChatUtil.Gi + "Counter Strike " + ChatColor.GRAY + "event and all the players on the winning team won " + ChatColor.GOLD + "500 coins!");
/* 191 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 195 */     if (this.terroistScore >= 5) {
/* 196 */       for (Player p : this.terroistPlayers) {
/* 197 */         Util.spawnFireworks(p.getLocation());
/* 198 */         this.hostedEventManager.getCore().getPlayerStatsManager().incrementInt(p.getUniqueId(), 500, PlayerStat.COINS);
/*     */       } 
/*     */       
/* 201 */       ChatUtil.broadcastMessage(ChatColor.RED + "TERRORIST" + ChatColor.GRAY + " team won the " + ChatUtil.Gi + "Counter Strike " + ChatColor.GRAY + "event and all the players on the winning team won " + ChatColor.GOLD + "500 coins!");
/* 202 */       this.hostedEventManager.stopCurrentEvent(null);
/*     */       
/*     */       return;
/*     */     } 
/* 206 */     if (this.isRoundStart) {
/* 207 */       switch (this.roundStart) {
/*     */         case 15:
/* 209 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "The round will start in " + ChatColor.GOLD + "15" + ChatColor.GRAY + " seconds! " + ChatUtil.Gi + "Round number: " + this.roundNumber);
/* 210 */           if (this.firstRun = true) {
/* 211 */             selectTeams();
/* 212 */             this.firstRun = false;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 216 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "The round will start in " + ChatColor.GOLD + "10" + ChatColor.GRAY + " seconds! " + ChatUtil.Gi + "Round number: " + this.roundNumber);
/*     */           break;
/*     */         case 3:
/* 219 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "The round will start in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds! " + ChatUtil.Gi + "Round number: " + this.roundNumber);
/* 220 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 2:
/* 223 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "The round will start in " + ChatColor.GOLD + "2" + ChatColor.GRAY + " seconds! " + ChatUtil.Gi + "Round number: " + this.roundNumber);
/* 224 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 1:
/* 227 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "The round will start in " + ChatColor.GOLD + "1" + ChatColor.GRAY + " second! " + ChatUtil.Gi + "Round number: " + this.roundNumber);
/* 228 */           broadcastEventSound(Sound.NOTE_PLING);
/*     */           break;
/*     */         case 0:
/* 231 */           broadcastEvent(String.valueOf(ChatUtil.Gi) + "Fight!");
/* 232 */           broadcastEventSound(Sound.ANVIL_LAND);
/* 233 */           this.isRoundStart = false; return;
/*     */       } 
/* 235 */       this
/* 236 */         .roundStart = this.roundStart - 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectTeams() {
/* 241 */     int playerIndex = 0;
/*     */     
/* 243 */     for (Player p : this.players) {
/* 244 */       if (playerIndex % 2 == 0) {
/* 245 */         p.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " are in team: " + ChatColor.BLUE + ChatColor.BOLD + "COUNTER TERRORIST!");
/* 246 */         this.cterroistPlayers.add(p);
/* 247 */         p.teleport(this.arena.getCTSpawn());
/*     */       } else {
/* 249 */         p.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " are in team: " + ChatColor.RED + ChatColor.BOLD + "TERRORIST!");
/* 250 */         this.terroistPlayers.add(p);
/* 251 */         p.teleport(this.arena.getTSpawn());
/*     */       } 
/*     */       
/* 254 */       playerIndex++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 260 */     this.roundStart = 13;
/* 261 */     this.isRoundStart = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 266 */     if (this.hostedEventManager.canRegisterEvent(player, this)) {
/* 267 */       this.arena = this.arenas.get(((Integer)data.get("arena")).intValue());
/* 268 */       this.hostedEventManager.registerEvent(player, (((Integer)data.get("mode")).intValue() == 1), this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\events\CSGOEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */