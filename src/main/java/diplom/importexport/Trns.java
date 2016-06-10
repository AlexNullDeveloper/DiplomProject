package diplom.importexport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

/**
 * Created by alexander.talismanov on 06.06.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactions")
public class Trns {
    public ArrayList<Trn> getListOfTrn() {
        return listOfTrn;
    }

    public void setListOfTrn(ArrayList<Trn> listOfTrn) {
        this.listOfTrn = listOfTrn;
    }

    private ArrayList<Trn> listOfTrn = new ArrayList<>();
}
