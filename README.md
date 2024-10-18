# SpringBoot_JS02
MemberBoard
<수정예정사항>
1. 섬네일 미리보기 :  하나의 이미지에 대해 두 개씩 동일 섬네일 미리보기 생성(배열2개)
![image](https://github.com/user-attachments/assets/e3ba2173-a6d7-4b74-810c-f1781bfa93ab)
![image](https://github.com/user-attachments/assets/497d47b4-84d8-4f9d-8577-11463e43ab39)

*해결 : Controller
list.add 중복 - 프론트로 보내는 List 데이터에 섬네일만 넣어야 하는데 원본파일도 넣는 과정이 중복됨

2. 섬네일 삭제 오류 : 섬네일 삭제 시 false ->  저장 데이터 삭제 안됨
