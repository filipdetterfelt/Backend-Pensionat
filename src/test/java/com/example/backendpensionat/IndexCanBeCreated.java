package com.example.backendpensionat;

import com.example.backendpensionat.Controllers.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class IndexCanBeCreated {

    @Autowired
    private IndexController controller;

    @Test
    public void contextLoads()throws Exception{
        assertThat(controller).isNotNull();
    }


}
