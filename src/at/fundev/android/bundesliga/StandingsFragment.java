package at.fundev.android.bundesliga;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import at.fundev.android.bundesliga.data.StandingsItem;
import at.fundev.android.bundesliga.ui.StandingsAdapter;

public class StandingsFragment extends ListFragment {

	protected StandingsItem[] items;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.standings_fragment, null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StandingsActivity.BUNDESLIGA_URL + ((StandingsItem)getListView().getItemAtPosition(position)).getTeamLink()));
		startActivity(browserIntent);
	}

	protected void setItems(ArrayList<StandingsItem> items, boolean force) {
		if(this.items == null || force)
		{
			this.items = new StandingsItem[10];
			
			items.toArray(this.items);
			
			setListAdapter(new StandingsAdapter(getActivity(), R.layout.standings_item, this.items));
		}
		
		return;
	}
}
