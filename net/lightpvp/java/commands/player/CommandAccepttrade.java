/*     */ package net.lightpvp.java.commands.player;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandAccepttrade
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Core core;
/*  26 */   private ItemStack[] tradeContent = new ItemStack[54];
/*     */   
/*     */   public CommandAccepttrade(Core core) {
/*  29 */     this.core = core;
/*     */     
/*  31 */     ItemStack fence = new ItemStack(Material.IRON_FENCE);
/*  32 */     ItemMeta fenceMeta = fence.getItemMeta();
/*  33 */     fenceMeta.setDisplayName(ChatColor.GOLD + "Border");
/*  34 */     fence.setItemMeta(fenceMeta);
/*     */     int i;
/*  36 */     for (i = 0; i < 6; i++) {
/*  37 */       this.tradeContent[i * 9 + 4] = fence;
/*     */     }
/*     */     
/*  40 */     for (i = 0; i < 9; i++) {
/*  41 */       this.tradeContent[36 + i] = fence;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  47 */     if (!(sender instanceof Player)) {
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     Player player = (Player)sender;
/*  52 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/*  54 */     if (args.length > 0) {
/*  55 */       Player target = Bukkit.getPlayer(args[0]);
/*     */       
/*  57 */       if (target != null) {
/*  58 */         User targetUser = this.core.getUser(target.getUniqueId());
/*  59 */         if (targetUser.getTradeInvite().equals(player.getName())) {
/*  60 */           targetUser.setTradeInvite("");
/*     */           
/*  62 */           Inventory tradeInventory = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Special Kit Trader");
/*  63 */           tradeInventory.setContents((ItemStack[])this.tradeContent.clone());
/*     */           
/*  65 */           ItemStack coreItem = new ItemStack(Material.GOLD_NUGGET);
/*  66 */           ItemMeta coreMeta = coreItem.getItemMeta();
/*  67 */           coreMeta.setDisplayName(ChatColor.GREEN + "Ready?");
/*  68 */           coreMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Click on this when you think", ChatColor.GRAY + "you are ready to make the trade", "", ChatColor.GOLD + "Players:", ChatColor.RED + player.getName(), ChatColor.RED + target.getName() }));
/*  69 */           coreItem.setItemMeta(coreMeta);
/*     */           
/*  71 */           tradeInventory.setItem(40, coreItem);
/*     */           
/*  73 */           int playerSlot = 0; byte b; int i; SpecialKit[] arrayOfSpecialKit1;
/*  74 */           for (i = (arrayOfSpecialKit1 = this.core.getKitManager().getSpecialKitArray()).length, b = 0; b < i; ) { SpecialKit k = arrayOfSpecialKit1[b];
/*  75 */             int freq = Collections.frequency(user.getSpecialKits(), k.getListedName());
/*  76 */             if (freq > 0) {
/*  77 */               ItemStack isKit = new ItemStack(k.getMaterial(), freq);
/*  78 */               ItemMeta isKitMeta = isKit.getItemMeta();
/*  79 */               isKitMeta.setDisplayName(ChatColor.GREEN + k.getName());
/*  80 */               isKitMeta.setLore(k.getAbilities());
/*  81 */               isKit.setItemMeta(isKitMeta);
/*     */               
/*  83 */               tradeInventory.setItem(playerSlot, isKit);
/*     */               
/*  85 */               playerSlot++;
/*     */               
/*  87 */               if (playerSlot == 4 || playerSlot == 13 || playerSlot == 22 || playerSlot == 31) {
/*  88 */                 playerSlot += 5;
/*     */               }
/*     */             } 
/*     */             b++; }
/*     */           
/*  93 */           int targetSlot = 5; SpecialKit[] arrayOfSpecialKit2;
/*  94 */           for (int j = (arrayOfSpecialKit2 = this.core.getKitManager().getSpecialKitArray()).length; i < j; ) { SpecialKit k = arrayOfSpecialKit2[i];
/*  95 */             int freq = Collections.frequency(targetUser.getSpecialKits(), k.getListedName());
/*  96 */             if (freq > 0) {
/*  97 */               ItemStack isKit = new ItemStack(k.getMaterial(), freq);
/*  98 */               ItemMeta isKitMeta = isKit.getItemMeta();
/*  99 */               isKitMeta.setDisplayName(ChatColor.GREEN + k.getName());
/* 100 */               isKitMeta.setLore(k.getAbilities());
/* 101 */               isKit.setItemMeta(isKitMeta);
/*     */               
/* 103 */               tradeInventory.setItem(targetSlot, isKit);
/*     */               
/* 105 */               targetSlot++;
/*     */               
/* 107 */               if (targetSlot == 9 || targetSlot == 18 || targetSlot == 27 || targetSlot == 36) {
/* 108 */                 targetSlot += 5;
/*     */               }
/*     */             } 
/*     */             i++; }
/*     */           
/* 113 */           player.openInventory(tradeInventory);
/* 114 */           target.openInventory(tradeInventory);
/*     */           
/* 116 */           player.sendMessage(ChatColor.GRAY + "You are now trading with " + RankUtil.getFullTag(target) + ChatColor.GRAY + "! \n \n" + ChatColor.GOLD + ChatColor.BOLD + "LightPvP Trading!");
/* 117 */           target.sendMessage(ChatColor.GRAY + "You are now trading with " + RankUtil.getFullTag(player) + ChatColor.GRAY + "! \n \n" + ChatColor.GOLD + ChatColor.BOLD + "LightPvP Trading!");
/* 118 */           return true;
/*     */         } 
/* 120 */         player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " didn't invite you to trade, or the invite wore off!");
/* 121 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandAccepttrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */