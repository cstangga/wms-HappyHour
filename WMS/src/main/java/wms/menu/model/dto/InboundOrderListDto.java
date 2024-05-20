package wms.menu.model.dto;

public class InboundOrderListDto {
    private int productNo;
    private String productName;
    private int quantity;
    private String  phone;
    private String manufacturerName;
    private String  manufacturerAddress;



    @Override
    public String toString() {
        return "InboundOrderListDto{" +
                "productNo=" + productNo +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", phone='" + phone + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", manufacturerAddress='" + manufacturerAddress + '\'' +
                '}';
    }

    public InboundOrderListDto() {
    }

    public InboundOrderListDto(int productNo, String productName, int quantity, String phone, String manufacturerName, String manufacturerAddress) {
        this.productNo = productNo;
        this.productName = productName;
        this.quantity = quantity;
        this.phone = phone;
        this.manufacturerName = manufacturerName;
        this.manufacturerAddress = manufacturerAddress;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerAddress() {
        return manufacturerAddress;
    }

    public void setManufacturerAddress(String manufacturerAddress) {
        this.manufacturerAddress = manufacturerAddress;
    }
}
