/**
 * 
 */
package com.t3h.project.easygo.fragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.PlaceDataSource;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class PlacesFragment extends Fragment implements OnClickListener {
	View v;
	private GoogleMap mMap;
	private MarkerOptions markerOptions;
	private UiSettings mUiSettings;
	private Location location;
	private LocationManager locationManager;
	private CameraPosition cameraPosition;
	private LatLng latLng;
	private AlertDialog.Builder alertDialog;
	private EditText editTextPlace;
	private String addr, placeName;
	private Button btnFind;
	private PlaceDataSource placeDataSource;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.places_layout, null);
		editTextPlace = (EditText) v.findViewById(R.id.editTextPlace);
		btnFind = (Button) v.findViewById(R.id.btnFind);
		btnFind.setOnClickListener(this);

		placeDataSource = new PlaceDataSource(getActivity());
		placeDataSource.open();

		if (isInternetOn() && isLocationServiceOn()) {
			String context = Context.LOCATION_SERVICE;
			locationManager = (LocationManager) getActivity().getSystemService(
					context);
			String provider = LocationManager.NETWORK_PROVIDER;
			location = locationManager.getLastKnownLocation(provider);
			
			Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
			try {
				List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				if (addresses != null) {
					Address returnedAddress = addresses.get(0);
					String addressText = String.format(
							"%s, %s",
							returnedAddress.getMaxAddressLineIndex() > 0 ? returnedAddress
									.getAddressLine(0) : "", returnedAddress
									.getCountryName());

					placeName = addressText;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			latLng = new LatLng(location.getLatitude(), location.getLongitude());
			cameraPosition = new CameraPosition.Builder().target(latLng)
					.zoom(17).bearing(320).tilt(30).build();

			setUpMapIfNeeded();
			
			editTextPlace.setText(placeName);
		} else {
			if (!isInternetOn()) {
				onCreateDialog(1);
			} else if (!isLocationServiceOn()) {
				onCreateDialog(2);
			}
		}

		return v;
	}

	private void setUpMapIfNeeded() {
		// TODO Auto-generated method stub
		if (mMap == null) {
			mMap = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// TODO Auto-generated method stub
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		Marker marker = mMap.addMarker(new MarkerOptions()
				.title("You are here!").position(latLng)
				.snippet("Hello From EasyGo"));
		marker.showInfoWindow();

		mMap.setMyLocationEnabled(true);

		mUiSettings = mMap.getUiSettings();
		mUiSettings.setZoomControlsEnabled(true);
		mUiSettings.setCompassEnabled(true);
		mUiSettings.setMyLocationButtonEnabled(true);
		mUiSettings.setScrollGesturesEnabled(true);
		mUiSettings.setZoomGesturesEnabled(true);
		mUiSettings.setTiltGesturesEnabled(true);
		mUiSettings.setRotateGesturesEnabled(true);
	}

	public boolean isInternetOn() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null)
			return false;
		State network = info.getState();
		return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
	}

	public boolean isLocationServiceOn() {
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// GPS enabled
			return true;
		}

		else {
			// GPS disabled
			return false;
		}
	}

	public void onCreateDialog(int i) {
		alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Sorry");
		if (i == 1) {
			alertDialog.setMessage("Please turn on internet connection!");
			alertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							startActivity(new Intent(Settings.ACTION_SETTINGS));
						}
					});
		} else {
			alertDialog.setMessage("Please turn on your location service!");
			alertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							startActivity(new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS));
						}
					});
		}

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		alertDialog.create().show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnFind:
			addr = editTextPlace.getText().toString();
			if (addr != null && !addr.equals("")) {
				new GeocoderTask().execute(addr);
			}
			break;

		default:
			break;
		}
	}

	// An AsyncTask class for accessing the GeoCoding Web Service
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getActivity());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			if (addresses == null || addresses.size() == 0) {
				Toast.makeText(getActivity(), "No Location found",
						Toast.LENGTH_SHORT).show();
			}

			// Clears all the existing markers on the map
			mMap.clear();

			// Adding Markers on Google Map for each matching address
			for (int i = 0; i < addresses.size(); i++) {

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				latLng = new LatLng(address.getLatitude(),
						address.getLongitude());

				String addressText = String.format(
						"%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address
								.getCountryName());

				placeName = addressText;

				markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title(addressText);

				mMap.addMarker(markerOptions);

				// Locate the first location
				if (i == 0)
					mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		menu.add("OK").setIcon(R.drawable.check)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("OK")) {
			if (placeDataSource.insert(placeName) > 0) {
				Toast.makeText(getActivity(), "Marked", Toast.LENGTH_SHORT)
						.show();
				getActivity().finish();				
			} else {
				Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT)
						.show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		placeDataSource.open();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		placeDataSource.close();
	}
}
