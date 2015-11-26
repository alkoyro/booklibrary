package by.es.bean;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UploadBean {

    @Inject
    private FileUploadBean fileUploadBean;

    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void uploadImage(Long bookId) {
        fileUploadBean.uploadImage(uploadedFile, bookId);
    }
}
