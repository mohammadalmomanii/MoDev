package com.mohammadalmomani.modevlib.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.SwipeRefreshViewBinding;

/**
 * RefrishFragment is a utility fragment designed to manage a swipe-to-refresh layout with a loading indicator.
 * It provides methods to show and hide a loading animation within an AppBarLayout.
 * <p>
 * ## How to Use:
 * <p>
 * ### 1. Initialize the Fragment:
 *    ```java
 *    RefrishFragment refreshFragment = new RefrishFragment();
 *    getSupportFragmentManager().beginTransaction()
 *        .replace(R.id.container, refreshFragment)
 *        .commit();
 *    ```
 * <p>
 * ### 2. Show the Loading Animation:
 *    ```java
 *    refreshFragment.showLoading();
 *    ```
 * <p>
 * ### 3. Hide the Loading Animation:
 *    ```java
 *    refreshFragment.hideLoading();
 *    ```
 * <p>
 * ### 4. Set a Custom GIF:
 *    ```java
 *    refreshFragment.setGif(R.drawable.custom_loading_gif);
 *    ```
 * <p>
 * ## XML Example:
 * ```xml
 * <androidx.coordinatorlayout.widget.CoordinatorLayout
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content">
 * <p>
 *     <com.google.android.material.appbar.AppBarLayout
 *         android:id="@+id/appBar"
 *         android:layout_width="match_parent"
 *         android:layout_height="145dp"
 *         app:expanded="false">
 * <p>
 *         <com.google.android.material.appbar.CollapsingToolbarLayout
 *             android:layout_width="match_parent"
 *             android:layout_height="match_parent"
 *             app:layout_scrollFlags="scroll|enterAlways|exitUntilCollapsed">
 * <p>
 *             <ImageView
 *                 android:id="@+id/imageView"
 *                 android:layout_width="match_parent"
 *                 android:layout_height="match_parent"
 *                 tools:src="@tools:sample/avatars" />
 * <p>
 *         </com.google.android.material.appbar.CollapsingToolbarLayout>
 * <p>
 *     </com.google.android.material.appbar.AppBarLayout>
 * <p>
 * </androidx.coordinatorlayout.widget.CoordinatorLayout>
 * ```
 * <p>
 * ## Notes:
 * - Call `showLoading()` to disable user interactions and expand the AppBarLayout.
 * - Call `hideLoading()` to re-enable user interactions and collapse the AppBarLayout.
 * - The default loading GIF is `R.drawable.gif_preloader`, but you can set a custom one.
 */

@Deprecated
public class RefrishFragment extends Fragment {

    private static SwipeRefreshViewBinding binding;


    public RefrishFragment() {
        // Required empty public constructor
    }

    public RefrishFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.swipe_refresh_view, container, false);

        Glide.with(this).load(R.drawable.gif_preloader).into(binding.imageView);


        return binding.getRoot();
    }


    public RefrishFragment setGif(int gif) {
        Glide.with(this).load(R.drawable.gif_preloader).into(binding.imageView);
        return this;
    }

     public void showLoading() {
         requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
         binding.appBar.setExpanded(true, true);
     }

     public void hideLoading() {
            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.appBar.setExpanded(false, true);
    }
}