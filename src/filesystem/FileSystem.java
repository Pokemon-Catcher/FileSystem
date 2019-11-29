/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Pokemon Catcher
 */
public class FileSystem implements Serializable {
    public Dir root;
    public String location;
    public HashSet<AFile> changedFiles;
    
    FileSystem(String path){
       location=path;
       root=new Dir("root");
       changedFiles=new HashSet<>();
    }
    
    FileSystem(){
        location=System.getProperty("user.dir");
        root=new Dir("root");
        changedFiles=new HashSet<>();
    }
    
    public static FileSystem Open(String filename){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
              FileSystem system=(FileSystem)ois.readObject();
              return system;
        }        
        catch(Exception ex){
            System.out.println(ex.getMessage());
        } 
        return null;
    }

    public boolean Save(String filename) throws IOException{
        Iterator<AFile> iter=changedFiles.iterator();
        while(iter.hasNext()){
            iter.next().Refresh();     
          }
        changedFiles.clear();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {   
            oos.writeObject(this);
            System.out.println(filename+" has been saved");
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        } 
        return false;
    }
}
