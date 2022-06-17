package tree;

import elements.Comment;
import java.awt.Color;
import java.awt.Graphics2D;
import pointedlist.PointedList;

/**
 * Nodo de los comentarios del árbol. Posee acceso a un comentario.
 * @author Leonardo
 */
public class NodoComment{
    
    Comment comment;
    NodoPost nodoPost;

    /**
     * Construye un nuevo nodo de comentario con el comentario que recibe por parámetro.
     * @param comment comentario al que tendrá acceso el nuevo nodo
     */
    public NodoComment(Comment comment, NodoPost nodoPost){
        this.comment=comment;
        this.nodoPost=nodoPost;
    }

    int numCommentsOfUser(int id){
        return (nodoPost.nodoUser.user.getId()==id)? 1:0;
    }
    
    PointedList<Comment> getCommentsByUser(int id, PointedList<Comment> array){
        if(nodoPost.nodoUser.user.getId()==id) array.add(comment);
        
        return array;
    }
    
    PointedList<Comment> getCommentsByUser(String email, PointedList<Comment> array){
        if(comment.getEmail().equals(email)) array.add(comment);
        
        return array;
    }
    
    void paintNodoComment(Graphics2D g2, int x, int y, int diam, int verticalInterval){
        g2.setColor(new Color(255,40,91));
        g2.fillOval(x-diam/2, y-diam/2, diam, diam);
        g2.setColor(Color.BLACK);
        g2.drawString(comment.getId()+"", x-diam/16*String.valueOf(comment.getId()).length(), y+diam/8);
        /*
        javax.swing.JPanel panel=vt.getPanel();
        panel.add(new ZoneEvent(x-diam/2, y-diam/2, diam, diam){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                vt.showComment(comment);
            }
        });
        */
    }
}