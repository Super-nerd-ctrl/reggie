package com.yt.reggie.mapper;

import com.yt.reggie.domain.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-01-22 13:22:06
* @Entity com.yt.reggie.domain.Employee
*/
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}




