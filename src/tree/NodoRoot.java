package tree;

import blogviewerdata.Apoyo;
import elements.*;
import java.awt.Color;
import java.awt.Graphics2D;
import pointedlist.PointedList;

/**
 * Nodo de la raíz del árbol. Posee acceso a todos los nodos de usuarios {@link NodoUser} del árbol.
 * @author Leonardo
 */
class NodoRoot{
    
    PointedList<NodoUser> nodoUsers;
    boolean desplegado;
    /**
     * Construye el nodo raíz del árbol.
     */
    public NodoRoot(){
        nodoUsers=new PointedList();
        desplegado=true;
    }
    
    void listToNodo(PointedList<Object> list){
        if(list.get(0) instanceof User)
            for(Object u: list) addNodoUser((User)u);
        else if(list.get(0) instanceof Post)
            for(Object p: list) addNodoPost((Post)p);
        else if(list.get(0) instanceof Comment)
            for(Object c: list) addNodoComment((Comment)c);
    }

    NodoUser getNodoUser(int i){
        return nodoUsers.get(i);
    }

    boolean addNodoUser(User user){
        nodoUsers.add(new NodoUser(user));
        return true; //:v
    }

    boolean addNodoPost(Post post){
        for(Object n: nodoUsers)
            if(((NodoUser)n).user.getId()==post.getUserId()) return ((NodoUser)n).addNodoPost(post);
        
        return false;
    }

    boolean addNodoComment(Comment comment){
        for(Object n: nodoUsers)
            if(((NodoUser)n).addNodoComment(comment)) return true;
        
        return false;
    }

    NodoUser searchNodoUser(int id){
        for(Object n: nodoUsers)
            if(((NodoUser)n).user.getId()==id) return (NodoUser)n;
        
        return null;
    }

    NodoUser searchNodoUserByPost(int postId){
        for(Object n: nodoUsers)
            if(((NodoUser)n).hasPost(postId)) return (NodoUser)n;
        
        return null;
    }

    NodoUser searchNodoUser(String email){
        for(Object n: nodoUsers)
            if(((NodoUser)n).user.getEmail().equals(email)) return (NodoUser)n;
        
        return null;
    }
    
    NodoPost searchNodoPost(int id){
        for(Object n: nodoUsers){
            NodoPost nodoPost=((NodoUser)n).searchNodoPost(id);
            if(nodoPost!=null) return nodoPost;
        }
        
        return null;
    }

    NodoComment searchNodoComment(final int id){
        for(Object n: nodoUsers){
            NodoComment nodoComment=((NodoUser)n).searchNodoComment(id);
            if(nodoComment!=null) return nodoComment;
        }
        
        return null;
    }

    int numPostsOfUser(int id){
        NodoUser nodoUser=searchNodoUser(id);
        if(nodoUser!=null) return nodoUser.numPosts();
        return -1;
    }

    int numCommentsOfPost(int id){
        NodoPost nodoPost=searchNodoPost(id);
        if(nodoPost!=null) return nodoPost.numComments();
        return -1;
    }

    int numCommentsOfUser(int id){
        int total=0;
        for(Object n: nodoUsers)
            total+=((NodoUser)n).numCommentsOfUser(id);
        
        return total;
    }
    
    int numUsers(){
        return nodoUsers.size();
    }

    NodoUser[] usersMásActivos(int numUsers){
        if(numUsers<1) numUsers=1;
        if(numUsers>numUsers()) numUsers=numUsers();
        
        NodoUser[] nUsers=new NodoUser[numUsers];
        for(Object n: nodoUsers){
            if(((NodoUser)n).numPosts==-1) ((NodoUser)n).numPosts();
            if(((NodoUser)n).numComments==-1) numCommentsOfUser( ((NodoUser)n).user.getId() );
            insertarUser(((NodoUser)n), nUsers);
        }
        return nUsers;
    }
    
    private void insertarUser(NodoUser nodoUser, NodoUser[] nUsers){
        int i=0;
        while(nUsers[i]!=null && nodoUser.numComments+nodoUser.numPosts<=nUsers[i].numComments+nUsers[i].numPosts){
            i++;
            if(i==nUsers.length) return;
        }
        if(nUsers[i]!=null)
            for(int j=nUsers.length-1; j>=i; j--)
                if(j!=nUsers.length-1)
                    nUsers[j+1]=nUsers[j];
        
        nUsers[i]=nodoUser;
    }
    
