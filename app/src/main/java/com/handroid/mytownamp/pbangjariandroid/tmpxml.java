package com.handroid.mytownamp.pbangjariandroid;



public class tmpxml {
String tmp ="타이틀바,앱바, 즐겨찾기를 위한 pc방고유코드(primary key),pc방리스트출력,클릭후 pc방 상세정보,전화예약 또는 좌석보기할건지(경로 축약)";
    String tmp1="pc방 상세 사진정보(스크롤가능한 뷰?,카드뷰?)";
    String tmp2="후기 평점";//zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz
    /*public ZoomView PCseatView(View inflateView, int i, int j, int[] num) {
        grid = new GridLayout(this); //새로생성
        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams(container.getLayoutParams()); //view그륩의 layoutparams 가져와서 넣기
        layoutParam.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParam.height = GridLayout.LayoutParams.WRAP_CONTENT;
        grid.setLayoutParams(layoutParam);
        grid.setColumnCount(i); //가로세로 설정
        grid.setRowCount(j);


        int count = 0;
        try {
            for (int c = 0; c < i; c++) {
                for (int d = 0; d < j; d++) {
                    TextView tmps = new TextView(mContext);
                    tmps.setHeight(100);
                    tmps.setWidth(100);
                    if (num[count] == 0) {
                        tmps.setBackgroundResource(R.drawable.unusable1);
                    } else if (num[count] == 1) {
                        tmps.setBackgroundResource(R.drawable.usable1);
                    } else if (num[count] == 2) {
                        tmps.setBackgroundResource(R.drawable.checking1);
                    } else {
                        tmps.setText("");
                    }
                    grid.addView(tmps);
                    count++;
                }


            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("asd", "asdasd");
        }
        pcmap.addView(grid);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(inflateView);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.zoomTo(2f, 500f, 300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;

    }*/

}
