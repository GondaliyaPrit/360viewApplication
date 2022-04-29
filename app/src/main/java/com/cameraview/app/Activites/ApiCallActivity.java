package com.cameraview.app.Activites;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cameraview.app.Interfaces.ApiClient;
import com.cameraview.app.Utils.Dialog;
import com.cameraview.app.databinding.ActivityApiCallBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiCallActivity extends AppCompatActivity {
    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    String[] cat = {"Gallery", "Camera"};
    ActivityApiCallBinding binding;
    AlertDialog.Builder dialog;
    private Bitmap photo;
    String imagePath;
    private Uri fileUri;
    private static final String tag = "Commons";
    List<MultipartBody.Part> listofimage;
    List<MultipartBody.Part> removebgofimage;
    List<String> imagebase64list;
    List<Integer> foregrounflist;
    WebSettings webSettings;
    //    String array = "" ;
    List<String> array;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApiCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkpermission();
        dialog = new AlertDialog.Builder(this);

        webSettings = binding.webview.getSettings();
        array = new ArrayList<>();
        array.add("Sanket");
        array.add("butani");
        array.add("prit");

        binding.getfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listofimage = new ArrayList<>();
                removebgofimage = new ArrayList<>();
                imagebase64list = new ArrayList<>();
                foregrounflist = new ArrayList<>();

                dialog.setTitle("Select Your Photo");
                dialog.setItems(cat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Gallary();
                        } else if (which == 1) {
                            Camera();
                        }
                    }
                });
                dialog.show();
            }
        });

        binding.removebg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.showdialog(ApiCallActivity.this, "Please wait");
                callbgremoveapi(0, removebgofimage);
            }
        });

        binding.printarray.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                StringJoiner sj = new StringJoiner(",");
                for (String st : array) {
                    sj.add(st);
                }
                Log.e("basearray", "" + sj.toString());
            }
        });

        binding.callapi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Log.e("filearray", "" + listofimage);
                Dialog.showdialog(ApiCallActivity.this, "Please wait");
                StringJoiner sj = new StringJoiner(",");
                for (String st : imagebase64list) {
                    sj.add(st);
                }
                Call<ResponseBody> call = ApiClient.LOCALAPI.uploadimage(sj.toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Dialog.dissmissdialog();
                        try {
                            String json = response.body().string();
                            Log.e("Response", "" + json);
                            JSONObject jsonObject = new JSONObject(json);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String url = jsonObject.getString("link");
                                String message = jsonObject.getString("message");
                                binding.webview.loadUrl(url);
                                webSettings.setJavaScriptEnabled(true);
                                Toast.makeText(ApiCallActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Dialog.dissmissdialog();
                        Log.e("Error", t.getMessage());
                    }
                });
            }
        });
    }

    private void Camera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1);

    }

    private void callbgremoveapi(int index, List<MultipartBody.Part> removebgofimage) {
        if (index == removebgofimage.size()) {
            Dialog.dissmissdialog();
            Toast.makeText(this, "Remove bg Sucessfully...", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody format = RequestBody.create(MediaType.parse("text/plain"), "jpg");
        Log.e("image", "" + removebgofimage.get(index));
        Call<ResponseBody> call = ApiClient.API.getimage(removebgofimage.get(index), format);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int curindex = index;
                try {
                    if (response.code() == 200) {
                        String json = response.body().string();
                        Log.e("Response", "" + json);
                        Log.e("Code", "" + response.code());
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject data = jsonObject.getJSONObject("data");
                        String result64 = data.getString("result_b64");
                        Integer foreground_top = data.getInt("foreground_top");
                        imagebase64list.add(result64);
                        // foregrounflist.add(foreground_top);
                        if (response.code() == 200) {
                            callbgremoveapi(index + 1, removebgofimage);
                        }
                    } else {
                        callbgremoveapi(curindex, removebgofimage);
//                        Dialog.dissmissdialog();
//                        Log.e("reponsecode",""+response.code());
//                        Toast.makeText(ApiCallActivity.this, "Not get"+index, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void Gallary() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                FromCamera(data);
            }
            if (requestCode == 0) {
//                FromGallery(data);
                ClipData cd = data.getClipData();
                if (cd == null) {
                    FromGallery(data.getData());
                } else {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        FromGallery(data.getClipData().getItemAt(i).getUri());
                    }
                }

            }
        }
    }

    private void FromGallery(Uri uri) {
        if (uri != null) {
            try {
                String fullPath = getPath(uri, this);
                imagePath = fullPath;
                fileUri = uri;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("uri", "uri is null");
        }


        File imageFilePath = new File(imagePath);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        imageFilePath
                );
        MultipartBody.Part profileImageBody = MultipartBody.Part.createFormData("media[]", imageFilePath.getName(), requestFile);
        MultipartBody.Part removebgImageBody = MultipartBody.Part.createFormData("image_file", imageFilePath.getName(), requestFile);
        listofimage.add(profileImageBody);
        removebgofimage.add(removebgImageBody);
    }

//    public static File changeExtension(File f, String newExtension) {
//        int i = f.getName().lastIndexOf('.');
//        Date currentTime = Calendar.getInstance().getTime();
//        String name = f.getName().substring(0,i);
//        return new File(f.getParent(), name + newExtension);
//    }

    private void FromCamera(Intent data) {
        Log.e("camera", "camera");
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePath = destination.getPath();
        fileUri = getImageUri(this, thumbnail);
    }


    public static String getPath(final Uri uri, Context context) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            return getForApi19(context, uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @TargetApi(19)
    private static String getForApi19(Context context, Uri uri) {
        Log.e(tag, "+++ API 19 URI :: " + uri);
        if (DocumentsContract.isDocumentUri(context, uri)) {
            Log.e(tag, "+++ Document URI");
            if (isExternalStorageDocument(uri)) {
                Log.e(tag, "+++ External Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    Log.e(tag, "+++ Primary External Document URI");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                Log.e(tag, "+++ Downloads External Document URI");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                Log.e(tag, "+++ Media Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    Log.e(tag, "+++ Image Media Document URI");
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    Log.e(tag, "+++ Video Media Document URI");
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    Log.e(tag, "+++ Audio Media Document URI");
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.e(tag, "+++ No DOCUMENT URI :: CONTENT ");
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.e(tag, "+++ No DOCUMENT URI :: FILE ");
            return uri.getPath();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void checkpermission() {
        for (int i = 0; i < perms.length; i++) {
            if (ContextCompat.checkSelfPermission(this, perms[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, perms, 51);
                break;
            }
        }
    }
}