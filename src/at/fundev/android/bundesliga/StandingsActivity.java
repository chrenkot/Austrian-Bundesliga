package at.fundev.android.bundesliga;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import at.fundev.android.bundesliga.data.StandingsItem;
import at.fundev.android.bundesliga.utils.StandingsContentHandler;

public class StandingsActivity extends FragmentActivity {
	
	private class PageFetcherTask extends AsyncTask<String, Void, InputStream> {

		private ProgressDialog progressDialog;
		
		PageFetcherTask() {
			super();
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = ProgressDialog.show(StandingsActivity.this, "", getString(R.string.downloadInformation));
		}

		@Override
		protected InputStream doInBackground(String... url) {
			if (url.length == 0) {
				throw new IllegalArgumentException("URL must be provided!");
			}
		
			try {
				
				Log.d(PageFetcherTask.class.getName(), "Fetching "+ url[0]);
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
		
				request.setURI(new URI(url[0]));
		
				HttpResponse response;
				response = client.execute(request);
				Log.d(PageFetcherTask.class.getName(), " ... fetching succeeded");
				
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    		{
					String toParse = EntityUtils.toString(response.getEntity());
					
					int start = toParse.indexOf(START_TAG);
					int end = start + toParse.substring(start).indexOf(END_TAG) + END_TAG.length();
					
					toParse = toParse.substring(start, end);
					
					ByteArrayInputStream input = new ByteArrayInputStream(toParse.getBytes());
					
	    			return input;
	    		}
				
			} catch (IOException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			} catch (IllegalStateException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			} catch (URISyntaxException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			}
		
			return null;
		}
		
		@Override
		protected void onPostExecute(InputStream result) {
			
			if(result == null)
			{
				progressDialog.cancel();
				mRetryDialog.show();
				return;
			}
			
			try {
				
				InputSource input = new InputSource(result);
				
				System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
				
				XMLReader reader = XMLReaderFactory.createXMLReader();
				StandingsContentHandler contentHandler = new StandingsContentHandler();
				reader.setContentHandler(contentHandler);
				reader.parse(input);
				
				if(mTabManager.mLastTab.tag.equals(BUNDESLIGA_TAG))
					mBundesligaItems = contentHandler.getStandingsItems();
				else
					mErsteLigaItems = contentHandler.getStandingsItems();
				
				setStandingsItems(true);
				
				progressDialog.cancel();
				
				mIsFetching = false;
			} catch (NullPointerException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			} catch (IndexOutOfBoundsException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			} catch (SAXException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			} catch (IOException e) {
				Log.e(StandingsActivity.class.getName(), e.getMessage());
			}
		}
	}
	
	private static final String BUNDESLIGA_STANDINGS = "index.php?id=558";
	
	private static final String ERSTELIGA_STANDINGS = "index.php?id=570";
	
	private static final String ROUND = "&round=%s";
	
	public static final String BUNDESLIGA_URL = "http://www.bundesliga.at/";
	
	private static final String BUNDESLIGA_TAG = "bundesliga";
	
	private static final String ERSTELIGA_TAG = "ersteliga";
	
	private static final String START_TAG = "<table class=\"standings bl-tbl\">";
	
	private static final String END_TAG = "</table>";
	
	private TabHost mTabHost;
	
	private TabManager mTabManager;
	
	private Dialog mRetryDialog;
	
	private Dialog mRoundDialog;
	
	private boolean mIsFetching;
	
	private boolean mIsInitialized;
	
	private ArrayList<StandingsItem> mBundesligaItems;
	
	private ArrayList<StandingsItem> mErsteLigaItems;
	
	private String mRound = "0";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.standings_activity);
        
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);

        mTabManager.addTab(mTabHost.newTabSpec(BUNDESLIGA_TAG).setIndicator(getString(R.string.bundesLiga), getResources().getDrawable(R.drawable.bundesliga)),
                StandingsFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec(ERSTELIGA_TAG).setIndicator(getString(R.string.ersteLiga), getResources().getDrawable(R.drawable.ersteliga)),
        		StandingsFragment.class, null);
        
        mIsInitialized = true;
        
        if (savedInstanceState != null) {
        	mBundesligaItems = savedInstanceState.getParcelableArrayList(BUNDESLIGA_TAG);
            mErsteLigaItems = savedInstanceState.getParcelableArrayList(ERSTELIGA_TAG);
            mRound = savedInstanceState.getString(ROUND);
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
        buildDialogs();
        
        fetchOrSet();
     }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDESLIGA_TAG, mBundesligaItems);
        outState.putParcelableArrayList(ERSTELIGA_TAG, mErsteLigaItems);
        outState.putString(ROUND, mRound);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
    
    private void fetchOrSet()
    {
    	if(mTabManager.mLastTab.tag.equals(BUNDESLIGA_TAG))
        	if(mBundesligaItems == null)
        		fetchInformation();
        	else
        		setStandingsItems(false);
    	else
    		if(mErsteLigaItems == null)
        		fetchInformation();
        	else
        		setStandingsItems(false);
    }
    
    private void fetchInformation()
    {
    	if(!mIsFetching)
    	{
    		mIsFetching = true;
    		
    		String round = String.format(ROUND, mRound);
    		
    		PageFetcherTask fetcher = new PageFetcherTask();
    		if(mTabManager.mLastTab.tag.equals(BUNDESLIGA_TAG))
    			fetcher.execute(BUNDESLIGA_URL + BUNDESLIGA_STANDINGS + round);
    		else
    			fetcher.execute(BUNDESLIGA_URL + ERSTELIGA_STANDINGS + round);
    	}
    }
    
    public void setStandingsItems(boolean force)
    {
    	if(mTabManager.mLastTab.fragment != null)
    	{
    		if(mTabManager.mLastTab.tag.equals(BUNDESLIGA_TAG))
    			((StandingsFragment)mTabManager.mLastTab.fragment).setItems(mBundesligaItems, force, mRound);
    		else
    			((StandingsFragment)mTabManager.mLastTab.fragment).setItems(mErsteLigaItems, force, mRound);
    	}
    }
    
    private void buildDialogs()
    {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String dlgTitle = getString(R.string.dlgTitle);
		String dlgText = getString(R.string.dlgText);
		String btnRetryText = getString(R.string.dlgRetry);
		String btnCancelText = getString(R.string.dlgCancel);
		
		builder.setTitle(dlgTitle);
		builder.setMessage(dlgText);
		builder.setPositiveButton(btnRetryText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				fetchInformation();
			}
		});
		builder.setNegativeButton(btnCancelText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		mRetryDialog = builder.create();
		
		final CharSequence[] items = {"1","2","3","4","5","6","7","8","9","10",
										"11","12","13","14","15","16","17","18","19","20",
											"21","22","23","24","25","26","27","28","29","30",
												"31","32","33","34","35","36"};
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.selectRound);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mRound = items[which].toString();
				fetchInformation();
			}
		});
		
		mRoundDialog = builder.create();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.standings_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.standings_refresh:
	        	mRound = "0";
	        	fetchInformation();
	            return true;
	        case R.id.standings_round:
	        	mRoundDialog.show();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between fragments.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabManager supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct fragment shown in a separate content area
     * whenever the selected tab changes.
     */
    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
                
                if(((StandingsActivity)mActivity).mIsInitialized)
                	((StandingsActivity)mActivity).fetchOrSet();
             }
        }
    }
}