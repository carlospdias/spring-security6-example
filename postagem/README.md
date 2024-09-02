- https://access.redhat.com/solutions/21906
- https://www.favicon-generator.org/
- https://www.codejava.net/java-ee/jstl/jakarta-taglib-uris
- https://www.javaguides.net/2019/12/spring-mvc-crud-example-with-hibernate-jsp-mysql-maven-eclipse.html
- https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
- https://mkyong.com/jsf2/jsf-2-0-spring-hibernate-integration-example/

----------------

```sh
$ npx tailwindcss init
$ npm install flowbite
$ npx tailwindcss -i ./src/input.css -o ./postagem-main.css --watch
```


## Lista de acessos
 - [maven-plugin](https://stackoverflow.com/questions/37047603/generate-jasper-with-maven-plugin)
 - [redirect-uri](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html#oauth2login-sample-redirect-uri)
 - [oauth2loginauthenticat](https://stackoverflow.com/questions/49718913/enable-oauth2basicpassword-granttype-in-springboot-2-0-oauth2loginauthenticat)
 - [spring-security](https://docs.spring.io/spring-security/reference/getting-spring-security.html)
 - [oauth2](https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html)
 - [create_the_java_files](https://developers.redhat.com/articles/2023/07/24/how-integrate-spring-boot-3-spring-security-and-keycloak#create_the_java_files)
 - [core](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html)
 - [Aula 3 Keycloak - Client Scopes e Atributos](https://www.youtube.com/watch?v=BNwaPazP-rY)


## Lista Final
/*@Bean
public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
KeyPairGenerator keyPairGenerator =  KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey   = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }*/]


 - https://medium.com/@wahyubagus1910/securing-spring-boot-with-keycloak-b352f05575f2
 - https://github.com/wahyubagus-ars/spring-oauth-keycloak/blob/master/src/main/java/com/ars/authservice/domain/dto/LoginRequestDto.java
 - https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-providermanager
 - https://stackoverflow.com/questions/76823906/http-exceptionhandler-deprecated-and-removed-in-spring-6-2
 - https://docs.spring.io/spring-security/reference/servlet/getting-started.html
 - https://www.baeldung.com/spring-data-redis-tutorial
 - https://docs.spring.io/spring-security/reference/modules.html
 - https://developers.redhat.com/articles/2023/07/24/how-integrate-spring-boot-3-spring-security-and-keycloak#test_the_application
 - https://docs.spring.io/spring-security/reference/reactive/oauth2/login/core.html
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/client/authorization-grants.html#_storing_the_authorization_request
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/client/authorization-grants.html
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/client/authorization-grants.html
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html#oauth2login-advanced-redirection-endpoint
 - 
 - https://medium.com/@mayankit3/migrating-from-spring-security-5-6-5-to-newer-version-70c396f7b506
 - https://medium.com/@wahyubagus1910/securing-spring-boot-with-keycloak-b352f05575f2
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html
 - https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client-enable-extension-grant-type
 - 