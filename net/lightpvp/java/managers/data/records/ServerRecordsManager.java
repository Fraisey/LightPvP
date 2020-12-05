/*    */ package net.lightpvp.java.managers.data.records;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Calendar;
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ 
/*    */ 
/*    */ public class ServerRecordsManager
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public ServerRecordsManager(Core core) {
/* 16 */     this.core = core;
/*    */   }
/*    */   
/*    */   public void addBanRecord(String caller, String banned, String reason) {
/* 20 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, reason, timestamp) VALUES('" + caller + "', '" + banned + "', 'BAN', '" + reason + "', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public void addTempBanRecord(String caller, String banned, String reason) {
/* 24 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, reason, timestamp) VALUES('" + caller + "', '" + banned + "', 'TEMPBAN', '" + reason + "', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public void addUnbanRecord(String caller, String unbanned) {
/* 28 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, timestamp) VALUES('" + caller + "', '" + unbanned + "', 'UNBAN', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public void addKickRecord(String caller, String kicked, String reason) {
/* 32 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, reason, timestamp) VALUES('" + caller + "', '" + kicked + "', 'KICK', '" + reason + "', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public void addMuteRecord(String caller, String muted) {
/* 36 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, timestamp) VALUES('" + caller + "', '" + muted + "', 'MUTE', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public void addUnmuteRecord(String caller, String muted) {
/* 40 */     this.core.getLocalDatabase().updateQuery("INSERT INTO records(caller, considered, type, timestamp) VALUES('" + caller + "', '" + muted + "', 'UNMUTE', " + System.currentTimeMillis() + ")");
/*    */   }
/*    */   
/*    */   public List<String> getRecords(String query) {
/* 44 */     Calendar cal = Calendar.getInstance();
/*    */     
/* 46 */     List<String> output = new ArrayList<>();
/* 47 */     ResultSet rs = this.core.getLocalDatabase().executeQuery(query);
/*    */     try {
/* 49 */       while (rs.next()) {
/* 50 */         cal.setTimeInMillis(rs.getLong(6));
/* 51 */         output.add(String.valueOf(rs.getInt(1)) + ". " + rs.getString(2) + " - " + rs.getString(4) + " - " + rs.getString(3) + " - " + ((rs.getString(5) != null) ? (String.valueOf(rs.getString(5)) + " - ") : "") + cal.get(5) + "/" + cal.get(2));
/*    */       }
/*    */     
/* 54 */     } catch (SQLException e) {
/* 55 */       e.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 58 */         rs.close();
/* 59 */       } catch (SQLException e) {
/* 60 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return output.isEmpty() ? null : output;
/*    */   }
/*    */   
/*    */   public int getRecordCount() {
/* 68 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT COUNT(*) FROM records");
/*    */     try {
/* 70 */       if (rs.next()) {
/* 71 */         return rs.getInt(1);
/*    */       }
/* 73 */     } catch (SQLException e) {
/* 74 */       e.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 77 */         rs.close();
/* 78 */       } catch (SQLException e) {
/* 79 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/* 82 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\data\records\ServerRecordsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */