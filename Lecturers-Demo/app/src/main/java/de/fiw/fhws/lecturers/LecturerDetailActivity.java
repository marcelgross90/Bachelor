package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Map;

import de.marcelgross.lecturer_lib.generic.activity.ResourceDetailActivity;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkResponse;
import de.marcelgross.lecturer_lib.specific.customView.LecturerDetailView;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;

public class LecturerDetailActivity extends ResourceDetailActivity implements View.OnClickListener {

	@Override
	protected Intent getIntentForClose() {
		return new Intent(this, MainActivity.class);
	}

	@Override
	protected int getDeleteErrorMessage() {
		return R.string.lecturer_delete_error;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_lecturer_detail;
	}

	@Override
	protected void initializeView() {
		resourceDetailView = (LecturerDetailView) findViewById(R.id.detail_view);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
	}

	@Override
	protected int getEnterAnim() {
		return R.anim.fade_out;
	}

	@Override
	protected int getExitAnim() {
		return R.anim.fade_in;
	}

	@Override
	protected Intent getIntentForEdit() {
		return new Intent(LecturerDetailActivity.this, LecturerActivity.class);
	}

	@Override
	protected Bundle prepareBundle() {
		Lecturer lecturer = (Lecturer) currentResource;
		Bundle bundle = new Bundle();
		bundle.putString("name", lecturer.getFirstName() + " " + lecturer.getLastName());
		return bundle;
	}

	@Override
	protected String extractTitleFromIntent(Intent intent) {
		return getIntent().getExtras().getString("fullName");
	}

	@Override
	protected NetworkCallback getCallback() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				currentResource = genson.deserialize(response.getResponseReader(), Lecturer.class);
				Map<String, Link> linkHeader = response.getLinkHeader();
				deleteLink = linkHeader.get(LecturerDetailActivity.this.getString(R.string.rel_type_delete_lecturer));
				updateLink = linkHeader.get(LecturerDetailActivity.this.getString(R.string.rel_type_update_lecturer));

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setUp((Lecturer) currentResource);
					}
				});
			}
		};
	}

	private void setUp(Lecturer lecturer) {
		invalidateOptionsMenu();
		((LecturerDetailView) resourceDetailView).setUpView(lecturer, this);
	}

	@Override
	public void onClick(View view) {
		Lecturer currentLecturer = (Lecturer) currentResource;
		switch (view.getId()) {
			case R.id.tvEmailValue:
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + currentLecturer.getEmail()));
				startActivity(Intent.createChooser(intent, "Send Email"));
				break;

			case R.id.tvPhoneValue:
				Intent intent1 = new Intent(Intent.ACTION_DIAL);
				String p = "tel:" + currentLecturer.getPhone();
				intent1.setData(Uri.parse(p));
				startActivity(intent1);
				break;

			case R.id.tvWebsiteValue:
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.setData(Uri.parse(currentLecturer.getUrlWelearn()));
				startActivity(intent2);
				break;

			case R.id.tvAddressValue:
				Uri location = Uri.parse("geo:0,0?q=" + currentLecturer.getAddress());
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
				mapIntent.setPackage("com.google.android.apps.maps");
				startActivity(mapIntent);
				break;

			case R.id.charges_btn:
				Intent intent3 = new Intent(LecturerDetailActivity.this, ChargeActivity.class);
				intent3.putExtra("name", currentLecturer.getFirstName() + " " + currentLecturer.getLastName());
				intent3.putExtra("url", currentLecturer.getChargeUrl().getHref());
				intent3.putExtra("mediaType", currentLecturer.getChargeUrl().getType());
				startActivity(intent3);
				break;

			case R.id.welearn:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentLecturer.getUrlWelearn()));
				startActivity(browserIntent);
				break;
		}
	}
}