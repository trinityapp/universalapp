package com.trinity.dynamicforms.Database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
@Entity
public class CheckPointsModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "chkp_Id")
    private String chkpId;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "typeId")
    private String typeId;
    @ColumnInfo(name = "mandatory")
    private String mandatory;
    @ColumnInfo(name = "correct")
    private String correct;
    @ColumnInfo(name = "size")
    private String size;
    @ColumnInfo(name = "Score")
    private String Score;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "Active")
    private String Active;
    @ColumnInfo(name = "Is_Dept")
    private String Is_Dept;
    @ColumnInfo(name = "Logic")
    private String Logic;
    @ColumnInfo(name = "Dependent")
    private String Dependent;
    @ColumnInfo(name = "dependents")
    private List<CheckPointsModel> dependents;
    @ColumnInfo(name = "answer")
    private String answer;

    private String editable;
    private boolean skipped = false;

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }


    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDependent() {
        return Dependent;
    }

    public List<CheckPointsModel> getDependents() {
        return dependents;
    }

    public void setDependents(List<CheckPointsModel> dependents) {
        this.dependents = dependents;
    }

    public void setDependent(String dependent) {
        Dependent = dependent;
    }

    public String getChkpId() {
        return chkpId;
    }

    public void setChkpId(String chkpId) {
        this.chkpId = chkpId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getIs_Dept() {
        return Is_Dept;
    }

    public void setIs_Dept(String is_Dept) {
        Is_Dept = is_Dept;
    }

    public String getLogic() {
        return Logic;
    }

    public void setLogic(String logic) {
        Logic = logic;
    }
    public CheckPointsModel(){

    }

    public CheckPointsModel(String chkpId, String description, String value, String tyId, String mandatory, String correct, String size, String score, String language, String active, String is_Dept, String logic, String Dependent, List<CheckPointsModel> dependent, String answer, String editable, boolean skipped) {
        this.chkpId = chkpId;
        this.description = description;
        this.value = value;
        this.typeId = tyId;
        this.mandatory = mandatory;
        this.correct = correct;
        this.size = size;
        Score = score;
        this.language = language;
        Active = active;
        Is_Dept = is_Dept;
        Logic = logic;
        this.Dependent = Dependent;
        this.dependents = dependent;
        this.answer = answer;
        this.editable = editable;
        this.skipped = skipped;
    }
}
