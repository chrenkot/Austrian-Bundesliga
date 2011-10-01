package at.fundev.android.bundesliga.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		lblPosition.setText(String.valueOf(items[position].getPosition()));
		
		TextView lblName = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblName);
		lblName.setText(items[position].getName());
		
		TextView lblGames = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblGames);
		lblGames.setText(String.valueOf(items[position].getGames()));
		
		TextView lblWins = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblWins);
		lblWins.setText(String.valueOf(items[position].getWins()));
		
		TextView lblDraws = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblDraws);
		lblDraws.setText(String.valueOf(items[position].getDraws()));
		
		TextView lblDefeats = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblDefeats);
		lblDefeats.setText(String.valueOf(items[position].getDefeats()));
		
		TextView lblGoalsScored = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblGoalsScored);
		lblGoalsScored.setText(String.valueOf(items[position].getGoalsScored()));
		
		TextView lblGoalsAgainst = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblGoalsAgainst);
		lblGoalsAgainst.setText(String.valueOf(items[position].getGoalsAgainst()));
		
		TextView lblGoalsDiff = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblGoalsDiff);
		lblGoalsDiff.setText(String.valueOf(items[position].getGoalsDiff()));
		
		TextView lblPoints = (TextView) convertView.findViewById(at.fundev.android.bundesliga.R.id.lblPoints);
		lblPoints.setText(String.valueOf(items[position].getPoints()));
		
		return convertView;
	}
}
