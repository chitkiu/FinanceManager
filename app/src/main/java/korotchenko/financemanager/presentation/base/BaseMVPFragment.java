package korotchenko.financemanager.presentation.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
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

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            isFirstAttach = savedInstanceState.getBoolean(IS_FIRST_ATTACH);
        }
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        getViewPresenter().attach(this);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        getViewPresenter().onViewAttach(isFirstAttach);
        isFirstAttach = false;
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FIRST_ATTACH, isFirstAttach);
    }

    @CallSuper
    @Override
    public void onStop() {
        getViewPresenter().detach();
        super.onStop();
    }

    private static String IS_FIRST_ATTACH = "is_first_attach";
}
