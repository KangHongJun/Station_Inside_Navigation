package org.starmine.station_inside_navigation;

import java.util.ArrayList;

class RoutePair{
    int ParentV; //시작 위치
    int ChildV; //끝 위치
}

public class Graph {
    ArrayList<Integer> route_list = new ArrayList<>();
    static int route[];

    private int n;           //노드들의 수
    private int maps[][];

    public Graph(int n){
        this.n = n;
        maps = new int[n][n]; //
    }

    public void input(int i,int j,int w){
//        maps[i-1][j-1] = w;
//        maps[j-1][i-1] = w;
        maps[i][j] = w;
        maps[j][i] = w;
    }

    public int dijkstra(int start, int end){
        System.out.println("알고리즘 시작");

        int distance[] = new int[n]; //         //최단 거리를 저장할 변수
        boolean[] visited = new boolean[n]; //    //해당 노드를 방문했는지 체크할 변수

        RoutePair routePair[] = new RoutePair[n]; //   //경로확인용 간선 저장 배열

        for (int i=0;i<routePair.length;i++){
            routePair[i]= new RoutePair();
        }//생성안할시시 오류

        //distance값 초기화.
        for(int i=0;i<n;i++){ //
            distance[i] = Integer.MAX_VALUE;
        }

        //시작노드값 초기화.
        distance[start] = 0;

        //시작 노드의 가중치 기록
        for(int i=0;i<n;i++){ //
            if(!visited[i] && maps[start][i] !=0){
                distance[i] = maps[start][i];
            }
        }

        visited[start] =true;
        routePair[start].ParentV = start;


        int current = start;

        for(int a=0;a<n-1;a++){

            //원래는 모든 노드가 true될때까지 인데
            //노드가 n개 있을 때 다익스트라를 위해서 반복수는 n-1번이면 된다.
            //원하지 않으면 각각의 노드가 모두 true인지 확인하는 식으로 구현해도 된다.
            int min=Integer.MAX_VALUE;
            //int min_index=-1;
            int min_index=current;


            //최소값 찾기
            for(int i=0;i<n;i++){ //
                if(!visited[i] && distance[i]!=Integer.MAX_VALUE){
                    if(distance[i]<min ){
                        min=distance[i];
                        min_index = i;
                    }
                }
            }

            current = min_index;


            visited[current] = true;

            //가중치 배열 수정
            for(int i=0;i<n;i++){
                if(!visited[i] && maps[current][i]!=0){
                    if(distance[i]>distance[current]+maps[current][i]){


                        routePair[current].ChildV = i;//#수정 필요가능성 존재
                        routePair[i].ParentV = current;
                        distance[i] = distance[current]+maps[current][i];
                    }
                }
            }

        }


        route = new int[routePair.length]; //999같은 적당하 값으로 초기화 해두기
        route[0] = end;
        route_list.add(end);

        System.out.println("경로 체크");
        System.out.println("소요시간 : "+distance[end]);

        //경로확인 https://develop-dream.tistory.com/89
        int routeStack[] = new int[n];
        int routeCount = 0;
        routeStack[routeCount] = end; //끝 위치 대입
        int now = end; //끝 노드 임시


        int i =1;

        while(true){
            if (routePair[now-1].ParentV == start){
                routeStack[routeCount] = start;
                break;
            }

            now = routePair[now].ParentV;
            if(now==0){ //바로 다음역이거나 환승역의 바로 다음역일 경우 0이 나오면서 에러가 나기 때문에 처리
                break;
            }
            routeStack[routeCount] = now;
            //System.out.println(now+"진행중");
            route[i]=now;
            route_list.add(now);
            i++;
        }

        route[i]=start;
        route_list.add(start);
        return distance[end];
    }
    static public int[] getRoute(){
        return route;
    }

    public ArrayList getRoute_list(){
        return route_list;
    }
}