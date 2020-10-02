package com.kuaishou.ljd.dao;

import com.kuaishou.ljd.model.Department;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by author on 2020/9/25 0025.
 */
@Repository
public class DepartmentDAO {

    private static Map<Integer, Department> initMap;

    static {
        initMap = new HashMap<>();
        initMap.put(101, new Department(101, "教学部"));
        initMap.put(102, new Department(102, "市场部"));
        initMap.put(103, new Department(103, "后勤部"));
        initMap.put(104, new Department(104, "教研部"));
    }

    public static Department getByMap(int id) {
        return initMap.get(id);
    }

    public Department get(int id) {
        return initMap.get(id);
    }

    public List<Department> getAll() {
        return new ArrayList<>(initMap.values());
    }
}
