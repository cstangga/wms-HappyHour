package wms.menu.model.service;

import org.apache.ibatis.session.SqlSession;
import wms.common.ErrorView;
import wms.menu.model.dao.InboundOrderMapper;
import wms.menu.model.dao.ReceiptProductLogMapper;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.InboundOrderListDto;
import wms.menu.model.dto.ReceiptProductLogDto;


import java.util.List;

import static wms.common.MyBatisTemplate.getSqlSession;

public class InboundManagementService {
    public List<InboundOrderListDto> inboundOrderAbleList() {
        // 주문 가능한 리스트 가져와서 콘트롤러로 반환해
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper=sqlSession.getMapper(InboundOrderMapper.class);
        List<InboundOrderListDto> inboundOrderListDtoList=inboundOrderMapper.findAbleOrderMenu();
        return inboundOrderListDtoList;

    }

    //

    public List<InboundOrderListDto> inboundOrderList()
    {
        SqlSession sqlSession=getSqlSession();

        InboundOrderMapper inboundOrderMapper =sqlSession.getMapper(InboundOrderMapper.class);
        List<InboundOrderListDto> inboundOrderList= inboundOrderMapper.findinboundOrderList();

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
        inboundOrderDto.setCargo_space(inboundOrderMapper.findCargoSpace(inboundOrderDto.getProductNo()));

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

    public ReceiptProductLogDto insertInbound(InboundOrderDto inboundOrderDto, String status) {

        ReceiptProductLogDto receiptProductLogDto = new ReceiptProductLogDto();
        int result;

        //1. 발주 inbound 테이블
        //2. 발주 상품번호 inbound_product
        //3. 입고 상품 receipt_product
        //4. 입고 기록 receipt_log 삽입 해야됨
        SqlSession sqlSession=getSqlSession();
        InboundOrderMapper inboundOrderMapper =sqlSession.getMapper(InboundOrderMapper.class);

        try {
            //1. 발주 inbound 테이블(제조사번호, 상태)
            inboundOrderDto.setInboundStatus(status);
            inboundOrderMapper.
                    insertInboundOrder(
                            inboundOrderDto
                    );

            //2. 발주 상품번호 테이블 inbound_product(상품번호, 수량)
            inboundOrderMapper.insertInboundProduct(
                    inboundOrderDto
            );

            sqlSession.commit();

            if(status.equals("completed"))
            {
                // 발주완료로 처리를 했으니 바로 입고기록, 입고상품 테이블을 입력한데

                // autoincrement키가 있으니 따로 속성 안만들고 인바운드에 들어간 객체의 키값을 쓰면 된다
                // 입고 상품테이블, 입고 기록 테이블 속성을 가진 객체
                receiptProductLogDto.setInboundNo(inboundOrderDto.getInboundNo()); // 발주 번호 주고
                receiptProductLogDto.setProductNo(inboundOrderDto.getProductNo()); // 상품 번호
                receiptProductLogDto.setAmount(inboundOrderDto.getAmount()); // 수량
                receiptProductLogDto.setCargoSpace(inboundOrderDto.getCargo_space()); // cargo_space

                return receiptProductLogDto;
            }
            return receiptProductLogDto;

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally {
            sqlSession.close();
        }

    }

    public int insertReceiptProductLog(ReceiptProductLogDto receiptProductLogDto)
    {
        SqlSession sqlSession=getSqlSession();
        ReceiptProductLogMapper receiptProductLogMapper=sqlSession.getMapper(ReceiptProductLogMapper.class);
        int result;

        try {
            //3. 입고 기록 receipt_log(입고번호, 일자)
            // ReceiptLog 테이블에 입력
            // receipt_log를 먼저 해야지 auto_increment 값을 받아와야 함
            receiptProductLogMapper.insertReceiptLog(receiptProductLogDto);

            //4. 입고 상품 테이블 receipt_product(입고번호, 발주번호, 상품번호, 수량, 적재)
            //ReceiptProduct 테이블에 입력
            result=receiptProductLogMapper.insertReceiptProduct(receiptProductLogDto);

            sqlSession.commit();

            return result;

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally {
            sqlSession.close();
        }



    }

    public int checkInputInfor(int productNo, int inboundQuantity) {
        if(productNo>60000 && productNo<60021) {
            if (inboundQuantity > 0)
                return 1;
        }
        return 0;
    }
}
