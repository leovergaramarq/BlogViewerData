/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import blogviewerdata.VentanaInfo;
import blogviewerdata.ZoneEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author leonardo
 */
public class Comment {

    int postId;
    int id;
    String name;
    String email;
    String body;

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body=body;
    }

    public void pintar(Graphics g, int x, int y, VentanaInfo venInfo) {
        g.setFont(new Font("Tahoma", Font.BOLD, 12));
        //g.setColor(new Color(99, 71, 238));
        g.setColor(new Color(51,51,51));
        g.fillRect(x, y, 680, 40);
        g.setColor(Color.WHITE);
        g.drawString(this.email, x + 50, y + 15);
        g.setColor(new Color(243,243,243));
        g.fillRect(x + 50, y + 20, 630, 110);
        Comment c=this;
        JPanel panel=venInfo.getPanel();
        panel.add(new ZoneEvent(x+50, y, 630, 130){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                venInfo.showComment(c);
            }
        });
        g.setColor(Color.WHITE);
        g.drawRect(x + 10, y + 5, 30, 25);
        g.drawString(this.id+"", x+25-(String.valueOf(this.id).length()-1)*6, y+20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(this.name, x + 60, y + 45);
        //Image imagenExterna = new ImageIcon("/src/practicandograficos/usuario.png").getImage();
        //g.drawImage(imagenExterna, WIDTH, WIDTH, this);
        g.setFont(new Font("Tahoma", Font.BOLD, 14));
        drawString(g, this.body, x + 60, y + 55);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        /*
        for (String line : Apoyo.split(text, "\\\\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
        */
        //[\\r\\n]+
        
        for (String line : text.split("\\\\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}