/*     */ package net.lightpvp.java.managers.kit.kits.specialkits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class NoSouperKit
/*     */   implements Listener, SpecialKit {
/*     */   private Core core;
/*  33 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  35 */   ItemStack[] content = new ItemStack[36];
/*  36 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public NoSouperKit(Core core) {
/*  39 */     this.core = core;
/*     */     
/*  41 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  43 */     ItemStack carrotSoup = new ItemStack(Material.CARROT_STICK);
/*  44 */     ItemMeta carrotMeta = carrotSoup.getItemMeta();
/*  45 */     carrotMeta.setDisplayName(ChatColor.GREEN + "No Soup Carrot " + ChatColor.GRAY + "(Right Click a Player)");
/*  46 */     carrotMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Use this to stop the player you click", ChatColor.GRAY + "being able to use soup for 3 seconds!" }));
/*  47 */     carrotSoup.setItemMeta(carrotMeta);
/*     */     
/*  49 */     this.content = new ItemStack[] { core.ironSwordSharpness, carrotSoup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  50 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*     */     
/*  52 */     for (int i = 0; i < this.armor.length; i++) {
/*  53 */       if (this.armor[i] != null) {
/*  54 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  56 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  57 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  58 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     this.armor[1].addUnsafeEnchantment(Enchantment.DURABILITY, 10);
/*  63 */     this.armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  68 */     return "No Souper";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  73 */     return "kit.nosouper";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rareness getRareness() {
/*  83 */     return Rareness.IMPOSSIBLY_RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/*  88 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/*  90 */     p.getInventory().setContents(this.content);
/*  91 */     p.getInventory().setArmorContents(this.armor);
/*  92 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  97 */     return Material.MUSHROOM_SOUP;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 102 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Right click a player with your carrot stick", ChatColor.GRAY + "to stop them from using soup for 3 seconds!", "", ChatColor.GOLD + "Rareness: " + ChatColor.GRAY + getRareness().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 107 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
/* 114 */     final Player player = evt.getPlayer();
/* 115 */     if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.CARROT_STICK) && 
/* 116 */       this.core.getKitManager().isKit(player, (Kit)this) && 
/* 117 */       evt.getRightClicked() instanceof Player)
/* 118 */       if (this.cooldowns.containsKey(player.getName())) {
/* 119 */         long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 120 */         player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 121 */         evt.setCancelled(true);
/*     */       } else {
/* 123 */         final Player rightClicked = (Player)evt.getRightClicked();
/* 124 */         if (rightClicked.hasMetadata("cantSoup")) {
/* 125 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(rightClicked)) + ChatColor.GRAY + " can already not use " + ChatUtil.Gi + "mushroom soup!");
/*     */         } else {
/* 127 */           rightClicked.setMetadata("cantSoup", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/* 128 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(rightClicked)) + ChatColor.GRAY + " can't use " + ChatUtil.Gi + "soup " + ChatColor.GRAY + "anymore for another " + ChatUtil.Gi + "3 seconds!");
/* 129 */           rightClicked.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " used their " + ChatUtil.Gi + "'NoSouper' " + ChatColor.GRAY + "ability on you. You can't use " + ChatUtil.Gi + "soup " + ChatColor.GRAY + "for another " + ChatUtil.Gi + " 3 seconds!");
/*     */           
/* 131 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 135 */                 rightClicked.removeMetadata("cantSoup", (Plugin)NoSouperKit.this.core);
/* 136 */                 rightClicked.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "can now use " + ChatUtil.Gi + "soup " + ChatColor.GRAY + "again!");
/* 137 */                 player.sendMessage(String.valueOf(RankUtil.getFullTag(rightClicked)) + ChatColor.GRAY + " can now use " + ChatUtil.Gi + "soup " + ChatColor.GRAY + "again!");
/*     */               }
/* 139 */             }).runTaskLater((Plugin)this.core, 60L);
/*     */           
/* 141 */           final String name = player.getName();
/* 142 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 143 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 147 */                   NoSouperKit.this.cooldowns.remove(name);
/* 148 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 151 */               600L);
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\specialkits\NoSouperKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */