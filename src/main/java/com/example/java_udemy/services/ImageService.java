package com.example.java_udemy.services;

import com.amazonaws.services.ec2.model.Image;
import com.example.java_udemy.services.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage geJpgImageFromFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!"png".equals(fileExtension) && !"jpg".equals(fileExtension)){
            throw  new FileException("Não é aceito extensões diferentes de PNG ou JPG");
        }
        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if("png".equals(fileExtension)){
                img = PngToJpg(img);
            }
            return img;
        }catch (IOException e){
            throw new FileException("Não foi possível ler o arquivo");
        }

    }

    public BufferedImage PngToJpg(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_INT_RGB);
        newImg.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return newImg;
    }

    public InputStream getInputStream(BufferedImage bfImage, String extension){
       try {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bfImage, extension, os);
        return new ByteArrayInputStream(os.toByteArray());
       }catch (IOException e){
           throw new FileException("Erro ao ler o arquivo");
       }

    }
}
