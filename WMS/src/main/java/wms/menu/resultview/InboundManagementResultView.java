package wms.menu.resultview;

import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

public class InboundManagementResultView {
    public static void nowInboundOrder(InboundOrderDto inboundOrderDto)
    {
        System.out.printf("발주 입력 정보 > 상품 : %s, 주문 수량 = %d\n",
                inboundOrderDto.getProductName(),
                inboundOrderDto.getAmount());
    }

    public static void inboundAbleList(List<InboundOrderListDto> inboundOrderDtoList)
    {
        // 우리 상품의 재고와 비교해서 주문 가능 여부를 판단하여 발주 메뉴들을 출력한다
        // 지금은 테이블에 없어서 임의로 만들었다
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("상품번호  "+ "상품이름       "+ "단위       "+ "제조사        "+"전화번호         "+  "주소  ");
        System.out.println("-----------------------------------------------------------------------------------------");
        for(InboundOrderListDto inboundOrderDto : inboundOrderDtoList) {
            System.out.printf("%d\t%-13s\t%d\t%-8s\t%s\t%s\n",
                    inboundOrderDto.getProductNo(),
                    inboundOrderDto.getProductName(),
                    inboundOrderDto.getQuantity(),
                    inboundOrderDto.getManufacturerName(),
                    inboundOrderDto.getPhone(),
                    inboundOrderDto.getManufacturerAddress()
            );
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }
    public static void inboundList(List<InboundOrderListDto> inboundOrderListDtoList)
    {
        System.out.printf("  발주번호  상품이름        수량        발주날짜                발주상태  \n");
        System.out.println("--------------------------------------------------------------------");

        for(InboundOrderListDto inboundOrderListDto : inboundOrderListDtoList)
        {
            System.out.printf("  %4d  \t %-10s %4d \t\t %s \t %s \n",
                    inboundOrderListDto.getInboundNo(),
                    inboundOrderListDto.getProductName(),
                    inboundOrderListDto.getAmount(),
                    inboundOrderListDto.getDate(),
                    inboundOrderListDto.getInboundStatus());
        }
    }

    public static void inboundResult() {
        System.out.println("발주가 정상적으로 입력 됐습니다 🍺🍺🍹🍹");
    }

    public static void receiptResultView() {
        System.out.println("입고 정상적으로 됐습니다 👍");
    }
    // 카테고리로 정렬
    // 상품넘버로 정렬
}
