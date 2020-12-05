/*    */ package net.lightpvp.java.utils.fancymessage;
/*    */ 
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class MessagePart
/*    */ {
/* 14 */   ChatColor color = ChatColor.WHITE;
/* 15 */   ArrayList<ChatColor> styles = new ArrayList<>();
/* 16 */   String clickActionName = null, clickActionData = null, hoverActionName = null, hoverActionData = null;
/* 17 */   String text = null;
/*    */   
/*    */   MessagePart(String text) {
/* 20 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean hasText() {
/* 27 */     return (this.text != null);
/*    */   }
/*    */   
/*    */   JsonWriter writeJson(JsonWriter json) {
/*    */     try {
/* 32 */       json.beginObject().name("text").value(this.text);
/* 33 */       json.name("color").value(this.color.name().toLowerCase());
/* 34 */       for (ChatColor style : this.styles) {
/*    */         String styleName;
/* 36 */         switch (style) {
/*    */           case MAGIC:
/* 38 */             styleName = "obfuscated";
/*    */             break;
/*    */           case UNDERLINE:
/* 41 */             styleName = "underlined";
/*    */             break;
/*    */           default:
/* 44 */             styleName = style.name().toLowerCase();
/*    */             break;
/*    */         } 
/* 47 */         json.name(styleName).value(true);
/*    */       } 
/* 49 */       if (this.clickActionName != null && this.clickActionData != null) {
/* 50 */         json.name("clickEvent").beginObject().name("action").value(this.clickActionName).name("value").value(this.clickActionData).endObject();
/*    */       }
/* 52 */       if (this.hoverActionName != null && this.hoverActionData != null) {
/* 53 */         json.name("hoverEvent").beginObject().name("action").value(this.hoverActionName).name("value").value(this.hoverActionData).endObject();
/*    */       }
/* 55 */       return json.endObject();
/* 56 */     } catch (Exception e) {
/* 57 */       e.printStackTrace();
/* 58 */       return json;
/*    */     } 
/*    */   }
/*    */   
/*    */   MessagePart() {}
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\fancymessage\MessagePart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */