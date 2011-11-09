package xcon.pilot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalsMess {

    private Map<String, String> lstBarring = new HashMap<String, String>();
    private Map<Integer, String> fetchPreDetails = new HashMap<Integer, String>();

    public GlobalsMess() {
        fetchPreDetails.put(1, "john,vikam,david");
        fetchPreDetails.put(14, "1,2,3");
    }

    public Map<String, String> getLstBarring() {
        
        List<String> tempKeys = setPreParameters(fetchPreDetails.get(1));
        System.out.println("KEY" + tempKeys);

        List<String> tempIds = setPreParameters(fetchPreDetails.get(14));
        System.out.println("VALUE" + tempIds);
        
        for (int index = 0; index < tempIds.size(); index++) {
            System.out.println("VALUE IN KEY" + tempKeys.get(index));
            System.out.println("VALUE IN VALUE" + tempIds.get(index));
            this.lstBarring.put(tempKeys.get(index), tempIds.get(index));
        }
        System.out.println("INSIDE ODB....>>>>>>>>>>>>>>" + lstBarring);

        return this.lstBarring;
    }

    public List<String> setPreParameters(String fetchPreDetailsValue) {
        List<String> arrLstData = new ArrayList<String>();
        Collections.addAll(arrLstData, fetchPreDetailsValue.split(","));
        return arrLstData;
    }

    public void myMethod() {

        System.out.println();
        synchronized(this) {
        }
    }
    public static void main(String[] args) {
        new GlobalsMess().getLstBarring();
    }
}
