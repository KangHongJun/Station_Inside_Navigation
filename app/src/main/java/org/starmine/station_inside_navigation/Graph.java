package org.starmine.station_inside_navigation;

class RoutePair{
    int ParentV; //시작 위치
    int ChildV; //끝 위치
}

public class Graph {
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

    public void dijkstra(int start, int end){
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


        System.out.println("경로 체크");

        //임시
        //start = 118
        //end = 37;

        //경로확인 https://develop-dream.tistory.com/89
        int routeStack[] = new int[n];
        int routeCount = 0;
        routeStack[routeCount] = end; //끝 위치 대입
        int now = end; //끝 노드 임시

        while(true){
            if (routePair[now-1].ParentV == start){
                routeStack[routeCount] = start;
                break;
            }
            now = routePair[now].ParentV;
            routeStack[routeCount] = now;
            System.out.println(now + "값");
        }

//        for (int i = routeCount-1;i>=0;i--){
//            if(i==routeCount-1){
//                System.out.println(routeStack[i] + "시작");
//            }else if(i == 1){
//                System.out.println(routeStack[i] + "도착");
//            }else{
//                System.out.println(routeStack[i] + "방문");
//            }
//        }




        System.out.print(distance[37]+" ");

        //결과값 출력
//        for(int i=1;i<n+1;i++){
//            System.out.print(distance[i]+" ");
//        }
        System.out.println("");

    }
}