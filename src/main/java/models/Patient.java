package models;


import java.util.Objects;

public class Patient {
    private String name;
    private String nationalId;
    private String dateTreated;
    private String infection;
    private String tel;
    private String amount;
    private int id;
    private int officerId;

    public Patient(String name, String nationalId, String dateTreated, String infection, String tel, String amount, int officerId) {
        this.name = name;
        this.nationalId = nationalId;
        this.dateTreated = dateTreated;
        this.infection = infection;
        this.tel = tel;
        this.amount = amount;
        this.officerId = officerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id == patient.id && officerId == patient.officerId && Objects.equals(name, patient.name) && Objects.equals(nationalId, patient.nationalId) && Objects.equals(dateTreated, patient.dateTreated) && Objects.equals(infection, patient.infection) && Objects.equals(tel, patient.tel) && Objects.equals(amount, patient.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nationalId, dateTreated, infection, tel, amount, id, officerId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getDateTreated() {
        return dateTreated;
    }

    public void setDateTreated(String dateTreated) {
        this.dateTreated = dateTreated;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}