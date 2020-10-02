package com.kuaishou.ljd.mapper;

import com.kuaishou.ljd.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by author on 2020/9/26 0026.
 */
@Mapper
@Repository
public interface StudentMapper {
    List<Student> listAll();

    Student getById(int id);

    int add(Student student);

    int update(Student student);

    int delete(int id);
}
