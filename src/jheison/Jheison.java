package jheison;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import pointedlist.PointedList;
import java.util.logging.Logger;


/**
 *
 * @author Leonardo
 */
public class Jheison {
    
    public final static int JSON=0,LIST=1,DICCIONARY_I=2,DICCIONARY_V=3;
    private String json;
    public Object convertido;
    
    public Jheison(String formato){
        reformat(formato);
        if(convertido==null)System.out.println("SDGSDGSRTARRABSVVVEFVV");
    }
    
    /**
     * Crea un Jheison en base a un archivo con la ruta especificada
     * @param Path
     * La ruta del archivo
     * @return 
     * Un Jhesion creado en base al contenido del archivo
     */
    public static Jheison jsonFrom(String Path){
        return new Jheison(conv(Path));
    }
    
    /**
     * Reestructura el Jheison en base a un nuevo contenido
     * @param formato 
     * La nueva estructura Jheison a representar
     */
    public void reformat(String formato){
        json=formato;
        convertido=recoleccion(json);
    }
    
    /**
     * En base a un archivo lo convierte a un String de formato Json
     * @param path
     * La ruta del archivo
     * @return 
     * El String con el presunto contenido en formato Jeison
     */
    public static String conv(String path){
        try(BufferedReader bf=new BufferedReader(new FileReader(path))){
            StringBuffer sb=new StringBuffer();
            
            String line;
            while((line=bf.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        }catch(IOException e){
            System.err.print(e);
        }
        return null;
    }
    
    /**
     * Separa una Lista Json en formato String en sus elementos
     * @param json
     * La lista Json en formato String
     * @return 
     * Una lista String de los presuntos elementos en formato json en la lista
     */
    public static String[] separar(String json){
        PointedList<String> ar=new PointedList();
        int i=0,fin,len=json.length();
        char c;
        while((c=json.charAt(i++))!='[' && i<len);
        if(c!='[')return null;
        fin=i+1;
        /*while((c=json.charAt(i++))!='{' && i<len);
        if(c!='{')return null;
        fin=i+1;*/
        
        int pAbierto=0,pCerrado=0;
        boolean lock=false;
        while(fin<=len && i<len){
            if(!lock){
                c=json.charAt(i++);
                if(c=='{'){
                    lock=true;
                    pAbierto=1;
                    fin=i;
                }
                
            }else{
                c=json.charAt(fin);
                switch(c){
                    case '{':
                        pAbierto++;
                        break;
                    case '}':
                        pCerrado++;
                }
                if(pAbierto>pCerrado) fin++;
                else{
                    lock=false;
                    pAbierto=pCerrado=0;
                    ar.add(json.substring(i, fin));
                    i=fin+1;
                }
            }
        }
        return ar.toArray(new String[0]);
    }
    
    private Diccionario<String,Object> prueba(String json){
        int i=0,fin=0,len=json.length();
        Diccionario<String, Object> map=new Diccionario();
        char c;boolean comilla=false,llave=true;
        String key="";
        
        while(fin<=len && i<len){
            if(llave)if(!comilla){
                c=json.charAt(i++);
                if(c=='\"'){
                    comilla=true;
                    fin=i;
                }else if(c==':')llave=false;
            }else{
                c=json.charAt(fin);
                if(c!='\"')fin++;
                else{
                    comilla=false;
                    key=json.substring(i, fin);
                    i=fin+1;
                }
            }
            else{
                c=json.charAt(i);
                switch(c){
                    case '-':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        fin=selNum(i,json,len);
                        if(comprobarFin(fin, json, LIST)){
                            //map.put(key,Integer.parseInt(json.substring(i, fin+1)));
                            String p=json.substring(i, fin+1);
                            if(p.contains("."))map.put(key,Float.parseFloat(json.substring(i, fin+1)));
                            else map.put(key,Integer.parseInt(json.substring(i, fin+1)));
                        }else return null;
                        i=fin+1;
                        llave=true;
                        break;
                    case '\"':
                        fin=value(json, ++i, '\"', len);
                        map.put(key, json.substring(i, fin));
                        i=fin;
                        llave=true;
                        break;
                    case '{':
                        fin=llaves(json,i,false);
                        map.put(key, prueba(json.substring(i+1, fin)));
                        i=fin;
                        llave=true;
                        break;
                }
                i++;
            }
        }
        
        return map;
    }
    
    private int value(String s, int in,char regla,int len){
        
        while(++in<len && s.charAt(in)!=regla);
        
        return in;
    }
    
    private int llaves(String s,int i,boolean corchete){
        int fin,len=s.length();
        char c,regla=corchete? '[':'{',regla2=corchete? ']':'}';
        while((c=s.charAt(i++))!=regla && i<len);
        if(c!=regla)return i;
        fin=i;
        
        int pAbiertos=1,pCerrados=0;
        while(fin <len && pAbiertos>pCerrados){
            c=s.charAt(fin++);
            if(c==regla)pAbiertos++;
            else if(c==regla2)pCerrados++;
        }
        fin--;
        
        return fin;
        
    }
    //-----------------------------------------------++++++++++++++++++++++++++++++++++++++++++++---------------------------------------
    private Object recoleccion(String json){
        int i=0,fin=0,len=json.length();
        char c;
        //byte estado=0;
        while(i<=len){
            c=json.charAt(i);
            switch(c){
                case '}':
                case ']':
                    return null;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    fin=selNum(i,json,len);
                    if(comprobarFin(fin, json, JSON)){
                        return Integer.parseInt(json.substring(i+1,fin));
                    }else return null;
                case '{':
                    fin=llaves(json, i,false);
                    if(comprobarFin(fin, json, JSON)){
                        return prueba(json.substring(i+1, fin));
                    }else return null;
                case '[':
                    fin=llaves(json, i,true);
                    if(comprobarFin(fin, json, JSON)){
                        return casoLista(json.substring(i+1, fin));
                    }else return null;
                case '\"':
                    fin=value(json, i++, '\"', len);
                    if(comprobarFin(fin,json,JSON)) return json.substring(i, fin);
                    else return null;
                default:
                    i++;
            }
        }
        
        
        return null;
    }
    
    private PointedList<Object> casoLista(String json){
        int i=0,fin=0,len=json.length();
        char c;
        PointedList<Object> array=new PointedList();
        
        while(fin<=len && i<len){
            c=json.charAt(i);
            
            switch(c){
                case '}':
                case ']':
                    return null;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    fin=selNum(i,json,len);
                    if(comprobarFin(fin, json, LIST)){
                        array.add(Integer.parseInt(json.substring(i, fin+1)));
                    }else return null;
                    i=fin+1;
                    break;
                case '{':
                    fin=llaves(json, i,false);
                    if(comprobarFin(fin, json, LIST)){
                        array.add(prueba(json.substring(i+1, fin)));
                    }else return null;
                    i=fin+1;
                    break;
                case '[':
                    fin=llaves(json, i,true);
                    if(comprobarFin(fin, json, LIST)){
                        array.add(casoLista(json.substring(i+1, fin)));
                    }else return null;
                    i=fin+1;
                    break;
                case '\"':
                    fin=value(json, i++, '\"', len);
                    if(comprobarFin(fin, json, LIST)){
                        array.add(json.substring(i, fin));
                    }else return null;
                    i=fin+1;
                    break;
                default:
                    i++;
            }
            
        }
        
        return array;
    }

    private boolean comprobarFin(int i,String json,int caso) {
        char c;
        while(++i<json.length()){
            c=json.charAt(i);
            if(caso==JSON && c!=' ') return false;
            if(caso==DICCIONARY_I){
                if(c==':') return true;
                else if(c!= ' ') return false;
            }
            if(caso==DICCIONARY_V || caso==LIST){
                if(c==',') return true;
                else if(c!= ' ') return false;
            }
        }
        return caso==DICCIONARY_I? false:true;
    }

    private int selNum(int i, String json, int len) {
        char c;
        while(i<len){
            c=json.charAt(i);
            if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9'||c=='.'||c=='-') i++;
            else return i-1;
        }
        return i-1;
    }
    
    private <V> V jsonTo(Class<V> v,Diccionario<String,Object> map) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        int num=0,cadena=0,iterables=0,objetos=0,flotante=0;
        Object[] values=map.values().toArray();
        
        for (Object value : values) {
            if(int.class.isInstance(value)|| value instanceof Integer){
                num++;
            }else if(value instanceof Float){
                flotante++;
            }else if(value instanceof String){
                cadena++;
            }else if(Iterable.class.isInstance(value)){
                iterables++;
            }else objetos++;
        }
        
        int num2=0,cadena2=0,iterables2=0,objetos2=0,flotante2=0;
        Constructor ideal=null;
        
        for (Constructor<?> constructor : v.getConstructors()) {
            for (Class<?> parameter : constructor.getParameterTypes()) {
                if(parameter==int.class){
                    num2++;
                }else if(parameter==float.class){
                    flotante2++;
                }else if(String.class==parameter){
                    cadena2++;
                }else if(Iterable.class.isInstance(parameter)){
                    iterables2++;
                }else objetos2++;
            }
            
            if(num==num2 && cadena==cadena2 && flotante==flotante2 && iterables==iterables2 && objetos==objetos2){
                ideal=constructor;
                break;
            }
            
        }
        
        if(ideal==null)return null;
        
        num=cadena=iterables=objetos=0;
        Object[] par=new Object[ideal.getParameterCount()];
        Class[] idealPar=ideal.getParameterTypes();
        for (int i = 0; i < par.length; i++) {
            if(int.class==idealPar[i]){
                while(!int.class.isInstance(values[num])&& !(values[num] instanceof Integer))num++;
                par[i]=values[num++];
            }else if(float.class==idealPar[i]){
                while(!float.class.isInstance(values[num])&& !(values[num] instanceof Float))flotante++;
                par[i]=values[flotante++];
            }else if(String.class==idealPar[i]){
                while(String.class!=values[cadena].getClass())cadena++;
                par[i]=values[cadena++];
            }else if(Iterable.class.isInstance(idealPar[i])){
                while(!Iterable.class.isInstance(values[iterables]))iterables++;
                par[i]=values[iterables++];
            }else{
                //---------------------------------------------------IMPORTANTE, REVISAR-----------------------------------------------
                while(Diccionario.class != values[objetos].getClass())objetos++;
                par[i]=jsonTo(idealPar[i],(Diccionario<String,Object>)values[objetos++]);
            }
        }
        return (V) ideal.newInstance(par);
    }
    
    /**
     * en base al Jheison devuelve un objeto o una lista de objetos con la estructura de
     * la clase enviada en parámetros, si esta tiene un constructor "compatible".
     * @param ob
     * La clase modelo en la que se creará el objeto/lista
     * Debe ser compatible
     * @return
     * Una lista o un objeto de la clase enviada por parámetros
     * @throws Exception 
     */
    public <Ob> Object jsonTo(Class<Ob> ob)throws Exception{
        if(int.class.isInstance(convertido)){
            if(int.class.isInstance(ob))return (int)convertido;
            else return null;//Cambio por excepción
        }else if(String.class.isInstance(convertido)){
            if(String.class.isInstance(ob))return (int)convertido;
            else return null;//Cambio por excepción
        }else if(PointedList.class.isInstance(convertido)){
            PointedList<Ob> ar=new PointedList();
            for (Object object : (PointedList)convertido) {
                ar.add((Ob)jsonTo(ob,(Diccionario)object));
            }
            return ar;
        }else{
            return (Ob)jsonTo(ob,(Diccionario)convertido);
        }
    }
}