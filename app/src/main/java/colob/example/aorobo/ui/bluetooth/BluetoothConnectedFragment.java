package colob.example.aorobo.ui.bluetooth;

import android.os.Bundle;
import android.view.View;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import colob.example.aorobo.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import colob.example.aorobo.databinding.FragmentBluetoothConnectedBinding;

public class BluetoothConnectedFragment extends Fragment{
    private FragmentBluetoothConnectedBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ImageView matchImage = getActivity().findViewById(R.id.gifView);
        Glide.with(this).load(R.raw.colob_roll).into(matchImage);

        binding = FragmentBluetoothConnectedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
