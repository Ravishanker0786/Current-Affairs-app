package com.prinfosys.news48.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.prinfosys.news48.R;

public class Utils {

    public static void goToFragment(Context mContext, Fragment fragment, int container, boolean addtoBackstack, boolean replcae) {
        FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, 0, 0, R.animator.slide_down);
        if (replcae) {
            transaction.replace(container, fragment);
        } else {
            transaction.add(container, fragment);
        }
        if (addtoBackstack)
            transaction.addToBackStack(null);

        try {
            transaction.commit();
            hideKeyboard(((Activity) mContext).getCurrentFocus(), mContext);
        } catch (Exception e) {

        }
    }

    public static void hideKeyboard(View view, Context mContext) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
