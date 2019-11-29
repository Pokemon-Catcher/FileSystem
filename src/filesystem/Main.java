/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Pokemon Catcher
 */
public class Main {
    
        public static void main(String[] args) throws IOException{
            while(true){
                System.out.println("Это файловая система. Какую операцию выполнить?");
                FileSystem system = null;
                Dir currentCatalog;
                String filename="";
                int num=0;
                while(num!=1&&num!=2){        
                    System.out.println("1.Подключиться");
                    System.out.println("2.Создать");
                    try {
                        Scanner in=new Scanner(System.in);
                        num=in.nextInt();
                        switch(num){
                            case 1:
                                System.out.println("Подключение. Введите имя файла:");
                                filename=in.next();
                                system=FileSystem.Open(filename);
                                break;
                            case 2:
                                System.out.println("Создание. Введите имя файла:");
                                filename=in.next();
                                system=new FileSystem(filename);
                                break;
                            default:
                                System.out.println("Недопустимое значение");
                        }
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if(system==null) return;
                currentCatalog=system.root;
                while(currentCatalog!=null){
                    System.out.print("\n");
                    System.out.print(currentCatalog.GetDirections());
                    System.out.println(filename+". Какую операцию выполнить?");
                    System.out.println("1.Создать каталог");
                    System.out.println("2.Создать файл");
                    System.out.println("3.Добавить файл");
                    System.out.println("4.Удалить каталог");
                    System.out.println("5.Удалить файл");
                    System.out.println("6.Перейти в другой каталог");
                    System.out.println("7.Открыть файл");
                    System.out.println("8.Вернуться");
                    Scanner in=new Scanner(System.in);
                    num=in.nextInt();
                    switch(num){
                        case 1:
                            System.out.println("Создание каталога. Введите имя каталога:");
                            filename=in.next();
                            currentCatalog.CreateCatalog(filename);
                            break;
                        case 2:
                            System.out.println("Создание файла. Введите имя файла:");
                            filename=in.next();
                            currentCatalog.CreateFile(filename);
                            break;
                        case 3:
                            System.out.println("Добавление файла. Введите название файла:");
                            addFile(in.next(),currentCatalog);
                            break;
                        case 4:
                            System.out.println("Удаление каталога. Введите название каталога:");
                            filename=in.next();
                            Dir deletedDir=currentCatalog.GetDir(filename);
                            currentCatalog.DeleteDir(deletedDir);
                            break;
                        case 5:
                            System.out.println("Удаление файла. Введите название файла:");
                            filename=in.next();
                            AFile deletedFile=currentCatalog.GetFile(filename);
                            currentCatalog.DeleteFile(deletedFile);
                            break;
                        case 6:
                            System.out.println("Переход в другой каталог. Введите название каталога:");
                            filename=in.next();
                            currentCatalog=currentCatalog.GetDir(filename);
                            break;
                        case 7:
                            System.out.println("Открытие файла. Введите название файла:");
                            openFile(in.next(),currentCatalog,system);
                            break;
                        case 8:
                            System.out.println("Возвращение.");
                            currentCatalog=currentCatalog.parentDir;
                            system.Save(system.location);
                            break;
                        default:
                            System.out.println("Недопустимое значение.");
                    }
              }
            }
        }
        
        static void addFile(String filename, Dir catalog){
            try {
                FileDialog dialog = new FileDialog((Frame)null, "Выберите файл для добавления");
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                catalog.AddFile(filename, new File(dialog.getFile()));
            } 
            catch (Exception ex) {
                System.out.print(ex.getMessage());
            }       
        }
        
        static void openFile(String filename, Dir catalog, FileSystem system){
            AFile file=catalog.GetFile(filename);
               try { 
                Desktop desktop = Desktop.getDesktop();
                 desktop.open(file.GetFile());
                 system.changedFiles.add(file);
              } catch (Exception ex) {
                System.out.print(ex.getMessage());
              }            
        }
}
