package com.example.jinwaterpractice.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductServiceTest {
//    @Autowired
//    private MockMvc mockMvc;

    @MockBean
    private ProductService productService; // ProductService가 실제로 구현된 빈 대신에 MockBean으로 대체합니다.

//    @Test
//    public void testCodeCheck() throws Exception {
//        String testProductCode = "YOUR_TEST_PRODUCT_CODE";
//
//        // productService.checkByProductCode()가 true를 반환하도록 설정합니다.
//        when(productService.checkByProductCode(testProductCode)).thenReturn(true);
//
//        // POST 요청을 보냅니다.
//        mockMvc.perform(post("/codeCheck")
//                        .param("code", testProductCode))
//                .andExpect(status().isOk());
//    }
}