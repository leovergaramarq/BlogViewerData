package blogviewerdata;

import elements.Post;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ViewPost extends javax.swing.JFrame {

    public Post post;
    public BlogViewerData venIni;

    public ViewPost(Post post, BlogViewerData venIni) {
        initComponents();
        this.venIni=venIni;
        this.post=post;
        
        labelId.setText("Id: "+post.getId());
        
        /*panelIcon=new javax.swing.JPanel(){
            @Override
            public void paint(Graphics g){
                //super.paintComponent(g);
                //setBackground(new Color(90,155,191));
                g.drawImage(Apoyo.post, 29, 20, 120, 140, null);
            }
        };*/
        panelTitle=new javax.swing.JPanel(){
            @Override
            public void paint(Graphics g){
                super.paintComponent(g);
                setBackground(new Color(207,207,207));
                g.setFont(new Font("Nirmala UI", Font.BOLD, 20));
                
                drawTitle(g, post.getTitle(), 20, 25);
            }
        };
        panelBody=new javax.swing.JPanel(){
            @Override
            public void paint(Graphics g){
                super.paintComponent(g);
                setBackground(new Color(243,243,243));
                g.setFont(new Font("Nirmala UI Semilight", Font.BOLD, 18));
                drawString(g, post.getBody(), 20, 25);
            }
        };
        //scrollIcon.setViewportView(panelIcon);
        scrollTitle.setViewportView(panelTitle);
        scrollBody.setViewportView(panelBody);
        panelIcon.setPreferredSize(new java.awt.Dimension(170, 140));
        panelTitle.setPreferredSize(new java.awt.Dimension(660, 60));
        panelBody.setPreferredSize(new java.awt.Dimension(670, 170));
        
        //this.getContentPane().setBackground(new Color(204,204,204));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private ViewPost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\\\\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    
    private void drawTitle(Graphics g, String text, int x, int y) {
        //System.out.println(x+text.length()*10+" VS "+(scrollTitle.getWidth()));
        if(x+text.length()*10>=scrollTitle.getWidth()-200){
            int i=0, j;
            while(i<text.length() && x+text.substring(0, i+1).length()*10 < scrollTitle.getWidth()) i++;
            
            j=i;
            while(j>=1 && !text.substring(j-1, j).equals(" ")) j--;
            
            String text1, text2;
            if(j>=1){
                text1=text.substring(0, j-1); text2=text.substring(j);
            }else{
                System.out.println(":'v");
                text1=text.substring(0, i-1); text2=(i<text.length())?text.substring(i):"";
            }
            g.drawString(text1, x, y);
            g.drawString(text2, x, y+g.getFontMetrics().getHeight());
            
        }else g.drawString(text, x, y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel(){

            @Override
            public void paint(Graphics g){
                //super.paint(g);
                g.drawImage(Apoyo.fondo, 0, 0,getWidth(),getHeight(), this);
                paintComponents(g);
            }

        };
        jLabel2 = new javax.swing.JLabel();
        labelId = new javax.swing.JLabel();
        botonComments = new javax.swing.JButton();
        botonUser = new javax.swing.JButton();
        panelIcon = new javax.swing.JPanel(){
            @Override
            public void paint(Graphics g){
                //super.paintComponent(g);
                //setBackground(new Color(90,155,191));
                g.drawImage(Apoyo.post, 29, 20, 120, 140, null);
            }
        };
        scrollTitle = new javax.swing.JScrollPane();
        panelTitle = new javax.swing.JPanel();
        scrollBody = new javax.swing.JScrollPane();
        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(90, 155, 191));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Blog Viewer Data");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        labelId.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        labelId.setForeground(new java.awt.Color(255, 255, 255));
        labelId.setText("Id: -");
        jPanel4.add(labelId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 90, -1));

        botonComments.setBackground(new java.awt.Color(51, 51, 51));
        botonComments.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        botonComments.setForeground(new java.awt.Color(255, 255, 255));
        botonComments.setText("Ver Comentarios");
        botonComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCommentsActionPerformed(evt);
            }
        });
        jPanel4.add(botonComments, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        botonUser.setBackground(new java.awt.Color(51, 51, 51));
        botonUser.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        botonUser.setForeground(new java.awt.Color(255, 255, 255));
        botonUser.setText("Ver Autor");
        botonUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUserActionPerformed(evt);
            }
        });
        jPanel4.add(botonUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 260, -1, -1));

        panelIcon.setBackground(new java.awt.Color(90, 155, 191));

        javax.swing.GroupLayout panelIconLayout = new javax.swing.GroupLayout(panelIcon);
        panelIcon.setLayout(panelIconLayout);
        panelIconLayout.setHorizontalGroup(
            panelIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelIconLayout.setVerticalGroup(
            panelIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        jPanel4.add(panelIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, 170));

        panelTitle.setBackground(new java.awt.Color(204, 204, 204));
        panelTitle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scrollTitle.setViewportView(panelTitle);

        jPanel4.add(scrollTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 690, 70));

        panelBody.setBackground(new java.awt.Color(227, 227, 230));
        panelBody.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scrollBody.setViewportView(panelBody);

        jPanel4.add(scrollBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 690, 190));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUserActionPerformed
        venIni.showAuthorFrom(post);
    }//GEN-LAST:event_botonUserActionPerformed

    private void botonCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCommentsActionPerformed
        venIni.showCommentsFrom(post);
    }//GEN-LAST:event_botonCommentsActionPerformed

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
            java.util.logging.Logger.getLogger(ViewPost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPost().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonComments;
    private javax.swing.JButton botonUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelId;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelIcon;
    private javax.swing.JPanel panelTitle;
    private javax.swing.JScrollPane scrollBody;
    private javax.swing.JScrollPane scrollTitle;
    // End of variables declaration//GEN-END:variables
}
