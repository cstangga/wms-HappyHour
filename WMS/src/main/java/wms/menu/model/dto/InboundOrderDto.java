package wms.menu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDto {
    private String productName;
    private int productNo;
    private int manufacturer;
    private int amount;
    private int cargo_space;
    private int categoryNo;
    private int inboundNo; // 발주번호
    private String inboundStatus;// 발주 상태
}