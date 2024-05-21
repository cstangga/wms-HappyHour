package wms.menu.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderListDto {

    private int productNo;
    private String productName;
    private int quantity;
    private String manufacturerName;
    private String  phone;
    private String  manufacturerAddress;
    private String date;
    private int amount;
    private String inboundStatus;
    private int inboundNo;
}
