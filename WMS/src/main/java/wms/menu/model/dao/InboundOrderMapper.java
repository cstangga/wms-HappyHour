package wms.menu.model.dao;

import org.apache.ibatis.annotations.Param;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

public interface InboundOrderMapper {
    // 발주 테이블에 있는 리스트를 가져온다
    List<InboundOrderListDto> findAbleOrderMenu(); // 주문가눙한 메뉴 출력

    void insertInboundOrder(InboundOrderDto inboundOrderDto);

    int findCategoryCode(int productNo);// 상품번호로 카테고리 번호찾기

    int findSumAmount(int categoryNo);// 카테고리를 넣어서 나온 현재 재고량을 가져온다

    int findCargoSpace(int categoryNo);// 카테고리 번호로 cargoSpace 찾기

    int findMaxAmount(int categoryNo); // 최대 수용량

    String findProductName(int productNo); // 상품번호로 상품이름 찾기

    int findManufacturer(int categoryCode); // 카테고리 번호로 제조사 찾기

    int insertInboundProduct(InboundOrderDto inboundOrderDto);

    List<InboundOrderListDto> findinboundOrderList();
}
