package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-04-19 제작
 *
 *                    Alpha Version 0.01
 *                  마지막 업데이트 04-25
 * 　　　　　$ ================================== $
 */
public class BasePlaceView extends LinearLayout {

    public interface OnPlaceClickListener {
        void onPlaceClicked ( View view, int position ) ;
    }

    public interface OnPlaceTextListener {
        String onPlaceTextListener ( View view, int position ) ;
    }

    public interface OnPlaceViewCreatedListener {
        void onPlaceViewCreate ( View view, int position ) ;
    }

    /**
     * 타입 : 기본값
     */
    public static final int PLACE_TYPE_DEFAULT = 0;
    /**
     * 타입 : 분단 나누기
     */
    public static final int PLACE_TYPE_BLANK = Integer.MIN_VALUE;
    /**
     * 타입 : 이 뒤로 데이터 없음
     */
    public static final int PLACE_TYPE_NO_SPACE = Integer.MIN_VALUE + 1;
    /**
     * 타입 : 이 뒤로 데이터가 끝에 몰려있음
     */
    public static final int PLACE_TYPE_NO_SPACE_CENTER = Integer.MIN_VALUE + 2;

    /**
     * 방향
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     */
    public static final int DIRECTION_LINE_1 = 0;

    public static final int PART_TO_RIGHT = 1;
    public static final int PART_TO_LEFT = 2;

    public static final int START_POSITION_LEFT_TOP = 0;
    public static final int START_POSITION_RIGHT_TOP = 1;
    public static final int START_POSITION_LEFT_BOTTOM = 2;
    public static final int START_POSITION_RIGHT_BOTTOM = 3;

    /**
     * 방향
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *    <-ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *    <-ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
     *      ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ->
     *    <-ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
     */
    public static final int DIRECTION_LINE_2 = 1;

    /**
     * 방향   1        2        3        4
     *      ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁ->
     *    <-ㅁㅁㅁ <-ㅁㅁㅁ <-ㅁㅁㅁ <-ㅁㅁ
     *      ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁ->
     *    <-ㅁㅁㅁ <-ㅁㅁㅁ <-ㅁㅁㅁ -<ㅁㅁ
     *      ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁㅁ-> ㅁㅁ->
     *    <-ㅁㅁㅁ <-ㅁㅁㅁ <-ㅁㅁㅁ <-ㅁㅁ
     */
    public static final int DIRECTION_LINE_3 = 2;

    // 일반 Margin 은 Divider 의 개념으로
    // 칸과 칸 사이의 Margin 을 설정
    private int mWidthMargin = 0;
    private int mHeightMargin = 0;

    // BlankMargin 은 완전한 공백을 의미
    // 한 분단의 크기 정도로 이해 할 수 있음
    private int mWidthBlankMargin = 0;
    private int mHeightBlankMargin = 0;

    // 기본적인 Cell 크기
    private int mCellWidth = 308;
    private int mCellHeight = 308;
    private int mCellMargin = 0;

    private int mCellMinWidth = mCellWidth;
    private int mCellMinHeight = mCellHeight;

    private int mCellMaxWidth = mCellWidth * 5;
    private int mCellMaxHeight = mCellHeight * 5;

    private int mTextSize = 14 ;
    private int mTextColor = Color.WHITE ;
    private Object mBackground = null ;

    private TableLayout mTableLayout = null ;
    private BaseNoneHorizontalScrollView mHorizontalScrollView;
    private BaseNoneScrollView mScrollView;

    private OnPlaceClickListener onPlaceClickListener = null;
    private OnPlaceTextListener onPlaceTextListener = null;
    private OnPlaceViewCreatedListener onPlaceViewCreatedListener = null;
    private ScaleGestureDetector onScaleGestureDetector = null;

    /**
     *  시작점
     */
    private int mStartPosition = 0 ;

    /**
     *  빈 공백 칸
     */
    private int mBlankTotal = 0;

    /**
     *  방향
     */
    private int mDirection = -1;

    // 크기
    private float mScaleFactor = 1f;

    private int[][] mTableData = null ;

    public BasePlaceView(Context context) {
        super(context);
        inflateBase();
    }

    public BasePlaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateBase();
    }

    public BasePlaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateBase();
    }

    @TargetApi(21)
    public BasePlaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateBase();
    }

    float mx, my;

    private ArrayList<Button> mButtons = new ArrayList<>();

    private boolean collisionCheck ( int x1, int y1, int xx1, int yy1, int x2, int y2, int xx2, int yy2 ) {
        /*
            x1 : 1번 사각형의 왼쪽 꼭지점
            xx1 : 1번 사각형의 오른쪽 꼭지점
            y1 : 1번 사각형의 오른쪽 꼭지점
            yy1 : 1번 사각형의 오른쪽 꼭지점
         */
        if ( xx1 < x2 ) return false ;
        if ( xx2 < x1 ) return false ;
        if ( yy2 < y1 ) return false ;
        if ( yy1 < y2 ) return false ;

        return true ;
    }

    private boolean mCollision = false ;
    private boolean mIsCanMove = false ;

    private int clickCount = 0;
    private int mMaxMove = 0;

    private float mDragNewDist = 0 ;
    private float mDragOldDist = 0 ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Button button = null ;
        float curX = 0, curY = 0;

        // Scale
        onScaleGestureDetector.onTouchEvent(event);

        Log.d("BasePlaceView", "event.getPointerCount():" + event.getPointerCount());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if ( event.getPointerCount() < 2 ) {

                    curX = event.getX();
                    curY = event.getY();

                    button = getCollisionButton(mx, my);

                    clickCount = clickCount >= 5 ? 5 : ++clickCount;
                    Log.d("BasePlaceView", "clickCount:" + clickCount);

                    // 충돌 체크 할 것
                    int resultX = (int) Math.abs(mx - curX);
                    int resultY = (int) Math.abs(my - curY);

                    if ( resultX >= 20 || resultY >= 20 ) {
                        mIsCanMove = true ;
                    }

                    if (clickCount >= 5 && !mIsCanMove) {
                        if (button != null) {
                            button.setPressed(true);
                        }
                    } else {
                        if (button != null) {
                            button.setPressed(false);
                        }
                    }

                    mScrollView.scrollBy((int) (mx - curX), (int) (my - curY));
                    mHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));
                    mx = curX;
                    my = curY;
                // 확대
                } else if ( event.getPointerCount() >= 2 ) {
                    mDragNewDist = getSpacing(event);

                    for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                        // TableRow
                        TableRow row = (TableRow) mTableLayout.getChildAt(i);
                        for (int i1 = 0; i1 < row.getChildCount(); i1++) {
                            View cellView = row.getChildAt(i1);

                            cellView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                            TableRow.LayoutParams params = (TableRow.LayoutParams) cellView.getLayoutParams();

                            mCellWidth = cellView.getWidth();
                            mCellHeight = cellView.getHeight();

                            int cellWidth = (int) (mCellWidth * mScaleFactor);
                            int cellHeight = (int) (mCellHeight * mScaleFactor);

                            if (mCellMinHeight <= cellHeight && cellHeight <= mCellMaxHeight) {
                                params.height = cellHeight;
                            }

                            if (mCellMinWidth <= cellWidth && cellWidth <= mCellMaxWidth) {
                                params.width = cellWidth;
                            }

                            cellView.setLayoutParams(params);
                        }
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                button = getCollisionButton(mx, my);

                if (!mIsCanMove) {
                    if (button != null) {
                        button.performClick();
                        button.setPressed(false);
                        mCollision = true;
                    }
                }

                mScrollView.scrollBy((int) (mx - curX), (int) (my - curY));
                mHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                clickCount = 0;
                mIsCanMove = false;
                mCollision = false;
                break;

        }

        Log.d("BasePlaceView", "============================");
        Log.d("BasePlaceView", "event.getAction():" + event.getAction());
        Log.d("BasePlaceView", "mx:" + mx);
        Log.d("BasePlaceView", "my:" + my);
        Log.d("BasePlaceView", "curX:" + curX);
        Log.d("BasePlaceView", "curY:" + curY);
        Log.d("BasePlaceView", "event.getRawX():" + event.getRawX());
        Log.d("BasePlaceView", "event.getRawY():" + event.getRawY());

        Log.d("BasePlaceView", "mScrollView.getScrollX():" + mScrollView.getScrollX());
        Log.d("BasePlaceView", "mScrollView.getScrollY():" + mScrollView.getScrollY());

        return true;
    }

    private Button getCollisionButton ( float mx, float my ) {
        int[] layoutPoint = new int[2];
        getLocationInWindow(layoutPoint);

        Button button = null ;
        for (Button mButton : mButtons) {
            int[] point = new int[2] ;
            mButton.getLocationInWindow(point);

            int x1 = (int) mx-5;
            int xx1 = (int) mx+5;
            int y1 = (int) my+5;
            int yy1 = (int) my+5;

            int buttonX1 = (int) point[0] - layoutPoint[0];
            int buttonXX1 = (int) point[0] + getCellWidth() - layoutPoint[0];
            int buttonY1 = (int) point[1] - layoutPoint[1];
            int buttonYY1 = (int) point[1] + getCellHeight() - layoutPoint[1];

            if (collisionCheck(x1, y1, xx1, yy1, buttonX1, buttonY1, buttonXX1, buttonYY1)) {
                if (!mCollision) {
                    button = mButton;
                    break ;
                }
            }
        }

        return button;
    }

    /**
     * ScrollView 랑 TableLayout 추가
     */
    private void inflateBase ( ) {
        onScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        if (getChildCount() <= 0) {
            // Max 값 초기화
            mMaxMove = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

            mTableLayout = new TableLayout(getContext());
            mScrollView = new BaseNoneScrollView(getContext());
            mHorizontalScrollView = new BaseNoneHorizontalScrollView(getContext());

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            mTableLayout.setLayoutParams(params);

            mScrollView.setLayoutParams(params);
            mHorizontalScrollView.setLayoutParams(params);

            mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollY = mScrollView.getScrollY();

                    Log.d("BasePlaceView", "scrollY:" + scrollY);
                    Log.d("BasePlaceView", "ScrollChanged");
                }
            });

            mHorizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollX = mHorizontalScrollView.getScrollX();

                    Log.d("BasePlaceView", "scrollX:" + scrollX);
                    Log.d("BasePlaceView", "ScrollChanged");
                }
            });

            mHorizontalScrollView.addView(mTableLayout);
            mScrollView.addView(mHorizontalScrollView);
            addView(mScrollView);
        }
    }

    /**
     * 현재 데이터 중 가로 데이터가 많은 것을 반환
     * @param tableData
     * @return
     */
    private int getLineMax ( int[][] tableData ) {
        int lineMax = 0;

        for (int[] ints : tableData) {
            int count = 0;
            for (int anInt : ints) {
                // 0 이상일 경우에만 count 에 누적
                if (anInt >= 0) {
                    count += anInt;
                }
            }

            lineMax = lineMax > count ? lineMax : count;
        }

        return lineMax;
    }

    public void setTable ( int[][] tableData ) {
        for ( int i=0; i<tableData.length; i++ ) {
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for ( int j=0; j<tableData[i].length; j++ ) {
                addTableView(tr, tableData[i][j], onPlaceClickListener);
            }

            mTableLayout.addView(tr);
        }

        // TABLE 로딩이 끝났을때, Button 전부 포커싱 안되도록 수정
        for (Button mButton : mButtons) {
            mButton.setClickable(false);
        }
    }

    /**
     * [세로 개수], [가로] = 타입 <br>
     *
     * @param tableData
     */
    public void setTable ( int[][] tableData, int direction ) {
        // 테이블 데이터 저장
        mTableData = tableData;

        // 방향 데이터 저장
        mDirection = direction;

        // Part
        int part = PART_TO_RIGHT;

        // 데이터 추가
        for (int i = 0; i < tableData.length; i++) {
            TableRow tr = new TableRow(getContext());

            for (int i1 = 0; i1 < tableData[i].length; i1++) {
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT ));

                // 타입
                int type = tableData[i][i1];

                // 공백일 경우 처리
                if ( type < 0 ) {
                    type = Math.abs(type);
                    for (int i2 = 0; i2 < type; i2++) {
                        mBlankTotal++;
                        addTableView(tr, -1, null);
                    }
                } else {
                    // TODO: 이부분에 텍스트를 받는 Listener 를 연동해서 View 를 만들때 처리할 수 있음
                    for (int i2 = 0; i2 < type; i2++) {
                        // TODO: 현재 포지션값의 숫자를 계산해야함
                        int position = 0;

                        switch (mDirection) {
                            case DIRECTION_LINE_1:
                                position = getDirectionLine1(i, i1, i2);
                                break ;
                            case DIRECTION_LINE_2:
                                position = getDirectionLine2(i, i1, i2, part);
                                break ;
                            case DIRECTION_LINE_3:
                                position = getDirectionLine3(i2);
                                break ;
                        }

                        switch (mStartPosition) {
                            case START_POSITION_LEFT_TOP :
                                break ;
                            case START_POSITION_LEFT_BOTTOM :
//                                position = getTotal() - position;
                                break ;
                            case START_POSITION_RIGHT_TOP :
                                break ;
                            case START_POSITION_RIGHT_BOTTOM :
                                break ;
                        }

                        addTableView(tr, position, onPlaceClickListener);
                    }
                }
            }

            // Part 는 바닥에서 부터
            part = part == PART_TO_RIGHT ? PART_TO_LEFT : PART_TO_RIGHT;

            // Table Row 가 모두 생성 됬을 경우 추가
            if (mTableLayout != null) {
                mTableLayout.addView(tr);
            }
        }

        // TABLE 로딩이 끝났을때, Button 전부 포커싱 안되도록 수정
        for (Button mButton : mButtons) {
            mButton.setClickable(false);
        }
    }

    /**
     * 총 채워야 할 포지션 값
     * @return
     */
    private int getTotal ( ) {
        int result = 0;

        for (int[] ints : mTableData) {
            for (int anInt : ints) {
                if ( anInt > 0 ) {
                    result += anInt;
                }
            }
        }

        return result;
    }

    /**
     * 1번 길의 알고리즘을 통한 포지션 값 정보
     * @return
     */
    private int getDirectionLine1 ( int y, int x, int count ) {
        int position = 0;

        // 세로 데이터가 이 전에도 있었을 경우
        position = getPositionCount(y, x) + count;

        return position;
    }

    /**
     * 2번 길의 알고리즘을 통한 포지션 값 정보
     * @param position
     * @return
     */
    private int getDirectionLine2 ( int y, int x, int count, int part ) {
        int position = 0;

        // 현재 라인의 카운트를 구해서,
        // 더한 값에서 - Position 값을 통해 위치 잡을 것
        if ( part == PART_TO_LEFT ) {
            position = (getDirectionLine1(y, x, 0) - getTotalInLine(y, x) + getTotalInLine(y)) -
                    (count + getTotalInLine(y, x)) - 1  - mBlankTotal;
        } else if ( part == PART_TO_RIGHT ) {
            position = getDirectionLine1(y, x, 0) + count - mBlankTotal;
        }

        return position;
    }

    /**
     * 3번 길의 알고리즘을 통한 포지션 값 정보
     * @param position
     * @return
     */
    private int getDirectionLine3 ( int position ) {
        return position;
    }

    /**
     * y 줄의 총 카운트 값 반환
     * @param y
     * @return
     */
    private int getLineCount ( int y ) {
        int count = 0;

        for (int i : mTableData[y]) {
            count += i;
        }

        return count;
    }

    /**
     * 현재 받은 세로와 가로위치에 맞춰 <br>
     * position 값을 반환
     * @param y
     * @param x
     * @return
     */
    private int getPositionCount ( int y, int x ) {
        int count = -1;

        if (mTableData != null) {
            count = 0;

            for (int i1 = 0; i1 <= y; i1++) {
                int[] ints = mTableData[i1];
                for (int i2 = 0; i2 < ints.length; i2++) {
                    // 마지막 줄의, 마지막 데이터 인지 확인
                    if ( i1 == y && i2 >= x ) {
                        break ;
                    }

                    if ( ints[i2] >= 0 ) {
                        count += ints[i2];
                    }
                }
            }
        }

        return count;
    }

    /**
     * 전달받은 Y 의 한줄 크기 값 반환
     * @param y
     * @return
     */
    private int getTotalInLine ( int y ) {
        int total = 0;

        int[] line = mTableData[y];
        for ( int i=0; i<line.length; i++ ) {
            int type = line[i];

            total += type ;
        }

        return total ;
    }

    private float getSpacing ( MotionEvent event ) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 0, 0 에서부터 전달받은 Y, X 까지의 포지션값을 구하여 반환
     * @param y
     * @param x
     * @return
     */
    private int getTotalInLine ( int y, int x ) {
        int total = 0;

        int[] line = mTableData[y];
        for ( int i=0; i<x; i++ ) {
            int type = line[i];

            total += type ;
        }

        return total ;
    }

    /**
     * 전달받은 Y 값 이전까지의 합
     * @param y
     * @return
     */
    private int getTotalPrivous ( int y ) {
        int total = 0;

        for (int i = 0; i < y; i++) {
            for (int i1 : mTableData[i]) {
                total += i1;
            }
        }

        return total;
    }

    /**
     * 전달받은 Y와 X를 통해 현재 자신의 포지션을 확인
     * @param y
     * @return
     */
    private int getPosition ( int y, int x ) {
        int max = 0;

        for (int i = 0; i < y; i++) {
            for (int i1 = 0; i1 < mTableData[i].length; i1++) {
                int data = mTableData[i][i1];

                if (i == y && i1 >= x) {
                    break ;
                }

                if (data >= 0) {
                    max += data;
                }
            }
        }

        return max;
    }

    /**
     * 해당 Y 값에서 가장 높은 Position 값 추출
     * @param y
     * @return
     */
    private int getMaxValueInLine ( int y ) {
        int max = 0;
        for (int i : mTableData[y]) {

        }
        return max ;
    }

    /**
     * 실제 TableView 추가
     */
    private void addTableView(TableRow tr, final int position, final OnPlaceClickListener onPlaceClickListener) {
        String str = String.valueOf(position);
        Button btn = new Button(getContext());

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                getCellWidth(),
                getCellHeight()
        );
        params.setMargins(mCellMargin, mCellMargin, mCellMargin, mCellMargin);

        setTableBackground(btn);
        btn.setText(str);
        btn.setTextSize(mTextSize);
        btn.setTextColor(mTextColor);
        btn.setLayoutParams(params);

        if ( position == PLACE_TYPE_BLANK ) {
            btn.setVisibility(View.INVISIBLE);
        }

        if (getOnPlaceTextListener() != null) {
            str = getOnPlaceTextListener().onPlaceTextListener(btn, position);
            btn.setText(str);
        }

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( position == PLACE_TYPE_BLANK ) {
                    return ;
                } else {
                    if (onPlaceClickListener != null) {
                        onPlaceClickListener.onPlaceClicked(v, position);
                    }
                }
            }
        });

        // View 가 만들어졌고, Row 에 추가되기 전
        if (getOnPlaceViewCreatedListener() != null) {
            getOnPlaceViewCreatedListener().onPlaceViewCreate(btn, position);
        }

        mButtons.add(btn);
        tr.addView(btn);
    }

    /*
        Getters / Setters
     */

    private void setTableBackground ( View view ) {
        if (mBackground instanceof Drawable) {
            view.setBackground((Drawable) mBackground);
        } else if ( mBackground instanceof Integer ) {
            view.setBackgroundResource((int) mBackground);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor = detector.getScaleFactor();

            Log.d("ScaleListener", "detector.getScaleFactor():" + detector.getScaleFactor());

//            mScaleFactor = Math.max(2.1f, Math.min(mScaleFactor, 10.0f));

            invalidate();
            return true;
        }
    }

    public void setBackground ( Object object ) {
        this.mBackground = object;
    }

    public TableLayout getTableLayout() {
        return mTableLayout;
    }

    public void setTableLayout(TableLayout mTableLayout) {
        this.mTableLayout = mTableLayout;
    }

    public int getCellHeight() {
        return mCellHeight;
    }

    public void setCellHeight(int mCellHeight) {
        this.mCellHeight = mCellHeight;
        this.mCellMinHeight = mCellHeight;
        this.mCellMaxHeight = mCellHeight * 5;
    }

    public int getCellWidth() {
        return mCellWidth;
    }

    public void setCellWidth(int mCellWidth) {
        this.mCellWidth = mCellWidth;
        this.mCellMinWidth = mCellWidth;
        this.mCellMaxWidth = mCellWidth * 5;
    }

    public int getHeightBlankMargin() {
        return mHeightBlankMargin;
    }

    public void setHeightBlankMargin(int mHeightBlankMargin) {
        this.mHeightBlankMargin = mHeightBlankMargin;
    }

    public int getWidthBlankMargin() {
        return mWidthBlankMargin;
    }

    public void setWidthBlankMargin(int mWidthBlankMargin) {
        this.mWidthBlankMargin = mWidthBlankMargin;
    }

    public int getHeightMargin() {
        return mHeightMargin;
    }

    public void setHeightMargin(int mHeightMargin) {
        this.mHeightMargin = mHeightMargin;
    }

    public int getWidthMargin() {
        return mWidthMargin;
    }

    public void setWidthMargin(int mWidthMargin) {
        this.mWidthMargin = mWidthMargin;
    }

    public OnPlaceClickListener getOnPlaceClickListener() {
        return onPlaceClickListener;
    }

    public void setOnPlaceClickListener(OnPlaceClickListener onPlaceClickListener) {
        this.onPlaceClickListener = onPlaceClickListener;
    }

    public int getStartPosition() {
        return mStartPosition;
    }

    public void setStartPosition(int mStartPosition) {
        this.mStartPosition = mStartPosition;
    }

    public OnPlaceTextListener getOnPlaceTextListener() {
        return onPlaceTextListener;
    }

    public void setOnPlaceTextListener(OnPlaceTextListener onPlaceTextListener) {
        this.onPlaceTextListener = onPlaceTextListener;
    }

    public OnPlaceViewCreatedListener getOnPlaceViewCreatedListener() {
        return onPlaceViewCreatedListener;
    }

    public void setOnPlaceViewCreatedListener(OnPlaceViewCreatedListener onPlaceViewCreatedListener) {
        this.onPlaceViewCreatedListener = onPlaceViewCreatedListener;
    }

}
