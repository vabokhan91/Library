package by.epam.bokhan.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public class ImageConverter {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Converts object of type Part to base64 String
     * @param userPhoto object to convert
     * @return converted String
     * */
    public static String convertImageToBase64(Part userPhoto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        String photo = null;
        try {
            inputStream = userPhoto.getInputStream();
            int reads = inputStream.read();
            while (reads != -1) {
                byteArrayOutputStream.write(reads);
                reads = inputStream.read();
            }
            byte[] b = byteArrayOutputStream.toByteArray();
            photo = Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, String.format("Can not convert to string. Reason : %s", e));
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close stream. Reason : %s", e));
            }
        }
        return photo;
    }
}
