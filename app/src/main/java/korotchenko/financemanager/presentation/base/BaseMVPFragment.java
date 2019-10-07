package korotchenko.financemanager.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dagger.android.support.AndroidSupportInjection;


/**
 * Wrote this part of code using Java because I have trouble with generic in Kotlin :c
 */
public abstract class BaseMVPFragment<P extends BasePresenter> extends Fragment implements BaseView {

    protected abstract P getViewPresenter();

    private Boolean isFirstAttach = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        getViewPresenter().attach(this);
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            isFirstAttach = savedInstanceState.getBoolean(IS_FIRST_ATTACH);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewPresenter().onViewAttach(isFirstAttach);
        isFirstAttach = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FIRST_ATTACH, isFirstAttach);
    }

    @Override
    public void onPause() {
        getViewPresenter().detach();
        super.onPause();
    }

    private static String IS_FIRST_ATTACH = "is_first_attach";
}
