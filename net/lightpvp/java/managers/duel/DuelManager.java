/*     */ package net.lightpvp.java.managers.duel;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.inventory.AdvancedInventory;
/*     */ import net.lightpvp.java.inventory.AdvancedInventoryTrigger;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuelManager
/*     */   implements AdvancedInventoryTrigger
/*     */ {
/*     */   private Core core;
/*  36 */   private Random random = new Random();
/*     */   
/*     */   private Location spawn;
/*     */   
/*  40 */   private List<DuelData> duelData = new ArrayList<>();
/*     */ 
/*     */   
/*  43 */   private List<DuelArena> arenas = new ArrayList<>();
/*  44 */   private List<DuelLoadout> loadouts = new ArrayList<>();
/*  45 */   private List<DuelPotion> potions = new ArrayList<>();
/*     */   
/*     */   private AdvancedInventory menu;
/*     */   
/*     */   public DuelManager(Core core) {
/*  50 */     this.core = core;
/*     */     
/*  52 */     this.spawn = new Location(Bukkit.getWorld("world"), -246.5D, 92.0D, 738.5D);
/*     */     
/*  54 */     this.arenas.add(new DuelArena("Clean", new Location(Bukkit.getWorld("world"), -272.5D, 80.0D, 690.5D, -180.0F, 0.0F), new Location(Bukkit.getWorld("world"), -272.5D, 80.0D, 666.5D, 0.0F, 0.0F)));
/*  55 */     this.arenas.add(new DuelArena("Clean II", new Location(Bukkit.getWorld("world"), -247.5D, 115.0D, 814.5D, 0.0F, 0.0F), new Location(Bukkit.getWorld("world"), -247.5D, 115.0D, 830.5D, 180.0F, 0.0F)));
/*  56 */     this.arenas.add(new DuelArena("Jungle", new Location(Bukkit.getWorld("world"), -171.5D, 54.0D, 728.5D, 0.0F, 0.0F), new Location(Bukkit.getWorld("world"), -171.5D, 54.0D, 758.5D, 180.0F, 0.0F)));
/*  57 */     this.arenas.add(new DuelArena("Ice", new Location(Bukkit.getWorld("world"), -339.5D, 59.0D, 740.5D, -50.0F, 0.0F), new Location(Bukkit.getWorld("world"), -327.5D, 59.0D, 750.5D, 130.0F, 0.0F)));
/*  58 */     this.arenas.add(new DuelArena("Castle", new Location(Bukkit.getWorld("world"), -318.5D, 144.0D, 854.5D, 48.0F, 0.0F), new Location(Bukkit.getWorld("world"), -342.5D, 144.0D, 875.5D, 228.0F, 0.0F)));
/*  59 */     this.arenas.add(new DuelArena("Buttons", new Location(Bukkit.getWorld("world"), -324.5D, 108.0D, 661.5D, 0.0F, 0.0F), new Location(Bukkit.getWorld("world"), -324.5D, 108.0D, 679.5D, -180.0F, 0.0F)));
/*     */     
/*  61 */     List<String> arenaValues = new ArrayList<>();
/*  62 */     for (DuelArena arena : this.arenas) {
/*  63 */       arenaValues.add(arena.getName());
/*     */     }
/*     */     
/*  66 */     this.loadouts.add(new DuelLoadout("Diamond Sharpness, Iron Armor", new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup }, new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) }));
/*  67 */     this.loadouts.add(new DuelLoadout("Stone Sword, No Armor", new ItemStack[] { new ItemStack(Material.STONE_SWORD), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup }, new ItemStack[0]));
/*  68 */     this.loadouts.add(new DuelLoadout("Diamond Sharpness, Diamond Armor", new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup }, new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET) }));
/*  69 */     this.loadouts.add(new DuelLoadout("Diamond Sharpness, Iron Armor, no soups", new ItemStack[] { core.diamondSwordSharpness, new ItemStack(Material.FISHING_ROD) }, new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) }));
/*  70 */     this.loadouts.add(new DuelLoadout("Diamond Sharpness, Enchant Diamond Armor, no soups", new ItemStack[] { core.diamondOpSharpness, new ItemStack(Material.FISHING_ROD), core.Stick, core.Bow, new ItemStack(Material.ARROW) }, new ItemStack[] { core.Boots, core.Leggings, core.Chestplate, core.Helmet }));
/*     */     
/*  72 */     List<String> loadoutValues = new ArrayList<>();
/*  73 */     for (DuelLoadout loadout : this.loadouts) {
/*  74 */       loadoutValues.add(loadout.getName());
/*     */     }
/*     */     
/*  77 */     this.potions.add(new DuelPotion("None", null));
/*  78 */     this.potions.add(new DuelPotion("Speed I", new PotionEffect(PotionEffectType.SPEED, 2147483647, 0)));
/*  79 */     this.potions.add(new DuelPotion("Strength I", new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 0)));
/*  80 */     this.potions.add(new DuelPotion("Speed II", new PotionEffect(PotionEffectType.SPEED, 2147483647, 1)));
/*  81 */     this.potions.add(new DuelPotion("Slow I", new PotionEffect(PotionEffectType.SLOW, 2147483647, 0)));
/*     */     
/*  83 */     List<String> potionValues = new ArrayList<>();
/*  84 */     for (DuelPotion potion : this.potions) {
/*  85 */       potionValues.add(potion.getName());
/*     */     }
/*     */     
/*  88 */     this.menu = new AdvancedInventory(core, this, 27, "Duel Maker");
/*     */     
/*  90 */     this.menu.addSelector(1, ChatColor.GOLD + "Arena", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select arena!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "arena", Material.BOAT, arenaValues);
/*  91 */     this.menu.addSelector(3, ChatColor.GOLD + "Loadout", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select loadout!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "loadout", Material.DIAMOND_CHESTPLATE, loadoutValues);
/*  92 */     this.menu.addSelector(5, ChatColor.GOLD + "Potion", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Select potion!", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "potion", Material.POTION, potionValues);
/*  93 */     this.menu.addSelector(7, ChatColor.GOLD + "Refills", Arrays.asList(new String[] { "", ChatColor.BLUE + ChatColor.BOLD + "Refills?", "", ChatColor.GOLD + ChatColor.BOLD + "Middle Click:" }, ), "refills", Material.MUSHROOM_SOUP, Arrays.asList(new String[] { "Off", "On" }));
/*     */     
/*  95 */     this.menu.addButton(22, ChatColor.GREEN + "Invite", Arrays.asList(new String[] { ChatColor.GOLD + "Click to invite the player with these settings" }, ), "invite", Material.ENDER_PEARL);
/*     */   }
/*     */   
/*     */   public void openMenu(Player player, Player target) {
/*  99 */     getDuelData(player).setInvited(target.getName());
/* 100 */     player.openInventory(this.menu.construct());
/*     */   }
/*     */ 
/*     */   
/*     */   public void toDuelArena(Player player) {
/* 105 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 107 */     if (!isInDuelArena(player)) {
/* 108 */       this.duelData.add(new DuelData(player));
/* 109 */       player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "You are now at the 1v1 Arena!");
/*     */     } 
/*     */     
/* 112 */     this.core.getKitManager().removeKit(player);
/* 113 */     this.core.setSpawnFlag(user, false);
/*     */     
/* 115 */     Util.clearPlayer(player);
/*     */     
/* 117 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 118 */       if (!p.getName().equals(player.getName())) {
/* 119 */         player.showPlayer(p);
/*     */       }
/*     */     } 
/*     */     
/* 123 */     player.getInventory().setItem(0, this.core.inviteTool);
/* 124 */     player.getInventory().setItem(8, this.core.quickMatchupOffTool);
/* 125 */     player.updateInventory();
/* 126 */     player.teleport(this.spawn);
/*     */   }
/*     */   
/*     */   public void removeDuelData(Player player) {
/* 130 */     DuelGame dg = getDuelData(player).getDuel();
/* 131 */     if (dg != null) {
/* 132 */       stopDuel(dg, player.getName().equals(dg.getPlayer1().getName()) ? dg.getPlayer2() : dg.getPlayer1());
/*     */     }
/*     */     
/* 135 */     for (int i = 0; i < this.duelData.size(); i++) {
/* 136 */       if (player.getName().equals(((DuelData)this.duelData.get(i)).getPlayer().getName())) {
/* 137 */         this.duelData.remove(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void quickMatchup(Player player) {
/* 143 */     DuelData dd = getDuelData(player);
/*     */     
/* 145 */     if (this.core.getHostedEventManager().getCurrentEvent() != null && 
/* 146 */       this.core.getHostedEventManager().getCurrentEvent().getQueue().contains(player.getName())) {
/* 147 */       player.sendMessage(ChatColor.GRAY + "You can't enter the " + ChatUtil.Gi + "Quick Matchup" + ChatColor.GRAY + ", because you are in the " + ChatUtil.Gi + "Event Queue!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 152 */     if (dd.isQuickMatchup()) {
/* 153 */       dd.setQuickMatchup(false);
/* 154 */       player.setItemInHand(this.core.quickMatchupOffTool);
/* 155 */       player.sendMessage(ChatColor.GRAY + "You left the " + ChatUtil.Gi + "Quick Matchup" + ChatColor.GRAY + " queue!");
/*     */     } else {
/* 157 */       for (DuelData cDD : this.duelData) {
/* 158 */         if (cDD.isQuickMatchup() && cDD.getDuel() == null) {
/*     */           
/* 160 */           Player invited = cDD.getPlayer();
/* 161 */           player.sendMessage(ChatColor.GRAY + "Match has been found against " + RankUtil.getFullTag(invited));
/* 162 */           invited.sendMessage(ChatColor.GRAY + "Match has been found against " + RankUtil.getFullTag(player));
/* 163 */           startDuel(new DuelGame(this, player, invited, ((DuelArena)this.arenas.get(this.random.nextInt(this.arenas.size()))).clone(), ((DuelLoadout)this.loadouts.get(0)).clone(), ((DuelPotion)this.potions.get(0)).clone()));
/*     */           return;
/*     */         } 
/*     */       } 
/* 167 */       dd.setQuickMatchup(true);
/* 168 */       player.setItemInHand(this.core.quickMatchupOnTool);
/* 169 */       player.sendMessage(ChatColor.GRAY + "You have been added to the " + ChatUtil.Gi + "Quick Matchup" + ChatColor.GRAY + " queue!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void inviteDuel(Player player, HashMap<String, Integer> data) {
/* 174 */     DuelData di = getDuelData(player);
/* 175 */     if (di != null) {
/* 176 */       Player invited = Bukkit.getPlayerExact(di.getInvited());
/* 177 */       if (invited != null) {
/* 178 */         DuelData ddInvited = getDuelData(invited);
/* 179 */         if (ddInvited != null) {
/* 180 */           if (ddInvited.getDuel() == null) {
/*     */             
/* 182 */             DuelArena arena = ((DuelArena)this.arenas.get(((Integer)data.get("arena")).intValue())).clone();
/*     */             
/* 184 */             DuelLoadout loadout = ((DuelLoadout)this.loadouts.get(((Integer)data.get("loadout")).intValue())).clone();
/*     */             
/* 186 */             if (((Integer)data.get("refills")).intValue() == 1) {
/* 187 */               loadout.addRefills();
/*     */             }
/*     */             
/* 190 */             DuelPotion potion = ((DuelPotion)this.potions.get(((Integer)data.get("potion")).intValue())).clone();
/*     */             
/* 192 */             di.setArena(arena);
/* 193 */             di.setLoadout(loadout);
/* 194 */             di.setPotion(potion);
/*     */ 
/*     */             
/* 197 */             di.setActive(true);
/*     */ 
/*     */             
/* 200 */             player.sendMessage(ChatColor.GRAY + " You have invited " + RankUtil.getFullTag(invited) + ChatColor.GRAY + " to a duel! \n(Arena: " + ChatUtil.Gi + di.getArena().getName() + ChatColor.GRAY + ", Loadout: " + ChatUtil.Gi + di.getLoadout().getName() + ChatColor.GRAY + ")");
/* 201 */             invited.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " has invited you to a duel! \n(Arena: " + ChatUtil.Gi + di.getArena().getName() + ChatColor.GRAY + ", Loadout: " + ChatUtil.Gi + di.getLoadout().getName() + ChatColor.GRAY + ")");
/*     */           } else {
/* 203 */             player.sendMessage(ChatColor.GRAY + "The player you tried to invite is already " + ChatUtil.Gi + "dueling!");
/*     */           } 
/*     */         } else {
/* 206 */           player.sendMessage(ChatColor.GRAY + "The player you tried to invite is not playing " + ChatUtil.Gi + "1v1!");
/*     */         } 
/*     */       } else {
/* 209 */         player.sendMessage(ChatColor.GRAY + "The player you tried to invite is not online!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void acceptDuel(Player player, Player target) {
/* 215 */     DuelData di = getDuelData(target);
/* 216 */     if (di != null && di.isActive() && di.getInvited().equals(player.getName())) {
/* 217 */       target.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " has accepted your " + ChatUtil.Gi + "1v1" + ChatColor.GRAY + " invite!");
/* 218 */       player.sendMessage(String.valueOf(ChatUtil.Gi) + "You" + ChatColor.GRAY + " have accepted " + RankUtil.getFullTag(target) + ChatUtil.Gi + " 1v1" + ChatColor.GRAY + " invite!");
/* 219 */       DuelGame dg = new DuelGame(this, player, target, di.getArena(), di.getLoadout(), di.getPotion());
/* 220 */       startDuel(dg);
/*     */     } else {
/* 222 */       player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " has not invited you to a duel!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startDuel(DuelGame dg) {
/* 227 */     getDuelData(dg.getPlayer1()).setDuel(dg);
/* 228 */     getDuelData(dg.getPlayer2()).setDuel(dg);
/*     */     
/* 230 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 231 */       if (!p.getName().equals(dg.getPlayer1().getName())) {
/* 232 */         dg.getPlayer1().hidePlayer(p);
/*     */       }
/*     */       
/* 235 */       if (!p.getName().equals(dg.getPlayer2().getName())) {
/* 236 */         dg.getPlayer2().hidePlayer(p);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     dg.start();
/*     */   }
/*     */   
/*     */   public void stopDuel(DuelGame dg, final Player winner) {
/* 244 */     Player player1 = dg.getPlayer1();
/* 245 */     Player player2 = dg.getPlayer2();
/* 246 */     dg.sendMessage(String.valueOf(RankUtil.getFullTag(winner)) + ChatColor.GRAY + " has won the " + ChatUtil.Gi + "duel!");
/* 247 */     this.core.getPlayerStatsManager().incrementInt(winner.getUniqueId(), 1, PlayerStat.DUELWINS);
/* 248 */     this.core.getPlayerStatsManager().incrementInt(winner.getUniqueId(), 250, PlayerStat.COINS);
/*     */     
/* 250 */     int soupsLeftPlayer1 = 0;
/* 251 */     int soupsLeftPlayer2 = 0; int i;
/* 252 */     for (i = 0; i < 36; i++) {
/* 253 */       ItemStack cStack = player1.getInventory().getItem(i);
/* 254 */       if (cStack != null && cStack.getType().equals(Material.MUSHROOM_SOUP)) {
/* 255 */         soupsLeftPlayer1++;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     for (i = 0; i < 36; i++) {
/* 260 */       ItemStack cStack = player2.getInventory().getItem(i);
/* 261 */       if (cStack != null && cStack.getType().equals(Material.MUSHROOM_SOUP)) {
/* 262 */         soupsLeftPlayer2++;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     if (winner.getName().equals(player1.getName())) {
/* 267 */       player1.sendMessage(String.valueOf(RankUtil.getFullTag(player2)) + ChatColor.GRAY + " had " + ChatColor.BLUE + soupsLeftPlayer2 + " soups " + ChatColor.GRAY + "left");
/* 268 */       player2.sendMessage(String.valueOf(RankUtil.getFullTag(player1)) + ChatColor.GRAY + " had " + ChatColor.BLUE + (new DecimalFormat("#.#", this.core.decimalSymbol)).format(((CraftPlayer)player1).getHealth() / 2.0D) + " health left" + ChatColor.GRAY + ", and " + ChatColor.BLUE + soupsLeftPlayer1 + " soups " + ChatColor.GRAY + "left");
/*     */     } else {
/* 270 */       player2.sendMessage(String.valueOf(RankUtil.getFullTag(player1)) + ChatColor.GRAY + " had " + ChatColor.BLUE + soupsLeftPlayer1 + " soups " + ChatColor.GRAY + "left");
/* 271 */       player1.sendMessage(String.valueOf(RankUtil.getFullTag(player2)) + ChatColor.GRAY + " had " + ChatColor.BLUE + (new DecimalFormat("#.#", this.core.decimalSymbol)).format(((CraftPlayer)player2).getHealth() / 2.0D) + " health left" + ChatColor.GRAY + ", and " + ChatColor.BLUE + soupsLeftPlayer2 + " soups " + ChatColor.GRAY + "left");
/*     */     } 
/*     */ 
/*     */     
/* 275 */     getDuelData(player1).reset();
/* 276 */     getDuelData(player2).reset();
/*     */     
/* 278 */     (new BukkitRunnable()
/*     */       {
/*     */         Location start;
/*     */         
/*     */         int count;
/*     */         
/*     */         public void run() {
/* 285 */           if (this.count <= 0) {
/* 286 */             DuelManager.this.toDuelArena(winner);
/* 287 */             cancel();
/*     */           } else {
/* 289 */             if (winner.getLocation().distanceSquared(this.start) <= 80.0D) {
/*     */               
/* 291 */               winner.playSound(winner.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
/* 292 */               if (this.count % 2 == 0) {
/* 293 */                 winner.playSound(winner.getLocation(), Sound.NOTE_BASS_DRUM, 1.0F, 1.0F);
/*     */               }
/* 295 */               if (this.count % 3 == 0) {
/* 296 */                 winner.playSound(winner.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 1.0F);
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 301 */             this.count--;
/*     */           } 
/*     */         }
/* 304 */       }).runTaskTimer((Plugin)this.core, 10L, 10L);
/*     */   }
/*     */   
/*     */   public boolean isInDuelArena(Player player) {
/* 308 */     for (DuelData dd : this.duelData) {
/* 309 */       if (player.getName().equals(dd.getPlayer().getName())) {
/* 310 */         return true;
/*     */       }
/*     */     } 
/* 313 */     return false;
/*     */   }
/*     */   
/*     */   public DuelGame isDueling(Player player) {
/* 317 */     DuelData dd = getDuelData(player);
/* 318 */     return (dd == null) ? null : dd.getDuel();
/*     */   }
/*     */   
/*     */   public DuelData getDuelData(Player player) {
/* 322 */     for (DuelData dd : this.duelData) {
/* 323 */       if (player.getName().equals(dd.getPlayer().getName())) {
/* 324 */         return dd;
/*     */       }
/*     */     } 
/* 327 */     return null;
/*     */   }
/*     */   
/*     */   public Core getCore() {
/* 331 */     return this.core;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(Player player, String title, String button, HashMap<String, Integer> data) {
/* 336 */     inviteDuel(player, data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */