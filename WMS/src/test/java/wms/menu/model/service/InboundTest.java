package wms.menu.model.service;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import wms.menu.model.dao.InboundOrderMapper;
import wms.menu.model.dao.ReceiptProductLogMapper;
import wms.menu.model.dto.InboundOrderDto;
import wms.menu.model.dto.ReceiptProductLogDto;

import static org.junit.jupiter.api.Assertions.*;
import static wms.common.MyBatisTemplate.getSqlSession;

class InboundTest {

    SqlSession sqlSession;
    InboundOrderMapper inboundOrderMapper;


    @Test
    void inorder()
    {
        this.sqlSession = getSqlSession();
        this.inboundOrderMapper = sqlSession.getMapper(InboundOrderMapper.class);

    }

    @Test
    void receipt()
    {
        SqlSession sqlSession=getSqlSession();
        ReceiptProductLogMapper receiptProductLogMapper=sqlSession.getMapper(ReceiptProductLogMapper.class);
        this.inboundOrderMapper = sqlSession.getMapper(InboundOrderMapper.class);

        ReceiptProductLogDto receiptProductLogDto=new ReceiptProductLogDto();

        InboundOrderDto inboundOrderDto=new InboundOrderDto();

        inboundOrderDto.setProductNo(60014);
        inboundOrderDto.setAmount(1);
        inboundOrderDto.setManufacturer(1);

        inboundOrderDto.setInboundStatus("completed");
        inboundOrderMapper.
                insertInboundOrder(
                        inboundOrderDto
                );
        System.out.printf("auto increment =  %d\n",inboundOrderDto.getInboundNo());

        //4. 입고 기록 receipt_log(입고번호, 일자)
        // ReceiptLog 테이블에 입력
        receiptProductLogMapper.insertReceiptLog(receiptProductLogDto);

        System.out.printf("receiptNo = %d",receiptProductLogDto.getReceiptNo());


        //3. 입고 상품 테이블 receipt_product(입고번호, 발주번호, 상품번호, 수량, 적재)
        //ReceiptProduct 테이블에 입력
        receiptProductLogMapper.
                insertReceiptProduct(
                        receiptProductLogDto
                );



    }

}