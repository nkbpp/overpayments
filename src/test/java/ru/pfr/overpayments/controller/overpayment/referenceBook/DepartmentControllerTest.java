package ru.pfr.overpayments.controller.overpayment.referenceBook;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.pfr.overpayments.config.sec.WithMockCustomUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@WebMvcTest(DepartmentController.class) // создаст только бин контроллера, а репозиторий создавать не будет.
class DepartmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    //@WithMockUser(username="spring")
    @WithMockCustomUser
    void vievReferenceBookReasonsForOverpayments() throws Exception {
        mvc.perform(get("/overpayment/referenceBook/vievDepartment"))
                .andExpect(status().isOk());
    }

}