    PointedList<User> getAllUsers(){
        PointedList<User> array=new PointedList();
        for(Object n: nodoUsers)
            array.add(((NodoUser)n).user);
        
        return array;
    }
    
    PointedList<Post> getAllPosts(){
        PointedList<Post> array=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).getAllPosts(array);
        
        return array;
    }
    
    PointedList<Comment> getAllComments(){
        PointedList<Comment> array=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).getAllComments(array);
        
        return array;
    }
    
    PointedList<Post> getPostsFromUser(int id){
        NodoUser nodoUser=searchNodoUser(id);
        if(nodoUser!=null) return nodoUser.getPosts();
        return null;
    }
    
    PointedList<Comment> getCommentsFromPost(int id){
        NodoPost nodoPost=searchNodoPost(id);
        if(nodoPost!=null) return nodoPost.getComments();
        return null;
    }
    
    PointedList<Comment> getCommentsByUser(int id){
        PointedList<Comment> array=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).getCommentsByUser(id, array);
        
        return array;
    }
    
    PointedList<Comment> getCommentsByUser(String email){
        PointedList<Comment> array=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).getCommentsByUser(email, array);
        
        return array;
    }
    
    PointedList<Object> searchAll(String string){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers){
            NodoUser nUser=(NodoUser)n;
            if(Apoyo.hasString(nUser.user.getName(), string) || Apoyo.hasString(nUser.user.getUsername(), string)
                    || Apoyo.hasString(nUser.user.getEmail(), string)) result.add(nUser.user);
            
            nUser.searchAll(string, result);
        }
        return result;
    }
    
    PointedList<Object> searchAll(int id){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers){
            if(((NodoUser)n).user.getId()==id)
                result.add(((NodoUser)n).user);
            
            ((NodoUser)n).searchAll(id, result);
        }
        return result;
    }
    
    PointedList<Object> searchAtUsers(String string){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers){
            NodoUser nUser=(NodoUser)n;
            if(Apoyo.hasString(nUser.user.getName(), string) || Apoyo.hasString(nUser.user.getUsername(), string)
                    || Apoyo.hasString(nUser.user.getEmail(), string)) result.add(nUser.user);
        }
        return result;
    }
    
    PointedList<Object> searchAtUsers(int id){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers)
            if(((NodoUser)n).user.getId()==id)
                result.add(((NodoUser)n).user);
        
        return result;
    }
    
    PointedList<Object> searchAtPosts(String string){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).searchAtPosts(string, result);
        
        return result;
    }
    
    PointedList<Object> searchAtPosts(int id){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).searchAtPosts(id, result);

        return result;
    }
    
    PointedList<Object> searchAtComments(String string){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).searchAtComments(string, result);
        
        return result;
    }
    
    PointedList<Object> searchAtComments(int id){
        PointedList<Object> result=new PointedList();
        for(Object n: nodoUsers)
            ((NodoUser)n).searchAtComments(id, result);

        return result;
    }
    
    void paintNodoRoot(Graphics2D g2, int x, int y, int diam, int horInterval, int verticalInterval){
        if(desplegado){
            g2.setColor(Color.BLACK);
            int size=nodoUsers.size(), i=0;
            for(Object n: nodoUsers){
                int x2=x+horInterval*4/10, y2=y-verticalInterval/2+verticalInterval*++i/(size+1);
                g2.drawLine(x, y, x2, y2);
                ((NodoUser)n).paintNodoUser(g2, x2, y2, diam, horInterval, verticalInterval/size);
            }
        }
        g2.setColor(new Color(90,155,191));
        g2.fillOval(x-diam/2, y-diam/2, diam, diam);
        g2.setColor(Color.BLACK);
        //g2.drawString("T", x-g2.getFontMetrics().getWidths()[0]/2, y+g2.getFontMetrics().getHeight()/2);
        g2.drawString("T", x-diam/16, y+diam/8);
    }
}