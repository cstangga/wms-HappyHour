package wms.menu.menuView;

import wms.menu.controller.InboundManagementController;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;
import wms.menu.resultview.InboundManagementResultView;

import java.util.List;
import java.util.Scanner;

public class InboundManagementView {
    InboundManagementController inboundManagementController = new InboundManagementController();
    // 발주 관리 ( 공장에 발주를 넣는다)

    /**
     * 실패하는 경우
     * 발주량 > 재고 max ->이 경우는 실패로, 이유와 다시 주문으로 간다
     * 우리가 가지고 있는 상품 테이블을 기준으로 발주 가능이 나오므로
     * 없는 물건을 사서 실패하는 경우는 없다
     */
    // 2. 발주 내역
    public void InboundManagementMenu() {
        Scanner sc = new Scanner(System.in);
        String choice;

        String menu = """
                ----------------발주---------------- 
                 1. 발주 신청(+발주 가능 상품 목록 출력)
                 2. 발주 내역
                 3. 나가기 (뒤로 가기 느낌으로)
                 메뉴 번호를 입력 해주세요 : """;
        System.out.printf(menu);
        choice = sc.next();

        while (true) {
            switch (choice) {
                // 발주 신청하기
                case "1": inputInboundOrder();

                //발주 내역 보기
                case "2" : inboundManagementController.inboundOrderList();

                // 메인 메뉴로 가기
                case "3" : return;
                default:
                    System.out.printf("잘못된 메뉴 번호입니다 : %s\n",choice);
            }
        }
    }

    private void inputInboundOrder()
    {
        // HashMap으로 쓴 이유는 상품번호 + 수량을 담기 위해
        // 장바구니같은 개념으로 담으려고 했지만 실력이 부족해서 안됨......

        Scanner sc=new Scanner(System.in);

        int product_no;
        int inbound_quantity;
        String choice;
        String status;

        InboundOrderDto result;
        List<InboundOrderListDto> inboundOrderList=inboundManagementController.inboundOrderAbleList();

        InboundManagementResultView.inboundAbleList(inboundOrderList);// 발주 메뉴 출력

        InboundOrderDto inboundOrderDto=new InboundOrderDto();

        while(true)
        {
            System.out.printf("상품 번호를 입력해주세요 : ");
            product_no=sc.nextInt();

            System.out.printf("수량을 입력 해주세요 : ");
            inbound_quantity=sc.nextInt();

            int resultInfor=inboundManagementController.checkInputInfor(product_no,inbound_quantity);
            if(resultInfor==0)
            {
                System.out.printf("입력 정보 > 상품번호 : %d 수량 : %d\n",product_no,inbound_quantity);
                System.out.printf("잘못된 입력 정보입니다, 상품번호 : 60000~60020, 수량 > 0 \n");
                continue;
            }

            inboundOrderDto.setProductNo(product_no);// Dto에 상품번호 입력
            inboundOrderDto.setAmount(inbound_quantity);// Dto에 수량 입력

            // 검색한 상품이 맞는지 확인
            result=inboundManagementController.inboundOrderCheck(inboundOrderDto);

            InboundManagementResultView.nowInboundOrder(result);// 주문한 내용 알려준다

            System.out.printf("\n이대로 발주를 하시겠습니까?? Y / N : ");
            choice=sc.next();
            if(choice.equals("y") || choice.equals("Y"))
            {
                System.out.printf("발주 및 입고를 진행하시 겠습니까?? : Y / N : ");
                status=sc.next();
                if(status.equals("Y") || status.equals("y"))
                {
                    status="completed";
                }
                else status="pending";
                // 다시 주문이 없으면 입력한 내용 테이블에 넣는다
                inboundManagementController.inputInbound(inboundOrderDto, status);
            }
        }
    }

    /**
     * 1. 발주 작성
     *  1) 상품 목록
     *  2) 선택, 선택을 하면 수량까지 입력하는건가??
     *  3) 취소 - 뒤로 가기 느낌인가??
     *
     * 2. 발주 내역
     *  1) 발주 내역 출력 로직 실행
     */
}
