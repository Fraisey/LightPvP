/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class ResurrectorKit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  43 */   ItemStack[] content = new ItemStack[36];
/*  44 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  46 */   ItemStack[] soups = new ItemStack[18];
/*     */   
/*  48 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public ResurrectorKit(Core core) {
/*  51 */     this.core = core;
/*     */     
/*  53 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  55 */     ItemStack resurrectItem = new ItemStack(Material.NETHER_STAR);
/*  56 */     ItemMeta resurrectMeta = resurrectItem.getItemMeta();
/*  57 */     resurrectMeta.setDisplayName(ChatColor.GREEN + "Resurrecting Star " + ChatColor.GRAY + "(Right Click)");
/*  58 */     List<String> resurrectLore = new ArrayList<>();
/*  59 */     resurrectLore.add(ChatColor.GRAY + "Use this and if you die in the 5 seconds of right clicking");
/*  60 */     resurrectLore.add(ChatColor.GRAY + "this item, you resurrect with two hotbars of soup and the default");
/*  61 */     resurrectLore.add(ChatColor.GRAY + "armour, sword etc...");
/*  62 */     resurrectMeta.setLore(resurrectLore);
/*  63 */     resurrectItem.setItemMeta(resurrectMeta);
/*     */     
/*  65 */     this.content = new ItemStack[] { core.ironSwordSharpness, resurrectItem, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  67 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  68 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  69 */     helmetMeta.setColor(Color.RED);
/*  70 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  72 */     ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  73 */     LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
/*  74 */     chestplateMeta.setColor(Color.RED);
/*  75 */     chestplate.setItemMeta((ItemMeta)chestplateMeta);
/*     */     
/*  77 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), chestplate, helmet };
/*     */     
/*  79 */     for (int i = 0; i < this.armor.length; i++) {
/*  80 */       if (this.armor[i] != null) {
/*  81 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*  82 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  83 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  84 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  89 */     this.armor[3].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */     
/*  91 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  92 */     this.armor[3].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  97 */     return "Resurrector";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/* 102 */     return "kit.resurrector";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/* 112 */     return Rareness.VERY_RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 117 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 119 */     p.getInventory().setContents(this.content);
/* 120 */     p.getInventory().setArmorContents(this.armor);
/* 121 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 126 */     return Material.NETHER_STAR;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 131 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your nether star", ChatColor.GRAY + "to have the ability to resurrect if you die", ChatColor.GRAY + "in 5 seconds after right clicking the nether star", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 136 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 143 */     if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 144 */       evt.getItem() != null && evt.getItem().getType().equals(Material.NETHER_STAR)) {
/* 145 */       final Player player = evt.getPlayer();
/* 146 */       if (this.cooldowns.containsKey(player.getName())) {
/* 147 */         long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 148 */         player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((60000.0D - cooldown) / 1000.0D) + " more seconds!");
/*     */       } else {
/* 150 */         player.setMetadata("resurrect", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/*     */         
/* 152 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "have " + ChatUtil.Gi + "5 " + ChatColor.GRAY + "seconds left to " + ChatUtil.Gi + "die " + ChatColor.GRAY + "to " + ChatUtil.Gi + "resurrect!");
/*     */         
/* 154 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run() {
/* 157 */               if (player.isOnline()) {
/* 158 */                 if (player.hasMetadata("resurrect")) {
/* 159 */                   player.removeMetadata("resurrect", (Plugin)ResurrectorKit.this.core);
/* 160 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "will not longer " + ChatUtil.Gi + "resurrect " + ChatColor.GRAY + "when you die!");
/*     */                 } 
/*     */               } else {
/* 163 */                 player.removeMetadata("resurrect", (Plugin)ResurrectorKit.this.core);
/*     */               } 
/*     */             }
/* 166 */           }).runTaskLater((Plugin)this.core, 100L);
/*     */         
/* 168 */         final String name = player.getName();
/* 169 */         this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 170 */         Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 174 */                 ResurrectorKit.this.cooldowns.remove(name);
/* 175 */                 player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */               }
/*     */             }, 
/* 178 */             1200L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageEvent(EntityDamageEvent evt) {
/* 186 */     if (evt.getEntity() instanceof Player) {
/* 187 */       final Player player = (Player)evt.getEntity();
/* 188 */       if (evt.getDamage() >= ((CraftPlayer)player).getHealth() && 
/* 189 */         this.core.getKitManager().isKit(player, (Kit)this) && 
/* 190 */         player.hasMetadata("resurrect")) {
/* 191 */         player.setHealth(((CraftPlayer)player).getMaxHealth());
/* 192 */         player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 4));
/* 193 */         this.soups = new ItemStack[] { this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup, this.core.soup };
/* 194 */         player.getInventory().addItem(this.soups);
/* 195 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "resurrected and gained " + ChatUtil.Gi + "2 hotbars " + ChatColor.GRAY + "of " + ChatUtil.Gi + "soup!");
/*     */         
/* 197 */         (new BukkitRunnable()
/*     */           {
/* 199 */             int count = 6;
/*     */ 
/*     */             
/*     */             public void run() {
/* 203 */               if (player.isOnline()) {
/* 204 */                 player.setVelocity(new Vector(0, 1, 0));
/* 205 */                 ResurrectorKit.this.core.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 20);
/* 206 */                 this.count--;
/* 207 */                 if (this.count <= 0) {
/* 208 */                   cancel();
/*     */                 }
/*     */               } else {
/* 211 */                 cancel();
/*     */               } 
/*     */             }
/* 214 */           }).runTaskTimer((Plugin)this.core, 0L, 3L);
/* 215 */         player.removeMetadata("resurrect", (Plugin)this.core);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\ResurrectorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */