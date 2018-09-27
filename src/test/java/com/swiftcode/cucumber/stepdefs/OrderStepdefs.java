package com.swiftcode.cucumber.stepdefs;

import com.google.common.collect.Maps;
import com.swiftcode.config.ApplicationProperties;
import com.swiftcode.service.PlanService;
import com.swiftcode.web.rest.PlanResource;
import com.swiftcode.web.rest.TestUtil;
import com.swiftcode.web.rest.UserJWTController;
import com.swiftcode.web.rest.errors.ExceptionTranslator;
import com.swiftcode.web.rest.vm.LoginVM;
import com.swiftcode.web.rest.vm.PlanVM;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.zh_cn.假如;
import cucumber.api.java.zh_cn.并且;
import cucumber.api.java.zh_cn.当;
import cucumber.api.java.zh_cn.那么;
import gherkin.deps.com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class OrderStepdefs extends StepDefs {
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private UserJWTController jwtController;
    @Autowired
    private PlanResource planResource;

    @Autowired
    private PlanService planService;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    private MockMvc mockMvc;
    private String token;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(jwtController, planResource)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
        MockitoAnnotations.initMocks(this);

        initPlanData();
    }

    private void initPlanData() {
        PlanVM planVM = new PlanVM();
        planVM.setName("fakePlanName");
        planVM.setPrice(25L);
        planVM.setSpeedLimit("100M");
        planVM.setTrafficLimit("50G");
        planVM.setIpLimit(2);
        planVM.setValidityPeriod(150);
        planService.addPlan(planVM);
    }

    @假如("^用户登陆了系统$")
    public void 用户登陆了系统() throws Throwable {
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");
        actions = this.mockMvc
            .perform(post("/api/authenticate")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM)))
            .andDo(print())
            .andExpect(status().isOk());
        token = actions.andReturn().getResponse().getHeader("Authorization");
    }

    @当("^用户查看套餐列表$")
    public void 用户查看套餐列表() throws Throwable {
        actions = this.mockMvc.perform(get("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andDo(print()).andExpect(jsonPath("$").isArray());
    }

    @并且("^用户选择了一个套餐$")
    public void 用户选择了一个套餐() throws Throwable {
        actions =  this.mockMvc.perform(get("/api/plans/1/buy").contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @那么("^会展示给用户当前套餐的付款码$")
    public void 会展示给用户当前套餐的付款码() throws Throwable {
        actions.andExpect(jsonPath("$.data").value(applicationProperties.getPayment().getCodePath() + "25.png"));
    }

    @当("^管理员确认用户付款后$")
    public void 管理员确认用户付款后() throws Throwable {
        Map<String, String> params = Maps.newHashMap();
        params.put("login", "admin");
        this.mockMvc.perform(put("/api/orders/confirm")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(params)))
            .andExpect(status().isOk());
    }

    @那么("^用户可以查看套餐下任一主机的端口号和密码$")
    public void 用户可以查看套餐下任一主机的端口号和密码() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
