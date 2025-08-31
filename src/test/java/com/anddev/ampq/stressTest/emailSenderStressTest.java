package com.anddev.ampq.stressTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class emailSenderStressTest {

    @Autowired
    private MockMvc mockMvc;

    // Integration test to see the power of the rabbitMQ
    @Test()
    void sending100000Emails() throws JsonProcessingException, Exception {

        HashMap<String, String> request = new HashMap<>(Map.of("message", "Testing email sender"));

        long total = 0;
        for (int i = 0; i < 100000; i++) {
            long start = System.nanoTime();
            mockMvc.perform(post("/api/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request)))
                    .andExpect(status().isOk());
            total += (System.nanoTime() - start);
        }
        System.out.println("medium latency: " + (total / 1000_000.0 / 100000) + " ms/request");

    }

}
