# Station_Inside_Navigation
Subway Information & Subway Station inside Navigation android APP

* implementing subway route with Dijkstra

* Inside navi using?
 <https://www.mappedin.com>
 <https://www.indooratlas.com>

# 개선사항&에러
* 역 내부 지도 개선 - 가는 방향 점선/화살표 형태로 알려주기 
 1. 각 좌표값 기준으로 화살표 긋고, 화살표에 대한 변곡점 구하여 화살표 만들기(다른 객체의 좌표와 겹치면 조정하기)

* 호선 데이터 추가
* 실시간 도착정보
* 카카오 지도API
* 팝업창 개선


# 리토링
*line fragment 하나로 생성하여 번들 데이터로 각각 보내기
* static 남용 수정
* line code구하는 class생성 - null일때 예외처리
* DB참조 class생성

* 시간표 코드 개선(다른 방법 찾아보기)
* 시간표기준 도착정보 코드 개선
