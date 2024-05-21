package wms.menu.model.dao;

import wms.menu.model.dto.ReceiptProductLogDto;

public interface ReceiptProductLogMapper {
    int insertReceiptProduct(ReceiptProductLogDto receiptProductLogDto);

    void insertReceiptLog(ReceiptProductLogDto receiptProductLogDto);
}
