# client-back
git pull, push 순서!!

0. git status => 본인 수정사항 없다면 git pull만 받아도 무관 

1. git stash

2. git pull origin master

3. git stash pop => conflict 없다면 다음 단계 진행, 있다면 conflict 수정 후 다음 단계 진행

4. git add . (전부 올려도 되는 파일일 경우에만)

5. git commit -m '작업 내용'

6. git push origin master


추후 포크 따서 작업 할 예정

※ 번외 - 소스트리 공부 조금할 수 있으면 소스트리 사용도 추천함.
- 소스트리
- https://ux.stories.pe.kr/181
- https://velog.io/@easyhyun00/Git-%EC%86%8C%EC%8A%A4%ED%8A%B8%EB%A6%ACSourceTree-%EA%B8%B0%EC%B4%88-%EC%82%AC%EC%9A%A9%EB%B2%95
