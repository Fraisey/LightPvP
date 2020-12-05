/*    */ package net.lightpvp.java.managers.data;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SQLite
/*    */ {
/*    */   private Core core;
/* 16 */   private Connection c = null;
/*    */   
/*    */   public SQLite(Core core, String path) {
/* 19 */     this.core = core;
/*    */     
/*    */     try {
/* 22 */       Class.forName("org.sqlite.JDBC");
/* 23 */       this.c = DriverManager.getConnection("jdbc:sqlite:" + path + ".sqlite");
/* 24 */     } catch (Exception e) {
/* 25 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void execute(String query) {
/*    */     try {
/* 31 */       this.c.createStatement().execute(query);
/* 32 */     } catch (SQLException e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void updateQuery(final String query) {
/* 38 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/* 43 */             SQLite.this.c.createStatement().executeUpdate(query);
/* 44 */           } catch (SQLException e) {
/* 45 */             e.printStackTrace();
/*    */           } 
/*    */         }
/* 48 */       }).runTaskAsynchronously((Plugin)this.core);
/*    */   }
/*    */   
/*    */   public ResultSet executeQuery(String query) {
/*    */     try {
/* 53 */       return this.c.createStatement().executeQuery(query);
/* 54 */     } catch (SQLException e) {
/* 55 */       e.printStackTrace();
/*    */       
/* 57 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\data\SQLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */