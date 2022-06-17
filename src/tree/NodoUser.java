package tree;

import blogviewerdata.Apoyo;
import elements.Comment;
import elements.Post;
import elements.User;
import java.awt.Color;
import java.awt.Graphics2D;
import pointedlist.PointedList;

/**
 * Nodo de los usuarios del árbol. Posee acceso a un usuario y a los nodos de post {@link NodoPost} asociados.
 * @author Leonardo
 */
class NodoUser{
    
    int numComments, numPosts;
    User user;
    PointedList<NodoPost> nodoPosts;
    boolean desplegado;
    
    /**
     * Construye un nuevo nodo de usuario con el usuario que recibe por parámetro.
     * @param user usuario al que tendrá acceso el nuevo nodo
     */
    public NodoUser(User user){
        this.user=user;
        nodoPosts=new PointedList();
        numPosts=numComments=-1;
        desplegado=true;
    }

    boolean addNodoPost(Post post){
        nodoPosts.add(new NodoPost(post, this));
        return true;
    }

    NodoPost getNodoPost(int i){
        return nodoPosts.get(i);
    }
    
    boolean addNodoComment(Comment comment){
        for(Object n: nodoPosts)
            if(((NodoPost)n).post.getId()==comment.getPostId()) return ((NodoPost)n).addNodoComment(comment);
        
        return false;
    }

    NodoPost searchNodoPost(int id){
        for(Object n: nodoPosts)
            if(((NodoPost)n).post.getId()==id) return ((NodoPost)n);
        
        return null;
    }

    NodoComment searchNodoComment(int id){
        for(Object n: nodoPosts){
            NodoComment nodoComment=((NodoPost)n).searchNodoComment(id);
            if(nodoComment!=null) return nodoComment;
        }
        
        return null;
    }

    int numPosts(){
        if(numPosts!=-1) return numPosts;
        return numPosts=nodoPosts.size();
    }

    int numCommentsOfUser(int id){
        int total=0;
        for(Object n: nodoPosts)
            total+=((NodoPost)n).numCommentsOfUser(id);
        
        return numComments=total;
    }
    
    PointedList<Post> getAllPosts(PointedList<Post> array){
        for(Object n: nodoPosts)
            array.add(((NodoPost)n).post);
        
        return array;
    }
    
    PointedList<Comment> getAllComments(PointedList<Comment> array){
        for(Object n: nodoPosts)
            ((NodoPost)n).getAllComments(array);
        
        return array;
    }
    
    boolean hasPost(int postId){
        for(Object n: nodoPosts)
            if(((NodoPost)n).post.getId()==postId) return true;
        
        return false;
    }
    
    PointedList<Post> getPosts(){
        PointedList<Post> posts=new PointedList();
        for(Object n: nodoPosts)
            posts.add(((NodoPost)n).post);
        
        return posts;
    }
    
    PointedList<Comment> getCommentsByUser(int id, PointedList<Comment> array){
        for(Object n: nodoPosts)
            ((NodoPost)n).getCommentsByUser(id, array);
        
        return array;
    }
    
    PointedList<Comment> getCommentsByUser(String email, PointedList<Comment> array){
        for(Object n: nodoPosts)
            ((NodoPost)n).getCommentsByUser(email, array);
        
        return array;
    }
    
    PointedList<Object> searchAll(String string, PointedList<Object> result){
        for(Object n: nodoPosts){
            NodoPost nPost=(NodoPost)n;
            if(Apoyo.hasString(nPost.post.getTitle(), string) || Apoyo.hasString(nPost.post.getBody(), string))
                result.add(nPost.post);
            
            nPost.searchAll(string, result);
        }
        return result;
    }
    
    PointedList<Object> searchAll(int id, PointedList<Object> result){
        for(Object n: nodoPosts){
            if(((NodoPost)n).post.getId()==id)
                result.add(((NodoPost)n).post);
            
            ((NodoPost)n).searchAll(id, result);
        }
        return result;
    }
    
    PointedList<Object> searchAtPosts(String string, PointedList<Object> result){
        for(Object n: nodoPosts){
            NodoPost nPost=(NodoPost)n;
            if(Apoyo.hasString(nPost.post.getTitle(), string) || Apoyo.hasString(nPost.post.getBody(), string))
                result.add(nPost.post);
        }
        return result;
    }
    
    PointedList<Object> searchAtPosts(int id, PointedList<Object> result){
        for(Object n: nodoPosts)
            if(((NodoPost)n).post.getId()==id)
                result.add(((NodoPost)n).post);

        return result;
    }
    
    PointedList<Object> searchAtComments(String string, PointedList<Object> result){
        for(Object n: nodoPosts)
            ((NodoPost)n).searchAll(string, result);
        
        return result;
    }
    
    PointedList<Object> searchAtComments(int id, PointedList<Object> result){
        for(Object n: nodoPosts)
            ((NodoPost)n).searchAll(id, result);

        return result;
    }
    
    void paintNodoUser(Graphics2D g2, int x, int y, int diam, int horInterval, int verticalInterval){
        if(desplegado){
            g2.setColor(Color.BLACK);
            int size=nodoPosts.size(), i=0;
            for(Object n: nodoPosts){
                int x2=x+horInterval*3/10, y2=y-verticalInterval/2+verticalInterval*++i/(size+1);
                g2.drawLine(x, y, x2, y2);
                ((NodoPost)n).paintNodoPost(g2, x2, y2, diam, horInterval, verticalInterval/size);
            }
        }
        g2.setColor(new Color(0,182,92));
        g2.fillOval(x-diam/2, y-diam/2, diam, diam);
        g2.setColor(Color.BLACK);
        g2.drawString(user.getId()+"", x-diam/16*String.valueOf(user.getId()).length(), y+diam/8);
        /*
        javax.swing.JPanel panel=vt.getPanel();
        panel.add(new ZoneEvent(x-diam/2, y-diam/2, diam, diam){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                vt.showUser(user);
            }
        });
        */
    }
}