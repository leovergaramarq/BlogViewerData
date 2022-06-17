package blogviewerdata;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tree.Tree;
import elements.*;

public class ViewTree extends javax.swing.JFrame {
    
    private Tree tree;
    private Graphics graph;
    private final int W_WIDTH=1200, W_HEIGHT=700;
    private static int ZOOM=1, MIN_ZOOM=1, MAX_ZOOM=4, NODODIAM=32;
    private BlogViewerData venIni;
    
    public ViewTree(){
        this(null, null);
    }
    
    public ViewTree(Tree tree, BlogViewerData venIni) {
        initComponents();
        this.venIni=venIni;
        this.tree=tree;
        
        graph=panel.getGraphics();
        
        //ViewTree xd=this;
        panel=new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                setBackground(new Color(243,243,243));
                Graphics2D g2=(Graphics2D)g;
                g2.setFont(new Font("Arial", Font.BOLD, NODODIAM*ZOOM/3));
                g2.setColor(new Color(90,155,191));
                g2.setStroke(new BasicStroke(2*ZOOM));
                
                //tree.paintTree(g2, 20+NODODIAM*ZOOM/2, panel.getPreferredSize().height/2, NODODIAM*ZOOM, panel.getPreferredSize().height, xd);
                tree.paintTree(g2, 20+NODODIAM*ZOOM/2, panel.getPreferredSize().height/2, NODODIAM*ZOOM, panel.getPreferredSize().width, panel.getPreferredSize().height);
            }
        };
        panelOptions=new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                
                setBackground(new Color(90,155,191));
                g.setFont(new Font("Arial", Font.BOLD, 18));
                
                g.setColor(new Color(0,182,92));
                g.fillOval(10, 10, 20, 20);
                g.setColor(new Color(255,40,91));
                g.fillOval(10, 45, 20, 20);
                g.setColor(new Color(255,210,40));
                g.fillOval(140, 10, 20, 20);
                
                g.setColor(new Color(51,51,51));
                g.drawString("Usuarios", 35, 25);
                g.drawString("Comentarios", 35, 60);
                g.drawString("Posts", 165, 25);
                
                g.drawString("Zoom", 320, 20);
                g.drawString(ZOOM+"", 340, 40);
                g.drawImage(Apoyo.back, 285, 20, 20, 20, null);
                g.drawImage(Apoyo.next, 380, 20, 20, 20, null);
                this.add(new ZoneEvent(ZoneEvent.BACK, 285, 20, 20, 20){
                    @Override
                    public void respuesta(java.awt.event.MouseEvent evt){
                        if(--ZOOM<MIN_ZOOM){
                            ZOOM=MIN_ZOOM;
                        }else{
                            double x=scroll.getHorizontalScrollBar().getValue(),
                            y=scroll.getVerticalScrollBar().getValue(),
                            mX=scroll.getHorizontalScrollBar().getMaximum(),
                            mY=scroll.getVerticalScrollBar().getMaximum();
                            panel.setPreferredSize(new Dimension(1200, (NODODIAM*ZOOM*4/3)*tree.getAllComments().size()));

                            double mX2=scroll.getHorizontalScrollBar().getMaximum(),
                            mY2=scroll.getVerticalScrollBar().getMaximum();

                            scroll.getHorizontalScrollBar().setValue((int)(x/mX*mX2));
                            //scroll.getVerticalScrollBar().setValue((int)(y/mY*mY2-Math.pow(MAX_ZOOM+0-ZOOM, 3)*2000));
                            scroll.getVerticalScrollBar().getModel().setValue((int)(y/mY*mY2));
                            
                            revalidate();
                            panel.revalidate();
                        }
                    }
                });
                this.add(new ZoneEvent(ZoneEvent.NEXT, 380, 20, 20, 20){
                    @Override
                    public void respuesta(java.awt.event.MouseEvent evt){
                        if(++ZOOM>MAX_ZOOM){
                            ZOOM=MAX_ZOOM;
                        }else{
                            double x=scroll.getHorizontalScrollBar().getValue(),
                            y=scroll.getVerticalScrollBar().getValue(),
                            mX=scroll.getHorizontalScrollBar().getMaximum(),
                            mY=scroll.getVerticalScrollBar().getMaximum();
                            panel.setPreferredSize(new Dimension(1600, (NODODIAM*ZOOM*4/3)*tree.getAllComments().size()));
                            System.out.println("w="+panel.getPreferredSize().width+", h="+panel.getPreferredSize().height);

                            double mX2=scroll.getHorizontalScrollBar().getMaximum(),
                            mY2=scroll.getVerticalScrollBar().getMaximum();

                            scroll.getHorizontalScrollBar().setValue((int)(x/mX*mX2));
                            //scroll.getVerticalScrollBar().setValue((int)(y/mY*mY2+Math.pow(MAX_ZOOM+1-ZOOM, 3)*2000));
                            scroll.getVerticalScrollBar().getModel().setValue((int)(y/mY*mY2));
                            
                            revalidate();
                            panel.revalidate();
                        }
                    }
                });
            }
        };
        
        scrollOptions.setViewportView(panelOptions);
        scroll.setViewportView(panel);
        panelOptions.setPreferredSize(new Dimension(240, 30));
        panel.setPreferredSize(new Dimension(1600, (NODODIAM*ZOOM*4/3)*tree.getAllComments().size()));
        System.out.println("w="+panel.getPreferredSize().width+", h="+panel.getPreferredSize().height);
        
        setSize(W_WIDTH, W_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void setAutoVerticalScrollBar(){
        //scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum()/2);
        scroll.getVerticalScrollBar().setValue(panel.getPreferredSize().height/2-260);
    }
    
    public JPanel getPanel(){
        return panel;
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        panelTitle = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        scrollOptions = new javax.swing.JScrollPane();
        panelOptions = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scroll.setViewportView(panel);

        panelTitle.setBackground(new java.awt.Color(90, 155, 191));
        panelTitle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 48)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(204, 204, 204));
        labelTitle.setText("Árbol de Datos");
        panelTitle.add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 390, 50));

        panelOptions.setBackground(new java.awt.Color(90, 155, 191));
        panelOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scrollOptions.setViewportView(panelOptions);

        panelTitle.add(scrollOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll)
            .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(ViewTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTree().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelOptions;
    private javax.swing.JPanel panelTitle;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scrollOptions;
    // End of variables declaration//GEN-END:variables
}