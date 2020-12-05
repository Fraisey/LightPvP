/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class SeparatorKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  40 */   ItemStack[] content = new ItemStack[36];
/*  41 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  43 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  45 */   private HashMap<String, Location> lastLoc = new HashMap<>();
/*  46 */   public static ArrayList<String> in1v1 = new ArrayList<>();
/*  47 */   private HashMap<String, Integer> timer = new HashMap<>();
/*     */   
/*     */   public SeparatorKit(Core core) {
/*  50 */     this.core = core;
/*     */     
/*  52 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  54 */     ItemStack gold1v1 = new ItemStack(Material.GOLD_INGOT);
/*  55 */     ItemMeta itemMeta1 = gold1v1.getItemMeta();
/*  56 */     itemMeta1.setDisplayName(ChatColor.GREEN + "Separator's separating 1v1 " + ChatColor.GRAY + "(Right Click Player)");
/*  57 */     ArrayList<String> lore1 = new ArrayList<>();
/*  58 */     lore1.add(ChatColor.GRAY + "Use this to instantly teleport up into a glass box in which");
/*  59 */     lore1.add(ChatColor.GRAY + "you will 1v1 your target until the death!");
/*  60 */     itemMeta1.setLore(lore1);
/*  61 */     gold1v1.setItemMeta(itemMeta1);
/*     */     
/*  63 */     this.content = new ItemStack[] { core.ironSwordSharpness, gold1v1, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  64 */     this.armor = new ItemStack[] { new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.GOLD_HELMET) };
/*     */     
/*  66 */     for (int i = 0; i < this.armor.length; i++) {
/*  67 */       if (this.armor[i] != null) {
/*  68 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*  69 */         this.armor[0].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  70 */         this.armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  71 */         this.armor[3].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */         
/*  73 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  74 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  75 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return "Separator";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  87 */     return "kit.separator";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  92 */     return 75000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  97 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  99 */     p.getInventory().setContents(this.content);
/* 100 */     p.getInventory().setArmorContents(this.armor);
/* 101 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 106 */     return Material.GOLD_INGOT;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 111 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your kit item to", ChatColor.GRAY + "separate a player into a private 1v1.", ChatColor.GRAY + "Use this against teams", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 116 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEntityEvent evt) {
/* 123 */     final Player player = evt.getPlayer();
/* 124 */     if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.GOLD_INGOT) && 
/* 125 */       evt.getRightClicked() instanceof Player) {
/* 126 */       final Player clicked = (Player)evt.getRightClicked();
/* 127 */       if (this.core.getKitManager().hasKit(player, this)) {
/* 128 */         if (this.cooldowns.containsKey(player.getName())) {
/* 129 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 130 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((90000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 131 */           evt.setCancelled(true);
/*     */         } else {
/* 133 */           if (in1v1.contains(clicked.getName()) || in1v1.contains(player.getName())) {
/* 134 */             player.sendMessage(ChatColor.RED + "AN ERROR OCCURED: You can't use this while in a 1v1!");
/*     */             return;
/*     */           } 
/* 137 */           Location locPlayer = player.getLocation();
/* 138 */           Location locClicked = clicked.getLocation();
/* 139 */           in1v1.add(player.getName());
/* 140 */           in1v1.add(clicked.getName());
/* 141 */           this.lastLoc.put(player.getName(), locPlayer);
/* 142 */           this.lastLoc.put(clicked.getName(), locClicked);
/* 143 */           this.timer.put(player.getName(), Integer.valueOf(0));
/* 144 */           for (Player p : Bukkit.getOnlinePlayers()) {
/* 145 */             clicked.hidePlayer(p);
/* 146 */             player.hidePlayer(p);
/*     */           } 
/* 148 */           clicked.showPlayer(player);
/* 149 */           player.showPlayer(clicked);
/* 150 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "are now in a " + ChatUtil.Gi + "separator " + ChatColor.GRAY + "1v1 against " + ChatUtil.Gi + RankUtil.getFullTag(clicked) + "!");
/* 151 */           clicked.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " used their " + ChatUtil.Gi + "separator " + ChatColor.GRAY + "ability against " + ChatUtil.Gi + "you!");
/* 152 */           player.teleport(new Location(player.getWorld(), -611.5D, 210.0D, 851.5D, 0.0F, 0.0F));
/* 153 */           clicked.teleport(new Location(clicked.getWorld(), -611.5D, 210.0D, 879.5D, -180.0F, 0.0F));
/*     */           
/* 155 */           (new BukkitRunnable() {
/*     */               public void run() {
/* 157 */                 if (SeparatorKit.this.timer.containsKey(player.getName())) {
/* 158 */                   SeparatorKit.this.timer.put(player.getName(), Integer.valueOf(((Integer)SeparatorKit.this.timer.get(player.getName())).intValue() + 1));
/*     */                 } else {
/* 160 */                   cancel();
/*     */                 } 
/* 162 */                 if (SeparatorKit.in1v1.contains(clicked.getName()) && !SeparatorKit.in1v1.contains(player.getName())) {
/* 163 */                   clicked.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "won the 1v1!");
/* 164 */                   if (SeparatorKit.this.lastLoc.containsKey(clicked.getName())) {
/* 165 */                     clicked.teleport((Location)SeparatorKit.this.lastLoc.get(clicked.getName()));
/*     */                   }
/* 167 */                   for (Player p : Bukkit.getOnlinePlayers()) {
/* 168 */                     clicked.showPlayer(p);
/*     */                   }
/* 170 */                   SeparatorKit.this.lastLoc.remove(clicked.getName());
/* 171 */                   SeparatorKit.in1v1.remove(clicked.getName());
/* 172 */                   clicked.removePotionEffect(PotionEffectType.WITHER);
/* 173 */                   SeparatorKit.this.timer.remove(player.getName());
/* 174 */                   cancel();
/* 175 */                 } else if (!SeparatorKit.in1v1.contains(clicked.getName()) && SeparatorKit.in1v1.contains(player.getName())) {
/* 176 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "won the 1v1!");
/* 177 */                   if (SeparatorKit.this.lastLoc.containsKey(player.getName())) {
/* 178 */                     player.teleport((Location)SeparatorKit.this.lastLoc.get(player.getName()));
/*     */                   }
/* 180 */                   for (Player p : Bukkit.getOnlinePlayers()) {
/* 181 */                     player.showPlayer(p);
/*     */                   }
/* 183 */                   SeparatorKit.in1v1.remove(player.getName());
/* 184 */                   SeparatorKit.this.lastLoc.remove(player.getName());
/* 185 */                   player.removePotionEffect(PotionEffectType.WITHER);
/* 186 */                   SeparatorKit.this.timer.remove(player.getName());
/* 187 */                   cancel();
/*     */                 } 
/* 189 */                 if (SeparatorKit.this.timer.containsKey(player.getName()) && (
/* 190 */                   (Integer)SeparatorKit.this.timer.get(player.getName())).intValue() == 60 && 
/* 191 */                   SeparatorKit.in1v1.contains(clicked.getName()) && SeparatorKit.in1v1.contains(player.getName())) {
/* 192 */                   clicked.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2147483647, 1));
/* 193 */                   player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2147483647, 1));
/* 194 */                   clicked.sendMessage(String.valueOf(ChatUtil.Gi) + "Quick! " + ChatColor.GRAY + "You gained the wither effect due to your time in the 1v1!");
/* 195 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "Quick! " + ChatColor.GRAY + "You gained the wither effect due to your time in the 1v1!");
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/* 200 */             }).runTaskTimer((Plugin)this.core, 20L, 20L);
/*     */           
/* 202 */           final String name = player.getName();
/* 203 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 204 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 208 */                   SeparatorKit.this.cooldowns.remove(name);
/*     */                 }
/*     */               }, 
/* 211 */               400L);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDeathEvent(PlayerDeathEvent evt) {
/* 220 */     Player player = evt.getEntity();
/* 221 */     if (evt.getEntity().getKiller() instanceof Player) {
/* 222 */       Player killer = player.getKiller();
/* 223 */       if (in1v1.contains(player.getName())) {
/* 224 */         if (killer != null) {
/* 225 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "lost the " + ChatUtil.Gi + "separator " + ChatColor.GRAY + "1v1 against " + ChatUtil.Gi + RankUtil.getFullTag(killer) + "!");
/* 226 */           killer.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "won the " + ChatUtil.Gi + "separator " + ChatColor.GRAY + "1v1 against " + ChatUtil.Gi + RankUtil.getFullTag(player) + "!");
/* 227 */           for (Player p : Bukkit.getOnlinePlayers()) {
/* 228 */             killer.showPlayer(p);
/* 229 */             player.showPlayer(p);
/*     */           } 
/* 231 */           if (this.lastLoc.containsKey(player.getName())) {
/* 232 */             final List<ItemStack> items = evt.getDrops();
/* 233 */             final ArrayList<ItemStack> itemlist = new ArrayList<>();
/* 234 */             itemlist.addAll(items);
/* 235 */             Location f = this.lastLoc.get(player.getName());
/* 236 */             evt.getDrops().clear();
/* 237 */             for (ItemStack it : itemlist) {
/* 238 */               final Item itemonGround = f.getWorld().dropItemNaturally(f, it);
/* 239 */               itemonGround.teleport(f);
/* 240 */               Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.core, new Runnable() {
/*     */                     public void run() {
/* 242 */                       itemlist.remove(itemonGround);
/* 243 */                       items.remove(itemonGround);
/*     */                     }
/* 245 */                   },  100L);
/*     */             } 
/*     */           } 
/* 248 */           in1v1.remove(player.getName());
/* 249 */           this.lastLoc.remove(player.getName());
/*     */         } else {
/* 251 */           for (Player p : Bukkit.getOnlinePlayers()) {
/* 252 */             player.showPlayer(p);
/*     */           }
/* 254 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "lost the " + ChatUtil.Gi + "separator " + ChatColor.GRAY + "1v1 against " + ChatUtil.Gi + RankUtil.getFullTag(killer) + "!");
/* 255 */           if (this.lastLoc.containsKey(player.getName())) {
/* 256 */             final List<ItemStack> items = evt.getDrops();
/* 257 */             final ArrayList<ItemStack> itemlist = new ArrayList<>();
/* 258 */             itemlist.addAll(items);
/* 259 */             Location f = this.lastLoc.get(player.getName());
/* 260 */             evt.getDrops().clear();
/* 261 */             for (ItemStack it : itemlist) {
/* 262 */               final Item itemonGround = f.getWorld().dropItemNaturally(f, it);
/* 263 */               itemonGround.teleport(f);
/* 264 */               Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.core, new Runnable() {
/*     */                     public void run() {
/* 266 */                       itemlist.remove(itemonGround);
/* 267 */                       items.remove(itemonGround);
/*     */                     }
/* 269 */                   },  100L);
/* 270 */               this.lastLoc.remove(player.getName());
/* 271 */               in1v1.remove(player.getName());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
/*     */   public void onPlayerQuitEvent(PlayerQuitEvent evt) {
/* 281 */     Player player = evt.getPlayer();
/* 282 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/* 284 */     if (in1v1.contains(player.getName())) {
/* 285 */       in1v1.remove(player.getName());
/* 286 */       this.lastLoc.remove(player.getName());
/* 287 */       this.core.toSpawn(user, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerPreprocessCommandEvent(PlayerCommandPreprocessEvent evt) {
/* 293 */     if (in1v1.contains(evt.getPlayer().getName())) {
/* 294 */       evt.setCancelled(true);
/* 295 */       evt.getPlayer().sendMessage(ChatColor.RED + "You can't type a command in a separator 1v1!");
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDropItemEvent(PlayerDropItemEvent evt) {
/* 301 */     if (in1v1.contains(evt.getPlayer().getName()))
/* 302 */       evt.getItemDrop().remove(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\SeparatorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */