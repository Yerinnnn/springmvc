package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
    // @Slf4j 어노테이션을 사용하는 것과 같은 효과
//    private final Logger log = LoggerFactory.getLogger(getClass());  // getClass() : 내 클래스를 지정해줌 LogTestController.class

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // 심각도 오름차순
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        // @Controller는 view 이름을 반환 (viewResolver를 찾는 단계 실행)
        // 그런데 @RestController라고 하면, 문자열을 반환하면 문자열이 그대로 반환됨
        // 간단한 테스트 할 때 사용하기 좋음
        return "ok";
    }
}
