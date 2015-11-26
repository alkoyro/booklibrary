package by.es.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 13.02.12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
class ToJson {
    ToJson() {
        labels = new ArrayList<String>();
        values = new ArrayList<Double>();
    }

    List<String> labels;
    List<Double> values;

}
