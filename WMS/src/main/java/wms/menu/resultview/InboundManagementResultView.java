package wms.menu.resultview;

import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

public class InboundManagementResultView {
    public static void nowInboundOrder(InboundOrderDto inboundOrderDto)
    {
        System.out.printf("현재 주문 상품 : %s, 주문 수량 = %d\n",
                inboundOrderDto.getProductName(),
                inboundOrderDto.getAmount());
    }
    public static void inboundOrderList(List<InboundOrderListDto> inboundOrderList)
    {
        //여기는 발주 했던 리스트를 보여준다
        System.out.println("---------------------------------------------------");
        System.out.printf(" 발주번호 상품명 제조사번호 수량 일자 상태");
        System.out.println("1번           Y             1병");
        System.out.println("2번           N             3병");
        System.out.println("3번           N             5병");
        System.out.println("4번           Y             2병");
        /*System.out.println("---------------------------------------------------");
        for(InboundOrderDto inboundOrderDto : inboundOrderDtoList) {
            System.out.printf("%d\t%-15s%s\t%d\t%s\n",
                    inboundOrderDto.getProduct_no(),
                    inboundOrderDto.getOrderable(),
                    inboundOrderDto.getInbound_quantity()
            );
        }
        System.out.println("---------------------------------------------------");*/
    }

    public static void inboundAbleList(List<InboundOrderListDto> inboundOrderDtoList)
    {
        // 우리 상품의 재고와 비교해서 주문 가능 여부를 판단하여 발주 메뉴들을 출력한다
        // 지금은 테이블에 없어서 임의로 만들었다
        System.out.println("---------------------------------------------------");
        System.out.println("상품번호"+ "상품이름"+ "단위"+ "전화번호"+"제조사"+  "주소");
        System.out.println("---------------------------------------------------");
        for(InboundOrderListDto inboundOrderDto : inboundOrderDtoList) {
            System.out.printf("%d\t%-5s\t%d\t%s\t%s\t%s\n",
                    inboundOrderDto.getProductNo(),
                    inboundOrderDto.getProductName(),
                    inboundOrderDto.getQuantity(),
                    inboundOrderDto.getManufacturerName(),
                    inboundOrderDto.getPhone(),
                    inboundOrderDto.getManufacturerAddress()
            );
        }
        System.out.println("---------------------------------------------------");
    }
    // 카테고리로 정렬
    // 상품넘버로 정렬
}
