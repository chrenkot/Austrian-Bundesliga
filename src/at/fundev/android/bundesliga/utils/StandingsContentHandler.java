package at.fundev.android.bundesliga.utils;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import at.fundev.android.bundesliga.data.StandingsEnum;
import at.fundev.android.bundesliga.data.StandingsItem;

public class StandingsContentHandler implements ContentHandler {

	private ArrayList<StandingsItem> standingsItems;
	
	private StandingsItem standingsItem;
	
	private StandingsEnum current;
	
	public ArrayList<StandingsItem> getStandingsItems() {
		return standingsItems;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		switch(current) {
		
			case POSITION:
				if(standingsItem.getPosition() == -1)
					standingsItem.setPosition(getNumber(ch, start, length));
				break;
				
			case TEAM:
				if(standingsItem.getName() == null)
					standingsItem.setName(getString(ch, start, length));
				break;
				
			case WINS:
				if(standingsItem.getWins() == -1)
					standingsItem.setWins(getNumber(ch, start, length));
				break;
				
			case DRAWS:
				if(standingsItem.getDraws() == -1)
					standingsItem.setDraws(getNumber(ch, start, length));
				break;
				
			case DEFEATS:
				if(standingsItem.getDefeats() == -1)
					standingsItem.setDefeats(getNumber(ch, start, length));
				break;
				
			case GOALSSCORED:
				if(standingsItem.getGoalsScored() == -1)
					standingsItem.setGoalsScored(getNumber(ch, start, length));
				break;
				
			case GOALSAGAINST:
				if(standingsItem.getGoalsAgainst() == -1)
					standingsItem.setGoalsAgainst(getNumber(ch, start, length));
				break;
				
			default:
				break;
		}
	}
	
	private String getString(char[] ch, int start, int length) {
		String retValue = "";
		
		for(int i=0;i<length;i++)
		{
			retValue = retValue + ch[start+i];
		}
		
		return retValue;
	}
	
//	private Drawable getImage(String source) {
//		try {
//			URL url = new URL(StandingsActivity.BUNDESLIGA_URL + source);
//			
//			return Drawable.createFromStream(url.openStream(), "src");
//		} catch (MalformedURLException e) {
//			Log.e(StandingsActivity.class.getName(), e.getMessage());
//		} catch (IOException e) {
//			Log.e(StandingsActivity.class.getName(), e.getMessage());
//		} 
//		
//		return null;
//	}
	
	private int getNumber(char[] ch, int start, int length) {
		String toParse = getString(ch, start, length);
		
		int retValue;
		
		try {
			retValue = Integer.parseInt(toParse);
		} catch (NumberFormatException e) {
			retValue = -1;
		}
		
		return retValue;
	}

	@Override
	public void startDocument() throws SAXException {
		standingsItems = new ArrayList<StandingsItem>();
		standingsItem = new StandingsItem();
		current = StandingsEnum.UNINITIALIZED;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		if(localName.equals("td")) {
			switch(current) {
			
				case UNINITIALIZED:
					current = StandingsEnum.POSITION;
					break;
			
				case POSITION:
					current = StandingsEnum.EMPTY;
					break;
					
				case EMPTY:
					current = StandingsEnum.TEAM;
					break;
					
				case TEAM:
					current = StandingsEnum.GAMES;
					break;
					
				case GAMES:
					current = StandingsEnum.WINS;
					break;
					
				case WINS:
					current = StandingsEnum.DRAWS;
					break;
					
				case DRAWS:
					current = StandingsEnum.DEFEATS;
					break;
					
				case DEFEATS:
					current = StandingsEnum.GOALSSCORED;
					break;
					
				case GOALSSCORED:
					current = StandingsEnum.SEPERATOR;
					break;
					
				case SEPERATOR:
					current = StandingsEnum.GOALSAGAINST;
					break;
					
				case GOALSAGAINST:
					current = StandingsEnum.GOALSDIFF;
					break;
					
				case GOALSDIFF:
					current = StandingsEnum.POINTS;
					standingsItems.add(standingsItem);
					break;
					
				case POINTS:
					current = StandingsEnum.POSITION;
					standingsItem = new StandingsItem();
					break;
					
				default:
					break;
			}
		}
		else if(localName.equals("a")) {
			if(current.equals(StandingsEnum.TEAM))
				standingsItem.setTeamLink(atts.getValue("href"));
		}
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) {
	}

	@Override
	public void processingInstruction(String target, String data) {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}
}
