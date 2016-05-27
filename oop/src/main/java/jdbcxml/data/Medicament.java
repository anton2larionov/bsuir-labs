package jdbcxml.data;

import java.time.LocalDate;

/**
 * Сущность Medicament.
 */
public final class Medicament {
    /**
     * ид
     */
    private String id;

    /**
     * название
     */
    private String title;

    /**
     * описание
     */
    private String description;

    /**
     * скидка
     */
    private String discount;

    /**
     * требование рецепта
     */
    private boolean onPrescription;

    /**
     * дата поступления
     */
    private LocalDate purchaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnPrescription() {
        return onPrescription;
    }

    public void setOnPrescription(boolean onPrescription) {
        this.onPrescription = onPrescription;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id='" + id + '\'' +
                ", discount='" + discount + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", on-prescription=" + onPrescription +
                ", purchase-date=" + purchaseDate +
                '}';
    }
}
