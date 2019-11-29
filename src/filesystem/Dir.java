/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Pokemon Catcher
 */
public class Dir  implements Serializable {
    String name;
    Dir parentDir;
    HashSet<AFile> files;
    HashSet<Dir> catalogs;
    Dir(String name){
        this.name=name;
        parentDir=null;
        catalogs=new HashSet<>();
        files=new HashSet<>();
    }
    Dir(String name, Dir parentDir){
    this.name=name;
    this.parentDir=parentDir;
    catalogs=new HashSet<>();
    files=new HashSet<>();
    }
    public Dir CreateCatalog(String name){
        Dir newDir=new Dir(name, this);
        catalogs.add(newDir);
        return newDir;
    }
    public AFile CreateFile(String name) throws IOException{
        AFile newFile=new AFile(name,this);
        files.add(newFile);
        return newFile;
    }
    public AFile AddFile(String name, File file) throws IOException{
        AFile newFile=new AFile(name,this,file);
        files.add(newFile);
        return newFile;
    }
    public String GetDirections(){
        String result=this.name+" содержит:\n";
        for(Dir dir : catalogs){
        result=result+"d: "+dir.name+"\n";
        }
        for(AFile file : files){
        result=result+"f: "+file.name+"\n"; 
        }
        if(files.isEmpty()&&catalogs.isEmpty())
            result+="Каталог пуст.\n";
        result+="======================\n";
        return result;
    }
    public AFile GetFile(String name){
        if(!files.isEmpty())
            for(AFile file : files){
                if(file.name.equals(name)) 
                    return file;
            }
        return null;
    }
    public Dir GetDir(String name){
        if(!catalogs.isEmpty())
            for(Dir dir : catalogs){
                if(dir.name.equals(name)) 
                    return dir;
            }
        return null;
    }
    public void Delete(){
        if(!catalogs.isEmpty())
            catalogs.forEach((dir) -> {
                dir.Delete();
            });
        if(!files.isEmpty())
            files.forEach((file) -> {
                file.Delete();
            });
        if(parentDir!=null) {
            parentDir.DeleteDir(this);
            parentDir=null;
        }
    }
    public void DeleteDir(Dir whichDir){
        catalogs.remove(whichDir);
    }
    public void DeleteFile(AFile whichFile){
        files.remove(whichFile);
        whichFile.Delete();
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dir other = (Dir) obj;
        return Objects.equals(this.name, other.name);
    }
}
