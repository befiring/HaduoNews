package com.befiring.haduonews.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.befiring.haduonews.R;

import java.util.ArrayList;

public class WuziqiPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 15;
    private int MAX_COUNT_IN_LINE = 5;

    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float ratioPieceOfLineheight = 3 * 1.0f / 4;

    //白棋先手或当前轮到白棋
    private boolean mIsWhite = true;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;

    static boolean[][][] wins = new boolean[15][15][572];
    static int count = 0;
    //赢法统计数组
    static int[] myWin ;
    static int[] computerWin ;


    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWins();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        Log.d("wm", "widthSize:" + widthSize);
        Log.d("wm", "heightSize:" + heightSize);
        Log.d("wm", "width:" + width);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineheight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x, y);

            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }

//            if(mIsWhite){
//                mWhiteArray.add(p);
//            }else {
//                mBlackArray.add(p);
//            }
            myStep(p);
//            mIsWhite = !mIsWhite;
            for (int k = 0; k < count; k++) {
                if (wins[p.x][p.y][k]) {
                    myWin[k]++;
                    computerWin[k] = 6;
                }
            }
            if(!mIsGameOver){
                computerAi();
            }

        }

        return true;
    }

    private void myStep(Point p) {
        if (mIsWhite)
            mWhiteArray.add(p);
        mIsWhite=false;
        invalidate();
    }
    private void computerStep(Point p) {
        if (!mIsWhite)
            mBlackArray.add(p);
        mIsWhite=true;
        invalidate();
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);

        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";

            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(ArrayList<Point> points) {

        for (Point p : points) {
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            win = checkVertical(x, y, points);
            if (win) return true;
            win = checkLeftDiagonal(x, y, points);
            if (win) return true;
            win = checkRightDiagonal(x, y, points);
            if (win) return true;
        }
        return false;
    }

    private boolean checkHorizontal(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    private boolean checkVertical(int x, int y, ArrayList<Point> points) {
        int count = 1;
        //向上数
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        //向下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    private boolean checkLeftDiagonal(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    private boolean checkRightDiagonal(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }


    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - ratioPieceOfLineheight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceOfLineheight) / 2) * mLineHeight, null);
        }

        for (int i = 0, n = mBlackArray.size(); i < n; i++) {
            Point whitePoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (whitePoint.x + (1 - ratioPieceOfLineheight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceOfLineheight) / 2) * mLineHeight, null);
        }
    }

    /**
     * 绘制棋盘
     *
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {

        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }

    private void start() {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }



    private static void initWins() {

        //记录纵向的赢法
        for (int i = 0; i < 15; i++) { //i:五个棋子中最始端的棋子的横坐标
            for (int j = 0; j < 11; j++) {//j:五个棋子中最始端的棋子的纵坐标
                for (int k = 0; k < 5; k++) {//k:一种赢法有五个棋子
                    wins[i][j + k][count] = true;  //每个棋子的横坐标相同，纵坐标从j开始，随k递增
                }
                count++;
            }
        }
        //记录横向的赢法
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    wins[j + k][i][count] = true;
                }
                count++;
            }
        }

        //记录反斜线方向的赢法
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    wins[i + k][j + k][count] = true;
                }
                count++;
            }
        }

        //记录斜线方向的赢法
        for (int i = 0; i < 11; i++) {
            for (int j = 14; j > 3; j--) {
                for (int k = 0; k < 5; k++) {
                    wins[i + k][j - k][count] = true;
                }
                count++;
            }
        }
        myWin = new int[count];
        computerWin = new int[count];
    }


    public static void main(String[] args) {
        initWins();
        System.out.println(count);
    }

    private void computerAi() {
        int[][] myScore = new int[15][15];
        int[][] computerScore = new int[15][15];
        int max = 0;
        int u = 0, v = 0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Point point = new Point(i, j);//遍历棋盘的每一个点
                if (!(mBlackArray.contains(point) || mWhiteArray.contains(point))) {//棋盘上没有落子的点
                    for (int k = 0; k < count; k++) {//遍历该点的所有赢法
                        if (wins[i][j][k]) {     //该点上的第k种赢法
                            if (myWin[k] == 1) {  //如果棋手（不是AI）在该点的第k种赢法上已经有了1颗棋子
                                myScore[i][j] += 200;
                            } else if (myWin[k] == 2) {
                                myScore[i][j] += 400;
                            } else if (myWin[k] == 3) {
                                myScore[i][j] += 2000;
                            } else if (myWin[k] == 4) {
                                myScore[i][j] += 10000;
                            }

                            if (computerWin[k] == 1) {
                                computerScore[i][j] += 220;
                            } else if (computerWin[k] == 2) {
                                computerScore[i][j] += 420;
                            } else if (computerWin[k] == 3) {
                                computerScore[i][j] += 2100;
                            } else if (computerWin[k] == 4) {
                                computerScore[i][j] += 20000;
                            }
                        }
                    }

                    if (myScore[i][j] > max) {
                        max = myScore[i][j];
                        u = i;
                        v = j;
                    } else if (myScore[i][j] == max) {
                        if (computerScore[i][j] > computerScore[u][v]) {
                            u = i;
                            v = j;
                        }
                    }

                    if (computerScore[i][j] > max) {
                        max = computerScore[i][j];
                        u = i;
                        v = j;
                    } else if (computerScore[i][j] == max) {
                        if (myScore[i][j] > myScore[u][v]) {
                            u = i;
                            v = j;
                        }
                    }
                }
            }
        }
        computerStep(new Point(u,v));
        for (int i = 0; i < count; i++) {
            if (wins[u][v][i]) {
                computerWin[i]++;
                myWin[i] = 6;
            }
        }
    }

    public void restart(){
        mBlackArray.clear();
        mWhiteArray.clear();
        mIsGameOver=false;
        mIsWhiteWinner=false;
        mIsWhite=true;
        myWin = new int[count];
        computerWin = new int[count];
        invalidate();
    }

}
