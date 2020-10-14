압정 백엔드 저장소
===
- [개요](#introduction)
- [개발에 참여하기](#dev)
  - [주요 기술](#dev_skill)
  - [개발 프로세스](#dev_proccess)
  - [개발 주의사항](#dev_style)
  - [API 문서 만들기](#api_doc)
  - [MesseageSource 사용](#message_source)
### 개요 <a id="introduction"></a>
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

# 개발에 참여하기 <a id="dev"></a>
### 주요 기술 <a id="dev_skill"></a>
개발에 참여하시기 위해서는 다음 기술들에 대한 이해가 필요합니다
<table>
<tr>
    <th>이름</th>
    <th></th>
</tr>
<tr>
    <td>Spring Boot</td>
    <td>웹 프레임워크</td>    
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


### 개발 프로세스 <a id="dev_proccess"></a>
![](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cc74f1d6-1ccc-4b3a-b030-b5fff0073f42/cicd_%281%29.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20200924%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20200924T074421Z&X-Amz-Expires=86400&X-Amz-Signature=fc28fb3e6871b4912eaa72b9eafb7be7d33ea0a30de247d82959d7c068f920e7&X-Amz-SignedHeaders=host&response-content-disposition=attachment%3B%20filename%20%3D%22cicd_%281%29.png%22)

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

### 개발 주의사항 <a id="dev_style"></a>
- 이슈를 기반으로 Pull Request를 만들어주세요 (없다면 이슈를 만들어 주세요)
- SOLID를 생각하며 개발해주세요 (KISS, DRY, YANGI도 함께 해주시면 더욱 좋습니다)
- 코드 정렬은 IntelliJ의 Cmd + L (윈도의 경우 Ctrl + L)을 활용해주세요
- 하드코딩은 제거해주세요. Message Source를 이용해주세요.
- API가 추가되었다면 RestDocs로 문서를 만들어주세요
- SonarQube를 확인해주세요
  - 코드 커버리지는 70%를 목표로 합니다
  - 코드 냄새는 가능한 제거해주세요
  
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
3. `gradlew test`를 통해 snippets파일들을 생성합니다
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

