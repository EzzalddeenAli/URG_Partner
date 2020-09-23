package com.urg.partner.ui.activity.instant_ride;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.urg.partner.R;
import com.urg.partner.base.BaseActivity;
import com.urg.partner.common.Constants;
import com.urg.partner.common.RecyclerItemClickListener;
import com.urg.partner.common.SharedHelper;
import com.urg.partner.data.network.model.EstimateFare;
import com.urg.partner.data.network.model.TripResponse;
//import com.rideon.partner.ui.adapter.PlacesAutoCompleteAdapter;
import com.urg.partner.ui.adapter.PlacesAutoCompleteAdapter;
import com.urg.partner.ui.countrypicker.Country;
import com.urg.partner.ui.countrypicker.CountryPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.urg.partner.MvpApplication.DEFAULT_ZOOM;
import static com.urg.partner.MvpApplication.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.urg.partner.MvpApplication.mLastKnownLocation;

public class InstantRideActivity extends BaseActivity
        implements GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        InstantRideIView {

    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));
    private final LatLng mDefaultLocation = new LatLng(-25.2744, 133.7751);
    protected GoogleApiClient mGoogleApiClient;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etDestination)
    EditText etDestination;
    @BindView(R.id.llPhoneNumberContainer)
    LinearLayout llPhoneNumberContainer;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.rvLocation)
    RecyclerView rvLocation;
    @BindView(R.id.cvLocationsContainer)
    CardView cvLocationsContainer;
    @BindView(R.id.countryImage)
    ImageView countryImage;
    @BindView(R.id.countryNumber)
    TextView tvCountryCode;

    private boolean isEnableIdle = false;
    private boolean canShowKeyboard, mLocationPermission, canEditSourceAddress = true;

    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocation;
    private BottomSheetBehavior mBottomSheetBehavior;
    String countryCode = "+593";
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    String countryFlag = "EC";
    CountryPicker mCountryPicker;
    int AUTOCOMPLETE_REQUEST_CODE=0101;
    private InstantRidePresenter<InstantRideActivity> presenter = new InstantRidePresenter<>();
    private Map<String, Object> instantRide;

    @Override

    public int getLayoutId() {
        return R.layout.activity_instant_ride;
    }

    @Override
    public void initView() {
        buildGoogleApiClient();
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.instant_ride));
        String apiKey = getString(R.string.google_map_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        instantRide = new HashMap<>();
        instantRide.put("service_type", SharedHelper.getIntKey(this, Constants.SharedPref.SERVICE_TYPE));

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            mGoogleMap = googleMap;
            try {
                mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            getLocationPermission();
            updateLocationUI();
            getDeviceLocation();
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(cvLocationsContainer);

       mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this, R.layout.list_item_location, mGoogleApiClient, BOUNDS_INDIA,placesClient);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rvLocation.setLayoutManager(mLinearLayoutManager);
       rvLocation.setAdapter(mAutoCompleteAdapter);

        /*etDestination.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (canShowKeyboard) hideKeyboard();
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    rvLocation.setVisibility(View.VISIBLE);
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
                    if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (!mGoogleApiClient.isConnected()) Log.e("ERROR", "API_NOT_CONNECTED");
                if (s.toString().equals("")) rvLocation.setVisibility(View.GONE);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });*/

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) hideKeyboard();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etPhoneNumber.setOnTouchListener((arg0, arg1) -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            rvLocation.setVisibility(View.GONE);
            return false;
        });

        /*etDestination.setOnTouchListener((arg0, arg1) -> {
            canShowKeyboard = false;

            return false;
        });*/

        rvLocation.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
                    if (mAutoCompleteAdapter.getItemCount() == 0) return;
                    final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mAutoCompleteAdapter.getItem(position);
                    final String placeId = String.valueOf(item.placeId);
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.ADDRESS);

            FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                LatLng latLng = place.getLatLng();
                canShowKeyboard = true;
                setLocationText(String.valueOf(item.address), latLng);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                rvLocation.setVisibility(View.GONE);

            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    // Handle error with given status code.
                    Log.e("TAG", "Place not found: " + exception.getMessage());
                }
            });



                })
        );

        setCountryList();
    }

    private void setLocationText(@NonNull String address, @NonNull LatLng latLng) {
        canShowKeyboard = true;
        etDestination.setText(address);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (canEditSourceAddress) {
            instantRide.put("s_latitude", latLng.latitude);
            instantRide.put("s_longitude", latLng.longitude);
            instantRide.put("s_address", address);
            canEditSourceAddress = false;
        }
        instantRide.put("d_latitude", latLng.latitude);
        instantRide.put("d_longitude", latLng.longitude);
        instantRide.put("d_address", address);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting())
            mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onCameraIdle() {
        try {
            CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
            if (isEnableIdle) {
                String address = getAddress(cameraPosition.target);
                System.out.println("onCameraIdle " + address);
                hideKeyboard();
                setLocationText(address, cameraPosition.target);
                rvLocation.setVisibility(View.GONE);
            }
            isEnableIdle = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
            hideKeyboard();
            setLocationText(getAddress(cameraPosition.target), cameraPosition.target);
            rvLocation.setVisibility(View.GONE);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraMove() {

    }

    void getDeviceLocation() {
        try {
            if (mLocationPermission) {
                Task<Location> locationResult = mFusedLocation.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastKnownLocation = task.getResult();
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        instantRide.put("s_latitude", mLastKnownLocation.getLatitude());
                        instantRide.put("s_longitude", mLastKnownLocation.getLongitude());
                        Geocoder gCoder = new Geocoder(InstantRideActivity.this);

                        try {
                            List<Address> addresses = null;
                            addresses = gCoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);
                            if (addresses != null && addresses.size() > 0) {
                                // Toast.makeText(myContext, "country: " + addresses.get(0).getCountryName(), Toast.LENGTH_LONG).show();
                                Log.d("TAG","Current Address"+addresses.get(0).getAddressLine(0));
                                instantRide.put("s_address",addresses.get(0).getAddressLine(0));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mLocationPermission = true;
        else ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) return;
        try {
            if (mLocationPermission) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.setOnCameraMoveListener(this);
                mGoogleMap.setOnCameraIdleListener(this);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermission = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermission = true;
                    updateLocationUI();
                    getDeviceLocation();
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(this, "API_NOT_CONNECTED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_pick_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (validate()) {
                    showLoading();
                    presenter.estimateFare(instantRide);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validate() {
        if (etPhoneNumber.getText().toString().length() > 0) {
            instantRide.put("mobile", etPhoneNumber.getText().toString());
            instantRide.put("country_code", tvCountryCode.getText().toString());
        } else {
            Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
            return false;
        }

        if (etDestination.getText().toString().length() > 3)
            instantRide.put("d_address", etDestination.getText().toString());
        else {
            Toasty.error(this, getString(R.string.enter_destination), Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    @OnClick({R.id.ivResetDest, R.id.qr_scan,R.id.etDestination})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivResetDest:
                etDestination.requestFocus();
                etDestination.setText(null);
                break;

            case R.id.qr_scan:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setPrompt("Scan a QRcode");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                break;
            case R.id.etDestination:
                getAutoComplete("SELECT_SOURCE");
                break;



        }
    }
    private void getAutoComplete(String status){
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS);

        if (!TextUtils.isEmpty(status) && status.equalsIgnoreCase("SELECT_SOURCE")) {
            AUTOCOMPLETE_REQUEST_CODE = 1;
        }

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE);

    }
    @Override
    public void onSuccess(EstimateFare estimateFare) {
        hideLoading();
        showConfirmationDialog(estimateFare.getEstimatedFare(), instantRide);
    }

    @Override
    public void onSuccess(TripResponse response) {
        hideLoading();
        mDialog.dismiss();
        finish();
    }

    private AlertDialog mDialog;

    private void showConfirmationDialog(double estimatedFare, Map<String, Object> params) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_instant_ride, null);

        TextView tvPickUp = view.findViewById(R.id.tvPickUp);
        TextView tvDrop = view.findViewById(R.id.tvDrop);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvFare = view.findViewById(R.id.tvFare);

        tvPickUp.setText(Objects.requireNonNull(params.get("s_address")).toString());
        tvDrop.setText(Objects.requireNonNull(params.get("d_address")).toString());
        tvPhone.setText(Objects.requireNonNull(params.get("mobile")).toString());
        tvFare.setText(String.valueOf(estimatedFare));

        builder.setView(view);
        mDialog = builder.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view.findViewById(R.id.tvSubmit).setOnClickListener(view1 -> {
            showLoading();
            presenter.requestInstantRide(params);
        });

        view.findViewById(R.id.tvCancel).setOnClickListener(view1 -> mDialog.dismiss());
        mDialog.show();
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        if (throwable != null)
            onErrorBase(throwable);
    }

    private void setCountryList() {
        mCountryPicker = CountryPicker.newInstance("Select Country");
        List<Country> countryList = Country.getAllCountries();
        Collections.sort(countryList, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        mCountryPicker.setCountriesList(countryList);

        setListener();
    }

    private void setListener() {
        mCountryPicker.setListener((name, code, dialCode, flagDrawableResID) -> {
            tvCountryCode.setText(dialCode);
            countryCode = dialCode;
            countryImage.setImageResource(flagDrawableResID);
            mCountryPicker.dismiss();
        });

        countryImage.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        tvCountryCode.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Country country = getDeviceCountry(InstantRideActivity.this);
        countryImage.setImageResource(R.drawable.flag_ec);
        tvCountryCode.setText("+593");
        countryCode = "+593";//country.getDialCode();
        countryFlag = "EC";//country.getCode();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null)
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            else try {
                String scanResult = result.getContents().trim();
                System.out.println("RRR scanResult = " + scanResult);
                JSONObject jObject = new JSONObject(scanResult);
                etPhoneNumber.setText(jObject.optString("phone_number"));
                tvCountryCode.setText(TextUtils.isEmpty(jObject.optString("country_code"))
                        ? countryCode : jObject.optString("country_code"));
            } catch (JSONException e) {
                e.printStackTrace();
                tvCountryCode.setText(countryCode);
            }

        } else super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

               LatLng latLng = place.getLatLng();
                String mStringLatitude = String.valueOf(latLng.latitude);
                String mStringLongitude = String.valueOf(latLng.longitude);
                Log.i("TAG", "Lat Long: " + mStringLatitude + ", " + mStringLongitude);
                etDestination.setText(place.getAddress());
                Log.i("TAG", "Place: " + place.getAddress());
                instantRide.put("d_latitude", latLng.latitude);
                instantRide.put("d_longitude", latLng.longitude);
               //instantRide.put("s_address", place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}