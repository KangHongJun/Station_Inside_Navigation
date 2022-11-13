# Station_Inside_Navigation - 캡스톤 졸업작품
Subway Information & Subway Station inside Navigation android APP in Korea
[main](https://github.com/KangHongJun/Station_Inside_Navigation/tree/main/app/src/main/java/org/starmine/station_inside_navigation)
[fragment](https://github.com/KangHongJun/Station_Inside_Navigation/tree/main/app/src/main/java/fragment)

[프로젝트 설명 pdf 다운로드](https://github.com/KangHongJun/Station_Inside_Navigation/raw/main/%EC%BA%A1%EC%8A%A4%ED%86%A4_Subwat_Inside_navaigaton.pdf)

* implementing subway route with Dijkstra

# 추가작업
* 역내부 안내
 1. 역 추가하여 테스트 및 적용
 2. 환승역
* DB읽어오기


# 개선사항&에러
* 역 내부 지도 개선 - 경로 안내 표시, 환승역 추가
 1. 각 지점 좌표값 지정해주고, 좌표값 기준으로 선긋기
  - 시작 지점에 canvas로 위치 표시 or 이미지 배치
  - 끝 지점에 canvas로 화살표 그리기(마지막 좌표간의 이동방향에 따라서 조절,ex)draw(765,190,765,230) : 동쪽 ) or 이미지 배치
  - 선그리기 완료, 지금은 특정역 기준이기 때문에 다른 역 데이터 받았을때 적용되는것 생각해야함
  

* 호선 데이터 추가
* 실시간 도착정보
* 팝업창 개선


# 리팩토링
* line fragment 하나로 생성하여 번들 데이터로 각각 보내기
* line code구하는 class생성 - null일때 예외처리
* DB참조 class생성

* 시간표 코드 개선
* 시간표기준 도착정보 코드 개선
