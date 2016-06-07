package diplom.catalogs;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by a.talismanov on 10.05.2016.
 */
public class CategoryGroup {
    private IntegerProperty category;
    private IntegerProperty group;
    private StringProperty categoryName;
    private StringProperty groupName;

    public IntegerProperty categoryProperty(){
        return category;
    }
    public Integer getCategory(){
        return category.getValue();
    }

    public IntegerProperty groupProperty(){
        return group;
    }
    public Integer getGroup(){
        return group.getValue();
    }

    public StringProperty categoryNameProperty(){
        return categoryName;
    }
    public String getCategoryName(){
        return categoryName.getValue();
    }

    public StringProperty groupNameProperty(){
        return groupName;
    }
    public String getGroupName(){
        return groupName.getValue();
    }



    public CategoryGroup(int cat, int grp, String nameOfCat, String nameOfGrp){
        this.category = new SimpleIntegerProperty(cat);
        this.group = new SimpleIntegerProperty(grp);
        this.categoryName = new SimpleStringProperty(nameOfCat);
        this.groupName = new SimpleStringProperty(nameOfGrp);
    }

}
