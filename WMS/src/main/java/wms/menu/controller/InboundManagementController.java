package wms.menu.controller;

import wms.menu.menuView.MainMenuView;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;
import wms.menu.model.dto.ReceiptProductLogDto;
import wms.menu.model.service.InboundManagementService;
import wms.menu.resultview.InboundManagementResultView;

import java.util.List;

public class InboundManagementController {
    InboundManagementService inboundManagementService=new InboundManagementService();

    public List<InboundOrderListDto> inboundOrderList() {
        // 발주 내역 컨트롤러 -> 발주 내역 서비스로 일을 시키고, 그 내역을 출력함
        // 발주 내역 보기
        List<InboundOrderListDto> inboundOrderList = inboundManagementService.inboundOrderList();
        InboundManagementResultView.inboundList(inboundOrderList);
        return inboundOrderList;
    }



    public InboundOrderDto inboundOrderCheck(InboundOrderDto inboundOrderDto) {
        InboundOrderDto result= inboundManagementService.inboundOrderCheck(inboundOrderDto);
        return result;
        // 발주가 우리 재고랑 확인 해야됨
        // 비교해서 컨트롤러한테 주고, 컨트롤러는 메뉴뷰에서 다시 신청 하겠습니까?? 를 해야되는데
        //
    }

    public void inputInbound(InboundOrderDto inboundOrderDto, String status) {

        int result;
        ReceiptProductLogDto receiptProductLogDto;
        receiptProductLogDto=inboundManagementService.insertInbound(inboundOrderDto, status);
        InboundManagementResultView.inboundResult();
        if(receiptProductLogDto!=null)
        {
            result=inboundManagementService.insertReceiptProductLog(receiptProductLogDto);
            if(result==1)
            {
                InboundManagementResultView.receiptResultView();
            }
        }
        new MainMenuView().mainView();
    }

    public List<InboundOrderListDto> inboundOrderAbleList() {
        // 발주 가능한 상품보기
        List<InboundOrderListDto> inboundOrderListDtoList=inboundManagementService.inboundOrderAbleList();
        return inboundOrderListDtoList;
    }

    public int checkInputInfor(int productNo, int inboundQuantity) {
        int result = inboundManagementService.checkInputInfor(productNo,inboundQuantity);
        return result;
    }
}
