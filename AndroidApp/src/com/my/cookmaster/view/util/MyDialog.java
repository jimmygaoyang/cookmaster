package com.my.cookmaster.view.util;


import android.app.Dialog;
import android.app.PendingIntent.OnFinished;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.cookmaster.R;


/**
 *
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to
 * create and use your own look & feel.
 *
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 *
 * @author antoine vianey
 *
 */
public class MyDialog extends Dialog {
	View view;
	Context context;

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public View getContentView() {
		return this.view;
	}

    @Override
    public void setContentView(View v) {
        this.view = v;
        super.setContentView(v);
    }


    public static void show(Context context, String title, String msg) {
		new MyDialog.Builder(context).setTitle(title).setMessage(msg)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create().show();
	}

    public static void show(Context context, String msg) {
		new MyDialog.Builder(context).setMessage(msg)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create().show();
	}

    //bSys：是否显示全局的Dialog
  	//这需要还需要在AndroidManifest.xml中增加权限：
  	//<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    public static void show(boolean bSys, Context context, String msg) {
    	Context con = context;
		if(bSys) {
			con = context.getApplicationContext();
		}

		MyDialog dlg = new MyDialog.Builder(con).setMessage(msg)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();
//		if(bSys) {
//			dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		}
		dlg.show();
	}

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
    	MyDialog m_dialog;
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String neutralButtonText;
        private String negativeButtonText;
        private View contentView;
        boolean isTransparent;

        private DialogInterface.OnClickListener
                        positiveButtonClickListener,
                        neutralButtonClickListener,
                        negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
            this.isTransparent = false;
        }

        public Builder(Context context, boolean isTransparent) {
            this.context = context;
            this.isTransparent = isTransparent;
        }

        /**
         * Set the Dialog message from String
         * @param title
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         * @param title
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the neutral button resource and it's listener
         * @param neutralButtonText
         * @param listener
         * @return
         */
        public Builder setNeutralButton(int neutralButtonText,
                DialogInterface.OnClickListener listener) {
            this.neutralButtonText = (String) context
                    .getText(neutralButtonText);
            this.neutralButtonClickListener = listener;
            return this;
        }

        /**
         * Set the neutral button text and it's listener
         * @param neutralButtonText
         * @param listener
         * @return
         */
        public Builder setNeutralButton(String neutralButtonText,
                DialogInterface.OnClickListener listener) {
            this.neutralButtonText = neutralButtonText;
            this.neutralButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        /**
         * Create the custom dialog
         */
        public MyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyDialog dialog;
            if(this.isTransparent){
            	dialog = new MyDialog(context, R.style.TransparentDialog);
            }
            else {
            	dialog = new MyDialog(context, R.style.Dialog);
            }
            View layout = inflater.inflate(R.layout.dlg_comm, null);
            dialog.setContentView(layout, new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            if(title == null || title.trim().equals("")) {
            	((TextView) layout.findViewById(R.id.title)).setVisibility(View.GONE);
            	//layout.findViewById(R.id.dlg_line).setVisibility(View.GONE);
            }
            else {
            	((TextView) layout.findViewById(R.id.title)).setText(title);
            }
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                    		dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the neutral button
            if (neutralButtonText != null) {
                ((Button) layout.findViewById(R.id.neutralButton))
                        .setText(neutralButtonText);
                if (neutralButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.neutralButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                	neutralButtonClickListener.onClick(
                                    		dialog,
                                            DialogInterface.BUTTON_NEUTRAL);
                                }
                            });
                }
                else {
                	((Button) layout.findViewById(R.id.neutralButton))
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                        	dialog.dismiss();
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.neutralButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                	negativeButtonClickListener.onClick(
                                    		dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
                else {
                	((Button) layout.findViewById(R.id.negativeButton))
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                        	dialog.dismiss();
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                		R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);

			Window dialogWindow = dialog.getWindow();
			WindowManager m = (WindowManager)context.getSystemService("window");
			DisplayMetrics dm = new DisplayMetrics();
			m.getDefaultDisplay().getMetrics(dm);
			Display dis = m.getDefaultDisplay();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			int width = (int) (dm.widthPixels * 0.8);
			lp.width = width;
//			lp.height = width*3/4;
			dialogWindow.setAttributes(lp);

            return dialog;
        }

    }
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_HOME) {
		return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}

	}

//	@Override
//	public void onAttachedToWindow() {
//	this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD );
//
//	super.onAttachedToWindow();
//	}

















}