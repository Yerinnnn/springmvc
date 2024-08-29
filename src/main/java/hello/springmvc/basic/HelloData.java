package hello.springmvc.basic;

import lombok.Data;

/**
 * 롬복 @Data
 * @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
 * 모두 자동으로 적용해줌
 */
@Data
public class HelloData {
    private String username;
    private int age;
}
