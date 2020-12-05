/*     */ package net.lightpvp.java.managers.kit;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.kits.BarbarianKit;
/*     */ import net.lightpvp.java.managers.kit.kits.FishermanKit;
/*     */ import net.lightpvp.java.managers.kit.kits.HulkKit;
/*     */ import net.lightpvp.java.managers.kit.kits.JokerKit;
/*     */ import net.lightpvp.java.managers.kit.kits.LassoKit;
/*     */ import net.lightpvp.java.managers.kit.kits.LongshotKit;
/*     */ import net.lightpvp.java.managers.kit.kits.PoseidonKit;
/*     */ import net.lightpvp.java.managers.kit.kits.PvPKit;
/*     */ import net.lightpvp.java.managers.kit.kits.RevealerKit;
/*     */ import net.lightpvp.java.managers.kit.kits.RocketKit;
/*     */ import net.lightpvp.java.managers.kit.kits.SeparatorKit;
/*     */ import net.lightpvp.java.managers.kit.kits.ShockwaveKit;
/*     */ import net.lightpvp.java.managers.kit.kits.SoldierKit;
/*     */ import net.lightpvp.java.managers.kit.kits.SonicKit;
/*     */ import net.lightpvp.java.managers.kit.kits.StomperKit;
/*     */ import net.lightpvp.java.managers.kit.kits.SummonerKit;
/*     */ import net.lightpvp.java.managers.kit.kits.SwitcherKit;
/*     */ import net.lightpvp.java.managers.kit.kits.TimebombKit;
/*     */ import net.lightpvp.java.managers.kit.kits.TitanKit;
/*     */ import net.lightpvp.java.managers.kit.kits.ViperKit;
/*     */ import net.lightpvp.java.managers.kit.kits.WitchKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.KangarooKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.LaserKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.NoSouperKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.OCANanoKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.PigC4Kit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.ResurrectorKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.RuneStoneKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.SprayerKit;
/*     */ import net.lightpvp.java.managers.kit.kits.specialkits.StunnerKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KitManager
/*     */ {
/*     */   private Core core;
/*  58 */   private Random random = new Random();
/*     */ 
/*     */   
/*  61 */   private Kit[] kitArray = null;
/*  62 */   private SpecialKit[] specialKitArray = null;
/*     */   
/*     */   public KitManager(Core core) {
/*  65 */     this.core = core;
/*     */     
/*  67 */     this.kitArray = new Kit[] { (Kit)new PvPKit(core), (Kit)new RevealerKit(core), (Kit)new ViperKit(core), (Kit)new ShockwaveKit(core), (Kit)new SwitcherKit(core), (Kit)new LongshotKit(core), (Kit)new FishermanKit(core), (Kit)new WitchKit(core), (Kit)new BarbarianKit(core), (Kit)new RocketKit(core), (Kit)new HulkKit(core), (Kit)new LassoKit(core), (Kit)new SonicKit(core), (Kit)new StomperKit(core), (Kit)new JokerKit(core), (Kit)new SoldierKit(core), (Kit)new TimebombKit(core), (Kit)new SeparatorKit(core), (Kit)new PoseidonKit(core), (Kit)new TitanKit(core), (Kit)new SummonerKit(core) };
/*  68 */     this.specialKitArray = new SpecialKit[] { (SpecialKit)new ResurrectorKit(core), (SpecialKit)new KangarooKit(core), (SpecialKit)new RuneStoneKit(core), (SpecialKit)new StunnerKit(core), (SpecialKit)new SprayerKit(core), (SpecialKit)new PigC4Kit(core), (SpecialKit)new LaserKit(core), (SpecialKit)new OCANanoKit(core), (SpecialKit)new NoSouperKit(core) };
/*     */   }
/*     */   
/*     */   public boolean hasKit(Player p, Kit kit) {
/*  72 */     if (kit instanceof SpecialKit) {
/*  73 */       return true;
/*     */     }
/*     */     
/*  76 */     return p.hasPermission(kit.getListedName());
/*     */   }
/*     */   
/*     */   public boolean choseKit(Player p) {
/*  80 */     User user = this.core.getUser(p.getUniqueId());
/*  81 */     if (!user.getKit().isEmpty()) {
/*  82 */       return true;
/*     */     }
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isKit(Player p, Kit k) {
/*  88 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  90 */     return user.getKit().equals(k.getName());
/*     */   }
/*     */   
/*     */   public void setKit(Player player, int kit) {
/*  94 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/*  96 */     if (hasKit(player, this.kitArray[kit])) {
/*  97 */       this.kitArray[kit].setKit(player);
/*  98 */       player.sendMessage(ChatColor.GRAY + "You chose the kit: " + ChatUtil.Gi + this.kitArray[kit].getName() + "!");
/*  99 */       player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1.0F, 0.7F);
/*     */       
/* 101 */       user.setLastKit(this.kitArray[kit].getName());
/*     */     } else {
/* 103 */       player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "don't own the " + ChatUtil.Gi + this.kitArray[kit].getName() + "! " + ChatColor.GRAY + "You can purchase it here: " + ChatUtil.Gi + "buy.lightpvp.net");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSpecialKit(Player player, int kit) {
/* 108 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 110 */     this.specialKitArray[kit].setKit(player);
/* 111 */     player.sendMessage(ChatColor.GRAY + "You chose the kit: " + ChatUtil.Gi + this.specialKitArray[kit].getName() + "!");
/* 112 */     player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1.0F, 0.7F);
/*     */     
/* 114 */     user.setLastKit(this.specialKitArray[kit].getName());
/*     */   }
/*     */   
/*     */   public void addSpecialKit(Player player, Kit k) {
/* 118 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 120 */     user.addSpecialKit(k.getListedName());
/*     */   }
/*     */   
/*     */   public void setKit(Player player, Kit kit, boolean deco) {
/* 124 */     if (hasKit(player, kit)) {
/* 125 */       kit.setKit(player);
/* 126 */       if (deco) {
/* 127 */         player.sendMessage(ChatColor.GRAY + "You chose the kit: " + ChatUtil.Gi + kit.getName());
/* 128 */         player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1.0F, 0.7F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKit(Player p) {
/* 135 */     User user = this.core.getUser(p.getUniqueId());
/* 136 */     user.setKit("");
/*     */   }
/*     */   
/*     */   public Kit[] getKitArray() {
/* 140 */     return this.kitArray;
/*     */   }
/*     */   
/*     */   public SpecialKit[] getSpecialKitArray() {
/* 144 */     return this.specialKitArray;
/*     */   }
/*     */   
/*     */   public Inventory getKitSelectorInventory(Player player) {
/* 148 */     Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Kit Selector"); byte b; int i; Kit[] arrayOfKit;
/* 149 */     for (i = (arrayOfKit = this.kitArray).length, b = 0; b < i; ) { Kit k = arrayOfKit[b];
/* 150 */       ItemStack isKit = new ItemStack(k.getMaterial());
/* 151 */       ItemMeta isKitMeta = isKit.getItemMeta();
/* 152 */       if (hasKit(player, k)) {
/* 153 */         isKitMeta.setDisplayName(ChatColor.GREEN + k.getName());
/* 154 */         isKitMeta.setLore(k.getAbilities());
/*     */       } else {
/* 156 */         isKitMeta.setDisplayName(ChatColor.RED + k.getName());
/* 157 */         isKitMeta.setLore(k.getAbilities());
/*     */       } 
/* 159 */       isKit.setItemMeta(isKitMeta);
/* 160 */       inv.setItem(inv.firstEmpty(), isKit); b++; }
/*     */     
/* 162 */     return inv;
/*     */   }
/*     */   
/*     */   public Inventory getSpecialKitSelectorInventory(Player player) {
/* 166 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 168 */     Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Special Kit Selector"); byte b; int i; SpecialKit[] arrayOfSpecialKit;
/* 169 */     for (i = (arrayOfSpecialKit = this.specialKitArray).length, b = 0; b < i; ) { Kit k = arrayOfSpecialKit[b];
/* 170 */       int freq = Collections.frequency(user.getSpecialKits(), k.getListedName());
/* 171 */       if (freq > 0) {
/* 172 */         ItemStack isKit = new ItemStack(k.getMaterial(), freq);
/* 173 */         ItemMeta isKitMeta = isKit.getItemMeta();
/* 174 */         isKitMeta.setDisplayName(ChatColor.GREEN + k.getName());
/* 175 */         isKitMeta.setLore(k.getAbilities());
/* 176 */         isKit.setItemMeta(isKitMeta);
/* 177 */         inv.setItem(inv.firstEmpty(), isKit);
/*     */       } else {
/* 179 */         ItemStack notUnlocked = new ItemStack(Material.IRON_FENCE);
/* 180 */         ItemMeta notUnlockedMeta = notUnlocked.getItemMeta();
/* 181 */         notUnlockedMeta.setDisplayName(ChatColor.RED + "Not unlocked yet!");
/* 182 */         notUnlockedMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Go win it at the casino! /warp casino" }));
/* 183 */         notUnlocked.setItemMeta(notUnlockedMeta);
/* 184 */         inv.setItem(inv.firstEmpty(), notUnlocked);
/*     */       }  b++; }
/*     */     
/* 187 */     return inv;
/*     */   }
/*     */   
/*     */   public SpecialKit getRandomSpecialKitByRareness(Rareness rareness) {
/* 191 */     List<SpecialKit> sortedSpecialKits = new ArrayList<>(); byte b; int i;
/*     */     SpecialKit[] arrayOfSpecialKit;
/* 193 */     for (i = (arrayOfSpecialKit = this.specialKitArray).length, b = 0; b < i; ) { SpecialKit sk = arrayOfSpecialKit[b];
/* 194 */       if (sk.getRareness().equals(rareness)) {
/* 195 */         sortedSpecialKits.add(sk);
/*     */       }
/*     */       b++; }
/*     */     
/* 199 */     if (sortedSpecialKits.size() > 0) {
/* 200 */       return sortedSpecialKits.get(this.random.nextInt(sortedSpecialKits.size()));
/*     */     }
/* 202 */     return this.specialKitArray[this.random.nextInt(this.specialKitArray.length)];
/*     */   }
/*     */ 
/*     */   
/*     */   public Inventory getKitShopInventory(Player player) {
/* 207 */     Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Kit Shop"); byte b; int i; Kit[] arrayOfKit;
/* 208 */     for (i = (arrayOfKit = this.kitArray).length, b = 0; b < i; ) { Kit k = arrayOfKit[b];
/* 209 */       ItemStack isKit = new ItemStack(k.getMaterial());
/* 210 */       ItemMeta isKitMeta = isKit.getItemMeta();
/* 211 */       if (hasKit(player, k)) {
/* 212 */         isKitMeta.setDisplayName(ChatColor.GREEN + k.getName());
/* 213 */         isKitMeta.setLore(Arrays.asList(new String[] { ChatColor.RED + "You already own this kit!" }));
/*     */       } else {
/* 215 */         isKitMeta.setDisplayName(ChatColor.RED + k.getName());
/* 216 */         isKitMeta.setLore(Arrays.asList(new String[] { ChatColor.GOLD + "Coins: " + k.getPrice() }));
/*     */       } 
/* 218 */       isKit.setItemMeta(isKitMeta);
/* 219 */       inv.setItem(inv.firstEmpty(), isKit); b++; }
/*     */     
/* 221 */     return inv;
/*     */   }
/*     */   
/*     */   public Kit getKit(Player player) {
/* 225 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 227 */     if (!user.getKit().isEmpty()) {
/* 228 */       return getKit(user.getKit());
/*     */     }
/*     */     
/* 231 */     return this.kitArray[0]; } public Kit getKit(String kitName) {
/*     */     byte b;
/*     */     int i;
/*     */     Kit[] arrayOfKit;
/* 235 */     for (i = (arrayOfKit = this.kitArray).length, b = 0; b < i; ) { Kit kit = arrayOfKit[b];
/* 236 */       if (kit.getName().equalsIgnoreCase(kitName))
/* 237 */         return kit; 
/*     */       b++; }
/*     */     
/*     */     SpecialKit[] arrayOfSpecialKit;
/* 241 */     for (i = (arrayOfSpecialKit = this.specialKitArray).length, b = 0; b < i; ) { Kit kit = arrayOfSpecialKit[b];
/* 242 */       if (kit.getName().equalsIgnoreCase(kitName)) {
/* 243 */         return kit;
/*     */       }
/*     */       b++; }
/*     */     
/* 247 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\KitManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */