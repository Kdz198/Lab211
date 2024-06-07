import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MotoList extends HashMap<String, Motorbike> {

    public void addElement(Motorbike m) {
       String licensePlate=m.getLicencePlate();
        put(licensePlate, m);

    }

    public void listByDistrict(String distCode) {
        for (Motorbike m : values()) {
            String licensePlate = m.getLicencePlate();
            String districtCode = licensePlate.substring(2, 3);
            
            if (districtCode.equalsIgnoreCase(distCode)) {
                String owner = m.getOwner();
                double value = m.getValue();
                System.out.printf("%s - %s - %s\n", licensePlate, owner, value);
            }
        }
        
        
    }

    public long totalValue() {
        //TODO: return a total of values in the list object.
        long value = 0;
        for (Motorbike m : values()) {
            value+=m.getValue();
        }
        return value;
    }

}