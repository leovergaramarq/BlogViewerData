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
public class Post {
    int userId;
    int id;
    String title;
    String body;

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
    
    public void pintar(Graphics g, int x, int y, VentanaInfo venInfo) {
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        //g.setColor(new Color(99, 71, 238));
        g.setColor(new Color(51,51,51));
        g.fillRect(x, y, 680, 40);
        g.setColor(Color.WHITE);
        g.drawString(this.title, x + 55, y + 20);
        g.setColor(new Color(243,243,243));
        g.fillRect(x + 50, y + 25, 630, 110);
        Post p=this;
        JPanel panel=venInfo.getPanel();
        panel.add(new ZoneEvent(x+50, y+20, 630, 110){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                venInfo.showPost(p);
            }
        });
        g.setColor(Color.WHITE);
        g.drawRect(x + 10, y + 5, 30, 25);
        g.drawString(this.id+"", x+25-(String.valueOf(this.id).length()-1)*6, y+20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 14));
        drawString(g, this.body, x + 60, y + 45);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\\\\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
}
