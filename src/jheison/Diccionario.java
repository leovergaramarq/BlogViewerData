package jheison;

import pointedlist.PointedList;
import java.util.Set;

/**
 *
 * @author Leonardo L.
 */
public class Diccionario<K,V> {
    
    private NodoPar raiz;
    
    Diccionario(){
        raiz=null;
    }
    
    /**
     * Retorna un PointedList conteniendo todas las llaves del diccionario
     * @return 
     * Devuelve una lista con las claves
     */
    public PointedList keySet(){
        PointedList<K> a=new PointedList();
        NodoPar p=raiz;
        
        while(p!=null){
            a.add(p.key);
            p=p.next;
        }
        
        return a;
    }
    
    /**
     * Introcuce o actualiza un valor
     * @param key
     * la clave asociada al valor
     * @param value 
     * el valor asociado a la clave
     */
    public void put(K key,V value){
        if(raiz==null){
            raiz=new NodoPar(key, value);
        }else{
            NodoPar p=raiz;
            while(p.next!=null && !p.key.equals(key)){
                p=p.next;
            }
            if(p.key.equals(key)) p.value=value;
            else p.next=new NodoPar(key,value);
        }
    }
    
    /**
     * Retorna un valor en relación a la clave ingresada
     * @param key
     * la clave en base a la cual hará la busqueda del valor
     * @return 
     * el valor asociado a la clave ingresada
     */
    public V get(K key){
        if(key==null)throw new NullPointerException();
        if(raiz==null)return null;
        NodoPar p=raiz;
        
        while(p.next!=null && !p.key.equals(key)){
            p=p.next;
        }
        
        return p.key.equals(key)? p.value:null;
    }
    
    /**
     * Retorna un PointedList conteniendo todos los valores del diccionario
     * @return 
     * Devuelve una lista con los valores
     */
    public PointedList values(){
        PointedList<V> a=new PointedList();
        NodoPar p=raiz;
        
        while(p!=null){
            a.add(p.value);
            p=p.next;
        }
        
        return a;
    }
    
    /**
     * Pues el clásico toString
     * @return 
     * La interpretación String del Diccionario
     */
    @Override
    public String toString(){
        StringBuffer bf=new StringBuffer("{");
        NodoPar p=raiz;
        if (p!=null) {
            bf.append(p.key).append("=").append(p.value);
            p=p.next;
        }
        while(p!=null){
            bf.append(", ").append(p.key).append("=").append(p.value);
            p=p.next;
        }
        bf.append("}");
        return bf.toString();
    }
    
    class NodoPar{
        K key;
        V value;
        NodoPar next;
        
        NodoPar(K k, V v){
            key=k;
            value=v;
        }
    }
}
