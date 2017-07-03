package com.avpc.avpcmobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.activities.base.BaseActivity;
import com.avpc.avpcmobile.fragments.MemberDetail;
import com.avpc.avpcmobile.fragments.MembersRecyclerViewFragment;
import com.avpc.avpcmobile.fragments.NewIncidentFragment;
import com.avpc.avpcmobile.fragments.MapVoluntarios;
import com.avpc.avpcmobile.fragments.NewsListFragment;
import com.avpc.avpcmobile.fragments.ServicesListFragment;
import com.avpc.avpcmobile.fragments.VehiclesListFragment;

import com.avpc.service.UpdateMemberLocationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainMenuActivity extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        MembersRecyclerViewFragment.OnMemberListListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        NewIncidentFragment.OnNewIncidentListener{

    private static final String MAIN_ACTIVITY_PICTURE = "MsgLogErrorPicture";
    private FirebaseUser mUser;
    private Fragment mMapVoluntarios;
    private ActionBarDrawerToggle mToggle;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private final int REQUEST_LOCATION = 10;
    static final int REQUEST_IMAGE_CAPTURE = 20;
    private NewIncidentFragment mNewIncidentFragment;
    private Uri mUriNewIncidentPhoto;
    private static final String SAVE_INSTANCE_PHOTO = "saveInstancePhoto";
    private String mCurrentPhotoPath;

    private FloatingActionButton fab;
    private SwitchCompat switchTrackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mToggle);
        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //getSupportActionBar().show();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initComponents();
        initNavigationViewComponents(navigationView);
        setComponentListeners();

        buildGoogleApiClient();
    }

    private void initNavigationViewComponents(NavigationView navigationView) {
        switchTrackPosition = (SwitchCompat) navigationView.getHeaderView(0).findViewById(R.id.menu_track_position_switch);
    }

    private void initComponents() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void setComponentListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewServiceActivity();
            }
        });

        switchTrackPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchCompat switchCompat = (SwitchCompat) v;
                if (switchCompat.isChecked()) {
                    startUpdatingMemberLocation();
                } else {
                    stopUpdatingMemberLocation();
                }
            }
        });

    }

    private void startUpdatingMemberLocation() {
        Intent intent = new Intent(MainMenuActivity.this, UpdateMemberLocationService.class);
        startService(intent);
    }

    private void  stopUpdatingMemberLocation() {
        Intent intent = new Intent(MainMenuActivity.this, UpdateMemberLocationService.class);
        stopService(intent);
    }

    private void openNewServiceActivity() {
        Intent newServiceIntent = new Intent(MainMenuActivity.this, NewServiceActivity.class);
        startActivity(newServiceIntent);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    public void redirectToLogin() {
        Intent loginIntent = new Intent(MainMenuActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mUriNewIncidentPhoto != null){
            outState.putString(SAVE_INSTANCE_PHOTO, mUriNewIncidentPhoto.toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SAVE_INSTANCE_PHOTO)){
            mUriNewIncidentPhoto = Uri.parse(savedInstanceState.getString(SAVE_INSTANCE_PHOTO));
            mNewIncidentFragment.receivePhotoUri(mUriNewIncidentPhoto);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        return selectItemMenu(id);
    }

    private boolean selectItemMenu(int id) {
        //mNavigationPosition = id;
        Fragment fragment = null;
        if (id == R.id.nav_voluntarios) {
            fragment = createFragment(MembersRecyclerViewFragment.class, fragment);
        } else if (id == R.id.nav_mapa) {
            mMapVoluntarios = createFragment(MapVoluntarios.class, mMapVoluntarios);
            fragment = mMapVoluntarios;
        } else if (id == R.id.nav_avisos) {
            fragment = createFragment(ServicesListFragment.class, fragment);
        } else if (id == R.id.nav_noticias) {
            fragment = createFragment(NewsListFragment.class, fragment);
        } else if (id == R.id.nav_vehicles) {
            fragment = createFragment(VehiclesListFragment.class, fragment);
        } else if (id == R.id.nav_logout) {
            logout();
            return true;
        }

        switchFragments(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        new AlertDialog.Builder(MainMenuActivity.this)
                .setTitle(R.string.app_name)
                .setMessage("Do you really want to logout from the application?")
                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                })
                .create()
                .show();
    }

    private Fragment createFragment(Class fragmentClass, Fragment fragment) {
        //Fragment fragment;
        try {
            if (fragment == null)
                fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            fragment = new Fragment();
        }
        return fragment;
    }

    @Override
    public void onMemberSelected(long memberId) {
        MemberDetail memberDetail = MemberDetail.newInstance(memberId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_content, memberDetail);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.commit();

    }

    private void showNewIncidentFragment() {
        NewIncidentFragment newIncidentFragment = new NewIncidentFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mNewIncidentFragment = newIncidentFragment;
        transaction.replace(R.id.fragment_content, newIncidentFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.commit();

        ViewCompat.animate(fab)
                .translationY(-100)
                .setDuration(300)
                .start();
    }

    private void switchFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_content, fragment);
        //transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION);
            return;

        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LatLng latLng = new LatLng(
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
            //setUserLocation(latLng);
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void showCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            dispatchTakePictureIntent();
        }

        //CameraManager cm =

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mNewIncidentFragment.receivePhotoBitmap(imageBitmap);
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error saving the picture", Toast.LENGTH_LONG);
                Log.e(MAIN_ACTIVITY_PICTURE, "Error:" + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.avpc.avpcmobile.fileprovider",
                        photoFile);
                mUriNewIncidentPhoto = photoURI;
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
