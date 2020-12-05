/*     */ package net.lightpvp.java.commands.player;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSpawn
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Core core;
/*     */   public Map<String, Integer> warping;
/*     */   
/*     */   public CommandSpawn(Core core) {
/*  29 */     this.warping = new HashMap<>();
/*     */     this.core = core;
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  34 */     final Player p = (Player)sender;
/*  35 */     final User user = this.core.getUser(p.getUniqueId());
/*  36 */     if (!p.isOp()) {
/*     */ 
/*     */       
/*  39 */       int i = 0;
/*  40 */       for (Entity ent : p.getNearbyEntities(32.0D, 32.0D, 32.0D)) {
/*     */         
/*  42 */         if (ent != null)
/*     */         {
/*  44 */           if (ent instanceof Player)
/*     */           {
/*  46 */             i++;
/*     */           }
/*     */         }
/*     */       } 
/*  50 */       if (i > 0)
/*     */       {
/*  52 */         p.sendMessage(ChatColor.YELLOW + "Someone is nearby! You will warp in 10 seconds. Don't move!");
/*  53 */         int tp = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.core, new Runnable()
/*     */             {
/*     */               
/*     */               public void run()
/*     */               {
/*  58 */                 CommandSpawn.this.core.toSpawn(user, false);
/*  59 */                 if (CommandSpawn.this.warping.containsKey(p.getName()))
/*     */                 {
/*  61 */                   CommandSpawn.this.warping.remove(p.getName());
/*     */                 }
/*  63 */                 p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
/*     */               }
/*  65 */             }200L);
/*  66 */         this.warping.put(p.getName(), Integer.valueOf(tp));
/*  67 */         p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10.0F, 10.0F);
/*     */       }
/*     */       else
/*     */       {
/*  71 */         this.core.toSpawn(user, false);
/*  72 */         p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10.0F, 10.0F);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  79 */       this.core.toSpawn(user, false);
/*  80 */       p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10.0F, 10.0F);
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent e) {
/*  91 */     Player p = e.getPlayer();
/*  92 */     if (this.warping.containsKey(p.getName()))
/*     */     {
/*  94 */       if (e.getFrom().getBlock().getLocation().getX() != e.getTo().getBlock().getLocation().getX() || e.getFrom().getBlock().getLocation().getY() != e.getTo().getBlock().getLocation().getY() || e.getFrom().getBlock().getLocation().getZ() != e.getTo().getBlock().getLocation().getZ()) {
/*     */         
/*  96 */         int tp = ((Integer)this.warping.get(p.getName())).intValue();
/*  97 */         Bukkit.getScheduler().cancelTask(tp);
/*  98 */         p.sendMessage(ChatColor.RED + "Teleportation cancelled!");
/*  99 */         this.warping.remove(p.getName());
/* 100 */         p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10.0F, 10.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandSpawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */