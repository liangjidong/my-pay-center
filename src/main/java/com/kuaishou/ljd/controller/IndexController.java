package com.kuaishou.ljd.controller;

import com.kuaishou.ljd.mapper.StudentMapper;
import com.kuaishou.ljd.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by author on 2020/9/25 0025.
 */
@Controller
public class IndexController {

    @Autowired
    StudentMapper studentMapper;

    @RequestMapping({"/", "/index.html"})
    public String index() {
        System.out.println("test");
        return "index";
    }

    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("login")
    public String login(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "password", required = false) String password, Model model) {
        System.out.println(username + "-" + password);

        return "redirect:/index.html";
    }

    @GetMapping("/stu")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentMapper.listAll();
    }

    @RequestMapping("/test/testIndex.html")
    public String toTestIndex() {
        return "test/testIndex";
    }

    @RequestMapping("/test/add")
    public String testAdd() {
        return "test/testAdd";
    }
    @RequestMapping("/test/update")
    public String testUpdate() {
        return "test/testUpdate";
    }
}
