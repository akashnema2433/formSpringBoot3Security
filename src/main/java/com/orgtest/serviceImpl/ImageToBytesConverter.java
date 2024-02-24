package com.orgtest.serviceImpl;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageToBytesConverter {
    public static byte[] convertImageToBytes(File imageFile,String contentType) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
