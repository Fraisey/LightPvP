/*     */ package net.lightpvp.java.managers.specialhead;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.SkullType;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ 
/*     */ public class SpecialHeadManager
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  30 */   private Random random = new Random();
/*     */   
/*     */   private Location[] headLocations;
/*  33 */   private Block oldSkull = null;
/*     */   
/*     */   private ItemStack[] soups;
/*     */   
/*     */   public SpecialHeadManager(Core core) {
/*  38 */     this.core = core;
/*     */     
/*  40 */     this.headLocations = new Location[] { new Location(core.getWorld(), -696.0D, 45.0D, 798.0D), new Location(core.getWorld(), -708.0D, 45.0D, 810.0D), new Location(core.getWorld(), -693.0D, 66.0D, 817.0D), new Location(core.getWorld(), -679.0D, 66.0D, 822.0D), new Location(core.getWorld(), -657.0D, 67.0D, 826.0D), new Location(core.getWorld(), -613.0D, 67.0D, 825.0D), new Location(core.getWorld(), -578.0D, 73.0D, 891.0D), new Location(core.getWorld(), -599.0D, 65.0D, 909.0D), new Location(core.getWorld(), -666.0D, 64.0D, 888.0D), new Location(core.getWorld(), -697.0D, 65.0D, 885.0D), new Location(core.getWorld(), -711.0D, 65.0D, 864.0D), new Location(core.getWorld(), -645.0D, 66.0D, 849.0D), new Location(core.getWorld(), -633.0D, 67.0D, 840.0D), new Location(core.getWorld(), -660.0D, 67.0D, 841.0D), new Location(core.getWorld(), -681.0D, 66.0D, 845.0D), new Location(core.getWorld(), -711.0D, 65.0D, 866.0D), 
/*  41 */         new Location(core.getWorld(), -624.0D, 65.0D, 905.0D), new Location(core.getWorld(), -616.0D, 63.0D, 921.0D), new Location(core.getWorld(), -585.0D, 65.0D, 944.0D), new Location(core.getWorld(), -617.0D, 63.0D, 947.0D), new Location(core.getWorld(), -651.0D, 66.0D, 936.0D), new Location(core.getWorld(), -657.0D, 65.0D, 925.0D), new Location(core.getWorld(), -659.0D, 64.0D, 913.0D), new Location(core.getWorld(), -678.0D, 64.0D, 886.0D), new Location(core.getWorld(), -659.0D, 67.0D, 812.0D), new Location(core.getWorld(), -661.0D, 84.0D, 762.0D), new Location(core.getWorld(), -640.0D, 81.0D, 754.0D), new Location(core.getWorld(), -631.0D, 122.0D, 797.0D), new Location(core.getWorld(), -622.0D, 123.0D, 828.0D), new Location(core.getWorld(), -587.0D, 116.0D, 868.0D), new Location(core.getWorld(), -593.0D, 123.0D, 902.0D), new Location(core.getWorld(), -619.0D, 118.0D, 914.0D), 
/*  42 */         new Location(core.getWorld(), -617.0D, 119.0D, 908.0D), new Location(core.getWorld(), -609.0D, 119.0D, 899.0D), new Location(core.getWorld(), -578.0D, 73.0D, 893.0D), new Location(core.getWorld(), -601.0D, 65.0D, 912.0D), new Location(core.getWorld(), -584.0D, 65.0D, 945.0D), new Location(core.getWorld(), -649.0D, 66.0D, 941.0D), new Location(core.getWorld(), -614.0D, 63.0D, 936.0D), new Location(core.getWorld(), -613.0D, 65.0D, 899.0D), new Location(core.getWorld(), -621.0D, 66.0D, 885.0D), new Location(core.getWorld(), -636.0D, 65.0D, 877.0D), new Location(core.getWorld(), -636.0D, 66.0D, 855.0D), new Location(core.getWorld(), -662.0D, 67.0D, 836.0D), new Location(core.getWorld(), -681.0D, 66.0D, 846.0D), new Location(core.getWorld(), -715.0D, 65.0D, 858.0D), new Location(core.getWorld(), -779.0D, 70.0D, 858.0D), new Location(core.getWorld(), -825.0D, 71.0D, 864.0D), new Location(core.getWorld(), -833.0D, 70.0D, 878.0D), 
/*  43 */         new Location(core.getWorld(), -845.0D, 70.0D, 906.0D), new Location(core.getWorld(), -836.0D, 70.0D, 918.0D), new Location(core.getWorld(), -813.0D, 72.0D, 936.0D), new Location(core.getWorld(), -800.0D, 73.0D, 952.0D), new Location(core.getWorld(), -788.0D, 72.0D, 941.0D), new Location(core.getWorld(), -787.0D, 74.0D, 919.0D), new Location(core.getWorld(), -778.0D, 73.0D, 907.0D), new Location(core.getWorld(), -763.0D, 72.0D, 899.0D), new Location(core.getWorld(), -753.0D, 69.0D, 885.0D), new Location(core.getWorld(), -742.0D, 67.0D, 887.0D), new Location(core.getWorld(), -728.0D, 66.0D, 876.0D), new Location(core.getWorld(), -711.0D, 65.0D, 876.0D) };
/*     */     
/*  45 */     this.soups = new ItemStack[] { core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  47 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */   }
/*     */   
/*     */   public void spawnSpecialHead() {
/*  51 */     if (this.oldSkull != null && this.oldSkull.getType().equals(Material.SKULL)) {
/*  52 */       this.oldSkull.setType(Material.AIR);
/*     */     }
/*     */     
/*  55 */     Location loc = this.headLocations[this.random.nextInt(this.headLocations.length)];
/*  56 */     Block block = loc.getBlock();
/*     */     
/*  58 */     block.setType(Material.SKULL);
/*  59 */     Skull skull = (Skull)block.getState();
/*  60 */     skull.setSkullType(SkullType.PLAYER);
/*  61 */     skull.setOwner("ieatboulders2");
/*  62 */     skull.update(true);
/*     */     
/*  64 */     this.oldSkull = block;
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/*  69 */     Player player = evt.getPlayer();
/*  70 */     if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && 
/*  71 */       evt.getClickedBlock() != null) {
/*  72 */       Block clickedBlock = evt.getClickedBlock();
/*  73 */       if (clickedBlock.getType().equals(Material.SKULL)) {
/*  74 */         Skull skull = (Skull)clickedBlock.getState();
/*  75 */         if (skull.getOwner().equalsIgnoreCase("ieatboulders2")) {
/*  76 */           player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */           
/*  78 */           clickedBlock.setType(Material.AIR);
/*     */           
/*  80 */           switch (this.random.nextInt(8)) {
/*     */             case 0:
/*  82 */               player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0));
/*  83 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Speed I");
/*     */               break;
/*     */             case 1:
/*  86 */               player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
/*  87 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Speed II");
/*     */               break;
/*     */             case 2:
/*  90 */               player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
/*  91 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Strength I");
/*     */               break;
/*     */             case 3:
/*  94 */               player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
/*  95 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Slowness I");
/*     */               break;
/*     */             case 4:
/*  98 */               player.getInventory().addItem(this.soups);
/*  99 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "power up: " + ChatColor.BLUE + "soups! A FULL INVENTORY");
/*     */               break;
/*     */             case 5:
/* 102 */               player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 0));
/* 103 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Jump Boost I");
/*     */               break;
/*     */             case 6:
/* 106 */               player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 0));
/* 107 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Jump Boost II");
/*     */               break;
/*     */             case 7:
/* 110 */               player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 1));
/* 111 */               player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "found a " + ChatUtil.Gi + "special head " + ChatColor.GRAY + "and gained the " + ChatUtil.Gi + "potion effect: " + ChatColor.BLUE + "Slowness II");
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\specialhead\SpecialHeadManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */