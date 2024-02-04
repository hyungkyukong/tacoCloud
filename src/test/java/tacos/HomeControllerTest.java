package tacos;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest()   //HomeController 웹페이지 테스트
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;    //MockMvc를 주입한다

    @Test
    public void testHomePage() throws Exception {
        log.info("testing...");
        mockMvc.perform(get("/")) //GET을 수행한다.
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")));
                //콘텐츠에 Welcome to가 포함되어야 한다.
    }
}