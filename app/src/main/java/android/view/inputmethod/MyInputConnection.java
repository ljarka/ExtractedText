package android.view.inputmethod;

import android.content.Context;
import android.text.Editable;

import ljarka.github.com.extractedtext.MyCustomView;

public class MyInputConnection extends BaseInputConnection {

    private MyCustomView customView;
    private InputMethodManager inputMethodManager;
    private int currentToken;
    private ExtractedText extractedText;

    public MyInputConnection(MyCustomView targetView, boolean fullEditor) {
        super(targetView, fullEditor);
        customView = targetView;
        inputMethodManager = (InputMethodManager) customView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public Editable getEditable() {
        return customView.mText;
    }

    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        boolean returnValue = super.commitText(text, newCursorPosition);
        customView.invalidate();
        extractedText = new ExtractedText();
        extractedText.text = customView.getText().toString();
        extractedText.selectionStart = Integer.MAX_VALUE;
        extractedText.selectionEnd = Integer.MAX_VALUE;
        extractedText.partialEndOffset = customView.getText().length();
        inputMethodManager.updateExtractedText(customView, currentToken, extractedText);
        return returnValue;
    }

    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
        currentToken = request.token;
        extractedText = new ExtractedText();
        return extractedText;
    }
}