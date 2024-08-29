package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/headers")
    /**
     * HttpMethod : HTTP 메서드를 조회 (org.springframework.http.HttpMethod)

     * Locale : Locale 정보를 조회

     * @RequestHeader MultiValueMap<String, String> headerMap :
     * 모든 HTTP 헤더를 MultiValueMap 형식으로 조회

     * @RequestHeader("host") String host :
     * 특정 HTTP 헤더를 조회
     * 속성 :: required : 필수 값 여부, defaultValue : 기본 값 속성

     * @CookieValue(value = "myCookie", required = false) String cookie :
     * 특정 쿠키를 조회
     * 속성 :: required : 필수 값 여부, defaultValue : 기본 값

     * MultiValueMap :
     * MAP과 유사, 하나의 키에 여러 값을 받을 수 있음 (같은 헤더에 여러 개의 값이 있을 때 사용)
     * HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용 (keyA=value1&keyA=value2)
     */
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}
