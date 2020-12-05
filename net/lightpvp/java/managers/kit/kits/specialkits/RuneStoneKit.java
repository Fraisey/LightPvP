/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.EnderCrystal;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class RuneStoneKit
/*     */   implements Listener, SpecialKit {
/*     */   private Core core;
/*  42 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  44 */   ItemStack[] content = new ItemStack[36];
/*  45 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public RuneStoneKit(Core core) {
/*  48 */     this.core = core;
/*     */     
/*  50 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  52 */     ItemStack teleporterBeacon = new ItemStack(Material.BEACON);
/*  53 */     ItemMeta teleportMeta = teleporterBeacon.getItemMeta();
/*  54 */     teleportMeta.setDisplayName(ChatColor.GREEN + "Teleporter's Block Teleport Location " + ChatColor.GRAY + "(Right Click)");
/*  55 */     List<String> teleportLore = new ArrayList<>();
/*  56 */     teleportLore.add(ChatColor.GRAY + "Use this to place a block where you are standing.");
/*  57 */     teleportLore.add(ChatColor.GRAY + "Once your block has been placed, Right Click your beacon");
/*  58 */     teleportLore.add(ChatColor.GRAY + "again, to be teleported back to that location with no delay!");
/*  59 */     teleportMeta.setLore(teleportLore);
/*  60 */     teleporterBeacon.setItemMeta(teleportMeta);
/*     */     
/*  62 */     ItemStack boots = new ItemStack(Material.IRON_BOOTS);
/*     */     
/*  64 */     ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
/*     */     
/*  66 */     ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  67 */     LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
/*  68 */     chestMeta.setColor(Color.BLUE);
/*  69 */     chest.setItemMeta((ItemMeta)chestMeta);
/*     */     
/*  71 */     ItemStack helm = new ItemStack(Material.IRON_HELMET);
/*  72 */     this.content = new ItemStack[] { core.ironSwordSharpness, teleporterBeacon, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  73 */     this.armor = new ItemStack[] { boots, legs, chest, helm };
/*     */     
/*  75 */     for (int i = 0; i < this.armor.length; i++) {
/*  76 */       if (this.armor[i] != null) {
/*  77 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  79 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  80 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  81 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  86 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return "Runestone";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  96 */     return "kit.runestone";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/* 106 */     return Rareness.VERY_RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 111 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 113 */     p.getInventory().setContents(this.content);
/* 114 */     p.getInventory().setArmorContents(this.armor);
/* 115 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 120 */     return Material.BEACON;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 125 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your beacon", ChatColor.GRAY + "to have the ability to set a block at your location", ChatColor.GRAY + "right click again to teleport back to that location!", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 130 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 137 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 138 */       final Player player = evt.getPlayer();
/*     */       
/* 140 */       if (evt.getItem() != null && evt.getItem().getType().equals(Material.BEACON) && 
/* 141 */         this.core.getKitManager().isKit(player, (Kit)this)) {
/* 142 */         if (this.cooldowns.containsKey(player.getName()) && !player.hasMetadata("canTeleport")) {
/* 143 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 144 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 145 */           evt.setCancelled(true);
/*     */         } else {
/* 147 */           EnderCrystal runeStone = getRunestone(player);
/*     */           
/* 149 */           if (runeStone == null) {
/* 150 */             final EnderCrystal newRuneStone = (EnderCrystal)this.core.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_CRYSTAL);
/*     */             
/* 152 */             (new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 156 */                   if (newRuneStone != null && !newRuneStone.isDead()) {
/* 157 */                     player.sendMessage(String.valueOf(ChatUtil.Gi) + "Your" + ChatColor.GRAY + " Runestone dissapeared!");
/* 158 */                     newRuneStone.removeMetadata("owner", (Plugin)RuneStoneKit.this.core);
/* 159 */                     newRuneStone.remove();
/*     */                   } 
/*     */                 }
/* 162 */               }).runTaskLater((Plugin)this.core, 1200L);
/*     */             
/* 164 */             newRuneStone.setMetadata("owner", (MetadataValue)new FixedMetadataValue((Plugin)this.core, player.getUniqueId().toString()));
/* 165 */             player.sendMessage(ChatColor.GRAY + "When you " + ChatUtil.Gi + "right click " + ChatColor.GRAY + "your " + ChatUtil.Gi + "Runestone " + ChatColor.GRAY + "you will be teleported back to this location!");
/* 166 */             player.sendMessage(ChatColor.RED + "NOTE: You HAVE to be in a 50 block radius of this location to teleport back here!");
/*     */           } else {
/* 168 */             player.teleport((Entity)runeStone);
/* 169 */             runeStone.removeMetadata("owner", (Plugin)this.core);
/* 170 */             runeStone.remove();
/* 171 */             player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "teleported to your " + ChatUtil.Gi + "ender crystal!");
/*     */             
/* 173 */             final String name = player.getName();
/* 174 */             this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 175 */             Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 179 */                     RuneStoneKit.this.cooldowns.remove(name);
/* 180 */                     player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                   }
/*     */                 }, 
/* 183 */                 600L);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerQuitEvent(PlayerQuitEvent evt) {
/* 194 */     Player player = evt.getPlayer();
/* 195 */     if (this.core.getKitManager().isKit(player, (Kit)this)) {
/* 196 */       EnderCrystal runeStone = getRunestone(player);
/* 197 */       if (runeStone != null) {
/* 198 */         runeStone.removeMetadata("owner", (Plugin)this.core);
/* 199 */         runeStone.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDeathEvent(PlayerDeathEvent evt) {
/* 206 */     Player player = evt.getEntity();
/* 207 */     if (this.core.getKitManager().isKit(player, (Kit)this)) {
/* 208 */       EnderCrystal runeStone = getRunestone(player);
/* 209 */       if (runeStone != null) {
/* 210 */         runeStone.removeMetadata("owner", (Plugin)this.core);
/* 211 */         runeStone.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnderCrystal getRunestone(Player player) {
/* 217 */     for (Entity e : this.core.getWorld().getEntities()) {
/* 218 */       if (e instanceof EnderCrystal && 
/* 219 */         e.hasMetadata("owner")) {
/* 220 */         UUID owner = UUID.fromString(((MetadataValue)e.getMetadata("owner").get(0)).asString());
/* 221 */         if (owner != null && player.getUniqueId().equals(owner)) {
/* 222 */           return (EnderCrystal)e;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\RuneStoneKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */