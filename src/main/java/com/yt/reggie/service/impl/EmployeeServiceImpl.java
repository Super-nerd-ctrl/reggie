package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Employee;
import com.yt.reggie.service.EmployeeService;
import com.yt.reggie.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
* @author Lenovo
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-01-22 13:22:06
*/
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    

    @Override
    public R<Employee> login(Employee employee, HttpServletRequest request) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = getOne(queryWrapper);

        if (emp == null) {
            return R.error("登录失败");
        }

        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        //清理session中保存当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @Override
    public R<String> Save(HttpServletRequest request, Employee employee) {
        //设置初始密码123456，需要进行MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empId = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        this.save(employee);

        return R.success("新增员工成功");
    }

    @Override
    public R<Page> page(int page, int pageSize, String name) {

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @Override
    public R<String> updateEmployee(HttpServletRequest request, Employee employee) {

        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);
        updateById(employee);
        return R.success("员工信息修改成功");
    }

    @Override
    public R<Employee> getById1(Long id) {

        return R.success(getById(id));
    }


}




