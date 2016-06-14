package diplom.importexport;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by alexander.talismanov on 06.06.2016.
 */
@XmlRootElement(name = "transactions")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "transactions")
public class Trns {

    @XmlElement(name = "transaction")
    private ArrayList<Trn> listOfTrn = new ArrayList<>();


    public ArrayList<Trn> getListOfTrn() {
        return listOfTrn;
    }

    public void setListOfTrn(ArrayList<Trn> listOfTrn) {
        this.listOfTrn = listOfTrn;
    }



    @Override
    public String toString() {
        return "Trns{" +
                "listOfTrn=" + listOfTrn +
                '}';
    }
}
