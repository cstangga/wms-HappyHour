package wms.menu.model.service;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wms.menu.model.dao.InboundOrderMapper;
import wms.menu.model.dto.InboundOrderDto;

import static wms.common.MyBatisTemplate.getSqlSession;

class InboundManagementServiceTest {

    SqlSession sqlSession;
    InboundOrderMapper inboundOrderMapper;
    @BeforeEach
    void setUp() {
        sqlSession=getSqlSession();
        inboundOrderMapper=sqlSession.getMapper(InboundOrderMapper.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void inboundOrderCheck() {
        InboundOrderDto inboundOrderDto=new InboundOrderDto();

        inboundOrderDto.setProductNo(60014);
        inboundOrderDto.setAmount(10);
        inboundOrderDto.setManufacturer(1);
        inboundOrderMapper.insertInboundProduct(
                inboundOrderDto
        );
        System.out.println(inboundOrderDto.getInboundNo());

    }
}