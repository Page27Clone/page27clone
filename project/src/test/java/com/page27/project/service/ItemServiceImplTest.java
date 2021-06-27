package com.page27.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    ItemServiceImpl itemServiceImpl;

    @Test
    public void 대표_상품찾기()throws Exception{
//        itemService.getItemDetailDto();
//        return null;
    }

}