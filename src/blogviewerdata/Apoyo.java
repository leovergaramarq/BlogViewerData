package blogviewerdata;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Apoyo {
    public static BufferedImage user=Apoyo.imageFrom("\\user.png"),
        users=Apoyo.imageFrom("\\users.png"),
        comments=Apoyo.imageFrom("\\comments.png"),
        posts=Apoyo.imageFrom("\\posts.png"),
        comment=Apoyo.imageFrom("\\comment.png"),
        post=Apoyo.imageFrom("\\post.png"),
        search=Apoyo.imageFrom("\\search.png"),
        back=Apoyo.imageFrom("\\back.png"),
        next=Apoyo.imageFrom("\\next.png"),
        add=Apoyo.imageFrom("\\add.png"),
        logo=Apoyo.imageFrom("\\logo.png"),
        settings=Apoyo.imageFrom("\\settings.png"),
        browse=Apoyo.imageFrom("\\browse.png"),
        translate=Apoyo.imageFrom("\\translate.png"),
        fondo=Apoyo.imageFrom("\\fondo.jpg"),
        fondoV=Apoyo.imageFrom("\\fondoV.jpg");
    /*
    private void initImages(){
        user=Apoyo.imageFrom("\\user.png");
        users=Apoyo.imageFrom("\\users.png");
        comments=Apoyo.imageFrom("\\comments.png");
        post=Apoyo.imageFrom("\\post.png");
        search=Apoyo.imageFrom("\\search.png");
        back=Apoyo.imageFrom("\\back.png");
        next=Apoyo.imageFrom("\\next.png");
        add=Apoyo.imageFrom("\\add.png");
        logo=Apoyo.imageFrom("\\logo.png");
        settings=Apoyo.imageFrom("\\settings.png");
        browse=Apoyo.imageFrom("\\browse.png");
        translate=Apoyo.imageFrom("\\translate.png");
    }
    */
    
    public static BufferedImage imageFrom(String ruta){
        try {
            return ImageIO.read(new File("src\\res"+ruta));
        } catch (IOException ex) {
            System.out.println("fail");
            Logger.getLogger(BlogViewerData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static String[] split(String cadena, String sep){
        ArrayList<String> split=new ArrayList();
        int i=-1, length=cadena.length(), l=sep.length();
        split.add("");
        while(++i<length){
            int size=split.size();
            if(i+l<length && cadena.substring(i, i+l).equals(sep))
                if(!split.get(size-1).isEmpty()){
                    split.add("");
                    size++;
                }
            
            while(i+l<=length && cadena.substring(i, i+l).equals(sep)) i+=l;
            
            if(i+l>length) split.set(size-1, split.get(size-1)+cadena.substring(i));
            else split.set(size-1, split.get(size-1)+cadena.substring(i, i+1));
        }
        return split.toArray(new String[split.size()]);
    }
    
    public static boolean hasString(String s1, String s2){
        int l1=s1.length(), l2=s2.length();
        if(l2>l1) return false;
        int i=-1;
        
        while(++i+l2<=l1) if(s1.substring(i, i+l2).equals(s2)) return true;
        
        return false;
    }
    
    public static String convertir(String file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }
}