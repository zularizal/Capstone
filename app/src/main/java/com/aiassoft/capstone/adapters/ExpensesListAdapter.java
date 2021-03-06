package com.aiassoft.capstone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiassoft.capstone.MyApp;
import com.aiassoft.capstone.R;
import com.aiassoft.capstone.model.Expense;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gvryn on 29/07/18.
 */

public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.ExpensesAdapterViewHolder> {

    private static final String LOG_TAG = MyApp.APP_TAG + ExpensesListAdapter.class.getSimpleName();

    /* This array holds a list of Expense objects */
    private ArrayList<Expense> mExpensesData = new ArrayList<>();

    /**
     * Defining an on-click handler to make it easy for an Activity
     * to interface with the RecyclerView
     */
    private final ExpensesAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives OnClick messages
     */
    public interface ExpensesAdapterOnClickHandler {
        void onClick(int ExpenseId);
    }

    /**
     * Creates a ExpensesAdapter
     *
     * @param clickHandler The on-click handler for this adapter. This single handler
     *                     is called when an item is clicked
     */
    public ExpensesListAdapter(ExpensesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a Expense list item
     */
    public class ExpensesAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /* The Views to display the Expense's Data */
        @BindView(R.id.vehicle_title) TextView mVehicleTitle;
        @BindView(R.id.expense_date) TextView mExpenseDate;
        @BindView(R.id.expense_type) TextView mExpenseType;
        @BindView(R.id.expense_subtype) TextView mExpenseSubtype;
        @BindView(R.id.expense_amount) TextView mExpenseAmount;

        public ExpensesAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int selectedExpense = mExpensesData.get(adapterPosition).getId();
            mClickHandler.onClick(selectedExpense);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new MovieVideosAdapterViewHolder that holds the View for each list item
     */
    @Override
    public ExpensesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_list_expense;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ExpensesAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the Expense
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the
     *                                contents of the item at the given position in the data set.
     * @param position                The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ExpensesAdapterViewHolder viewHolder, int position) {
        viewHolder.mVehicleTitle.setText(mExpensesData.get(position).getVehicle());
        viewHolder.mExpenseDate.setText(mExpensesData.get(position).getDate());
        viewHolder.mExpenseType.setText(mExpensesData.get(position)
                .getExpenseTypeStr(viewHolder.mExpenseType.getContext()));
        viewHolder.mExpenseSubtype.setText(mExpensesData.get(position)
                .getSubtypeStr(viewHolder.mExpenseSubtype.getContext()));
        viewHolder.mExpenseAmount.setText(String.valueOf(mExpensesData.get(position).getAmount()));
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our Expenses list
     */
    @Override
    public int getItemCount() {
        if (null == mExpensesData) return 0;
        return mExpensesData.size();
    }

    /**
     * This method is used to set the Expense on a ExpensesAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ExpensesAdapter to display it.
     *
     * @param ExpensesData The new Expenses data to be displayed.
     */
    public void setExpensesData(List<Expense> ExpensesData) {
        if (ExpensesData == null) return;
        mExpensesData.addAll(ExpensesData);
        notifyDataSetChanged();
    }

    /**
     * This method is used when we are resetting data
     */
    public void invalidateData() {
        mExpensesData = new ArrayList<Expense>();
        notifyDataSetChanged();
    }
    
}
