package com.newsclient.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.view.VDetails;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefresh {
    static DNewsList list;
    static Activity v;
    static public ArrayList<String> getList() {
        return list.list();
    }
    static public void setV(Activity a) {
        v = a;
    }
    static public void setList(DNewsList list) {
        SwipeRefresh.list = list;
    }

    /**
     * Subclass of {@link android.support.v4.app.ListFragment} which provides automatic support for
     * providing the 'swipe-to-refresh' UX gesture by wrapping the the content view in a
     * {@link android.support.v4.widget.SwipeRefreshLayout}.
     */
    public static class SwipeRefreshListFragment extends ListFragment {

        private SwipeRefreshLayout mSwipeRefreshLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Create the list fragment's content view by calling the super method
            final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);

            // Now create a SwipeRefreshLayout to wrap the fragment's content view
            mSwipeRefreshLayout = new ListFragmentSwipeRefreshLayout(container.getContext());

            // Add the list fragment's content view to the SwipeRefreshLayout, making sure that it fills
            // the SwipeRefreshLayout
            mSwipeRefreshLayout.addView(listFragmentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            // Make sure that the SwipeRefreshLayout will fill the fragment
            mSwipeRefreshLayout.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            // Now return the SwipeRefreshLayout as this fragment's content view
            return mSwipeRefreshLayout;
        }

        /**
         * Set the {@link SwipeRefreshLayout.OnRefreshListener} to listen for
         * initiated refreshes.
         *
         * @see SwipeRefreshLayout#setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener)
         */
        public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
            mSwipeRefreshLayout.setOnRefreshListener(listener);
        }

        /**
         * Returns whether the {@link SwipeRefreshLayout} is currently
         * refreshing or not.
         *
         * @see SwipeRefreshLayout#isRefreshing()
         */
        public boolean isRefreshing() {
            return mSwipeRefreshLayout.isRefreshing();
        }

        /**
         * Set whether the {@link SwipeRefreshLayout} should be displaying
         * that it is refreshing or not.
         *
         * @see SwipeRefreshLayout#setRefreshing(boolean)
         */
        public void setRefreshing(boolean refreshing) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }

        /**
         * Set the color scheme for the {@link SwipeRefreshLayout}.
         *
         * @see SwipeRefreshLayout#setColorScheme(int, int, int, int)
         */
        public void setColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
            mSwipeRefreshLayout.setColorScheme(colorRes1, colorRes2, colorRes3, colorRes4);
        }

        /**
         * @return the fragment's {@link SwipeRefreshLayout} widget.
         */
        public SwipeRefreshLayout getSwipeRefreshLayout() {
            return mSwipeRefreshLayout;
        }

        /**
         * Sub-class of {@link SwipeRefreshLayout} for use in this
         * {@link ListFragment}. The reason that this is needed is because
         * {@link SwipeRefreshLayout} only supports a single child, which it
         * expects to be the one which triggers refreshes. In our case the layout's child is the content
         * view returned from
         * {@link ListFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
         * which is a {@link ViewGroup}.
         *
         * <p>To enable 'swipe-to-refresh' support via the {@link android.widget.ListView} we need to
         * override the default behavior and properly signal when a gesture is possible. This is done by
         * overriding {@link #canChildScrollUp()}.
         */
        private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {

            public ListFragmentSwipeRefreshLayout(Context context) {
                super(context);
            }

            /**
             * As mentioned above, we need to override this method to properly signal when a
             * 'swipe-to-refresh' is possible.
             *
             * @return true if the {@link android.widget.ListView} is visible and can scroll up.
             */
            @Override
            public boolean canChildScrollUp() {
                final ListView listView = getListView();
                if (listView.getVisibility() == View.VISIBLE) {
                    return canListViewScrollUp(listView);
                } else {
                    return false;
                }
            }

        }

        // BEGIN_INCLUDE (check_list_can_scroll)
        /**
         * Utility method to check whether a {@link ListView} can scroll up from it's current position.
         * Handles platform version differences, providing backwards compatible functionality where
         * needed.
         */
        private static boolean canListViewScrollUp(ListView listView) {
            if (android.os.Build.VERSION.SDK_INT >= 14) {
                // For ICS and above we can call canScrollVertically() to determine this
                return ViewCompat.canScrollVertically(listView, -1);
            } else {
                // Pre-ICS we need to manually check the first visible item and the child view's top
                // value
                return listView.getChildCount() > 0 &&
                        (listView.getFirstVisiblePosition() > 0
                                || listView.getChildAt(0).getTop() < listView.getPaddingTop());
            }
        }
        // END_INCLUDE (check_list_can_scroll)


        @Override
        public void onListItemClick(ListView lv, View v, int position, long id) {
            Intent intent = new Intent(SwipeRefresh.v, VDetails.class);
            try {
                intent.putExtra("news_id", DNewsList._news_list.get(position).news_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }

    /**
     * A sample which shows how to use {@link SwipeRefreshLayout} within a
     * {@link ListFragment} to add the 'swipe-to-refresh' gesture to a
     * {@link ListView}. This is provided through the provided re-usable
     * {@link SwipeRefreshListFragment} class.
     *
     * <p>To provide an accessible way to trigger the refresh, this app also provides a refresh
     * action item. This item should be displayed in the Action Bar's overflow item.
     *
     * <p>In this sample app, the refresh updates the ListView with a random set of new items.
     *
     * <p>This sample also provides the functionality to change the colors displayed in the
     * {@link SwipeRefreshLayout} through the options menu. This is meant to
     * showcase the use of color rather than being something that should be integrated into apps.
     */
    public static class SwipeRefreshListFragmentFragment extends SwipeRefreshListFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Notify the system to allow an options menu for this fragment.
            setHasOptionsMenu(true);

        }

        // BEGIN_INCLUDE (setup_views)
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            setColorScheme(R.color.color_scheme_1_1, R.color.color_scheme_1_2,
                    R.color.color_scheme_1_3, R.color.color_scheme_1_4);
            /**
             * Create an ArrayAdapter to contain the data for the ListView. Each item in the ListView
             * uses the system-defined simple_list_item_1 layout that contains one TextView.
             */
            ListAdapter adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    getList());

            // Set the adapter between the ListView and its backing data.
            setListAdapter(adapter);

            // BEGIN_INCLUDE (setup_refreshlistener)
            /**
             * Implement {@link SwipeRefreshLayout.OnRefreshListener}. When users do the "swipe to
             * refresh" gesture, SwipeRefreshLayout invokes
             * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}. In
             * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}, call a method that
             * refreshes the content. Call the same method in response to the Refresh action from the
             * action bar.
             */
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initiateRefresh();
                }
            });
            // END_INCLUDE (setup_refreshlistener)
        }
        // END_INCLUDE (setup_views)

        // BEGIN_INCLUDE (initiate_refresh)
        /**
         * By abstracting the refresh process to a single method, the app allows both the
         * SwipeGestureLayout onRefresh() method and the Refresh action item to refresh the content.
         */
        private void initiateRefresh() {
            /**
             * Execute the background task, which uses {@link android.os.AsyncTask} to load the data.
             */
            new DummyBackgroundTask().execute();
        }
        // END_INCLUDE (initiate_refresh)

        // BEGIN_INCLUDE (refresh_complete)
        /**
         * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
         * ListAdapter and turns off the progress bar.
         */
        private void onRefreshComplete(List<String> result) {
            // Remove all items from the ListAdapter, and then replace them with the new items
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
            adapter.clear();
            for (String cheese : result) {
                adapter.add(cheese);
            }

            // Stop the refreshing indicator
            setRefreshing(false);
        }
        // END_INCLUDE (refresh_complete)

        /**
         * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
         */
        private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

            static final int TASK_DURATION = 3 * 500; // 3 seconds

            @Override
            protected List<String> doInBackground(Void... params) {
                // Sleep for a small amount of time to simulate a background-task
                try {
                    Thread.sleep(TASK_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Return a new random list of cheeses
                return getList();
            }

            @Override
            protected void onPostExecute(List<String> result) {
                super.onPostExecute(result);

                // Tell the Fragment that the refresh has completed
                onRefreshComplete(result);
            }

        }

    }
}
