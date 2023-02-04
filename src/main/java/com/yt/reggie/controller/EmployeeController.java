package com.yt.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Employee;
import com.yt.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        return employeeService.login(employee, request);
    }


    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.Save(request, employee);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

         return employeeService.page(page, pageSize, name);
    }

    @PutMapping
    public R<String> updateEmployee(HttpServletRequest request,@RequestBody Employee employee) {

        return employeeService.updateEmployee(request, employee);
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        R<Employee> employee = employeeService.getById1(id);
        if (employee != null) {
            return employee;
        }
        return R.error("没有查询到员工信息");
    }
}
