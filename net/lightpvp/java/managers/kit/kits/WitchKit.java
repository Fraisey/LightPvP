/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WitchKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  41 */   ItemStack[] content = new ItemStack[36];
/*  42 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  44 */   List<Item> trackedGrenades = new ArrayList<>();
/*     */   
/*  46 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public WitchKit(Core core) {
/*  49 */     this.core = core;
/*     */     
/*  51 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  53 */     this.content = new ItemStack[] { core.diamondSwordSharpness, new ItemStack(Material.BAKED_POTATO, 10), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  55 */     ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
/*  56 */     LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
/*  57 */     helmetMeta.setColor(Color.PURPLE);
/*  58 */     helmet.setItemMeta((ItemMeta)helmetMeta);
/*     */     
/*  60 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.CHAINMAIL_CHESTPLATE), helmet };
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
/*  72 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  76 */           Iterator<Item> it = WitchKit.this.trackedGrenades.iterator();
/*  77 */           while (it.hasNext()) {
/*  78 */             Item item = it.next();
/*  79 */             if (item.isDead() || item == null) {
/*  80 */               it.remove();
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*  86 */       }).runTaskTimer((Plugin)core, 0L, 2L);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return "Witch";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  96 */     return "kit.witch";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 101 */     return 68000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 106 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 108 */     p.getInventory().setContents(this.content);
/* 109 */     p.getInventory().setArmorContents(this.armor);
/* 110 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 115 */     return Material.BAKED_POTATO;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 120 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right Click your potato at a player and", "give him poison III and blindness", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 125 */     return new ItemStack(Material.BAKED_POTATO, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 132 */     if (evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 133 */       final Player player = evt.getPlayer();
/* 134 */       if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.BAKED_POTATO) && 
/* 135 */         this.core.getKitManager().isKit(player, this))
/* 136 */         if (this.cooldowns.containsKey(player.getName())) {
/* 137 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 138 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((7000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 139 */           evt.setCancelled(true);
/*     */         } else {
/* 141 */           final Item i = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.BAKED_POTATO));
/* 142 */           i.setVelocity(player.getLocation().getDirection().multiply(0.7F));
/* 143 */           i.setPickupDelay(80);
/* 144 */           i.getWorld().playSound(player.getLocation(), Sound.WOOD_CLICK, 2.0F, 0.7F);
/* 145 */           this.trackedGrenades.add(i);
/* 146 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 150 */                 if (i != null)
/*     */                 {
/*     */                   
/* 153 */                   i.getWorld().playSound(i.getLocation(), Sound.WITHER_SHOOT, 3.0F, 1.0F);
/* 154 */                   for (Entity e : i.getNearbyEntities(4.0D, 3.0D, 4.0D)) {
/* 155 */                     if (e instanceof Player) {
/* 156 */                       Player target = (Player)e;
/* 157 */                       User targetUser = WitchKit.this.core.getUser(target.getUniqueId());
/*     */                       
/* 159 */                       if (!WitchKit.this.core.hasSpawnFlag(targetUser) && 
/* 160 */                         !WitchKit.this.core.getKitManager().isKit(target, WitchKit.this)) {
/* 161 */                         target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0), true);
/* 162 */                         target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 2), true);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   
/* 167 */                   i.remove();
/*     */                 }
/*     */               
/*     */               }
/* 171 */             }).runTaskLater((Plugin)this.core, 40L);
/*     */           
/* 173 */           final String name = player.getName();
/* 174 */           this.cooldowns.put(name, Long.valueOf(System.currentTimeMillis()));
/* 175 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 179 */                 WitchKit.this.cooldowns.remove(name);
/* 180 */                 player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */               }
/* 183 */             }).runTaskLater((Plugin)this.core, 140L);
/*     */           
/* 185 */           if (player.getItemInHand().getAmount() != 1) {
/* 186 */             player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
/*     */           } else {
/* 188 */             (new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 192 */                   player.getInventory().remove(new ItemStack(Material.BAKED_POTATO));
/*     */                 }
/* 194 */               }).runTaskLater((Plugin)this.core, 1L);
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\WitchKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */