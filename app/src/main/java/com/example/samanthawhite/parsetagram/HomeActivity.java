package com.example.samanthawhite.parsetagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samanthawhite.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

  public final String APP_TAG = "MyCustomApp";
  public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
  public String photoFileName = "photo.jpg";
  File photoFile;
  public final String TAG = "HomeActivity";

  static final int REQUEST_IMAGE_CAPTURE = 1;

  private static final String imagePath = "/...";
  private EditText descriptionInput;
  private Button createButton;
  private Button refreshButton;
  private Button pictureButton;
  // ImageView mImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    descriptionInput = findViewById(R.id.etDescription);
    createButton = findViewById(R.id.btnCreate);
    refreshButton = findViewById(R.id.btnRefresh);
    pictureButton = findViewById(R.id.takepic_btn);
    // mImageView = findViewById(R.id.picture);
    //        View view = findViewById(R.id.home_activity);

    // onLaunchCamera(view);

    //        if(isStoragePermissionGranted()){
    //            onLaunchCamera(view);
    //
    //        }

    createButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            final String description = descriptionInput.getText().toString();
            final ParseUser user = ParseUser.getCurrentUser();

            // ask user to select file or take a photo!
            //                final File file = new File(imagePath);
            //                //create parse file
            //                final ParseFile parseFile = new ParseFile(file);

            // IS THIS SUPPOSED TO RETURN A FILE OR A URI??
            File photoFile = getPhotoFile(photoFileName);

            // IT SEEMS LIKE PARSE FILES HAS TO TAKE IN BYTE?
            // https://docs.parseplatform.org/android/guide/#files
            ParseFile parseFile = new ParseFile(photoFile);

            createPost(description, parseFile, user);
          }
        });

    // set up an onclick listener
    refreshButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            loadTopPosts();
          }
        });

    pictureButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            onLaunchCamera(view);
          }
        });
  }

  public void onLaunchCamera(View view) {
    // create Intent to take a picture and return control to the calling application
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Create a File reference to access to future access
    photoFile = getPhotoFile(photoFileName);

    // wrap File object into a content provider
    // required for API >= 24
    // See
    // https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
    Uri fileProvider =
        FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

    // If you call startActivityForResult() using an intent that no app can handle, your app will
    // crash.
    // So as long as the result is not null, it's safe to use the intent.
    if (intent.resolveActivity(getPackageManager()) != null) {
      // Start the image capture intent to take photo
      startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
  }

  // Returns the File for a photo stored on disk given the fileName
  public File getPhotoFile(String fileName) {
    // Get safe storage directory for photos
    // Use `getExternalFilesDir` on Context to access package-specific directories.
    // This way, we don't need to request external read/write runtime permissions.
    File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
      Log.d(APP_TAG, "failed to create directory");
    }

    // Return the file target for the photo based on filename
    File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

    return file;
  }

  public Uri getPhotoFileUri(String fileName) {
    // Get safe storage directory for photos
    // Use `getExternalFilesDir` on Context to access package-specific directories.
    // This way, we don't need to request external read/write runtime permissions.
    File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
      Log.d(APP_TAG, "failed to create directory");
    }

    // Return the file target for the photo based on filename
    File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
    Uri uri = Uri.fromFile(file);

    return uri;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        // by this point we have the camera photo on disk
        Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

        takenImage = BitmapScaler.scaleToFitWidth(takenImage, 100);
        // TODO RESIZE BITMAP, see section below but first understand how URI is used
        // Uri takenPhotoUri = getPhotoFileUri(photoFileName);

        // Configure byte output stream
        try {
          ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          // Compress the image further
          takenImage.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
          // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
          File resizedFile = new File(photoFile.getAbsolutePath());
          resizedFile.createNewFile();
          FileOutputStream fos = null;
          fos = new FileOutputStream(resizedFile);
          fos.write(bytes.toByteArray());
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        // Write the bytes of the bitmap to file

        // Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 100, true);

        // Load the taken image into a preview
        ImageView ivPreview = (ImageView) findViewById(R.id.picture);
        ivPreview.setImageBitmap(takenImage);
      } else { // Result was a failure
        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
      }
    }
  }

  public boolean isStoragePermissionGranted() {
    if (Build.VERSION.SDK_INT >= 23) {
      if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
          == PackageManager.PERMISSION_GRANTED) {
        Log.v(TAG, "Permission is granted");
        return true;
      } else {

        Log.v(TAG, "Permission is revoked");
        ActivityCompat.requestPermissions(
            this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        return false;
      }
    } else { // permission is automatically granted on sdk<23 upon installation
      Log.v(TAG, "Permission is granted");
      return true;
    }
  }

  // create options menu to be able to logout
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  public void onLogout(MenuItem mi) {
    ParseUser.logOut();
    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser == null) {
      Log.i("homeactivity", "logged out");
      Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
      startActivity(intent);
    } else {
      Log.i("homeactivity", "not logged out");
    }
  }

  private void createPost(String description, ParseFile imageFile, ParseUser user) {
    final Post newPost = new Post();
    newPost.setDescription(description);
    newPost.setImage(imageFile);
    newPost.setUser(user);

    newPost.saveInBackground(
        new SaveCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.d("HomeActivity", "create post success");

            } else {
              e.printStackTrace();
            }
          }
        });
  }

  private void loadTopPosts() {
    final Post.Query postsQuery = new Post.Query();
    // modifying the query to make it more specific
    postsQuery.getTop().withUser();

    // make a query to make sure we can get the posts without users inflated
    postsQuery.findInBackground(
        new FindCallback<Post>() {
          @Override
          // this will find all of the posts in background thread
          public void done(List<Post> objects, ParseException e) {
            if (e == null) {
              for (int i = 0; i < objects.size(); i++) {
                Log.d(
                    "HomeActivity",
                    "Post["
                        + i
                        + "] = "
                        + objects.get(i).getDescription()
                        + "\nusername = "
                        + objects.get(i).getUser().getUsername());
              }
            } else {
              e.printStackTrace();
            }
          }
        });
  }
}
