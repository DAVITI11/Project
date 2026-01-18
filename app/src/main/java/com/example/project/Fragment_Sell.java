package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;

public class Fragment_Sell extends Fragment {

    Button SubmitBtn;
    ImageView AddPhoto;
    TextInputEditText Model, Year, Price, Description;

    private static final int REQUEST_PERMISSION = 100;
    private Uri selectedImageUri = null;

    ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    AddPhoto.setImageURI(selectedImageUri);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AddPhoto = view.findViewById(R.id.carImage);
        SubmitBtn = view.findViewById(R.id.SubmitBtn);
        Model = view.findViewById(R.id.Model);
        Year = view.findViewById(R.id.Year);
        Price = view.findViewById(R.id.Price);
        Description = view.findViewById(R.id.Description);

        Spinner spinner = view.findViewById(R.id.spinnerCars);

        String[] carsTp = {
                "Select Car Type", "Sedan", "SUV", "Coupe", "Pickup", "Sport", "Van"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                carsTp
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        AddPhoto.setOnClickListener(v -> checkPermission());

        SubmitBtn.setOnClickListener(v -> {
            String carType = spinner.getSelectedItem().toString();
            String model = Model.getText().toString();
            String year = Year.getText().toString();
            String price = Price.getText().toString();
            String desc = Description.getText().toString();

            if (carType.equals("Select Car Type") ||
                    model.isEmpty() ||
                    year.isEmpty() ||
                    price.isEmpty() ||
                    desc.isEmpty() ||
                    selectedImageUri == null) {

                Toast.makeText(requireContext(),
                        "Please fill all fields and select an image",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String base64Image = convertImageToBase64(selectedImageUri);

            if (base64Image.isEmpty()) {
                Toast.makeText(requireContext(), "Image error!", Toast.LENGTH_SHORT).show();
                return;
            }

            ((MainActivity) getActivity()).AddCarInfo(
                    carType, model, year, price, desc, base64Image
            );

            Toast.makeText(requireContext(), "Car Added!", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                openGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private String convertImageToBase64(Uri uri) {
        try {
            InputStream inputStream =
                    requireContext().getContentResolver().openInputStream(uri);

            byte[] bytes = inputStream.readAllBytes();
            return Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
