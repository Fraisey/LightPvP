/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class OCANanoKit
/*     */   implements Listener, SpecialKit
/*     */ {
/*     */   private Core core;
/*  38 */   private ArrayList<String> ocaCooldown = new ArrayList<>();
/*     */   
/*  40 */   ItemStack[] content = new ItemStack[36];
/*  41 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public OCANanoKit(Core core) {
/*  44 */     this.core = core;
/*     */     
/*  46 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  48 */     ItemStack ocaNano = new ItemStack(Material.GOLD_BARDING);
/*  49 */     ItemMeta ocaNanoMeta = ocaNano.getItemMeta();
/*  50 */     ocaNanoMeta.setDisplayName(ChatColor.GREEN + "OCA Nano Gun " + ChatColor.GRAY + "(Right Click)");
/*  51 */     ocaNanoMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Use this to shoot a bullet that deals damage to whom it hits!" }));
/*  52 */     ocaNano.setItemMeta(ocaNanoMeta);
/*     */     
/*  54 */     ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
/*  55 */     LeatherArmorMeta legsMeta = (LeatherArmorMeta)boots.getItemMeta();
/*  56 */     legsMeta.setColor(Color.NAVY);
/*  57 */     boots.setItemMeta((ItemMeta)legsMeta);
/*     */     
/*  59 */     this.content = new ItemStack[] { core.ironSwordSharpness, ocaNano, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  60 */     this.armor = new ItemStack[] { boots, new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
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
/*  72 */     this.armor[1].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  73 */     this.armor[2].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  74 */     this.armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*  75 */     this.armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  80 */     return "OCA Nano";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  85 */     return "kit.ocanano";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  90 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  95 */     return Rareness.COMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 100 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 102 */     p.getInventory().setContents(this.content);
/* 103 */     p.getInventory().setArmorContents(this.armor);
/* 104 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 109 */     return Material.BREWING_STAND_ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 114 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click your gold horse armour", ChatColor.GRAY + "to shoot a bullet that deals damage", ChatColor.GRAY + "to whoever it hits, with a small cooldown.", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 119 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onProjectileLaunchEvent(PlayerInteractEvent evt) {
/* 126 */     Player player = evt.getPlayer();
/* 127 */     if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta() && player.getItemInHand().getType().equals(Material.GOLD_BARDING) && 
/* 128 */       !this.ocaCooldown.contains(player.getName())) {
/*     */       
/* 130 */       final Snowball bullet = (Snowball)player.launchProjectile(Snowball.class);
/*     */       
/* 132 */       bullet.setMetadata("oca", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/*     */       
/* 134 */       int bulletID = bullet.getEntityId();
/*     */       
/* 136 */       PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { bulletID });
/* 137 */       for (Player p : Bukkit.getOnlinePlayers()) {
/* 138 */         (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet);
/*     */       }
/*     */       
/* 141 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 145 */             if (bullet == null || bullet.isDead())
/*     */             {
/*     */               
/* 148 */               cancel();
/*     */             }
/*     */           }
/* 151 */         }).runTaskTimer((Plugin)this.core, 0L, 1L);
/*     */       
/* 153 */       this.core.getWorld().playSound(player.getLocation(), Sound.DRINK, 0.2F, 1.5F);
/*     */       
/* 155 */       final String name = player.getName();
/* 156 */       this.ocaCooldown.add(name);
/* 157 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 161 */             OCANanoKit.this.ocaCooldown.remove(name);
/*     */           }
/* 163 */         }).runTaskLater((Plugin)this.core, 10L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
/* 170 */     if (evt.getDamager() instanceof Snowball) {
/* 171 */       Snowball snowball = (Snowball)evt.getDamager();
/* 172 */       if (snowball.hasMetadata("oca")) {
/* 173 */         evt.setDamage(8.0D);
/* 174 */         snowball.removeMetadata("oca", (Plugin)this.core);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\OCANanoKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */