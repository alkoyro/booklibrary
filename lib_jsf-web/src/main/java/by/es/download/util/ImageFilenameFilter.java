package by.es.download.util;

import java.io.File;
import java.io.FilenameFilter;


public class ImageFilenameFilter implements FilenameFilter {
    
    private String imageId;

    public ImageFilenameFilter (String imageId) {
        this.imageId = imageId;
    }
    @Override
    public boolean accept(File dir, String name) {
        String fileImageId = name.substring(0, name.lastIndexOf('.'));
        if (fileImageId.equals(imageId)) {
            return true;
        }
        return false;
    }
}
