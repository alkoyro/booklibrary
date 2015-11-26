package by.es.download.util;


import by.es.download.ImageServlet;

import java.io.*;
import java.util.logging.Logger;

public class FileStreamer {

    private static final Logger log = Logger.getLogger(ImageServlet.class.getName());

    public void streamFile(File file, OutputStream responseOutputStream, int bufferSize) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file), bufferSize);
            outputStream = new BufferedOutputStream(responseOutputStream, bufferSize);
            byte[] buffer = new byte[bufferSize];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            log.warning(e.getMessage());
        } catch (IOException e) {
            log.warning(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warning(e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.warning(e.getMessage());
                }
            }
        }
    }

}
