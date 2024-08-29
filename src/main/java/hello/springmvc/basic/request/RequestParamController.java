package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력 */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능 */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int, Integer 등의 단순 타입이면 @RequestParam도 생략 가능
     * 다시 말하면, 애노테이션을 생략했는데 단순 타입이면 RequestParam이 자동 적용됨
     * 이렇게까지 생략하는 건 조금 과하다고 생각함 (팀내에서 상의 후 결정) */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required : 파라미터 필수 여부
     * 기본값이 파라미터 필수 (true)
     * /request-param-required -> username이 없으므로 예외 (400 Bad Request)

     * 주의!
     * /request-param-required?username= -> 200 OK
     * 파라미터 이름만 있고 값이 없는 경우 : null과 "" 은 다르기 때문에, OK됨

     * 주의!
     * /request-param-required?username=hello
     * 스펙대로 전송한 건데도, int age로 하면 500에러가 발생함
     * /request-param-required?username=hello 는, username=hello, age=null 이라는 건데,
     * JAVA에서 int에 null을 입력하는 것은 불가능하기 때문
     * 따라서 Integer 변경해야 함 (또는 다음에 나오는 defaultValue 사용) */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용 *
     * 참고: defaultValue는 빈 문자 ("") 의 경우에도 적용
     * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * 파라미터를 Map, MultiValueMap으로 조회할 수 있음
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...])
     * 예시) /?userIds=id1&userIds=id2 -> key=userIds, value=[id1, id2]
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨,
     * 뒤에 model을 설명할 때 자세히 설명
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        // public String modelAttributeV1(@RequestParam String username, @RequestParam int age)
//        HelloData helloData = new HelloData();
        // 객체에 getUsername(), setUsername() 메서드가 있으면, 이 객체는 username 이라는 프로퍼티를 가지고 있다고 함
//        helloData.setUsername(username);
//        helloData.setAge(age);

        // age=abc 처럼 숫자가 들어가야 할 곳에 문자를 넣으면 BindException 이 발생
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능
     * @ModelAttribute 는 생략할 수 있음..
     * 그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있음!
     * String, int, Integer 같은 단순 타입 = @RequestParam
     * 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외) */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }
}
