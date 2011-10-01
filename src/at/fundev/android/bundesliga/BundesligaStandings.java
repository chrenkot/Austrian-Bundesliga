package at.fundev.android.bundesliga;

import java.util.ArrayList;

import at.fundev.android.bundesliga.data.StandingsItem;

public class BundesligaStandings extends Standings {

	@Override
	protected void setArray(ArrayList<StandingsItem> items) {
		items.subList(0, 10).toArray(this.items);
	}

}
