압정 백엔드 저장소
===
- [서비스 개요](#introduction)
- [프로젝트 개발하기](#dev)
  - [주요 기술](#dev_skill)
  - [로컬 개발 환경 구축](#local_dev)
  - [개발 스타일](#dev_style)
- [프로젝트 기술 개요](#tech)
  - [개발 파이프라인](#dev_pipeline)
  - [API 문서 만들기](#api_doc)
  - [MesseageSource 사용](#message_source)
  - [코드 정적분석 데이터 활용](#sonarqube)
  - [데이터베이스 마이그레이션과 히스토리 관리](#flyway)
### 서비스 개요 <a id="introduction"></a>
서비스 URL은 다음과 같습니다. Credentails 정보는 팀 게시판을 확인해주세요.
<table>
<tr>
    <th>분류</th>
    <th>URL</th>
</tr>
<tr>
    <td colspan="2">API 서버</td>
</tr>
<tr>
    <td>Prod Server</td>
    <td>https://api.apjung.me</td>
</tr>
<tr>
    <td>Dev Server</td>
    <td>https://api.apjung.xyz</td>
</tr> 
<tr>
    <td colspan="2">유틸 서버</td>
</tr>
<tr>
    <td>Jenkins</td>
    <td>https://jenkins.apjung.me</td>
</tr>
<tr>
    <td>ArgoCD</td>
    <td>https://argocd.apjung.me</td>
</tr>
<tr>
    <td>SonarQube</td>
    <td>http://sonarqube.apjung.me</td>
</tr>
<tr>
    <td>Prometheus</td>
    <td>https://prometheus.apjung.me</td>
</tr>
<tr>
    <td>Grafana</td>
    <td>https://grafana.apjung.me</td>
</tr>
</table>

서비스 소개는 다음을 참고해주세요
<table>
<tr>
    <th>분류</th>
    <th></th>
</tr>
<tr>
    <td>기획서</td>
    <td></td>
</tr>
<tr>
    <td>프로토타입</td>
    <td><a href="https://ovenapp.io/view/pv3QDRVUGALt5z47LEKvy53AiEXKxoAr/">링크</a> (비밀번호 : cocoding)</td>
</tr>
<tr>
    <td>DB 스키마</td>
    <td><a href="https://aquerytool.com:443/aquerymain/index/?rurl=5bc87c1d-60a6-4c49-8617-1c35033cc4d0">링크</a> (비밀번호 : if680q)</td>
</tr>
</table>

# 프로젝트 개발하기 <a id="dev"></a>
### 선행 학습 <a id="dev_skill"></a>
개발에 참여하시기 위해서는 다음 기술들에 대한 이해가 필요합니다
<table>
<tr>
    <th>이름</th>
    <th></th>
</tr>
<tr>
    <td>Spring Boot</td>
    <td>프레임워크</td>    
</tr>

<tr>
    <td>Spring Boot JPA</td>
    <td>ORM</td>
</tr>
<tr>
    <td>QueryDSL</td>
    <td>쿼리</td>
</tr>
<tr>
    <td>RestDocs</td>
    <td>문서 자동화</td>
</tr>
</table>

### 로컬 개발환경 구축 <a id="local_dev"></a>
도커 컴포즈를 이용해 서버를 구축합니다. 프로젝트 루트 폴더에서 아래 명령어를 실행해주세요.
```bash
docker-compose up -d
```
그리고 인텔리제이나 이클립스를 통해 스프링부트 애플리케이션을 실행해주세요.


로컬에서 사용하는 포트는 다음과 같습니다.
<table>
<tr>
    <th>이름</th>
    <th>설명</th>
    <th>포트</th>
</tr>
<tr>
    <td>Application</td>
    <td>스프링부트 애플리케이션 포트</td>
    <td>8080</td>
</tr>
<tr>
    <td>MySQL</td>
    <td>데이터베이스</td>
    <td>3306</td>
</tr>
</table>

### 개발하고 서버에 반영하기
1. 이슈만들기 & 브랜치 만들기
  - 이슈를 생성실 때 우측의 Projects(apjung)을 꼭 설정해주세요
  - 브랜치 이름은 이슈의 번호로 작성해주세요 `issue#{이슈번호}`
2. Pull Request 만들기
  - PR은 develop 브랜치로만 만들어주세요
  - PR을 생설하실 때 우측의 Linked Issue와 Request Reviewer를 꼭 설정해주세요
  - PR을 생성하시면 자동으로 브랜치 테스트가 동작합니다
  - 브랜치 테스트가 성공하면 Merge버튼이 활성화됩니다
3. Merge하고 Slack에서 Notification 보기
  - 머지한 후 몇분 기다리면 빌드 성공여부와 서버 배포여부 알림을 슬랙에서 볼 수 있습니다
  - 보통 2~3분정도 걸립니다

### 개발 스타일 <a id="dev_style"></a>
기본적인 개발 스타일입니다. 자세한 것은 아래를 참고해주세요.
- 코드 정렬은 IntelliJ의 정렬 기능을 활용해주세요
- 하드코딩은 제거해주세요. Message Source를 이용해주세요
- API가 추가되었다면 RestDocs로 문서를 만들어주세요
- SonarQube를 확인해주세요
  - 코드 커버리지는 70%를 목표로 합니다
  - 코드 냄새는 가능한 제거해주세요

# 프로젝트 기술 개요 <a id="dev"></a>
### 개발 파이프라인 <a id="dev_pipeline"></a>
![cicd_(1)](https://user-images.githubusercontent.com/35277854/96132104-ee8f0b00-0f34-11eb-8dd4-08f14b3f4aec.png)

개발서버와 프로덕션서버는 자동으로 소스와 동기화 됩니다.
<table>
<tr>
    <th>서버</th>
    <th>브랜치</th>
    <th>URL</th>
</tr>
<tr>
    <td>Dev 서버</td>
    <td>develop</td>
    <td>https://api.onoffmix.xyz</td>
</tr>
<tr>
    <td>Prod 서버</td>
    <td>master</td>
    <td>https://api.onoffmix.me</td>
</tr>
</table>

다음 순서에 따라 develop 브랜치와 master 브랜치에 변경을 반영해주실 수 있습니다.
1. feature/{branchename} 브랜치를 생성합니다.
2. 위 브랜치에서 개발 후 push합니다.
3. develop 브랜치로 pull request를 생성합니다.
4. 모든 테스트가 성공하면 merge request합니다.
5. slack 채널의 apjung-log 채널에서 성공적으로 배포되었는지 확인합니다.
  
### API 문서 만들기 <a id="api_doc"></a>
Spring RestDocs에 의해서 개발되어집니다.

중요 파일 위치는 다음과 같습니다
<table>
<tr>
    <th>경로</th>
    <th>설명</th>
</tr>
<tr>
    <td>/api/src/test/java/me/apjung/backend/api</td>
    <td>컨트롤러에 대한 테스 코드 위치</td>
</tr>
<tr>
    <td>/api/src/docs/asciidoc</td>
    <td>AsciiDoc 문서 위치</td>
</tr>
<tr>
    <td>/api/build/generated-snippets</td>
    <td>RestDocs에 의해 생성된 snippets 파일들 (gradlew test 후)</td>
</tr>
<tr>
    <td>/api/build/asciidoc</td>
    <td>asciidoctor에 의해 생성된 최종 HTML 결과 파일들 (gradlew asciidoctor 후)</td>
</tr>
<tr>
    <td>build된 JAR안의 /static</td>
    <td>위의 최종생성된 HTML파일들이 이 경로로 들어감</td>
</tr>
</table>

실제 결과 확인을 위한 경로는 다음과 같습니다
<table>
<tr>
    <th>경로</th>
    <th>설명</th>
</tr>
<tr>
    <td>https://example/docs/**</td>
    <td>/api/build/asciidoc/auth.html이 생성되어 있다면 localhost/docs/auth에 웹페에지가 생성됨</td>
</tr>
</table>

1. 컨트롤러를 개발합니다 (@RestController)
2. 해당 컨트롤러에 대한 RestDocsMvc 테스트코드를 작성합니다
3. `gradlew test -Pprofile=test`를 통해 snippets파일들을 생성합니다
4. /api/src/docs/asciidoc에 API 문서를 작성합니다 `example.adoc`
5. `gradlew build`를 통해 JAR을 빌드합니다
6. `java -jar api/build/libs/api-1.0.jar`을 통해 빌드된 jar을 실행합니다
7. `/docs/example.html`에서 결과를 확인합니다

### MessageSource 사용 <a id="message_source"></a>
하드코딩을 피하기 위해 Response, Exception 등에 사용되는 모든 상수 문자열은 MessageSource를 통해 관리합니다

MessageSource는 `resoucres/locale` 경로에저장되어있으며 파일 형식은 `yaml`입니다.

컨트롤러와 서비스 등에서는 `CustomMessageSourceResolver`를 통해 가져올 수 있고, 이 객체는 빈으로 등록되어있습니다.

```java
// java

@RestController
public class Test {
    private final CustomMessageSourceResolver customMessageSourceResolver;

    public CustomMessageSourceResolverTest(CustomMessageSourceResolver customMessageSourceResolver) {
        this.customMessageSourceResolver = customMessageSourceResolver;
    }

    public String hello() {
        String code = "test.hello";
        String validationMsg = customMessageSourceResolver.getValidationMessage(code);
        String businessMsg = customMessageSourceResolver.getBusinessMessage(code);
        System.out.println(validationMsg); // output: validation
        System.out.println(businessMsg); // output: business
    }
}
```

유효성 검사 `@Valid`에서는 다음과 같이 사용할 수 있습니다. @Valid 객체에서는 무조건 `locale/validation/message`로 매핑됩니다.
```java
public static class Register {
    @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.email}")
    private String email;

    @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.password}")
    private String password;

    @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.name}")
    private String name;

    @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.mobile}")
    private String mobile;
}
```

thymeleaf 템플릿에서는 다음과 같이 사용할 수 있습니다. 템플릿에서는 무조건 `locale/business/message`로 매핑됩니다
```html
<h1 th:text="#{templates.hello.world}"></h1>
```

### 코드 정적분석 데이터 활용 <a id="sonarqube"></a>
소나큐브에 접속해서 코드커버리지와 코드스멜을 확인해주세요! 코드커버리지가 부족한 클래스가 있다면 테스트코드를 만들어 주시고 코드스멜은 제거해주세요.

![스크린샷 2020-10-17 오전 12 02 54](https://user-images.githubusercontent.com/35277854/96275048-359bff80-100c-11eb-81f3-56a8ad34e036.png)
![스크린샷 2020-10-17 오전 12 03 03](https://user-images.githubusercontent.com/35277854/96275052-37fe5980-100c-11eb-8f09-8df5bba30ef1.png)
![스크린샷 2020-10-17 오전 12 03 12](https://user-images.githubusercontent.com/35277854/96275055-392f8680-100c-11eb-86d2-27c789101051.png)

### 데이터베이스 마이그레이션과 히스토리 관리 <a id="flyway"></a>
현재 데이터베이스는 MySQL을 이용하고 있고, flyway를 통해 변경이력을 관리하고 있습니다. Hibernate를 통해 테이블을 마이그레이션 하는 것이 아닌 flyway로 마이그레이션하고 Hibernate를 통해 validation을 진행하면서 개발해주세요. (개발서버와 프로덕션 서버 빌드과정 중에는 `./gradlew flywayValidation`을 통해 한번더 검사합니다.

새로운 버전을 만드실 때에는 반드시 `V{현재시각}__{설명}.sql` 파일명으로 해주시고 IntelliJ의 flyway Plugin을 사용하시면 쉽게 이러한 파일을 생성하실 수 있습니다.

데이터베이스 마이그레이션 파일 경로
- `api/src/main/resoucres/db/migration`

`spring.jpa.hibernate.ddl-auto : validate`로 유효성 검사를 진행하며 개발합니다. 개발서버와 실서버에서는 `flywayValidate`를 사용합니다.

아래는 flyway 명령어입니다. dev, prod 프로필은 환경변수를 설정해놓으셔야 하고 CI/CD과정중에서 자동으로 진행됩니다.
```bash
./gradlew -Pprofile={profile} flywayClean
./gradlew -Pprofile={profile} flywayValidate
./graldew -Pprofile={profile} flywayMigrate
```
