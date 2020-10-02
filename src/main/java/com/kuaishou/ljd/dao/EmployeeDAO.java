package com.kuaishou.ljd.dao;

import com.kuaishou.ljd.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by author on 2020/9/25 0025.
 */
@Repository
public class EmployeeDAO {

    private static Map<Integer, Employee> initMap;
    private static int initId = 1004;

    static {
        initMap = new HashMap<>();
        initMap.put(1001, new Employee(1001, "张三", "51111111@qq.com", 1, DepartmentDAO.getByMap(101), new Date()));
        initMap.put(1001, new Employee(1002, "李四", "51112221@qq.com", 1, DepartmentDAO.getByMap(102), new Date()));
        initMap.put(1001, new Employee(1003, "王五", "51112222@qq.com", 1, DepartmentDAO.getByMap(104), new Date()));
    }

    @Autowired
    private DepartmentDAO departmentDAO;

    public boolean add(Employee employee) {
        if (employee.getId() <= 0) {
            employee.setId(initId++);
        }
        employee.setDepartment(departmentDAO.get(employee.getDepartment().getId()));
        initMap.put(employee.getId(), employee);
        return true;
    }

    public Employee get(int id) {
        return initMap.get(id);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(initMap.values());
    }
}
