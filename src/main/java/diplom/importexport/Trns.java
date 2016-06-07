package diplom.importexport;

import java.util.ArrayList;

/**
 * Created by alexander.talismanov on 06.06.2016.
 */
public class Trns {
    public ArrayList<Trn> getListOfTrn() {
        return listOfTrn;
    }

    public void setListOfTrn(ArrayList<Trn> listOfTrn) {
        this.listOfTrn = listOfTrn;
    }

    private ArrayList<Trn> listOfTrn = new ArrayList<>();
}
