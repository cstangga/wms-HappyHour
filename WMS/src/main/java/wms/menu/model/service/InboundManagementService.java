package wms.menu.model.service;

import org.apache.ibatis.session.SqlSession;
import wms.common.ErrorView;
import wms.menu.model.dao.InboundOrderMapper;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

import static wms.common.MyBatisTemplate.getSqlSession;

public class InboundManagementService {
    Thread checkStatus= new InboundcheckStatus();


    //
    public List<InboundOrderListDto> inboundOrderList()
    {
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper =sqlSession.getMapper(InboundOrderMapper.class);
        List<InboundOrderListDto> inboundOrderList= inboundOrderMapper.findAllInboundOrderList();
        return inboundOrderList;
    }

    public InboundOrderDto inboundOrderCheck(InboundOrderDto inboundOrderDto)
    {
        // 상품번호로 카테고리 알고 -> 카테고리로 최대수용량, 현재 재고량을 알아와 비고를 한다

        int categoryCode=0;
        int nowAmount=0;
        int maxAmount=0;
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper =sqlSession.getMapper(InboundOrderMapper.class);

        //카테고리 코드를 얻고
        categoryCode=inboundOrderMapper.findCategoryCode(inboundOrderDto.getProductNo());

        //상품이름
        inboundOrderDto.setProductName(inboundOrderMapper.findProductName(inboundOrderDto.getProductNo()));

        //cargo_space
        inboundOrderDto.setCargo_space(inboundOrderMapper.findCargoSpace(categoryCode));

        //제조사 번호 manufacturer, 상품넘버로 제조사번호를 얻는다
        inboundOrderDto.setManufacturer(inboundOrderMapper.findManufacturer(inboundOrderDto.getProductNo()));

        nowAmount=inboundOrderMapper.findSumAmount(categoryCode);// 현재 재고량을 구하고
        nowAmount+=inboundOrderDto.getAmount();// 현재 재고량에서 발주량을 더하고

        // 최대 수용량
        maxAmount=inboundOrderMapper.findMaxAmount(categoryCode);

        // 최대보다 많으니 에러가 난다
        try {
            if (maxAmount < nowAmount) {
                ErrorView.inboundError(inboundOrderDto, nowAmount, maxAmount);
            }
            //된다고 컨트롤러에 보내고->메뉴로 보내서 할건지 확인 -> 컨트롤러 -> 서비스- > 나머지 다 입력
            return inboundOrderDto;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }finally {
            if(sqlSession!=null)
                sqlSession.close();
        }
    }

    // 이제 발주 확인까지 됐으니 발주리스트에 넣어야 한다
    public void insertInbound(InboundOrderDto inboundOrderDto) {
        int inboundNo;
        //1. 발주 inbound 테이블
        //2. 발주 상품번호 inbound_product
        //3. 입고 상품 receipt_product
        //4. 입고 기록 receipt_log 삽입 해야됨
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper =getSqlSession().getMapper(InboundOrderMapper.class);

        //1. 발주 inbound 테이블(제조사번호,
        inboundOrderMapper.insertInboundOrder(inboundOrderDto.getManufacturer(),"발주확인");
        // inboundNo=inboundOrderListMapper.findInboundNo(특정 값) 발주번호를 알아야한다

        //2. 발주 상품번호 inbound_product(발주번호, 상품번호, 수량)
        //inboundOrderListMapper.insertInboundProduct(발주번호, 상품번호, 수량)

        //3. 입고 상품 receipt_product(입고번호, 발주번호, 상품번호, 수량, 적재)

        //4. 입고 기록 receipt_log(입고번호, 일자)

    }
    static class InboundcheckStatus extends Thread
    {
        String status;
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper =getSqlSession().getMapper(InboundOrderMapper.class);
        InboundOrderListDto inboundOrderListDto=new InboundOrderListDto();
        int receiptNo;
        int outBoundNo;

        @Override
        public void run() {
            try {
                // 하나씩 순서대로 확인한다는 가정하에
                // 제일 최신인 튜플을 가져온다
                status = inboundOrderMapper.findInoundOrderStatus();

                if(status.equals("check"))
                {
                    //쿼리는 짜서 넣어야될 정보를 Dto에 넣고, 테이블에 정보를 넣는다
                    // 그렇게 해야 다른 변수를 안써도 된다
                    //check된 것중에 제일 최신인 튜플의 발주번호, 제조사번호
                    outBoundNo= inboundOrderMapper.findoutBoundNo();

                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
