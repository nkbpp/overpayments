package ru.pfr.overpayments.controller.overpayment.referenceBook;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.pfr.overpayments.config.sec.WithMockCustomUser;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.Department;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.DepartmentRequestMapper;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.DepartmentResponseMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc//запускается полный контекст приложения Spring, но без сервера
class DepartmentControllerRestTest {

    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private DepartmentResponseMapper departmentResponseMapper;
    @Autowired
    private DepartmentRequestMapper departmentRequestMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    //@WithMockUser(username="spring", roles = {"USER"})
    //@WithUserDetails(userDetailsServiceBeanName="myUserDetailsService")
    @WithMockCustomUser
    void findById() throws Exception {
        Mockito.when(departmentService.findById(10L))
                .thenReturn(
                        Department.builder()
                                .id(10L)
                                .build()
                );

        mvc.perform(
                        get("/overpayment/referenceBook/department/10"))
                .andExpect(status().isOk()
                );
    }

}