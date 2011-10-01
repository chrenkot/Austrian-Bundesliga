package at.fundev.android.bundesliga.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import at.fundev.android.bundesliga.data.StandingsItem;

public class StandingsAdapter extends ArrayAdapter<StandingsItem> {

	private StandingsItem[] items;
	
	public StandingsAdapter(Context context, int textViewResourceId, StandingsItem[] objects) {
		super(context, textViewResourceId, objects);
		items = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// convertView = new TextView(getContext());
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(at.fundev.android.bundesliga.R.layout.standings_item, null);
		}
		
		TextView lblPosition = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblPosition);
		lblPosition.setText(items[position].getPosition().toString());
		
		ImageView imgLogo = (ImageView) convertView.findViewById(at.fundev.android.bundesliga.R.id.imgLogo);
		imgLogo.setImageDrawable(items[position].getImage());

		TextView lblName = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblName);
		lblName.setText(items[position].getName());
		
		TextView lblGames = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblGames);
		lblGames.setText(items[position].getGames().toString());
		
		TextView lblPoints = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblPoints);
		lblPoints.setText(items[position].getPoints().toString());
		
		return convertView;
	}
}
