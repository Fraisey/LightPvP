/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RocketRunnable
/*     */   extends BukkitRunnable
/*     */ {
/*     */   private Core core;
/*     */   private Kit rocketKit;
/* 180 */   private final int MAX_TICKS = 10;
/* 181 */   private int ticksLeft = 10;
/*     */   
/*     */   Player player;
/*     */   Vector direction;
/*     */   
/*     */   public RocketRunnable(Core core, Player player) {
/* 187 */     this.core = core;
/* 188 */     this.rocketKit = core.getKitManager().getKit("Rocket");
/*     */     
/* 190 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void launch() {
/* 194 */     if (this.player != null && this.player.isOnline()) {
/* 195 */       this.player.setMetadata("launch", (MetadataValue)new FixedMetadataValue((Plugin)this.core, Boolean.valueOf(true)));
/* 196 */       this.direction = this.player.getLocation().getDirection().multiply(1.8D);
/* 197 */       runTaskTimer((Plugin)this.core, 0L, 2L);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 202 */     this.player.removeMetadata("launch", (Plugin)this.core);
/* 203 */     cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 209 */     if (!this.core.getKitManager().isKit(this.player, this.rocketKit)) {
/* 210 */       stop();
/*     */       
/*     */       return;
/*     */     } 
/* 214 */     if (this.ticksLeft > 0) {
/*     */       
/* 216 */       if (this.ticksLeft <= 8 && (
/* 217 */         this.player.isOnGround() || !this.player.hasMetadata("launch"))) {
/* 218 */         stop();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 223 */       this.player.setFallDistance(0.0F);
/* 224 */       this.player.setVelocity(this.direction);
/*     */ 
/*     */       
/* 227 */       if (this.ticksLeft % 2 == 0) {
/* 228 */         this.player.getWorld().playSound(this.player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
/*     */       }
/*     */ 
/*     */       
/* 232 */       this.direction.multiply(0.95D);
/*     */       
/* 234 */       this.ticksLeft--;
/*     */     } else {
/* 236 */       stop();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\RocketRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */