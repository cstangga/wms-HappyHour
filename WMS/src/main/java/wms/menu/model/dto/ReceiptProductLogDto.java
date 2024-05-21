package wms.menu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptProductLogDto {
    private int receiptNo; // 입고번호
    private int inboundNo; // 발주번호
    private int productNo; // 상품번호
    private int amount; // 수량
    private int cargoSpace; // 적재

}
