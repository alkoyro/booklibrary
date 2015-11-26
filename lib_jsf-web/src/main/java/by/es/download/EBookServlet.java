package by.es.download;


import by.es.bean.FileUploadBean;
import by.es.download.util.FileStreamer;
import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.entity.predefined.BookVersion;
import by.es.util.AppProps;

import javax.ejb.EJB;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class EBookServlet extends GenericServlet {

    private static final Logger log = Logger.getLogger(EBookServlet.class.getName());

    public static final int DEFAULT_BUFFER_SIZE = 1024 * 25;
    @EJB
    private PurchaseDAO purchaseDAO;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String fileName = ((HttpServletRequest) request).getPathInfo();
        String suffix = '/' + AppProps.get("ebook.preview.suffix");
        File file = new File(request.getServletContext().getRealPath(AppProps.get("ebook.folder.path") + fileName));
        FileStreamer streamer = new FileStreamer();
        if (fileName.startsWith(suffix)) {
            streamer.streamFile(file, response.getOutputStream(), DEFAULT_BUFFER_SIZE);
        } else {
            Long userId = (Long) request.getAttribute("userId");
            Long bookId = (Long) request.getAttribute("bookId");
            if (purchaseDAO.isExists(userId, bookId, BookVersion.EBOOK)) {
                streamer.streamFile(file, response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            }  else {
                throw new RuntimeException("You must buy this book before downloading");
            }
        }
    }
}
