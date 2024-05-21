package wms.menu.resultview;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wms.menu.model.dao.InboundOrderMapper;
import wms.menu.model.dto.InboundOrderListDto;

import java.util.List;

import static wms.common.MyBatisTemplate.getSqlSession;

class InboundManagementResultViewTest {
    SqlSession sqlSession;
    InboundOrderMapper inboundOrderMapper;

    @AfterEach
    void tearDown() {
    }


    @DisplayName("섹션위치 확인 후, 발주수량 + 재고량 > 수용량 확인")
    @Test
    void inbound(){
        this.sqlSession=getSqlSession();
        this.inboundOrderMapper =sqlSession.getMapper(InboundOrderMapper.class);
        //InboundCompare inboundCompare=inboundOrderListMapper.compareInfor(500);
/*
        System.out.println(inboundCompare.getAbleAmount()+" "+inboundCompare.getSectionNo());
*/

        Integer section= inboundOrderMapper.findCategoryCode(60020);
        if(section==0)
        {
            System.out.printf("널입니다");
        }
        else
        System.out.println(section);
    }
}