/*    */ package net.lightpvp.java;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerTick
/*    */   extends BukkitRunnable
/*    */ {
/*    */   private Core core;
/* 16 */   private long tick = 1L;
/*    */   
/*    */   private InfoBar infoBar;
/*    */   
/* 20 */   private List<String> messages = new ArrayList<>();
/* 21 */   private Random random = new Random();
/*    */   
/*    */   public ServerTick(Core core) {
/* 24 */     this.core = core;
/* 25 */     this.infoBar = new InfoBar(core);
/*    */     
/* 27 */     this.messages.add(String.valueOf(ChatUtil.Gi) + "Donate" + ChatColor.GRAY + " at" + ChatColor.GOLD + " buy.lightpvp.net " + ChatColor.GRAY + "to get cool " + ChatUtil.Gi + "Ranks!");
/* 28 */     this.messages.add(String.valueOf(ChatUtil.Gi) + "Subscribe" + ChatColor.GRAY + " to us on " + ChatColor.DARK_RED + ChatColor.BOLD + "You" + ChatColor.WHITE + ChatColor.BOLD + "Tube!" + ChatColor.GRAY + " -" + ChatColor.GOLD + " www.youtube.com/user/LightPvPMC");
/* 29 */     this.messages.add(String.valueOf(ChatUtil.Gi) + "Follow" + ChatColor.GRAY + " to us on " + ChatColor.AQUA + ChatColor.BOLD + "Twitter!" + ChatColor.GRAY + " -" + ChatColor.GOLD + " @LightPvPMC");
/* 30 */     this.messages.add(String.valueOf(ChatUtil.Gi) + "Follow" + ChatColor.GRAY + " us on " + ChatColor.DARK_RED + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Twitch!" + ChatColor.GRAY + " -" + ChatColor.GOLD + " www.twitch.tv/LightPvPMC");
/* 31 */     this.messages.add(ChatColor.GRAY + "Found a " + ChatUtil.Gi + "hacker? " + ChatColor.GRAY + "Report them using " + ChatColor.GOLD + "/report");
/* 32 */     this.messages.add(ChatColor.GRAY + "Want more " + ChatUtil.Gi + "Casino Tries? " + ChatColor.GRAY + "Ranks get 60/per hour!");
/* 33 */     this.messages.add(ChatColor.GRAY + "Players" + ChatColor.GRAY + " with " + ChatUtil.Gi + "Ranks" + ChatColor.GRAY + " get " + ChatColor.GOLD + "x2 coins!");
/* 34 */     this.messages.add(ChatColor.GRAY + "Did you know, " + ChatUtil.Gi + "The Rank: Veteran+, " + ChatColor.GRAY + "has a unique feature, allowing the user to type /trail <trailname>! " + ChatUtil.Gi + "Buy the rank here: http://buy.lightpvp.net/category/72009");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 39 */     this.tick++;
/*    */     
/* 41 */     this.infoBar.update();
/*    */     
/* 43 */     if (this.tick % 2L == 0L) {
/* 44 */       this.core.getHostedEventManager().updateCurrentEvent();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 49 */     if (this.tick % 100L == 0L) {
/* 50 */       ChatUtil.broadcastMessage(String.valueOf(ChatUtil.LPp) + " " + (String)this.messages.get(this.random.nextInt(this.messages.size())));
/*    */     }
/*    */     
/* 53 */     if (this.tick % 1800L == 0L) {
/* 54 */       this.core.getChatGamesManager().askRandomQuestion();
/*    */     }
/*    */     
/* 57 */     this.tick % 40L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\ServerTick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */