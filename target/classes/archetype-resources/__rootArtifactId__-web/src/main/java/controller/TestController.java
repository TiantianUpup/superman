package ${package}.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类，用于测试项目是否正常构建
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public Object hello() {
        return "hello world";
    }
}
