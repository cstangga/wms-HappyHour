package wms.common;

import wms.common.error.ErrorCode;
import wms.menu.controller.InboundManagementController;
import wms.menu.menuView.MainMenuView;
import wms.menu.model.dto.InboundOrderDto;

public class ErrorView {

    public static void inboundError(InboundOrderDto inboundOrderDto, int nowAmount, int maxAmount) {
        System.out.printf("발주 주문에 실패 했습니다 \n발주 개수 : %d, 현재 재고량 = %d, 최대 재고량 = %d\n",
                inboundOrderDto.getAmount(),
                nowAmount, maxAmount);
        new MainMenuView();
    }
}
