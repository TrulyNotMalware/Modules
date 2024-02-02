# Common Module
The 'Common' module provides a basic utility for jwt (Json Web Token) auth.
Extract the RSA key from the jks (Java Key Store) and provide to java bean.
JwtTokenProvider helps to easily create and manage your own RefreshToken and AccessToken.

## Included
- JWT Modules with RSA

## Getting Started
### Required Dependencies
```yaml
ext{
    jjwtVersion = "0.11.5"
}
dependencies {
    // CookieProvider dependency
    implementation 'org.springframework:spring-web'

    // jjwt module dependency
    api "io.jsonwebtoken:jjwt-api:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-impl:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-jackson:${jjwtVersion}"
}
```
Use [jjwt](https://github.com/jwtk/jjwt) module to create Jwt token value.
## Configurations
This module uses RSA authentication, so you have to generate your own key. 
If you don't have any key, check [generateToken.sh](https://github.com/TrulyNotMalware/Spring-Stack/blob/main/security/src/main/resources/generateToken.sh) for token generation.
```yaml
spring:
  config:
    activate:
      on-profile: jwt-configuration

jwt:
  token:
    accessTokenExpiredTime: ${ACCESS_EXPIRED_TIME}
    refreshTokenExpiredTime: ${REFRESH_EXPIRED_TIME}
    keystore:
      classpath: ${KEY_NAME}
      password: ${KEYSTORE_PASSWORD}
    key:
      alias: ${KEY_ALIAS}
      privateKeyPassPhrase: ${PRIVATE_KEY_PASS_PHRASE}
```
The accessTokenExpiredTime and refreshTokenExpiredTime determine the validity time (ms) of each token.  
Enter the key information that you used to create the JKS. (Check the example script [generateToken.sh ] (https://github.com/TrulyNotMalware/Spring-Stack/blob/main/security/src/main/resources/generateToken.sh) )


---
# Common Module
'Common' 모듈은 jwt(Json Web Token) 를 사용할 수 있는 기본 유틸리티를 제공합니다. 
jks(Java Key Store)로부터 RSA 방식의 Key 를 추출하여 Bean 으로 제공합니다.
사용자는 JwtTokenProvider Bean 을 이용하면 손쉽게 RefreshToken 과 AccessToken 을 발급받고 토큰을 관리할 수 있습니다.

## Included
- RSA 인증 Jwt 모듈

## Getting Started
### 의존성
```groovy
ext{
    jjwtVersion = "0.11.5"
}
dependencies {
    // CookieProvider dependency
    implementation 'org.springframework:spring-web'

    // jjwt module dependency
    api "io.jsonwebtoken:jjwt-api:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-impl:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-jackson:${jjwtVersion}"
}
```
Jwt 토큰을 생성하기 위해 [jjwt](https://github.com/jwtk/jjwt) 모듈을 사용합니다.

### 모듈 설정
이 모듈은 RSA 인증을 사용하므로 비대칭 키를 생성해야 합니다. 키가 없는 경우에는 [generateToken.sh ](https://github.com/TrulyNotMalware/Spring-Stack/blob/main/security/src/main/resources/generateToken.sh)스크립트에서 토큰을 생성하는 방법을 확인하세요.
```yaml
spring:
  config:
    activate:
      on-profile: jwt-configuration

jwt:
  token:
    accessTokenExpiredTime: ${ACCESS_EXPIRED_TIME}
    refreshTokenExpiredTime: ${REFRESH_EXPIRED_TIME}
    keystore:
      classpath: ${KEY_NAME}
      password: ${KEYSTORE_PASSWORD}
    key:
      alias: ${KEY_ALIAS}
      privateKeyPassPhrase: ${PRIVATE_KEY_PASS_PHRASE}
```
accessTokenExpiredTime 과 refreshTokenExpiredTime 은 각 토큰의 유효시간(ms)을 결정합니다.  
key 정보는 JKS 를 생성할 때 사용헀던 key 정보를 입력합니다. (예제 스크립트인 [generateToken.sh ](https://github.com/TrulyNotMalware/Spring-Stack/blob/main/security/src/main/resources/generateToken.sh) 을 확인하세요.)

### 사용법
유효한 JKS 소스와 올바른 설정값을 기입했다면 "jwt" 프로파일을 활성화 하는것으로 쉽게 jwt 토큰을 생성할 수 있습니다.  
```yaml
spring:
  profiles:
    group:
      "example": "jwt"
    active: "example"
...
```
```java
@Component
@RequiredArgsConstructor
public class MyJwtService{
    private final JwtTokenProvider jwtProvider;
    
    // example create token
    public void createJwtToken(Long id, List<String> roles){
        ...
        String accessToken = this.jwtProvider.createJwtAccessToken(id, roles);
        String refreshToken = this.jwtProvider.createJwtRefreshToken();
        ...
        // Do Something
    }
    // Simple validation.
    public void validateToken(String token){
        return this.jwtProvider.validateJwtToken(token);
    }
}
```
Bean 으로 등록된 JwtProvider 를 주입받아 손쉽게 토큰을 조작합니다. refreshToken 을 Cookie 에 제공하려는 경우에는 CookieProvider 를 이용합니다.  
아래는 CookieProvider 를 이용해 생성한 refreshToken 을 쿠키에 제공하는 예시입니다.
```java
@RestController
@RequiredArgsConstructor
public class MyJwtController{
    private final CookieProvider cookieProvider;
    private final JwtTokenProvider jwtProvider;
    
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reissueAccessToken(){
        
        // create token
        String accessToken = this.jwtProvider.createJwtAccessToken(id, roles);
        String refreshToken = this.jwtProvider.createJwtRefreshToken();
        // httpOnly & secure
        ResponseCookie responseCookie = this.cookieProvider.createRefreshTokenCookie(refreshToken);
        
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(accessToken);
    }
}
```