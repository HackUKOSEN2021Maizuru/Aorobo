package colob.example.aorobo.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import colob.example.aorobo.R;
import colob.example.aorobo.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private Handler mHandler = new Handler();
    /** テキストオブジェクト */
    private Runnable updateText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        
        View root = binding.getRoot();


        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        loadImage(Glide.with(this), R.drawable.loading, getActivity().findViewById(R.id.gifStartView));
    }
    static void loadImage(RequestManager glide, int url, ImageView view) {
        glide.load(url).into(view);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        /*
        ImageButton imageButton = view.findViewById(R.id.app_start_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_title_to_home);
            }
        });
        */
        updateText = new Runnable() {
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_title_to_home);
            }
        };
        mHandler.postDelayed(updateText, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        binding = null;

    }
}