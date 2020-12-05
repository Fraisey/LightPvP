/*     */ package net.lightpvp.java.utils.fancymessage;
/*     */ 
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FancyMessage
/*     */ {
/*     */   private final List<MessagePart> messageParts;
/*     */   private String jsonString;
/*     */   private boolean dirty;
/*     */   
/*     */   public FancyMessage(String firstPartText) {
/*  25 */     this.messageParts = new ArrayList<>();
/*  26 */     this.messageParts.add(new MessagePart(firstPartText));
/*  27 */     this.jsonString = null;
/*  28 */     this.dirty = false;
/*     */   }
/*     */   
/*     */   public FancyMessage() {
/*  32 */     this.messageParts = new ArrayList<>();
/*  33 */     this.messageParts.add(new MessagePart());
/*  34 */     this.jsonString = null;
/*  35 */     this.dirty = false;
/*     */   }
/*     */   
/*     */   public FancyMessage text(String text) {
/*  39 */     MessagePart latest = latest();
/*  40 */     if (latest.hasText()) {
/*  41 */       throw new IllegalStateException("text for this message part is already set");
/*     */     }
/*  43 */     latest.text = text;
/*  44 */     this.dirty = true;
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage color(ChatColor color) {
/*  49 */     if (!color.isColor()) {
/*  50 */       throw new IllegalArgumentException(String.valueOf(color.name()) + " is not a color");
/*     */     }
/*  52 */     (latest()).color = color;
/*  53 */     this.dirty = true;
/*  54 */     return this; } public FancyMessage style(ChatColor... styles) {
/*     */     byte b;
/*     */     int i;
/*     */     ChatColor[] arrayOfChatColor;
/*  58 */     for (i = (arrayOfChatColor = styles).length, b = 0; b < i; ) { ChatColor style = arrayOfChatColor[b];
/*  59 */       if (!style.isFormat())
/*  60 */         throw new IllegalArgumentException(String.valueOf(style.name()) + " is not a style"); 
/*     */       b++; }
/*     */     
/*  63 */     (latest()).styles.addAll(Arrays.asList(styles));
/*  64 */     this.dirty = true;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage file(String path) {
/*  69 */     onClick("open_file", path);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage link(String url) {
/*  74 */     onClick("open_url", url);
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage suggest(String command) {
/*  79 */     onClick("suggest_command", command);
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage command(String command) {
/*  84 */     onClick("run_command", command);
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage tooltip(String text) {
/*  89 */     onHover("show_text", text);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage then(Object obj) {
/*  94 */     if (!latest().hasText()) {
/*  95 */       throw new IllegalStateException("previous message part has no text");
/*     */     }
/*  97 */     this.messageParts.add(new MessagePart(obj.toString()));
/*  98 */     this.dirty = true;
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage then() {
/* 103 */     if (!latest().hasText()) {
/* 104 */       throw new IllegalStateException("previous message part has no text");
/*     */     }
/* 106 */     this.messageParts.add(new MessagePart());
/* 107 */     this.dirty = true;
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public String toJSONString() {
/* 112 */     if (!this.dirty && this.jsonString != null) {
/* 113 */       return this.jsonString;
/*     */     }
/* 115 */     StringWriter string = new StringWriter();
/* 116 */     JsonWriter json = new JsonWriter(string);
/*     */     try {
/* 118 */       if (this.messageParts.size() == 1) {
/* 119 */         latest().writeJson(json);
/*     */       } else {
/* 121 */         json.beginObject().name("text").value("").name("extra").beginArray();
/* 122 */         for (MessagePart part : this.messageParts) {
/* 123 */           latest().writeJson(json);
/*     */         }
/* 125 */         json.endArray().endObject();
/* 126 */         json.close();
/*     */       } 
/* 128 */     } catch (Exception e) {
/* 129 */       throw new RuntimeException("invalid message");
/*     */     } 
/* 131 */     this.jsonString = string.toString();
/* 132 */     this.dirty = false;
/* 133 */     return this.jsonString;
/*     */   }
/*     */   
/*     */   public void send(Player player) {
/*     */     try {
/* 138 */       (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(toJSONString())));
/* 139 */     } catch (Exception e) {
/* 140 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendAll() {
/* 145 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 146 */       send(p);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toOldMessageFormat() {
/* 151 */     StringBuilder result = new StringBuilder();
/* 152 */     for (MessagePart part : this.messageParts) {
/* 153 */       result.append(part.color).append(part.text);
/*     */     }
/* 155 */     return result.toString();
/*     */   }
/*     */   
/*     */   private MessagePart latest() {
/* 159 */     return this.messageParts.get(this.messageParts.size() - 1);
/*     */   }
/*     */   
/*     */   private void onClick(String name, String data) {
/* 163 */     MessagePart latest = latest();
/* 164 */     latest.clickActionName = name;
/* 165 */     latest.clickActionData = data;
/* 166 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   private void onHover(String name, String data) {
/* 170 */     MessagePart latest = latest();
/* 171 */     latest.hoverActionName = name;
/* 172 */     latest.hoverActionData = data;
/* 173 */     this.dirty = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\fancymessage\FancyMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */