package com.yt.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author Lenovo
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-01-22 13:22:06
*/
public interface EmployeeService extends IService<Employee> {

    /**
     * 登录功能
     * @param employee
     * @param request
     * @return
     */
    R<Employee> login(Employee employee, HttpServletRequest request);


    /**
     * 用户退出功能
     * @param request
     * @return
     */
    R<String> logout(HttpServletRequest request);

    /**
     * 新增员工
     * @param employee
     * @return
     */
    R<String> Save(HttpServletRequest request, Employee employee);


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    public R<Page> page(int page, int pageSize, String name);

    public R<String> updateEmployee(HttpServletRequest request, Employee employee);

    public R<Employee> getById1(Long id);
}
