package com.trinity.dynamicforms.Utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextInputFilter implements InputFilter {
    Pattern mPattern;
    String blockCharacters;
    public EditTextInputFilter(String blockCharacters) {
        this.blockCharacters = blockCharacters;
        mPattern=Pattern.compile(blockCharacters);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher=mPattern.matcher(source);
        if(!blockCharacters.isEmpty()) {
//            if (!matcher.matches()) {
//                String string = (source + "").replaceAll(blockCharacters, "");
//                if (source.length() > 1) {
//                    return (source + "").substring(0, source.length() - 1);
//                } else {
//                    return "";
//                }
//            } else {
//                String string = (source + "").replaceAll(blockCharacters, "");
//                return string;
//            }
            if (source != null && blockCharacters.contains(("" + source))) {
                return "";
            }
        }
        return null;
    }

}