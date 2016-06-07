package diplom.catalogs;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by a.talismanov on 06.06.2016.
 */
public class NumOfClients {
    private IntegerProperty clientId;

    public NumOfClients(int clientId) {
        this.clientId = new SimpleIntegerProperty(clientId);
    }

    public int getClientId() {
        return clientId.get();
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }
}
