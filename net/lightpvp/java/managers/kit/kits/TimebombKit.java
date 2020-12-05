/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class TimebombKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  35 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*  37 */   private Random random = new Random();
/*     */   
/*  39 */   ItemStack[] content = new ItemStack[36];
/*  40 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public TimebombKit(Core core) {
/*  43 */     this.core = core;
/*     */     
/*  45 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  47 */     ItemStack Bomb = new ItemStack(Material.FIREWORK_CHARGE);
/*  48 */     ItemMeta BombMeta = Bomb.getItemMeta();
/*  49 */     BombMeta.setDisplayName(ChatColor.GREEN + "Bomb " + ChatColor.GRAY + "(Right Click a Player)");
/*  50 */     ArrayList<String> lore = new ArrayList<>();
/*  51 */     lore.add(ChatColor.GRAY + "Use this to put a bomb in a players inventory. The bomb");
/*  52 */     lore.add(ChatColor.GRAY + "start throwing out the players items in his inventory 1 by 1");
/*  53 */     lore.add(ChatColor.GRAY + "every 1 second!");
/*  54 */     BombMeta.setLore(lore);
/*  55 */     Bomb.setItemMeta(BombMeta);
/*     */     
/*  57 */     this.content = new ItemStack[] { core.diamondSwordSharpness, Bomb, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  58 */     this.armor = new ItemStack[] { new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_HELMET) };
/*     */     
/*  60 */     for (int i = 0; i < this.armor.length; i++) {
/*  61 */       if (this.armor[i] != null) {
/*  62 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  64 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  65 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  66 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  73 */     return "TimeBomb";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  78 */     return "kit.timebomb";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/*  83 */     return 40000;
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
/*  97 */     return Material.FIREWORK_CHARGE;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 102 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your bomb and plant it", ChatColor.GRAY + "into someones inventory", ChatColor.GRAY + "the bomb will drop out items!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
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
/*     */   public void onPlayerInteractEvent(PlayerInteractEntityEvent evt) {
/* 114 */     final Player player = evt.getPlayer();
/* 115 */     if (evt.getRightClicked() instanceof Player) {
/* 116 */       final Player target = (Player)evt.getRightClicked();
/*     */       
/* 118 */       if (player.getItemInHand().getType() != null && player.getItemInHand().getType().equals(Material.FIREWORK_CHARGE) && 
/* 119 */         this.core.getKitManager().isKit(player, this))
/* 120 */         if (this.cooldowns.containsKey(player.getName())) {
/* 121 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 122 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((20000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 123 */           evt.setCancelled(true);
/*     */         }
/* 125 */         else if (!this.core.hasSpawnFlag(this.core.getUser(target.getUniqueId()))) {
/* 126 */           final ItemStack bomb = new ItemStack(Material.FIREWORK_CHARGE);
/* 127 */           ItemMeta bombmeta = bomb.getItemMeta();
/* 128 */           bombmeta.setDisplayName(ChatColor.GREEN + "Bomb");
/* 129 */           ArrayList<String> lore = new ArrayList<>();
/* 130 */           lore.add(ChatColor.GRAY + "Quick! Remove this - It's throwing your items out!");
/* 131 */           bombmeta.setLore(lore);
/* 132 */           bomb.setItemMeta(bombmeta);
/*     */           
/* 134 */           player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + " put a " + ChatUtil.Gi + "Bomb " + ChatColor.GRAY + "in " + RankUtil.getFullTag(target) + "'s " + ChatColor.GRAY + "inventory!");
/*     */           
/* 136 */           final PlayerInventory targetInventory = target.getInventory();
/*     */           
/* 138 */           targetInventory.setItem(this.random.nextInt(27) + 9, bomb);
/*     */           
/* 140 */           (new BukkitRunnable() {
/*     */               public void run() {
/* 142 */                 if (targetInventory.contains(bomb)) {
/* 143 */                   int dropSlot = TimebombKit.this.random.nextInt(27);
/* 144 */                   ItemStack item = targetInventory.getItem(dropSlot + 9);
/* 145 */                   if (item != null && item.getType() != bomb.getType()) {
/* 146 */                     Item drop = target.getWorld().dropItem(target.getEyeLocation(), item);
/* 147 */                     drop.setVelocity(target.getLocation().getDirection().normalize().multiply(0.6D));
/* 148 */                     targetInventory.setItem(dropSlot + 9, new ItemStack(Material.AIR));
/*     */                   } 
/*     */                 } else {
/* 151 */                   cancel();
/*     */                 } 
/*     */               }
/* 154 */             }).runTaskTimer((Plugin)this.core, 10L, 10L);
/*     */           
/* 156 */           final String name = player.getName();
/* 157 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 158 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 162 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/* 163 */                   TimebombKit.this.cooldowns.remove(name);
/*     */                 }
/*     */               }, 
/* 166 */               400L);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\TimebombKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */