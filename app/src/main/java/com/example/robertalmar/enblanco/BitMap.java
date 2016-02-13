package com.example.robertalmar.enblanco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class BitMap {
    public BitMap(){}
    //Para insertar a la database
    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, stream);
        return stream.toByteArray();
    }

    //Para obtener de la database
    public static Bitmap getImatge(byte[] imatge){
        return BitmapFactory.decodeByteArray(imatge,0,imatge.length);
    }
}
