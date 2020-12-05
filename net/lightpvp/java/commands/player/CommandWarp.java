/*     */ package net.lightpvp.java.commands.player;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.Util;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandWarp
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Core core;
/*  31 */   private ItemStack[] ehgContent = new ItemStack[36];
/*  32 */   private ItemStack[] casinoArmor = new ItemStack[4];
/*     */   
/*     */   public CommandWarp(Core core) {
/*  35 */     this.core = core;
/*     */     
/*  37 */     ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
/*  38 */     LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
/*  39 */     bootsMeta.setColor(Color.BLACK);
/*  40 */     boots.setItemMeta((ItemMeta)bootsMeta);
/*     */     
/*  42 */     ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
/*  43 */     LeatherArmorMeta legsMeta = (LeatherArmorMeta)legs.getItemMeta();
/*  44 */     legsMeta.setColor(Color.BLACK);
/*  45 */     legs.setItemMeta((ItemMeta)legsMeta);
/*     */     
/*  47 */     ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  48 */     LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
/*  49 */     chestMeta.setColor(Color.BLACK);
/*  50 */     chest.setItemMeta((ItemMeta)chestMeta);
/*     */     
/*  52 */     ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
/*  53 */     LeatherArmorMeta helmMeta = (LeatherArmorMeta)helm.getItemMeta();
/*  54 */     helmMeta.setColor(Color.BLACK);
/*  55 */     helm.setItemMeta((ItemMeta)helmMeta);
/*     */     
/*  57 */     this.casinoArmor[0] = boots;
/*  58 */     this.casinoArmor[1] = legs;
/*  59 */     this.casinoArmor[2] = chest;
/*  60 */     this.casinoArmor[3] = helm;
/*     */     
/*  62 */     for (int i = 0; i < this.casinoArmor.length; i++) {
/*  63 */       if (this.casinoArmor[i] != null) {
/*  64 */         ItemMeta meta = this.casinoArmor[i].getItemMeta();
/*  65 */         meta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Good Luck" }));
/*  66 */         this.casinoArmor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     this.ehgContent = new ItemStack[] { new ItemStack(Material.STONE_SWORD), core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*  71 */     this.casinoArmor = new ItemStack[] { boots, legs, chest, helm };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  76 */     if (!(sender instanceof Player)) {
/*  77 */       return false;
/*     */     }
/*  79 */     Player player = (Player)sender;
/*  80 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/*  82 */     if (args.length > 0) {
/*  83 */       if (player.getFallDistance() == 0.0F) {
/*  84 */         if (!player.getAllowFlight()) {
/*  85 */           if (user.isCombatLog() == null) {
/*  86 */             if (args[0].equalsIgnoreCase("earlyhg") || args[0].equalsIgnoreCase("ehg")) {
/*  87 */               backupPlayer(player, user);
/*     */               
/*  89 */               Util.clearPlayer(player);
/*     */               
/*  91 */               this.core.toWarp(user, "EarlyHG", new Location(player.getWorld(), -1350.5D, 52.0D, 623.5D, -45.0F, 0.0F), false);
/*     */               
/*  93 */               user.setKit("EarlyHG");
/*     */               
/*  95 */               player.getInventory().setContents(this.ehgContent);
/*  96 */             } else if (args[0].equalsIgnoreCase("feast")) {
/*  97 */               for (PotionEffect pe : player.getActivePotionEffects()) {
/*  98 */                 player.removePotionEffect(pe.getType());
/*     */               }
/*     */               
/* 101 */               this.core.toWarp(user, "Feast", new Location(player.getWorld(), -1311.5D, 124.0D, 217.5D, 0.0F, 0.0F), true);
/* 102 */             } else if (args[0].equalsIgnoreCase("fisherfight")) {
/* 103 */               backupPlayer(player, user);
/*     */               
/* 105 */               Util.clearPlayer(player);
/*     */               
/* 107 */               this.core.toWarp(user, "FisherFight", new Location(player.getWorld(), -918.5D, 93.0D, -91.5D, 0.0F, 0.0F), false);
/*     */               
/* 109 */               this.core.getKitManager().getKit("Fisherman").setKit(player);
/*     */               
/* 111 */               ItemStack sword = new ItemStack(Material.STONE_SWORD);
/* 112 */               sword.addEnchantment(Enchantment.KNOCKBACK, 1);
/* 113 */               player.getInventory().setItem(0, sword);
/* 114 */             } else if (args[0].equalsIgnoreCase("hardcore") || args[0].equalsIgnoreCase("hc")) {
/* 115 */               backupPlayer(player, user);
/*     */               
/* 117 */               Util.clearPlayer(player);
/*     */               
/* 119 */               this.core.toWarp(user, "Hardcore", new Location(player.getWorld(), -1269.5D, 20.0D, -742.5D, -90.0F, 0.0F), false);
/*     */               
/* 121 */               this.core.getKitManager().setKit(player, this.core.getKitManager().getKit("PvP"), false);
/*     */               
/* 123 */               player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 0));
/* 124 */               player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 1));
/* 125 */             } else if (args[0].equalsIgnoreCase("1v1")) {
/* 126 */               Bukkit.dispatchCommand((CommandSender)player, "1v1");
/*     */             }
/* 128 */             else if (args[0].equalsIgnoreCase("casino")) {
/* 129 */               backupPlayer(player, user);
/*     */               
/* 131 */               Util.clearPlayer(player);
/*     */               
/* 133 */               this.core.toWarp(user, "Casino", new Location(player.getWorld(), -364.5D, 73.0D, 1815.5D, 0.0F, 0.0F), false);
/*     */               
/* 135 */               user.setKit("Casino");
/*     */               
/* 137 */               player.getInventory().setArmorContents(this.casinoArmor);
/*     */             } else {
/* 139 */               player.sendMessage(ChatColor.DARK_AQUA + "Warps: " + ChatColor.GRAY + "EarlyHG/EHG" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "Feast" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "FisherFight" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "Hardcore" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "1v1.");
/* 140 */               return true;
/*     */             } 
/*     */           } else {
/* 143 */             player.sendMessage(ChatColor.RED + "You can't do that while you are in combat.");
/* 144 */             return true;
/*     */           } 
/*     */         } else {
/* 147 */           player.sendMessage(ChatColor.RED + "You can't warp while you are flying!");
/* 148 */           return true;
/*     */         } 
/*     */       } else {
/* 151 */         player.sendMessage(ChatColor.RED + "You have to stand still to do that!");
/* 152 */         return true;
/*     */       } 
/*     */     } else {
/* 155 */       player.sendMessage(ChatColor.GRAY + "Usage: " + ChatUtil.Gi + "/warp <warpname>");
/* 156 */       player.sendMessage(ChatColor.DARK_AQUA + "Warps: " + ChatColor.GRAY + "EarlyHG/EHG" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "Feast" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "FisherFight" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "Hardcore" + ChatColor.DARK_AQUA + ", " + ChatColor.GRAY + "1v1.");
/* 157 */       return true;
/*     */     } 
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   public void backupPlayer(Player player, User user) {
/* 163 */     if (user.getResetContent() == null) {
/* 164 */       Kit kit = this.core.getKitManager().getKit(player);
/* 165 */       if (kit != null) {
/* 166 */         user.setResetKit(kit.getName());
/*     */       }
/*     */       
/* 169 */       user.setResetContent(player.getInventory().getContents());
/*     */       
/* 171 */       user.setResetArmour(player.getInventory().getArmorContents());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandWarp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */