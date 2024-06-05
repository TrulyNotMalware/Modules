# Spring modules
    Preconfigured modules and services for Java web applications based on Spring boot 3

The primary objective of this project is to provide a simplified approach for creating Java web backend applications using the Spring framework.  
To achieve this, it plans to include essential and well-known features like user authentication (Login) and messaging solutions, which are frequently required in web application development.

## Test environments
It's just a test environment, so you don't have to install all of these services. There is no need to use Kubernetes. You can only use the module.

### Infra & basic services
|Services|version|installation|explain|last modified|
|:---|:---:|:---:|:---:|:---:|
|[Kubernetes](https://kubernetes.io/docs/home/)|1.26.2|Managed Kubernetes|[Oracle Cloud kubernetes](https://www.oracle.com/cloud/free)|2023.09.07|
|[Harbor](https://harbor.registry.notypie.dev/)|2.8.0|Docker-compose|Docker image repository|2023.09.07|
|[Zipkin](https://github.com/openzipkin/zipkin)|2.24.1|[Helm chart](https://github.com/openzipkin/zipkin/tree/master/charts/zipkin)|Metric tracing|2023.09.07|

### Databases
|Services|version|installation|explain|last modified|
|:---|:---:|:---:|:---:|:---:|
|[Oracle](https://www.oracle.com/autonomous-database/)|19c|Oracle cloud|SQL Database|2023.09.07|
|[ElasticSearch](https://github.com/elastic/elasticsearch)|7.17.3|[Helm chart](https://github.com/elastic/helm-charts)|Zipkin storage|2023.09.07|
|[MySQL](https://github.com/bitnami/charts/tree/main/bitnami/mysql)|8.0.33|[Helm chart](https://github.com/bitnami/charts/tree/main/bitnami/mysql)|SQL Database|2023.09.07|
## List
An '**Application**' refers to a combination of modules and examples that are inherently runnable, whereas simply using the term '**Modules**' implies that there is no separate execution context.  
If you want to execute the module, it requires additional configurations to run. The basic use case for each module is documented in the README file located within each module directory.
### Spring MVC
*Basic*
1. [*Common*](https://github.com/TrulyNotMalware/Modules/blob/main/common/README.md) : Mvc & Reactor Common Jwt and Util module.
2. [*Core*](https://github.com/TrulyNotMalware/Modules/blob/main/core/README.md) : Database JPA Configurations & Utils module.
3. [*Kafka*](https://github.com/TrulyNotMalware/Modules/blob/main/kafka/README.md) : Apache Kafka Producer & Consumer example & configuration module.
4. [*Event*](https://github.com/TrulyNotMalware/Modules/blob/main/event/README.md) : [Axon](https://docs.axoniq.io/reference-guide/) framework based event-sourcing participants package.

*Security*
1. [*Security*](https://github.com/TrulyNotMalware/Modules/blob/main/security/README.md) : Jwt authentication configuration & implements module.
2. [*Auth*](https://github.com/TrulyNotMalware/Modules/blob/main/auth/README.md) : OAuth Client & Authentication application.
3. [*OAuth*](https://github.com/TrulyNotMalware/Modules/blob/main/oauthServer/README.md) : OAuth [authorization server](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/index.html) application with JPA
4. [*Resource Server*](https://github.com/TrulyNotMalware/Modules/blob/main/resourceServer/README.md) : OAuth Resource Server application.


*EDA & EventSourcing*
1. [*Assistant*](https://github.com/TrulyNotMalware/Modules/blob/main/assistant/README.md) : Implements event sourcing & useful eda components.

### Spring Webflux
*Basic*
1. [*Reactor Core*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorCore/README.md) : Tracing(Zipkin & Micrometer) context configuration & Utils module
2. [*Reactor kafka*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorKafka/README.md) : Reactive Kafka Consumer & Producer configuration module

*Security*
1. [*Reactor Security*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorSecurity/README.md) : Reactor based Jwt authentication configuration & implements module.

*Gateway*
1. [*Gateway*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorSecurity/README.md) : Spring cloud gateway application include jwt validation

### For More Information
For more information, see README for each module.

---
# Spring modules
    Java - Spring boot 3 웹 백엔드 어플리케이션을 위한 모듈 및 서비스 구현

이 프로젝트의 주요 목적은 Spring 프레임워크를 사용하여 Java 웹 백엔드 애플리케이션을 생성하는 쉬운 방식을 제공하는 것입니다.  
이를 위해 웹 애플리케이션 개발에서 자주 요구되는 사용자 인증(Login), 메시징 솔루션 등 잘 알려진 기능을 포함할 계획입니다.

## 테스트 환경
이 환경은 단지 테스트 환경이기 때문에, 동일하게 구성하지 않아도 됩니다. Kubernetes 를 사용할 필요도 없으며, 각 서비스만 독립적으로 사용할 수 있습니다.

### 인프라 & 서비스
|Services|version|installation|explain|last modified|
|:---|:---:|:---:|:---:|:---:|
|[Kubernetes](https://kubernetes.io/docs/home/)|1.26.2|Managed Kubernetes|[Oracle Cloud kubernetes](https://www.oracle.com/cloud/free)|2023.09.07|
|[Harbor](https://harbor.registry.notypie.dev/)|2.8.0|Docker-compose|Docker image repository|2023.09.07|
|[Zipkin](https://github.com/openzipkin/zipkin)|2.24.1|[Helm chart](https://github.com/openzipkin/zipkin/tree/master/charts/zipkin)|Metric tracing|2023.09.07|

### 데이터베이스
|Services|version|installation|explain|last modified|
|:---|:---:|:---:|:---:|:---:|
|[Oracle](https://www.oracle.com/autonomous-database/)|19c|Oracle cloud|SQL Database|2023.09.07|
|[ElasticSearch](https://github.com/elastic/elasticsearch)|7.17.3|[Helm chart](https://github.com/elastic/helm-charts)|Zipkin storage|2023.09.07|
|[MySQL](https://github.com/bitnami/charts/tree/main/bitnami/mysql)|8.0.33|[Helm chart](https://github.com/bitnami/charts/tree/main/bitnami/mysql)|SQL Database|2023.09.07|
## 목록
'**어플리케이션**'은 실행 가능한 예제와 모듈을 모두 포함하고 있지만, 단순히 '**모듈**'이라는 용어를 사용하는 것은 별도의 실행 컨텍스트가 없다는 것을 의미합니다.  
따라서 모듈을 실행시키고 싶다면 추가적인 구성이 필요합니다. 각 모듈의 기본 사용 사례는 각 모듈 디렉토리 내에 있는 README 파일에 기록되어 있습니다.  
### Spring MVC
*Basic*
1. [*Common*](https://github.com/TrulyNotMalware/Modules/blob/main/common/README.md) : Mvc & Reactor 공통 Jwt와 Util 모듈.
2. [*Core*](https://github.com/TrulyNotMalware/Modules/blob/main/core/README.md) : JPA 설정 & 유틸리티 모듈.
3. [*Kafka*](https://github.com/TrulyNotMalware/Modules/blob/main/kafka/README.md) : 아파치 카프카 Producer & Consumer example 와 설정 모듈.
4. [*Event*](https://github.com/TrulyNotMalware/Modules/blob/main/event/README.md) : [Axon](https://docs.axoniq.io/reference-guide/) 프레임워크 기반 이벤트 소싱 참여자 설정 모듈.

*Security*
1. [*Security*](https://github.com/TrulyNotMalware/Modules/blob/main/security/README.md) : Jwt 인증을 위한 설정과 그 구현체를 만든 모듈
2. [*Auth*](https://github.com/TrulyNotMalware/Modules/blob/main/auth/README.md) : OAuth 클라이언트와 인증서비스를 포함하는 어플리케이션.
3. [*OAuth*](https://github.com/TrulyNotMalware/Modules/blob/main/oauthServer/README.md) : JPA를 사용한 OAuth [인가 서버](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/index.html) 어플리케이션
4. [*Resource Server*](https://github.com/TrulyNotMalware/Modules/blob/main/resourceServer/README.md) : OAuth 리소스 서버 어플리케이션.

*EDA & EventSourcing*
1. [*Assistant*](https://github.com/TrulyNotMalware/Modules/blob/main/assistant/README.md) : 이벤트 소싱과 EDA 를 위한 모듈.

### Spring Webflux
*Basic*
1. [*Reactor Core*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorCore/README.md) : 트레이싱(Zipkin & Micrometer) 설정과 유틸리티 모듈.
2. [*Reactor kafka*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorKafka/README.md) : 아파치 카프카 Consumer & Producer 설정 모듈.

*Security*
1. [*Reactor Security*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorSecurity/README.md) : 리액터 기반의 Jwt 인증 & 설정 모듈.

*Gateway*
1. [*Gateway*](https://github.com/TrulyNotMalware/Modules/blob/main/reactorSecurity/README.md) : Jwt 검증 로직을 포함한 Spring cloud gateway 어플리케이션.

### For More Information
더 자세한 정보는 각 모듈 디렉터리의 README 를 참고해주세요.
