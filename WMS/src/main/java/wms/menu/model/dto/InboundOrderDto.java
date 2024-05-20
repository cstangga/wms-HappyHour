package wms.menu.model.dto;

public class InboundOrderDto {
    private String productName;
    private int productNo;
    private int manufacturer;
    private int amount;
    private int cargo_space;
    private int categoryNo;

    public InboundOrderDto() {
    }

    public InboundOrderDto(String productName, int productNo, int manufacturer, int amount, int cargo_space, int categoryCode) {
        this.productName = productName;
        this.productNo = productNo;
        this.manufacturer = manufacturer;
        this.amount = amount;
        this.cargo_space = cargo_space;
        this.categoryNo = categoryCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCargo_space() {
        return cargo_space;
    }

    public void setCargo_space(int cargo_space) {
        this.cargo_space = cargo_space;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }
}