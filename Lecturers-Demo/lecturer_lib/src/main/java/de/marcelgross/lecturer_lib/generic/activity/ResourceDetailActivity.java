package de.marcelgross.lecturer_lib.generic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.owlike.genson.Genson;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.ResourceDetailView;
import de.marcelgross.lecturer_lib.generic.fragment.DeleteDialogFragment;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkClient;
import de.marcelgross.lecturer_lib.generic.network.NetworkRequest;


public abstract class ResourceDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener {

	protected abstract Intent getIntentForClose();

	protected abstract int getDeleteErrorMessage();

	protected abstract int getLayout();

	protected abstract void initializeView();

	protected abstract int getEnterAnim();

	protected abstract int getExitAnim();

	protected abstract Intent getIntentForEdit();

	protected abstract Bundle prepareBundle();

	protected abstract String extractTitleFromIntent(Intent intent);

	protected abstract NetworkCallback getCallback();

	protected ResourceDetailView resourceDetailView;
	protected final Genson genson = new Genson();
	protected Link deleteLink;
	protected Link updateLink;
	protected Toolbar toolbar;
	protected Resource currentResource;

	@Override
	public void onDialogClosed(boolean successfullyDeleted) {
		if (successfullyDeleted) {
			startActivity(getIntentForClose());
			finish();
		} else {
			Toast.makeText(this, getDeleteErrorMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());

		initializeView();

		setUpToolbar();

		loadLecturer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail_menu, menu);
		MenuItem deleteItem = menu.findItem(R.id.delete_item);
		MenuItem updateItem = menu.findItem(R.id.edit_item);
		deleteItem.setVisible(deleteLink != null);
		updateItem.setVisible(updateLink != null);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int i = item.getItemId();
		if (i == android.R.id.home) {
			onBackPressed();
			overridePendingTransition(getEnterAnim(), getExitAnim());
			return true;
		} else if (i == R.id.edit_item) {
			Intent intent = getIntentForEdit();
			intent.putExtra("url", updateLink.getHref());
			intent.putExtra("mediaType", updateLink.getType());
			startActivity(intent);
			return true;
		} else if (i == R.id.delete_item) {
			Bundle bundle = prepareBundle();
			bundle.putString("url", deleteLink.getHref());
			DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
			deleteDialogFragment.setArguments(bundle);
			deleteDialogFragment.show(getSupportFragmentManager(), null);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void setUpToolbar() {
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		setTitle(extractTitleFromIntent(getIntent()));
	}

	private void loadLecturer() {
		Intent intent = getIntent();
		String selfUrl = intent.getExtras().getString("selfUrl", "");
		String mediaType = intent.getExtras().getString("mediaType", "");

		NetworkClient client = new NetworkClient(this, new NetworkRequest().acceptHeader(mediaType).url(selfUrl));
		client.sendRequest(getCallback());
	}
}
