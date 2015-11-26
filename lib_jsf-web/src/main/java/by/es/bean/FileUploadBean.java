package by.es.bean;

import by.es.download.util.ImageFilenameFilter;
import by.es.util.AppProps;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@ApplicationScoped
public class FileUploadBean implements Serializable {

    private static final String ROOT_PATH = System.getProperty("java.io.tmpdir");
    private static final String DEFAULT_IMAGE_FOLDER = ROOT_PATH + AppProps.get("image.folder.relative.path");
    private static final String DEFAULT_EBOOKS_FOLDER = ROOT_PATH + AppProps.get("ebook.folder.relative.path");
    private static final String DEFAULT_PREVIEW_SUFFIX = AppProps.get("ebook.preview.suffix");
    private static final int DEFAULT_PREVIEW_SIZE = 15;

    private static final Logger log = Logger.getLogger(FileUploadBean.class.getName());

    public void uploadEBook(UploadedFile uploadedFile) {
        uploadFile(uploadedFile, DEFAULT_EBOOKS_FOLDER);
        createEbookPreview(uploadedFile.getName());
    }

    public void uploadImage(UploadedFile uploadedFile, Long bookId) {
        String imageFileName = uploadedFile.getName().substring(uploadedFile.getName().lastIndexOf('.'));
        imageFileName = bookId + imageFileName;
        uploadFile(uploadedFile, DEFAULT_IMAGE_FOLDER, imageFileName);
    }

    public boolean deleteImage(Long bookId) {
        File[] image = (new File(DEFAULT_IMAGE_FOLDER)).listFiles(new ImageFilenameFilter(Long.toString(bookId)));
        if (image.length == 1) {
            return image[0].delete();
        }
        return false;
    }

    public boolean deleteEBook(String bookFileName) {
        File eBook = new File(DEFAULT_EBOOKS_FOLDER + '/' + bookFileName);
        File eBookPreview = new File(DEFAULT_EBOOKS_FOLDER + '/' + DEFAULT_PREVIEW_SUFFIX + bookFileName);
        boolean result = false;
        boolean resultPreview = false;
        if (eBook != null) {
            result = eBook.delete();
        }
        if (eBook != null) {
            resultPreview = eBookPreview.delete();
        }
        return result && resultPreview;
    }

    /**
     * ********************************************************************************
     */

    private void createEbookPreview(String bookFileName) {
        File eBook = new File(DEFAULT_EBOOKS_FOLDER + '/' + bookFileName);
        PDDocument document = null;
        int numberOfPages = 0;
        try {
            document = PDDocument.load(eBook);
            if (!document.isEncrypted()) {
                numberOfPages = document.getNumberOfPages();
                log.info("pages: " + numberOfPages);
                if (numberOfPages > DEFAULT_PREVIEW_SIZE) {
                    while (document.getNumberOfPages() > DEFAULT_PREVIEW_SIZE) {
                        document.removePage(DEFAULT_PREVIEW_SIZE);
                    }
                }
                document.save(DEFAULT_EBOOKS_FOLDER + '/' + DEFAULT_PREVIEW_SUFFIX + bookFileName);
            } else {
                throw new RuntimeException("PDF preview can not be created! File is encrypted!");
            }
        } catch (COSVisitorException e) {
            log.severe(e.getMessage());
        } catch (IOException e) {
            log.severe(e.getMessage());
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
        }

    }

    private void uploadFile(UploadedFile uploadedFile, String path) {
        uploadFile(uploadedFile, path, uploadedFile.getName());
    }

    private void uploadFile(UploadedFile uploadedFile, String path, String fileName) {
        if ((uploadedFile != null) && (uploadedFile.getSize() > 0)) {
            FileOutputStream fileOutputStream = null;
            try {
                File dir = new File(path);
                dir.mkdirs();
                File file = new File(path + '/' + fileName);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(uploadedFile.getBytes());
            } catch (IOException ex) {
                log.severe(ex.getMessage());
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException ex) {
                    log.severe(ex.getMessage());
                }
            }
        }
    }


}
