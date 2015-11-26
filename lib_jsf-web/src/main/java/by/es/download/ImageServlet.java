package by.es.download;


import by.es.download.util.FileStreamer;
import by.es.download.util.ImageFilenameFilter;
import by.es.util.AppProps;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.logging.Logger;


public class ImageServlet extends GenericServlet {

    private static final Logger log = Logger.getLogger(ImageServlet.class.getName());
    private static final String PATH_TO_IMG_FOLDER = AppProps.get("image.folder.path");
    private static final String DEFAULT_IMAGE = AppProps.get("image.default.path");
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String imageId = ((HttpServletRequest)request).getPathInfo().substring(1);
        File imgFolder = new File(request.getServletContext().getRealPath(PATH_TO_IMG_FOLDER));
        File[] imageFileList = imgFolder.listFiles(new ImageFilenameFilter(imageId));
        File imgFile = null;
        if ((imageFileList != null) && (imageFileList.length > 0)) {
            imgFile = imageFileList[0];
        } else {
            imgFile = new File(request.getServletContext().getRealPath(DEFAULT_IMAGE));
        }
        FileStreamer streamer = new FileStreamer();
        streamer.streamFile(imgFile, response.getOutputStream(), DEFAULT_BUFFER_SIZE);
    }
}
