1. aws.eazysecure-auth.com으로 접속
-> '/'->'/login'-> signup누르기->id,pw입력->제출->signup.vue 열림

2. signup.vue에서 일단 DB에 id,pw를 저장
3. Usercontroller.java(request mapping=/api/v1/user)
-><result> resist에서 id가 존재하는지 확인 후 있는지 없는지 signup.vue로 반환
4. signup.vue에서 makegetrequestJson(userID,DeviceID 등 )을 Fido2Controller로 보냄
5. Fido2Controller(request mapping=/api/v1/fido2)
-> 매핑에 따라서 4번의 값을 attestation/options로 보내고 그 다음 attestation/result로 보내고 이게 다시 signup.vue로 들어옴
6. signup.vuefh attestation한 결과가 넘어오고 fido2util>resistration실행
7. fido2util에서 publickey 생성해서 fido2서버로 넘기고 응답받으면 state ok 끝...?
