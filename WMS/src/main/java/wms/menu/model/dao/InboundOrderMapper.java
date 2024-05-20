package wms.menu.model.dao;

import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

public interface InboundOrderMapper {
    // 발주 테이블에 있는 리스트를 가져온다
    List<InboundOrderListDto> findAllInboundOrderList();

    int insertInboundOrder(int manufacturer, String status);

    int findCategoryCode(int productNo);

    int findSumAmount(int categoryNo);// 카테고리를 넣어서 나온 현재 재고량을 가져온다

    int findCargoSpace(int categoryNo);

    int findMaxAmount(int categoryNo);

    String findProductName(int productNo);


    int findManufacturer(int categoryCode);
}
