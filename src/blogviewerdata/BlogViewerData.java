package blogviewerdata;

import elements.Comment;
import elements.Post;
import elements.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import pointedlist.PointedList;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import jheison.Jheison;
import tree.Tree;

public class BlogViewerData extends javax.swing.JFrame {
    
    public Tree tree;
    public PointedList<Object> search;
    public PointedList<User> searchUsers;
    public PointedList<Post> searchPosts;
    public PointedList<Comment> searchComments;
    public VentanaInfo venInfo;
    public ViewTree viewTree;
    private Graphics graph;
    private final int WIDTH=1280, HEIGHT=720;
    public String directory;
    
    public BlogViewerData() {
        initComponents();
        
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        graph=panel.getGraphics();
        /*panel1=new JPanel(){
            @Override
            public void paint(Graphics g){
                //super.paint(g);
                //setBackground(new Color(90,155,191));
                paintImagesDown(g);
            }
        };*/
        panelUp=new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                setBackground(new Color(243,243,243));
                paintImagesUp(g);
            }
        };
        panelUp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelUp.setOpaque(false);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        //scroll1.setViewportView(panel1);
        scroll2.setViewportView(panelUp);
        
        initJson(directory="src\\res\\json");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooser.setFileFilter(new FileNameExtensionFilter("Archivos Json", "json"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos Json", "json"));
        chooser.setCurrentDirectory(new File(directory));
        directory=chooser.getCurrentDirectory().getAbsolutePath();
        fieldBrowse.setText(directory);
        
        comboSearch.addItem("Todo");
        comboSearch.addItem("Usuarios");
        comboSearch.addItem("Posts");
        comboSearch.addItem("Comentarios");
        
        frameSettings.setSize(new Dimension(600, 450));
        frameSettings.setResizable(false);
        venInfo=new VentanaInfo(this);
        viewTree=new ViewTree(tree, this);
    }
    
    private void initJson(String directory){
        this.directory=directory;
        tree=new Tree();
        Jheison json=new Jheison(Jheison.conv(directory+"\\users.json"));
        try {
            searchUsers=(PointedList<User>)(json.jsonTo(User.class));
            tree.listToNodo((PointedList)searchUsers);
            
            json.reformat(Jheison.conv(directory+"\\posts.json"));
            searchPosts=(PointedList<Post>)(json.jsonTo(Post.class));
            tree.listToNodo((PointedList)searchPosts);
            
            json.reformat(Jheison.conv(directory+"\\comments.json"));
            searchComments=(PointedList<Comment>)(json.jsonTo(Comment.class));
            tree.listToNodo((PointedList)searchComments);
        } catch (Exception ex) {
            System.out.println("Algo anda mal");
        }
    }
    
    private void paintImagesDown(Graphics g){
        int x, y, w, h;
        x=WIDTH/4; y=panelDown.getHeight()/2; w=WIDTH/6; h=panelDown.getHeight()/2;
        g.drawImage(Apoyo.users, x-w/2, y-h/2, w, h, null);
        x=WIDTH/2;
        g.drawImage(Apoyo.posts, x-w/2, y-h/2, w, h, null);
        x=WIDTH*3/4;
        g.drawImage(Apoyo.comments, x-w/2, y-h/2, w, h, null);
    }
    
    private void paintImagesUp(Graphics g){
        int x, y, w, h;
        x=WIDTH/2; y=panelUp.getHeight()/2; w=WIDTH/3; h=panelUp.getHeight()*2/3;
        g.drawImage(Apoyo.logo, x-w/2, y-h/2, w, h, null);
        x=WIDTH*15/16; w=h=panelUp.getHeight()/2;
        g.drawImage(Apoyo.settings, x-w/2, y-h/2, w, h, null);
        BlogViewerData xd=this;
        panelUp.add(new ZoneEvent(x-w/2, y-h/2, w, h){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                //frameSettings.setLocationRelativeTo(null);
                labelMsg.setText("");
                frameSettings.setLocationRelativeTo(xd); //:v
                frameSettings.setVisible(true);
            }
        });
    }
    
    public void showUser(User user){
        ViewUser v=new ViewUser(user, this);
        v.setResizable(false);
        v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        v.setLocationRelativeTo(this);
        v.setVisible(true);
    }
    
    public void showPost(Post post){
        ViewPost v=new ViewPost(post, this);
        v.setResizable(false);
        v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        v.setLocationRelativeTo(this);
        v.setVisible(true);
    }
    
    public void showComment(Comment comment){
        ViewComment v=new ViewComment(comment, this);
        v.setResizable(false);
        v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        v.setLocationRelativeTo(this);
        v.setVisible(true);
    }
    
    public void showPostsBy(User user){
        search=(PointedList)tree.getPostsFromUser(user.getId());
        if(search==null) return;
        venInfo.setSearch(search, ZoneEvent.MIX);
        venInfo.setVisible(true);
    }
    
    public void showCommentsBy(User user){
        search=(PointedList)tree.getCommentsByUser(user.getEmail());
        if(search==null) return;
        venInfo.setSearch(search, ZoneEvent.MIX);
        venInfo.setVisible(true);
    }
    
    public void showCommentsFrom(Post post){
        search=(PointedList)tree.getCommentsFromPost(post.getId());
        if(search==null) return;
        venInfo.setSearch(search, ZoneEvent.MIX);
        venInfo.setVisible(true);
    }
    
    public void showAuthorFrom(Post post){
        User user=tree.searchUser(post.getUserId());
        if(user==null) return;
        showUser(user);
    }
    
    public void showPostFrom(Comment comment){
        Post post=tree.searchPost(comment.getPostId());
        if(post==null) return;
        showPost(post);
    }
    
    public void showAuthorFrom(Comment comment){
        Post post=tree.searchPost(comment.getPostId());
        if(post==null) return;
        User user=tree.searchUser(post.getUserId());
        if(user==null) return;
        showUser(user);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();
        frameSettings = new javax.swing.JFrame();
        xdPanel = new javax.swing.JPanel();
        otroLabel = new javax.swing.JLabel();
        fieldBrowse = new javax.swing.JTextField();
        unLabel = new javax.swing.JLabel();
        botonBrowse = new javax.swing.JButton();
        botonSave = new javax.swing.JButton();
        botonCancel = new javax.swing.JButton();
        labelMsg = new javax.swing.JLabel();
        panel = new javax.swing.JPanel(){

            @Override
            public void paint(Graphics g){
                //super.paint(g);
                g.drawImage(Apoyo.fondo, 0, 0,getWidth(),getHeight(), this);
                paintComponents(g);
            }

        };
        fieldSearch = new javax.swing.JTextField();
        botonSearch = new javax.swing.JButton();
        botonUsers = new javax.swing.JButton();
        botonPosts = new javax.swing.JButton();
        botonComments = new javax.swing.JButton();
        botonFullTree = new javax.swing.JButton();
        scroll1 = new javax.swing.JScrollPane(){

            @Override
            public void paint(Graphics g){
                paintComponents(g);
            }

        };
        panelDown = new javax.swing.JPanel(){

            @Override
            public void paint(Graphics g){
                paintImagesDown(g);
            }

        };
        scroll2 = new javax.swing.JScrollPane();
        panelUp = new javax.swing.JPanel();
        comboSearch = new javax.swing.JComboBox<>();

        xdPanel.setBackground(new java.awt.Color(90, 155, 191));
        xdPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        otroLabel.setBackground(new java.awt.Color(51, 51, 51));
        otroLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        otroLabel.setForeground(new java.awt.Color(51, 51, 51));
        otroLabel.setText("CONFIGURACIÓN");
        xdPanel.add(otroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        fieldBrowse.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        xdPanel.add(fieldBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 390, 40));

        unLabel.setBackground(new java.awt.Color(51, 51, 51));
        unLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        unLabel.setForeground(new java.awt.Color(51, 51, 51));
        unLabel.setText("Ubicación de archivos Json");
        xdPanel.add(unLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        botonBrowse.setBackground(new java.awt.Color(51, 51, 51));
        botonBrowse.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonBrowse.setForeground(new java.awt.Color(255, 255, 255));
        botonBrowse.setText("Examinar");
        botonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBrowseActionPerformed(evt);
            }
        });
        xdPanel.add(botonBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));

        botonSave.setBackground(new java.awt.Color(51, 51, 51));
        botonSave.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonSave.setForeground(new java.awt.Color(255, 255, 255));
        botonSave.setText("Guardar Cambios");
        botonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSaveActionPerformed(evt);
            }
        });
        xdPanel.add(botonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        botonCancel.setBackground(new java.awt.Color(51, 51, 51));
        botonCancel.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonCancel.setForeground(new java.awt.Color(255, 255, 255));
        botonCancel.setText("Cancelar");
        botonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelActionPerformed(evt);
            }
        });
        xdPanel.add(botonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, -1, -1));

        labelMsg.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelMsg.setForeground(new java.awt.Color(255, 0, 0));
        labelMsg.setText("msg");
        xdPanel.add(labelMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, -1, -1));

        javax.swing.GroupLayout frameSettingsLayout = new javax.swing.GroupLayout(frameSettings.getContentPane());
        frameSettings.getContentPane().setLayout(frameSettingsLayout);
        frameSettingsLayout.setHorizontalGroup(
            frameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        frameSettingsLayout.setVerticalGroup(
            frameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xdPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(90, 155, 191));
        panel.setPreferredSize(new java.awt.Dimension(750, 500));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fieldSearch.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        fieldSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSearchActionPerformed(evt);
            }
        });
        panel.add(fieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(415, 94, 460, 40));

        botonSearch.setBackground(new java.awt.Color(51, 51, 51));
        botonSearch.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonSearch.setForeground(new java.awt.Color(255, 255, 255));
        botonSearch.setText("Buscar");
        botonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSearchActionPerformed(evt);
            }
        });
        panel.add(botonSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        botonUsers.setBackground(new java.awt.Color(51, 51, 51));
        botonUsers.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonUsers.setForeground(new java.awt.Color(255, 255, 255));
        botonUsers.setText("Usuarios");
        botonUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUsersActionPerformed(evt);
            }
        });
        panel.add(botonUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, -1));

        botonPosts.setBackground(new java.awt.Color(51, 51, 51));
        botonPosts.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonPosts.setForeground(new java.awt.Color(255, 255, 255));
        botonPosts.setText("Posts");
        botonPosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPostsActionPerformed(evt);
            }
        });
        panel.add(botonPosts, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, -1, -1));

        botonComments.setBackground(new java.awt.Color(51, 51, 51));
        botonComments.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonComments.setForeground(new java.awt.Color(255, 255, 255));
        botonComments.setText("Comentarios");
        botonComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCommentsActionPerformed(evt);
            }
        });
        panel.add(botonComments, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 470, -1, -1));

        botonFullTree.setBackground(new java.awt.Color(51, 51, 51));
        botonFullTree.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        botonFullTree.setForeground(new java.awt.Color(255, 255, 255));
        botonFullTree.setText("Árbol Completo");
        botonFullTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFullTreeActionPerformed(evt);
            }
        });
        panel.add(botonFullTree, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 550, -1, -1));

        panelDown.setBackground(new java.awt.Color(255, 255, 204));
        panelDown.setPreferredSize(new java.awt.Dimension(1280, 200));
        panelDown.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scroll1.setViewportView(panelDown);

        panel.add(scroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 1340, 270));

        panelUp.setBackground(new java.awt.Color(90, 155, 191));
        panelUp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelUp.setOpaque(false);
        panelUp.setPreferredSize(new java.awt.Dimension(1280, 70));
        panelUp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scroll2.setViewportView(panelUp);

        panel.add(scroll2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 90));

        comboSearch.setBackground(new java.awt.Color(102, 102, 102));
        comboSearch.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        comboSearch.setForeground(new java.awt.Color(255, 255, 255));
        comboSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSearchActionPerformed(evt);
            }
        });
        panel.add(comboSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 141, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonPostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPostsActionPerformed
        //searchPosts=tree.getAllPosts();
        //if(searchPosts==null) return;
        venInfo.setSearch(searchPosts, ZoneEvent.POSTS);
        setVisible(false);
        venInfo.setVisible(true);
    }//GEN-LAST:event_botonPostsActionPerformed

    private void botonUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUsersActionPerformed
        //searchUsers=tree.getAllUsers();
        //if(searchUsers==null) return;
        venInfo.setSearch(searchUsers, ZoneEvent.USERS);
        setVisible(false);
        venInfo.setVisible(true);
    }//GEN-LAST:event_botonUsersActionPerformed

    private void botonCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCommentsActionPerformed
        //searchComments=tree.getAllComments();
        //if(searchComments==null) return;
        venInfo.setSearch(searchComments, ZoneEvent.COMMENTS);
        setVisible(false);
        venInfo.setVisible(true);
    }//GEN-LAST:event_botonCommentsActionPerformed

    private void botonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSearchActionPerformed
        String field=fieldSearch.getText();
        if(field==null || field.isEmpty()) return;
        try{
            int id=Integer.parseInt(field);
            switch(comboSearch.getSelectedIndex()){
                case 1: search=tree.searchAtUsers(id); break;
                case 2: search=tree.searchAtPosts(id); break;
                case 3: search=tree.searchAtComments(id); break;
                default: search=tree.searchAll(id); break;
            }
            venInfo.setSearch(search, ZoneEvent.MIX);
        }catch(NumberFormatException e){
            switch(comboSearch.getSelectedIndex()){
                case 1: search=tree.searchAtUsers(field); break;
                case 2: search=tree.searchAtPosts(field); break;
                case 3: search=tree.searchAtComments(field); break;
                default: search=tree.searchAll(field); break;
            }
            venInfo.setSearch(search, ZoneEvent.MIX);
        }
        setVisible(false);
        venInfo.setVisible(true);
    }//GEN-LAST:event_botonSearchActionPerformed

    private void botonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelActionPerformed
        frameSettings.setVisible(false);
        fieldBrowse.setText(directory);
    }//GEN-LAST:event_botonCancelActionPerformed

    private void botonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSaveActionPerformed
        if(validDirectory()){
            directory=fieldBrowse.getText();
            frameSettings.setVisible(false);
            initJson(directory);
        }else labelMsg.setText("Directorio no válido");
    }//GEN-LAST:event_botonSaveActionPerformed

    private boolean validDirectory(){
        String dir=fieldBrowse.getText();
        File file=new File(dir);
        if(file.exists() && !file.isFile()){
            File[] files=file.listFiles();
            boolean users=false, posts=false, comments=false;
            for(File f: files){
                if(f.getName().equals("users.json")) users=true;
                else if(f.getName().equals("posts.json")) posts=true;
                else if(f.getName().equals("comments.json")) comments=true;

                if(users && posts && comments) return true;
            }
        }
        return false;
    }
    
    private void botonBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBrowseActionPerformed
        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            //fieldBrowse.setText(chooser.getSelectedFile().getAbsolutePath());
            
            fieldBrowse.setText((new File(chooser.getSelectedFile().getAbsolutePath()).isFile())?
                chooser.getCurrentDirectory().getAbsolutePath():chooser.getSelectedFile().getAbsolutePath());
        }
        labelMsg.setText("");
    }//GEN-LAST:event_botonBrowseActionPerformed

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldSearchActionPerformed

    private void botonFullTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFullTreeActionPerformed
        viewTree.setLocationRelativeTo(this);
        viewTree.setVisible(true);
        viewTree.setAutoVerticalScrollBar();
    }//GEN-LAST:event_botonFullTreeActionPerformed

    private void comboSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSearchActionPerformed

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
            java.util.logging.Logger.getLogger(BlogViewerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BlogViewerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BlogViewerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BlogViewerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BlogViewerData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBrowse;
    private javax.swing.JButton botonCancel;
    private javax.swing.JButton botonComments;
    private javax.swing.JButton botonFullTree;
    private javax.swing.JButton botonPosts;
    private javax.swing.JButton botonSave;
    private javax.swing.JButton botonSearch;
    private javax.swing.JButton botonUsers;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JComboBox<String> comboSearch;
    private javax.swing.JTextField fieldBrowse;
    private javax.swing.JTextField fieldSearch;
    private javax.swing.JFrame frameSettings;
    private javax.swing.JLabel labelMsg;
    private javax.swing.JLabel otroLabel;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelDown;
    private javax.swing.JPanel panelUp;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JLabel unLabel;
    private javax.swing.JPanel xdPanel;
    // End of variables declaration//GEN-END:variables
}