package com.mohammadalmomani.modevlib.gellary;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mohammadalmomani.modevlib.databinding.FragmentImageBinding;
import com.mohammadalmomani.modevlib.databinding.FragmentVideoBinding;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class GalleryVideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private static Object media; // يمكن يكون URL أو File
    private ExoPlayer player;

    public GalleryVideoFragment() {}

    public static GalleryVideoFragment builder(Object mediaPath) {
        GalleryVideoFragment fragment = new GalleryVideoFragment();
        GalleryVideoFragment.media = mediaPath;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);

        initializePlayer(media);

        return binding.getRoot();
    }

    private void initializePlayer(Object mediaPath) {
        player = new ExoPlayer.Builder(requireContext()).build();
        PlayerView playerView = binding.ivZoomImage; // استخدم نفس ImageView كـ PlayerView
        playerView.setPlayer(player);

        Uri uri;
        if (mediaPath instanceof String) {
            uri = Uri.parse((String) mediaPath);
        } else {
            // إذا كان File
            uri = Uri.parse(mediaPath.toString());
        }

        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);
        player.prepare();
       // player.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
