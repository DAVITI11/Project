package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileOutputStream;
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
            String model = Model.getText().toString().trim();
            String year = Year.getText().toString().trim();
            String price = Price.getText().toString().trim();
            String desc = Description.getText().toString().trim();

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

            String savedPath = saveImageToInternalStorage(selectedImageUri);

            if (savedPath == null) {
                Toast.makeText(requireContext(), "Error saving image!", Toast.LENGTH_SHORT).show();
                return;
            }

            ((MainActivity) getActivity()).AddCarInfo(
                    carType, model, year, price, desc, savedPath
            );
            AddPhoto.clearColorFilter();
            spinner.setSelection(0);
            Model.setText("Model");
            Year.setText("Year");
            Price.setText("Price");
            Description.setText("Description");
            Toast.makeText(requireContext(), "Car Added!", Toast.LENGTH_SHORT).show();
        });
    }

    private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);

            String fileName = "car_" + System.currentTimeMillis() + ".jpg";
            File file = new File(requireContext().getFilesDir(), fileName);

            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fos.close();
            inputStream.close();

            return file.getAbsolutePath();  // path saved in database
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            } else openGallery();
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            openGallery();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }
}
