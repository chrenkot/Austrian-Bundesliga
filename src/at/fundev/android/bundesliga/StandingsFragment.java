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
import android.widget.TextView;
import at.fundev.android.bundesliga.data.StandingsItem;
import at.fundev.android.bundesliga.ui.StandingsAdapter;

public class StandingsFragment extends ListFragment {

	private StandingsItem[] mItems;
	
	private String mRound;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		setRound(view);
	}

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

	protected void setItems(ArrayList<StandingsItem> items, boolean force, String round) {
		if(this.mItems == null || force)
		{
			mRound = round;
			
			setRound(getView());
			
			this.mItems = new StandingsItem[10];
			
			items.toArray(this.mItems);
			
			setListAdapter(new StandingsAdapter(getActivity(), R.layout.standings_item, this.mItems));
		}
		
		return;
	}
	
	private void setRound(View view) {
		if(view != null) {
			TextView lblRound = (TextView) view.findViewById(R.id.lblRound);
			
			if(mRound == null || mRound.equals("0"))
	    		lblRound.setText("");
	    	else
	    		lblRound.setText(getString(R.string.round) + " " + mRound);
		}
	}
}
