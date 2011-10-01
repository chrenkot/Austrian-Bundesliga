package at.fundev.android.bundesliga;

import java.util.ArrayList;

import at.fundev.android.bundesliga.data.StandingsItem;

public class ErsteLigaStandings extends Standings {

	@Override
	protected void setArray(ArrayList<StandingsItem> items) {
		items.subList(10, 20).toArray(this.items);
	}

}
