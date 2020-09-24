압정 백엔드 저장소
===
### 개요
서비스 URL은 다음과 같습니다. Credentails 정보는 팀 게시판을 확인해주세요.
<table>
<tr>
    <th>분류</th>
    <th>URL</th>
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
    <td></td>
</tr>
<tr>
    <td>DB 스키마</td>
    <td></td>
</tr>
</table>

### 주요 기술
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

## 개발 프로세스
![](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cc74f1d6-1ccc-4b3a-b030-b5fff0073f42/cicd_%281%29.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20200924%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20200924T074421Z&X-Amz-Expires=86400&X-Amz-Signature=fc28fb3e6871b4912eaa72b9eafb7be7d33ea0a30de247d82959d7c068f920e7&X-Amz-SignedHeaders=host&response-content-disposition=attachment%3B%20filename%20%3D%22cicd_%281%29.png%22)

개발서버와 프로덕션서버는 자동으로 소스와 동기화 됩니다.
<table>
<tr>
    <th>서버</th>
    <th>브랜치</th>
</tr>
<tr>
    <td>Dev 서버</td>
    <td>develop</td>
</tr>
<tr>
    <td>Prod 서버</td>
    <td>master</td>
</tr>
</table>

다음 순서에 따라 develop 브랜치와 master 브랜치에 변경을 반영해주실 수 있습니다.
1. feature/{branchename} 브랜치를 생성합니다.
2. 위 브랜치에서 개발 후 push합니다.
3. develop 브랜치로 pull request를 생성합니다.
4. 모든 테스트가 성공하면 merge request합니다.
5. slack 채널의 apjung-log 채널에서 성공적으로 배포되었는지 확인합니다.
