package tree;

import blogviewerdata.Apoyo;
import elements.Comment;
import elements.Post;
import java.awt.Color;
import java.awt.Graphics2D;
import pointedlist.PointedList;

/**
 * Nodo de los posts del árbol. Posee acceso a un post y a los nodos de comentarios {@link NodoComment} asociados.
 * @author Leonardo
 */
public class NodoPost{
    
    NodoUser nodoUser;
    Post post;
    PointedList<NodoComment> nodoComments;
    boolean desplegado;
    
    /**
     * Construye un nuevo nodo de post con el post que recibe por parámetro.
     * @param post post al que tendrá acceso el nuevo nodo
     */
    public NodoPost(Post post, NodoUser nodoUser){
        this.post=post;
        this.nodoUser=nodoUser;
        nodoComments=new PointedList();
        desplegado=true;
    }

    NodoComment getNodoComment(int i){
        return nodoComments.get(i);
    }

    boolean addNodoComment(Comment comment){
        nodoComments.add(new NodoComment(comment, this));
        return true;
    }

    NodoComment searchNodoComment(int id){
        for(Object n: nodoComments)
            if(((NodoComment)n).comment.getId()==id) return ((NodoComment)n);
        
        return null;
    }

    int numComments(){
        return nodoComments.size();
    }

    int numCommentsOfUser(int id){
        int total=0;
        for(Object n: nodoComments)
            total+=((NodoComment)n).numCommentsOfUser(id);
        
        return total;
    }
    
    PointedList<Comment> getAllComments(PointedList<Comment> array){
        for(Object n: nodoComments)
            array.add(((NodoComment)n).comment);
        
        return array;
    }
    
    PointedList<Comment> getComments(){
        PointedList<Comment> comments=new PointedList();
        for(Object n: nodoComments)
            comments.add(((NodoComment)n).comment);
        
        return comments;
    }
    
    PointedList<Comment> getCommentsByUser(int id, PointedList<Comment> array){
        for(Object n: nodoComments)
            ((NodoComment)n).getCommentsByUser(id, array);
        
        return array;
    }
    
    PointedList<Comment> getCommentsByUser(String email, PointedList<Comment> array){
        for(Object n: nodoComments)
            ((NodoComment)n).getCommentsByUser(email, array);
        
        return array;
    }
    
    PointedList<Object> searchAll(String string, PointedList<Object> result){
        for(Object n: nodoComments){
            NodoComment nComment=(NodoComment)n;
            if(Apoyo.hasString(nComment.comment.getName(), string) || Apoyo.hasString(nComment.comment.getBody(), string))
            result.add(nComment.comment);
        }
        return result;
    }
    
    PointedList<Object> searchAll(int id, PointedList<Object> result){
        for(Object n: nodoComments)
            if(((NodoComment)n).comment.getId()==id)
                result.add(((NodoComment)n).comment);
        
        return result;
    }
    
    void paintNodoPost(Graphics2D g2, int x, int y, int diam, int horInterval, int verticalInterval){
        if(desplegado){
            g2.setColor(Color.BLACK);
            int size=nodoComments.size(), i=0;
            for(Object n: nodoComments){
                int x2=x+horInterval*2/10, y2=y-verticalInterval/2+verticalInterval*++i/(size+1);
                g2.drawLine(x, y, x2, y2);
                ((NodoComment)n).paintNodoComment(g2, x2, y2, diam, verticalInterval/size);
            }
        }
        g2.setColor(new Color(255,210,40));
        g2.fillOval(x-diam/2, y-diam/2, diam, diam);
        g2.setColor(Color.BLACK);
        g2.drawString(post.getId()+"", x-diam/16*String.valueOf(post.getId()).length(), y+diam/8);
        /*
        javax.swing.JPanel panel=vt.getPanel();
        panel.add(new ZoneEvent(x-diam/2, y-diam/2, diam, diam){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                vt.showPost(post);
            }
        });
        */
    }
}