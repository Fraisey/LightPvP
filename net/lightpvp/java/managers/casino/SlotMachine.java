/*     */ package net.lightpvp.java.managers.casino;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.managers.kit.Rareness;
/*     */ import net.lightpvp.java.managers.kit.SpecialKit;
/*     */ import net.lightpvp.java.utils.BlockUtil;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Firework;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotMachine
/*     */ {
/*  31 */   private static Material[] fruit = new Material[] { Material.COAL_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK };
/*     */   
/*     */   private CasinoManager casinoManager;
/*     */   
/*  35 */   private Random random = new Random();
/*     */   
/*  37 */   private Block[][] cells = new Block[3][3];
/*     */   
/*  39 */   private String running = "";
/*     */   
/*     */   public SlotMachine(CasinoManager casinoManager, Block coreBlock) {
/*  42 */     this.casinoManager = casinoManager;
/*     */     
/*  44 */     this.cells[0][0] = coreBlock.getRelative(BlockFace.UP).getRelative(BlockFace.EAST);
/*  45 */     this.cells[1][0] = coreBlock.getRelative(BlockFace.UP);
/*  46 */     this.cells[2][0] = coreBlock.getRelative(BlockFace.UP).getRelative(BlockFace.WEST);
/*  47 */     this.cells[0][1] = coreBlock.getRelative(BlockFace.EAST);
/*  48 */     this.cells[1][1] = coreBlock;
/*  49 */     this.cells[2][1] = coreBlock.getRelative(BlockFace.WEST);
/*  50 */     this.cells[0][2] = coreBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST);
/*  51 */     this.cells[1][2] = coreBlock.getRelative(BlockFace.DOWN);
/*  52 */     this.cells[2][2] = coreBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST);
/*     */   }
/*     */   
/*     */   public void play(final Player player) {
/*  56 */     this.running = player.getName();
/*     */     
/*  58 */     (new BukkitRunnable()
/*     */       {
/*  60 */         int count = 14;
/*     */ 
/*     */         
/*     */         public void run() {
/*  64 */           if (this.count > 0) {
/*  65 */             SlotMachine.this.casinoManager.getCore().getWorld().playSound(SlotMachine.this.cells[1][1].getLocation(), Sound.NOTE_STICKS, 0.6F, 1.0F);
/*     */             
/*  67 */             BlockUtil.setBlockType(SlotMachine.this.cells[0][2], SlotMachine.this.cells[0][1].getType());
/*  68 */             BlockUtil.setBlockType(SlotMachine.this.cells[0][1], SlotMachine.this.cells[0][0].getType());
/*  69 */             BlockUtil.setBlockType(SlotMachine.this.cells[0][0], SlotMachine.fruit[SlotMachine.this.random.nextInt(SlotMachine.fruit.length)]);
/*     */             
/*  71 */             if (this.count > 4) {
/*  72 */               BlockUtil.setBlockType(SlotMachine.this.cells[1][2], SlotMachine.this.cells[1][1].getType());
/*  73 */               BlockUtil.setBlockType(SlotMachine.this.cells[1][1], SlotMachine.this.cells[1][0].getType());
/*  74 */               BlockUtil.setBlockType(SlotMachine.this.cells[1][0], SlotMachine.fruit[SlotMachine.this.random.nextInt(SlotMachine.fruit.length)]);
/*     */             }
/*  76 */             else if (this.count == 4) {
/*  77 */               SlotMachine.this.casinoManager.getCore().getWorld().playSound(SlotMachine.this.cells[1][1].getLocation(), Sound.NOTE_PLING, 1.0F, 1.2F);
/*     */             } 
/*     */ 
/*     */             
/*  81 */             if (this.count > 8) {
/*  82 */               BlockUtil.setBlockType(SlotMachine.this.cells[2][2], SlotMachine.this.cells[2][1].getType());
/*  83 */               BlockUtil.setBlockType(SlotMachine.this.cells[2][1], SlotMachine.this.cells[2][0].getType());
/*  84 */               BlockUtil.setBlockType(SlotMachine.this.cells[2][0], SlotMachine.fruit[SlotMachine.this.random.nextInt(SlotMachine.fruit.length)]);
/*     */             }
/*  86 */             else if (this.count == 8) {
/*  87 */               SlotMachine.this.casinoManager.getCore().getWorld().playSound(SlotMachine.this.cells[1][1].getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */             } 
/*     */ 
/*     */             
/*  91 */             this.count--;
/*     */           } else {
/*  93 */             SlotMachine.this.casinoManager.getCore().getWorld().playSound(SlotMachine.this.cells[1][1].getLocation(), Sound.NOTE_PLING, 1.0F, 1.4F);
/*     */             
/*  95 */             boolean won = false;
/*     */             
/*  97 */             if (!won && SlotMachine.this.cells[0][1].getType().equals(SlotMachine.this.cells[1][0].getType()) && SlotMachine.this.cells[1][0].getType().equals(SlotMachine.this.cells[2][1].getType()) && SlotMachine.this.cells[2][1].getType().equals(SlotMachine.this.cells[1][2].getType()) && SlotMachine.this.cells[1][2].getType().equals(SlotMachine.this.cells[1][1].getType())) {
/*  98 */               won = true;
/*     */               
/* 100 */               Rareness rareness = Rareness.getNaturalRareness();
/* 101 */               SpecialKit specialKit = SlotMachine.this.casinoManager.getCore().getKitManager().getRandomSpecialKitByRareness(rareness);
/*     */               
/* 103 */               SlotMachine.this.casinoManager.getCore().getKitManager().addSpecialKit(player, (Kit)specialKit);
/*     */ 
/*     */ 
/*     */               
/* 107 */               if (player.getLocation().distance(SlotMachine.this.cells[1][1].getLocation()) < 10.0D) {
/* 108 */                 (new BukkitRunnable()
/*     */                   {
/* 110 */                     int count = 4;
/*     */ 
/*     */                     
/*     */                     public void run() {
/* 114 */                       if (this.count > 0) {
/* 115 */                         Firework fw = (Firework)(SlotMachine.null.access$0(SlotMachine.null.this)).casinoManager.getCore().getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
/* 116 */                         FireworkMeta fwMeta = fw.getFireworkMeta();
/* 117 */                         fwMeta.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());
/* 118 */                         fw.setFireworkMeta(fwMeta);
/* 119 */                         fw.detonate();
/* 120 */                         this.count--;
/*     */                       } else {
/* 122 */                         cancel();
/*     */                       } 
/*     */                     }
/* 125 */                   }).runTaskTimer((Plugin)SlotMachine.this.casinoManager.getCore(), 0L, 10L);
/*     */               }
/*     */ 
/*     */               
/* 129 */               player.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "WIN: " + ChatUtil.Gi + "Special Kit: " + ChatColor.GOLD + specialKit.getName() + ChatColor.GRAY + " (" + specialKit.getRareness().getName() + ChatColor.GRAY + ")" + ChatColor.GRAY + " for getting a " + ChatUtil.Gi + "plus pattern");
/*     */             } 
/*     */             
/* 132 */             if ((!won && SlotMachine.this.cells[0][0].getType().equals(SlotMachine.this.cells[1][1].getType()) && SlotMachine.this.cells[1][1].getType().equals(SlotMachine.this.cells[2][2].getType())) || (SlotMachine.this.cells[0][2].getType().equals(SlotMachine.this.cells[1][1].getType()) && SlotMachine.this.cells[1][1].getType().equals(SlotMachine.this.cells[2][0].getType()))) {
/* 133 */               won = true;
/*     */               
/* 135 */               User user = SlotMachine.this.casinoManager.getCore().getUser(player.getUniqueId());
/* 136 */               user.setSlotmachineCount(user.getSlotmachineCount() - 4);
/* 137 */               player.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "WIN: " + ChatUtil.Gi + "4 extra tries" + ChatColor.GRAY + " for getting a " + ChatUtil.Gi + "diagonal line");
/*     */             } 
/*     */             
/* 140 */             int highestHorizontalValue = -1;
/* 141 */             if (SlotMachine.this.cells[0][0].getType().equals(SlotMachine.this.cells[1][0].getType()) && SlotMachine.this.cells[1][0].getType().equals(SlotMachine.this.cells[2][0].getType())) {
/* 142 */               int value = SlotMachine.this.valueOfFruit(SlotMachine.this.cells[0][0].getType());
/* 143 */               highestHorizontalValue = (value > highestHorizontalValue) ? value : highestHorizontalValue;
/*     */             } 
/*     */             
/* 146 */             if (SlotMachine.this.cells[0][1].getType().equals(SlotMachine.this.cells[1][1].getType()) && SlotMachine.this.cells[1][1].getType().equals(SlotMachine.this.cells[2][1].getType())) {
/* 147 */               int value = SlotMachine.this.valueOfFruit(SlotMachine.this.cells[0][1].getType());
/* 148 */               highestHorizontalValue = (value > highestHorizontalValue) ? value : highestHorizontalValue;
/*     */             } 
/*     */             
/* 151 */             if (SlotMachine.this.cells[0][2].getType().equals(SlotMachine.this.cells[1][2].getType()) && SlotMachine.this.cells[1][2].getType().equals(SlotMachine.this.cells[2][2].getType())) {
/* 152 */               int value = SlotMachine.this.valueOfFruit(SlotMachine.this.cells[0][2].getType());
/* 153 */               highestHorizontalValue = (value > highestHorizontalValue) ? value : highestHorizontalValue;
/*     */             } 
/*     */             
/* 156 */             if (!won && highestHorizontalValue != -1) {
/* 157 */               won = true;
/*     */               
/* 159 */               int coins = 0;
/*     */               
/* 161 */               switch (highestHorizontalValue) {
/*     */                 case 0:
/* 163 */                   coins = 2950;
/*     */                   break;
/*     */                 case 1:
/* 166 */                   coins = 3950;
/*     */                   break;
/*     */                 case 2:
/* 169 */                   coins = 4850;
/*     */                   break;
/*     */                 case 3:
/* 172 */                   coins = 6000;
/*     */                   break;
/*     */               } 
/*     */               
/* 176 */               player.sendMessage(ChatColor.GOLD + ChatColor.BOLD + "WIN: " + ChatColor.GOLD + coins + " coins" + ChatColor.GRAY + " for getting a " + ChatUtil.Gi + "horizontal line!");
/* 177 */               SlotMachine.this.casinoManager.getCore().getPlayerStatsManager().incrementInt(player.getUniqueId(), coins, PlayerStat.COINS);
/*     */             } 
/*     */             
/* 180 */             if (won) {
/* 181 */               (new BukkitRunnable()
/*     */                 {
/* 183 */                   int count = 8;
/*     */                   
/*     */                   public void run() {
/* 186 */                     if (this.count > 0) {
/* 187 */                       (SlotMachine.null.access$0(SlotMachine.null.this)).casinoManager.getCore().getWorld().playSound((SlotMachine.null.access$0(SlotMachine.null.this)).cells[1][1].getLocation(), Sound.NOTE_PLING, 1.0F, (SlotMachine.null.access$0(SlotMachine.null.this)).random.nextFloat() + 1.0F);
/* 188 */                       (SlotMachine.null.access$0(SlotMachine.null.this)).casinoManager.getCore().getWorld().playEffect((SlotMachine.null.access$0(SlotMachine.null.this)).cells[(SlotMachine.null.access$0(SlotMachine.null.this)).random.nextInt(((SlotMachine.null.access$0(SlotMachine.null.this)).cells[0]).length)][(SlotMachine.null.access$0(SlotMachine.null.this)).random.nextInt(((SlotMachine.null.access$0(SlotMachine.null.this)).cells[0]).length)].getLocation(), Effect.ENDER_SIGNAL, 10);
/* 189 */                       this.count--;
/*     */                     } else {
/* 191 */                       cancel();
/*     */                     }
/*     */                   
/*     */                   }
/* 195 */                 }).runTaskTimer((Plugin)SlotMachine.this.casinoManager.getCore(), 0L, 3L);
/*     */             } else {
/* 197 */               SlotMachine.this.casinoManager.getCore().getWorld().playSound(SlotMachine.this.cells[1][1].getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */             } 
/*     */             
/* 200 */             SlotMachine.this.running = "";
/*     */             
/* 202 */             cancel();
/*     */ 
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 208 */       }).runTaskTimer((Plugin)this.casinoManager.getCore(), 0L, 4L);
/*     */   }
/*     */   
/*     */   public int valueOfFruit(Material mat) {
/* 212 */     for (int i = 0; i < fruit.length; i++) {
/* 213 */       if (fruit[i].equals(mat)) {
/* 214 */         return i;
/*     */       }
/*     */     } 
/* 217 */     return -1;
/*     */   }
/*     */   
/*     */   public String isRunning() {
/* 221 */     return this.running;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\casino\SlotMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */