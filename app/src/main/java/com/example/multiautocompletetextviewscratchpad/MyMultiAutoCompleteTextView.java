package com.example.multiautocompletetextviewscratchpad;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;

public class MyMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView {
    private Tokenizer mTokenizer;

    public MyMultiAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTokenizer(Tokenizer t) {
        // Capture the Tokenizer otherwise I can't get it
        super.setTokenizer(t);
        this.mTokenizer = t;
    }

    @Override
    public void performValidation() {
        Validator v = getValidator();

        if (v == null || mTokenizer == null) {
            return;
        }

        Editable e = getText();
        int i = getText().length();
        while (i > 0) {
            int start = mTokenizer.findTokenStart(e, i);
            int end = mTokenizer.findTokenEnd(e, start);

            CharSequence sub = e.subSequence(start, end);
            if (TextUtils.isEmpty(sub)) {
                e.replace(start, i, "");
            } else if (!v.isValid(sub)) {
                e.replace(start, i,
                        mTokenizer.terminateToken(v.fixText(sub)));
            }

            // -- MultiAutoCompleteTextView start --
            // i = start;
            // -- MultiAutoCompleteTextView end --
            //
            // replace with:
            i = start - 2;
        }
    }
}
