/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public final class CustomELFunctions {
    
    private static final Logger log = Logger.getLogger(CustomELFunctions.class.getName());


    public static List getSubList(List<?> list, int firstIndex, int endIndex) {
        if (list == null) {
            log.info("List is null!!");
            list = new ArrayList<Object>();
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        if (firstIndex < 0) {
            firstIndex = 0;
        }
        return list.subList(firstIndex, endIndex);
    }
}
