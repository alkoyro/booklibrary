package by.es.ejb.entity;

import javax.persistence.*;


@Entity
@Table(name = "PRICE")
public class Price extends PersistenceEntity {
    @Column(name = "COST")
    private double cost;

    @Column(name = "COST_EBOOK")
    private double costEbook;

    public double getCostEbook() {
        return costEbook;
    }

    public void setCostEbook(double costEbook) {
        this.costEbook = costEbook;
    }

     public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
