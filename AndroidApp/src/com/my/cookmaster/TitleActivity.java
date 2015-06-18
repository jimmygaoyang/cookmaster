package com.my.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 
 
/**
 * @author gao_chun
 * �Զ��������
 */
public class TitleActivity extends FragmentActivity implements OnClickListener{
 
    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mForwardButton;
    private RelativeLayout mContentLayout;
 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();   //���� activity_title ���� ������ȡ���⼰���ఴť
    }
 
 
    private void setupViews() {
        super.setContentView(R.layout.title_activity);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mContentLayout = (RelativeLayout) findViewById(R.id.layout_content);
        mBackwardbButton = (Button) findViewById(R.id.button_backward);
        mForwardButton = (Button) findViewById(R.id.button_forward);
    }
 
    /**
     * �Ƿ���ʾ���ذ�ť
     * @param backwardResid  ����
     * @param show  true����ʾ
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
 
    /**
     * �ṩ�Ƿ���ʾ�ύ��ť
     * @param forwardResId  ����
     * @param show  true����ʾ
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText(forwardResId);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
 
    /**
     * ���ذ�ť����󴥷�
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        Toast.makeText(this, "������أ����ڴ˴�����finish()", Toast.LENGTH_LONG).show();
        //finish();
    }
 
    /**
     * �ύ��ť����󴥷�
     * @param forwardView
     */
    protected void onForward(View forwardView) {
        Toast.makeText(this, "����ύ", Toast.LENGTH_LONG).show();
    }
 
 
    //���ñ�������
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }
 
    //���ñ�������
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }
 
    //���ñ���������ɫ
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }
 
 
    //ȡ��FrameLayout�����ø���removeAllViews()����
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }
 
    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }
 
    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }
 
 
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * ��ť������õķ���
     */
    @Override
    public void onClick(View v) {
 
        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;
 
            case R.id.button_forward:
                onForward(v);
                break;
 
            default:
                break;
        }
    }
}
