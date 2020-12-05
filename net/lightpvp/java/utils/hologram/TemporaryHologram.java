/*    */ package net.lightpvp.java.utils.hologram;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.minecraft.server.v1_8_R3.Entity;
/*    */ import net.minecraft.server.v1_8_R3.EntityHorse;
/*    */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*    */ import net.minecraft.server.v1_8_R3.EntityWitherSkull;
/*    */ import net.minecraft.server.v1_8_R3.Packet;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class TemporaryHologram {
/* 26 */   private List<String> lines = null;
/*    */   private Core core;
/* 28 */   private int[] ids = null;
/*    */   
/*    */   public TemporaryHologram(Core core, List<String> line) {
/* 31 */     this.core = core;
/*    */     
/* 33 */     this.lines = line;
/*    */   }
/*    */   
/*    */   public void show(final List<Player> players, Location loc, int ticks) {
/* 37 */     loc.add(0.0D, this.lines.size() * 0.175D, 0.0D);
/*    */     
/* 39 */     for (int i = this.lines.size() - 1; i >= 0; i--) {
/* 40 */       showLine(players, loc.add(0.0D, 0.35D, 0.0D).clone(), this.lines.get(i));
/*    */     }
/*    */     
/* 43 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run()
/*    */         {
/* 47 */           TemporaryHologram.this.destroy(players);
/*    */         }
/* 49 */       }).runTaskLater((Plugin)this.core, ticks);
/*    */   }
/*    */   
/*    */   private void showLine(List<Player> players, Location loc, String text) {
/* 53 */     WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
/*    */     
/* 55 */     EntityWitherSkull skull = new EntityWitherSkull((World)world);
/* 56 */     skull.setInvisible(true);
/* 57 */     skull.setLocation(loc.getX(), loc.getY() + 53.0D, loc.getZ(), 0.0F, 0.0F);
/* 58 */     PacketPlayOutSpawnEntity packet1 = new PacketPlayOutSpawnEntity((Entity)skull, 66);
/*    */     
/* 60 */     EntityHorse horse = new EntityHorse((World)world);
/* 61 */     horse.setLocation(loc.getX(), loc.getY() + 55.0D, loc.getZ(), 0.0F, 0.0F);
/* 62 */     horse.setAge(-1700000);
/* 63 */     horse.setCustomName(text);
/* 64 */     horse.setCustomNameVisible(true);
/* 65 */     PacketPlayOutSpawnEntityLiving packet2 = new PacketPlayOutSpawnEntityLiving((EntityLiving)horse);
/*    */     
/* 67 */     PacketPlayOutAttachEntity packet3 = new PacketPlayOutAttachEntity(0, (Entity)horse, (Entity)skull);
/*    */     
/* 69 */     for (Player p : players) {
/* 70 */       EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
/*    */       
/* 72 */       nmsPlayer.playerConnection.sendPacket((Packet)packet1);
/* 73 */       nmsPlayer.playerConnection.sendPacket((Packet)packet2);
/*    */       
/* 75 */       nmsPlayer.playerConnection.sendPacket((Packet)packet3);
/*    */     } 
/*    */     
/* 78 */     this.ids = (this.ids == null) ? new int[2] : Arrays.copyOf(this.ids, this.ids.length + 2);
/* 79 */     this.ids[this.ids.length - 2] = skull.getId();
/* 80 */     this.ids[this.ids.length - 1] = horse.getId();
/*    */   }
/*    */   
/*    */   public void destroy(List<Player> players) {
/* 84 */     PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.ids);
/*    */     
/* 86 */     for (Player p : players) {
/* 87 */       EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
/*    */       
/* 89 */       nmsPlayer.playerConnection.sendPacket((Packet)packet);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\hologram\TemporaryHologram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */