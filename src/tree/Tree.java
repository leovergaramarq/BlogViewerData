package tree;
import elements.*;
import java.awt.Graphics2D;
import pointedlist.PointedList;

/**
 * Estructura de datos que almacena, organiza y procesa información de usuarios, posts y comentarios.
 * @author Leonardo
 */
public class Tree{
    
    public NodoRoot root;
    /**
     * Construye un nuevo árbol de datos.
     */
    public Tree(){
        root=new NodoRoot();
    }
    /**
     * Guarda la lista en el árbol en el nivel adecuado, dependiendo de si es de usuarios, posts o comentarios.
     * @param list lista contendora de los datos
     */
    public void listToNodo(PointedList<Object> list){
        if(list==null || list.isEmpty()) return;
        root.listToNodo(list);
    }
    /**
     * Añade un usuario al árbol.
     * @param user usuario a ser agregado
     * @return true si el usuario fue agregado correctamente
     */
    public boolean addNodoUser(User user){
        return root.addNodoUser(user);
    }
    /**
     * Añade un post al árbol.
     * @param post post a ser agregado
     * @return true si el post fue agregado correctamente
     */
    public boolean addNodoPost(Post post){
        return root.addNodoPost(post);
    }
    /**
     * Añade un comentario al árbol.
     * @param comment comentario a ser agregado
     * @return true si el comentario fue agregado correctamente
     */
    public boolean addNodoComment(Comment comment){
        return root.addNodoComment(comment);
    }
    /**
     * Devuelve el usuario con el id especificado. Retorna null si el elemento no se halla en el árbol o no ha sido encontrado.
     * @param id id del usuario a buscar
     * @return usuario con el id indicado
     */
    public User searchUser(int id){
        NodoUser nodoUser=root.searchNodoUser(id);
        if(nodoUser!=null) return nodoUser.user;
        return null;
    }
    /**
     * Devuelve el usuario con el email especificado. Retorna null si el elemento no se halla en el árbol o no ha sido encontrado.
     * @param email email del usuario a buscar
     * @return usuario con el email indicado
     */
    public User searchUserByEmail(String email){
        NodoUser nodoUser=root.searchNodoUser(email);
        if(nodoUser!=null) return nodoUser.user;
        return null;
    }
    /**
     * Devuelve el usuario autor de un post, basado en el id de este último. Retorna null si el elemento no se halla en el árbol o no ha sido encontrado.
     * @param postId id del post
     * @return usuario con el id indicado
     */
    public User searchUserByPost(int postId){
        NodoUser nodoUser=root.searchNodoUserByPost(postId);
        if(nodoUser!=null) return nodoUser.user;
        return null;
    }
    /**
     * Devuelve el post con el id especificado. Retorna null si el elemento no se halla en el árbol o no ha sido encontrado.
     * @param id id del post a buscar
     * @return post con el id indicado
     */
    public Post searchPost(int id){
        NodoPost nodoPost=root.searchNodoPost(id);
        if(nodoPost!=null) return nodoPost.post;
        return null;
    }
    /**
     * Devuelve el comentario con el id especificado. Retorna null si el elemento no se halla en el árbol o si no ha sido encontrado.
     * @param id id del comentario a buscar
     * @return comentario con el id indicado
     */
    public Comment searchComment(int id){
        NodoComment nodoComment=root.searchNodoComment(id);
        if(nodoComment!=null) return nodoComment.comment;
        return null;
    }
    /**
     * Devuelve el número de posts del usuario con el id especificado. Retorna -1 si el usuario no se halla en el árbol o si no ha sido encontrado.
     * @param id del usuario
     * @return número de posts que ha realizado el usuario
     */
    public int numPostOfUser(int id) {
        return root.numPostsOfUser(id);
    }
    /**
     * Devuelve el número de comentarios en el post con el id especificado. Retorna -1 si el post no se halla en el árbol o si no ha sido encontrado.
     * @param id del post
     * @return número de comentarios hechos sobre el post
     */
    public int numCommentsOfPost(int id) {
        return root.numCommentsOfPost(id);
    }
    /**
     * Devuelve el número de comentarios del usuario con el id especificado. Retorna -1 si el usuario no se halla en el árbol o si no ha sido encontrado.
     * @param id del usuario
     * @return número de comentarios que ha realizado el usuario
     */
    public int numCommentsOfUser(int id){
        return root.numCommentsOfUser(id);
    }
    /**
     * Devuelve un vector con los usuarios más activos (basado en la suma de sus posts y comentarios), el cual está organizado descendentemente. El tamaño del vector viene dado por parámetro.
     * @param numUsers número de usuarios en el vector resultante
     * @return vector con los usuarios con mayor actividad
     */
    public NodoUser[] usersMásActivos(int numUsers){
        return root.usersMásActivos(numUsers);
    }
    /**
     * Devuelve una lista con todos los usuarios de la estructura de datos.
     * @return lista con los usuarios del árbol
     */
    public PointedList<User> getAllUsers(){
        return root.getAllUsers();
    }
    /**
     * Devuelve una lista con todos los posts de la estructura de datos.
     * @return lista con los posts del árbol
     */
    public PointedList<Post> getAllPosts(){
        return root.getAllPosts();
    }
    /**
     * Devuelve una lista con todos los comentarios de la estructura de datos.
     * @return lista con los comentarios del árbol
     */
    public PointedList<Comment> getAllComments(){
        return root.getAllComments();
    }
    /**
     * Devuelve una lista con todos los posts del usuario con el id especificado. Retorna null si el usuario no se halla en el árbol o si no ha sido encontrado.
     * @param id id del usuario
     * @return lista con todos los posts de un usuario
     */
    public PointedList<Post> getPostsFromUser(int id){
        return root.getPostsFromUser(id);
    }
    /**
     * Devuelve una lista con todos los comentarios en el post con el id especificado. Retorna null si el post no se halla en el árbol o si no ha sido encontrado.
     * @param id id del post
     * @return lista con todos los omentarios hechos sobre un post
     */
    public PointedList<Comment> getCommentsFromPost(int id){
        return root.getCommentsFromPost(id);
    }
    /**
     * Devuelve una lista con todos los comentarios del usuario con el id especificado. Retorna null si el usuario no se halla en el árbol o si no ha sido encontrado.
     * @param id id del usuario
     * @return lista con todos los comentarios de un usuario
     */
    public PointedList<Comment> getCommentsByUser(int id){
        return root.getCommentsByUser(id);
    }
    /**
     * Devuelve una lista con todos los comentarios del usuario con el email especificado. Retorna null si el usuario no se halla en el árbol o si no ha sido encontrado.
     * @param email id del usuario
     * @return lista con todos los comentarios de un usuario
     */
    public PointedList<Comment> getCommentsByUser(String email){
        return root.getCommentsByUser(email);
    }
    /**
     * Devuelve una lista con todos los elementos de la estructura de datos que contienen la cadena especificada dentro de su información.
     * @param string cadena a buscar dentro del árbol
     * @return lista con todos los elementos que contienen la cadena indicada
     */
    public PointedList<Object> searchAll(String string){
        return root.searchAll(string);
    }
    /**
     * Devuelve una lista con todos los elementos de la estructura de datos cuyo id es el especificado por parámetro.
     * @param id id de los elementos a buscar dentro del árbol
     * @return lista con todos los elementos que poseen el id indicado
     */
    public PointedList<Object> searchAll(int id){
        return root.searchAll(id);
    }
    /**
     * Devuelve una lista con todos los usuarios de la estructura de datos que contienen la cadena especificada dentro de su información.
     * @param string cadena a buscar dentro de los usuarios del árbol
     * @return lista con todos los usuarios que contienen la cadena indicada
     */
    public PointedList<Object> searchAtUsers(String string){
        return root.searchAtUsers(string);
    }
    /**
     * Devuelve una lista con el usuario de la estructura de datos cuyo id es el especificado por parámetro.
     * @param id id del usuario a buscar dentro del árbol
     * @return lista con el usuario que posee el id indicado
     */
    public PointedList<Object> searchAtUsers(int id){
        return root.searchAtUsers(id);
    }
    /**
     * Devuelve una lista con todos los posts de la estructura de datos que contienen la cadena especificada dentro de su información.
     * @param string cadena a buscar dentro de los posts del árbol
     * @return lista con todos los posts que contienen la cadena indicada
     */
    public PointedList<Object> searchAtPosts(String string){
        return root.searchAtPosts(string);
    }
    /**
     * Devuelve una lista con el post de la estructura de datos cuyo id es el especificado por parámetro.
     * @param id id del post a buscar dentro del árbol
     * @return lista con el post que posee el id indicado
     */
    public PointedList<Object> searchAtPosts(int id){
        return root.searchAtPosts(id);
    }
    /**
     * Devuelve una lista con todos los comentarios de la estructura de datos que contienen la cadena especificada dentro de su información.
     * @param string cadena a buscar dentro de los comentarios del árbol
     * @return lista con todos los comentarios que contienen la cadena indicada
     */
    public PointedList<Object> searchAtComments(String string){
        return root.searchAtComments(string);
    }
    /**
     * Devuelve una lista con el comentario de la estructura de datos cuyo id es el especificado por parámetro.
     * @param id id del comentario a buscar dentro del árbol
     * @return lista con el comentario que posee el id indicado
     */
    public PointedList<Object> searchAtComments(int id){
        return root.searchAtComments(id);
    }
    /**
     * Representa gráficamente el árbol en sentido horizontal basado en las coordenadas, el diámetro de cada nodo y los intervalos vertical y horizontal especificados por parámetro.
     * @param g2 objeto de la clase {@link java.awt.Graphics2D} asociado al objeto {@link java.awt.Graphics} del panel en que se dibujará el árbol.
     * @param x coordenada horizontal respecto al origen del panel
     * @param y coordenada vertical respecto al origen del panel
     * @param diam diámetro de los nodos a dibujar
     * @param horInterval longitud en pixeles del intervalo horizontal en el que se dibujará el árbol
     * @param verticalInterval longitud en pixeles del intervalo vertical en el que se dibujará el árbol
     */
    public void paintTree(Graphics2D g2, int x, int y, int diam, int horInterval, int verticalInterval){
        root.paintNodoRoot(g2, x, y, diam, horInterval, verticalInterval);
    }
}