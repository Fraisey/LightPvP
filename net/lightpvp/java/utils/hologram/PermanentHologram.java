/*    */ package net.lightpvp.java.utils.hologram;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.minecraft.server.v1_8_R3.Entity;
/*    */ import net.minecraft.server.v1_8_R3.EntityHorse;
/*    */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*    */ import net.minecraft.server.v1_8_R3.EntityWitherSkull;
/*    */ import net.minecraft.server.v1_8_R3.Packet;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PermanentHologram
/*    */ {
/*    */   private Core core;
/* 24 */   private List<String> lines = null;
/*    */   
/*    */   public PermanentHologram(Core core, List<String> line) {
/* 27 */     this.core = core;
/*    */     
/* 29 */     this.lines = line;
/*    */   }
/*    */   
/*    */   public void show(List<Player> players, Location loc) {
/* 33 */     loc.add(0.0D, this.lines.size() * 0.175D, 0.0D);
/*    */     
/* 35 */     for (int i = this.lines.size() - 1; i >= 0; i--) {
/* 36 */       showLine(players, loc.add(0.0D, 0.35D, 0.0D).clone(), this.lines.get(i));
/*    */     }
/*    */   }
/*    */   
/*    */   private void showLine(List<Player> players, Location loc, String text) {
/* 41 */     WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
/*    */     
/* 43 */     EntityWitherSkull skull = new EntityWitherSkull((World)world);
/* 44 */     skull.setInvisible(true);
/* 45 */     skull.setLocation(loc.getX(), loc.getY() + 53.0D, loc.getZ(), 0.0F, 0.0F);
/* 46 */     PacketPlayOutSpawnEntity packet1 = new PacketPlayOutSpawnEntity((Entity)skull, 66);
/*    */     
/* 48 */     EntityHorse horse = new EntityHorse((World)world);
/* 49 */     horse.setLocation(loc.getX(), loc.getY() + 55.0D, loc.getZ(), 0.0F, 0.0F);
/* 50 */     horse.setAge(-1700000);
/* 51 */     horse.setCustomName(text);
/* 52 */     horse.setCustomNameVisible(true);
/* 53 */     PacketPlayOutSpawnEntityLiving packet2 = new PacketPlayOutSpawnEntityLiving((EntityLiving)horse);
/*    */     
/* 55 */     PacketPlayOutAttachEntity packet3 = new PacketPlayOutAttachEntity(0, (Entity)horse, (Entity)skull);
/*    */     
/* 57 */     for (Player p : players) {
/* 58 */       EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
/*    */       
/* 60 */       nmsPlayer.playerConnection.sendPacket((Packet)packet1);
/* 61 */       nmsPlayer.playerConnection.sendPacket((Packet)packet2);
/*    */       
/* 63 */       nmsPlayer.playerConnection.sendPacket((Packet)packet3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\hologram\PermanentHologram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */