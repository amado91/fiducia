package com.bancomext.fiducia.tools;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

public class ManageFile {
    final Context c;
    String confFileName;

    public  ManageFile (final Context c){
        this.c=c;
    }

    public void generateFile(final File f, final String nombre, final Object data){
        confFileName=nombre;
        final File file = new File(f,nombre);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException ioEx) {
            return;
        }
        try{
            final FileOutputStream fos = new FileOutputStream(file);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Object readFile(final File f, final String nombre){
        confFileName=nombre;
        final File file = new File(f,nombre);
        if ((file.exists()) && (file.length() > 0))
        {
            Object data=null;
            ObjectInputStream ois = null;
            try{

                final FileInputStream fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                data = ois.readObject();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (OptionalDataException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    ois.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return data;
        }
        else{
            return null;
        }
    }
}
