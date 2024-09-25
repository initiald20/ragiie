package com.ding.raggiestart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ding.raggiestart.entity.Employee;
import com.ding.raggiestart.mapper.EmployeeMapper;
import com.ding.raggiestart.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
