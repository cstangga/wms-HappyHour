package wms.menu.controller;

import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;
import wms.menu.model.service.InboundManagementService;

import java.util.List;

public class InboundManagementController {
    InboundManagementService inboundManagementService=new InboundManagementService();

    public List<InboundOrderListDto> inboundOrderList() {
        // 발주 내역 컨트롤러 -> 발주 내역 서비스로 일을 시키고, 그 내역을 출력함
        // 발주 내역 보기
        List<InboundOrderListDto> inboundOrderList = inboundManagementService.inboundOrderList();
        return inboundOrderList;
    }

    public InboundOrderDto inboundOrderCheck(InboundOrderDto inboundOrderDto) {
        InboundOrderDto result= inboundManagementService.inboundOrderCheck(inboundOrderDto);
        return result;
        // 발주가 우리 재고랑 확인 해야됨
        // 비교해서 컨트롤러한테 주고, 컨트롤러는 메뉴뷰에서 다시 신청 하겠습니까?? 를 해야되는데
        //
    }

    public void inputInbound(InboundOrderDto inboundOrderDto) {
        System.out.printf("발주테이블 입력을 합니다\n");
        inboundManagementService.insertInbound(inboundOrderDto);
    }
}
