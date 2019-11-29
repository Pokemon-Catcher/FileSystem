/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Objects;

/**
 *
 * @author Pokemon Catcher
 */
public class AFile implements Serializable  {
    String name;
    Dir parentDir;
    byte[] data;
    File tempCopy;
    
    AFile(String fName, Dir parentDir, File whichFile) throws IOException {
        name=fName;
        this.parentDir=parentDir;
        data=Files.readAllBytes(whichFile.toPath());
    }
    AFile(String fName, Dir parentDir) throws IOException {
        name=fName;
        this.parentDir=parentDir;
        data=new byte[0];
    }
    public void Delete(){
        parentDir=null;
    }
    
    public File GetFile() throws IOException{
        File file = File.createTempFile(name+"012345", ".");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
        tempCopy=file;
        return file;
    }
    
    public void Refresh() throws IOException{
        if(tempCopy==null || !tempCopy.exists()) return;
        data=Files.readAllBytes(tempCopy.toPath());
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
        final AFile other = (AFile) obj;
        return Objects.equals(this.name, other.name);
    }
    
}
