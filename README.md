# Station_Inside_Navigation
Subway Information & Subway Station inside Navigation android APP in Korea
[java file](https://github.com/KangHongJun/Station_Inside_Navigation/tree/main/app/src/main/java/org/starmine/station_inside_navigation)



* implementing subway route with Dijkstra

# 추가작업
* 역내부 안내
 1. 환승역
 2. 리스트뷰 버튼 버그 수정
 3. 경로찾기 개선 방식 적용

# 개선사항&에러
* 역 내부 지도 개선 - 경로 안내 표시
 1. 각 지점 좌표값 지정해주고, 좌표값 기준으로 선긋기
  - 시작 지점에 canvas로 위치 표시 or 이미지 배치
  - 끝 지점에 canvas로 화살표 그리기(마지막 좌표간의 이동방향에 따라서 조절,ex)draw(765,190,765,230) : 동쪽 ) or 이미지 배치
  - 선그리기 완료, 지금은 특정역 기준이기 때문에 다른 역 데이터 받았을때 적용되는것 생각해야함
  지금 방식은 매개변수로 지도와 현재층 받아서 그때그때 경찾기+그림그리기 이므로 
  경로찾기를 inside navigation에서 실행후 프라그먼트에 번들 데이터로 보내서 진행하는 방식이 나아보임
  

* 호선 데이터 추가
* 실시간 도착정보
* 팝업창 개선


# 리팩토링
* line fragment 하나로 생성하여 번들 데이터로 각각 보내기
* line code구하는 class생성 - null일때 예외처리
* DB참조 class생성

* 시간표 코드 개선
* 시간표기준 도착정보 코드 개선
