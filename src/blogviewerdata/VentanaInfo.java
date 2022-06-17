package blogviewerdata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import elements.*;
import pointedlist.PointedList;


/**
 *
 * @author leonardo
 */
public class VentanaInfo extends javax.swing.JFrame {

    Graphics g;
    
    public BlogViewerData venIni;
    public int type, pages, pageNow;
    public static final int cantPerPage=10;
    public PointedList<Object> search;
    
    public VentanaInfo(){
        this(null);
    }
    
    public VentanaInfo(BlogViewerData venIni){
        this(venIni, ZoneEvent.USERS);
    }
    
    public VentanaInfo(BlogViewerData venIni, int type) {
        initComponents();
        this.venIni=venIni;
        
        g = panel.getGraphics();
        panel = new Panel(this);
        jScrollPane1.setViewportView(panel);
        jScrollPane1.setSize(new Dimension(1366, 720));
        panel.setPreferredSize(new Dimension(1000, 220*cantPerPage));
        
        this.setBounds(0, 0, 1100, 720);
        
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void setSearch(Object search, int type){
        this.search=(PointedList)search;
        if(type<ZoneEvent.USERS || type>ZoneEvent.MIX) type=ZoneEvent.USERS;
        this.type=type;
        initPages();
        panel.paint(g);
    }
    
    public void showUser(User user){
        venIni.showUser(user);
    }
    
    public void showPost(Post post){
        venIni.showPost(post);
    }
    
    public void showComment(Comment comment){
        venIni.showComment(comment);
    }
    
    private void initPages(){
        int size=search.size();
        pages=size/cantPerPage+((size%cantPerPage)!=0?1:0);
        pageNow=0;
        System.out.println("Pages: "+pages);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setPreferredSize(new java.awt.Dimension(1324, 168));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1324, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInfo().setVisible(true);
            }
        });
    }
    
    public Panel getPanel(){
        return (Panel)panel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
    
    class Panel extends JPanel{
        
        VentanaInfo venInfo;
        public Panel(VentanaInfo ventana){
            super();
            this.venInfo=ventana;
        }
        
        @Override
        public void paint(Graphics g) {
            if(!venInfo.isVisible()) return;
            super.paint(g);
            g.drawImage(Apoyo.fondoV, 0, 0,getWidth(),getHeight(),this);
            
            setBackground(new Color(90,155,191));
            g.setColor(new Color(243,243,243));
            g.fillRect(0, 0, 1100, 100);
            g.setColor(new Color(51,51,51));
            if(venIni!=null){
                g.drawImage(Apoyo.back, 20, 20, 60, 60, null);
                add(new ZoneEvent(20, 20, 60, 60){
                    @Override
                    public void respuesta(java.awt.event.MouseEvent evt){
                        venIni.setVisible(true);
                        venIni.venInfo.setVisible(false);
                    }
                });
            }
            
            int x = venInfo.getWidth()/6;
            int y = 130;
            //int cont = 0;
            int deltaX=(String.valueOf(pageNow+1).length()-1)*14;

            String title="";
            switch(type){
                case ZoneEvent.USERS: title="USUARIOS"; break;
                case ZoneEvent.POSTS: title="POSTS"; break;
                case ZoneEvent.COMMENTS: title="COMENTARIOS"; break;
                case ZoneEvent.MIX: title="RESULTADOS"; break;
            }
            g.setFont(new java.awt.Font("Nirmala UI", java.awt.Font.BOLD, 20));
            g.drawString(title, x+390-title.length()*10, y-100);
            g.setFont(new java.awt.Font("Nirmala UI Semilight", java.awt.Font.BOLD, 20));
            g.drawString("Página "+(pageNow+1)+" de "+pages, x+300, y-42);
            g.drawImage(Apoyo.back, x+270, y-60, 20, 20, null);
            g.drawImage(Apoyo.next, x+445+deltaX, y-60, 20, 20, null);
            add(new ZoneEvent(x+270, y-60, 20, 20){
                @Override
                public void respuesta(java.awt.event.MouseEvent evt){
                    if(--pageNow<0) pageNow++;
                    revalidate();
                }
            });
            add(new ZoneEvent(x+445+deltaX, y-60, 20, 20){
                @Override
                public void respuesta(java.awt.event.MouseEvent evt){
                    if(++pageNow>=pages) pageNow--;
                    revalidate();
                }
            });
            if(search.isEmpty()){
                g.setColor(new Color(243,243,243));
                g.drawString("No hay resultados", x+280, y+50);
                return;
            }
            int size=search.size();
            switch(type){
                case ZoneEvent.USERS:
                    for(int i=pageNow*cantPerPage; i<(pageNow+1)*cantPerPage && i<size; i++){
                        ((User)search.get(i)).pintar(g, x, y, venInfo);
                        y = y + 200;
                    }
                    break;
                case ZoneEvent.POSTS:
                    for(int i=pageNow*cantPerPage; i<(pageNow+1)*cantPerPage && i<size; i++){
                        ((Post)search.get(i)).pintar(g, x, y, venInfo);
                        y = y + 200;
                    }
                    break;
                case ZoneEvent.COMMENTS:
                    for(int i=pageNow*cantPerPage; i<(pageNow+1)*cantPerPage && i<size; i++){
                        ((Comment)search.get(i)).pintar(g, x, y, venInfo);
                        y = y + 200;
                    }
                    break;
                case ZoneEvent.MIX:
                    if(search==null || search.isEmpty()) return;
                    size=search.size();
                    for(int i=pageNow*cantPerPage; i<(pageNow+1)*cantPerPage && i<size; i++){
                        if(search.get(i) instanceof User)
                            ((User)search.get(i)).pintar(g, x, y, venInfo);
                        else if(search.get(i) instanceof Post)
                            ((Post)search.get(i)).pintar(g, x, y, venInfo);
                        else if(search.get(i) instanceof Comment)
                            ((Comment)search.get(i)).pintar(g, x, y, venInfo);
                        y = y + 200;
                    }
                    break;
            }
        }
    }
}