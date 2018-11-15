package de.hda.fbi.db2.stud.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * A Category.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String nameOfCategory;

    private List<Question> qList;

    @OneToMany
    @JoinTable(
            name = "OneToMany",
            joinColumns = {@JoinColumn(name = "fk_Cid")},
            inverseJoinColumns = {@JoinColumn(name = "fk_qListid")},
            uniqueConstraints = {@UniqueConstraint(name = "c_oneToMany", columnNames = {"fk_Qid"})}
    )
    private Collection<Question> cquestion;

    //Constructor
    public Category() {
    }

    public Category(String nameOfCategory, List<Question> qList) {
        this.nameOfCategory = nameOfCategory;
        this.qList = qList;
    }

    //Getter & Setter
    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    public int getCategoryIdId() {
        return cId;
    }

    public List<Question> getqList() {
        return qList;
    }

    public Collection<Question> getCquestion() {
        return cquestion;
    }

    public void setCquestion(Collection<Question> cquestion) {
        this.cquestion = cquestion;
    }

    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Category category = (Category) o;
        return cId == category.cId &&
                Objects.equals(nameOfCategory, category.nameOfCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, nameOfCategory);
    }

    // add List
    public void addqList(Question quest) {

        this.qList.add(quest);
    }
}
