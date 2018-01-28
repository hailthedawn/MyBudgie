package com.example.varni.mybudgie;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().setTitle("Jan. 27th Summary");
        int month = getIntent().getIntExtra("month",0);
        int day = getIntent().getIntExtra("day",0);
        ArrayList<Expense> expenses = (ArrayList<Expense>)getIntent().getSerializableExtra("expenses");
        ArrayList<Earning> earnings = (ArrayList<Earning>)getIntent().getSerializableExtra("earnings");
        setUpActionBar(month,day);

        TextView totalText = (TextView)findViewById(R.id.total_amt);
        ListView expensesView = (ListView)findViewById(R.id.expenses_list);
        ListView earningsView = (ListView)findViewById(R.id.earnings_list);

        BigDecimal expenseTotal = calculateTotal(expenses).setScale(2, RoundingMode.HALF_UP);
        BigDecimal earningTotal = calculateEarnTotal(earnings).setScale(2, RoundingMode.HALF_UP);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getResources().getConfiguration().locale);
        ((TextView)findViewById(R.id.expenses_total)).setText(currencyFormatter.format(expenseTotal));
        ((TextView)findViewById(R.id.earnings_total)).setText(currencyFormatter.format(earningTotal));

        double d = earningTotal.subtract(expenseTotal).doubleValue();
        String value = currencyFormatter.format(d);
        if(d >=0){
            totalText.setText(value);
            totalText.setTextColor(getResources().getColor(R.color.green));
        }else{
            totalText.setText(value);
            totalText.setTextColor(getResources().getColor(R.color.red));
        }


        EntryAdapter expensesAdapter = new EntryAdapter(expenses);
        EntryAdapter earningsAdapter = new EntryAdapter(earnings);
        expensesView.setAdapter(expensesAdapter);
        earningsView.setAdapter(earningsAdapter);
    }

    private class EntryAdapter extends BaseAdapter {

        private ArrayList<Entry> expenses;
        private final int RESOURCE_ID = R.layout.item_conjugation;

        public EntryAdapter(ArrayList expenses) {
            this.expenses = expenses;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(RESOURCE_ID, viewGroup, false);
            }
            TextView nameView = (TextView)view.findViewById(R.id.conjFormal);
            TextView amtView = (TextView) view.findViewById(R.id.conjText);
            nameView.setText(expenses.get(i).getSource());

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getResources().getConfiguration().locale);
            String value = currencyFormatter.format(expenses.get(i).getValue());
            amtView.setText(value);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DayActivity.this,EntryActivity.class);
                    startActivity(i);
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return expenses.size();
        }

        @Override
        public Object getItem(int i) {
            return expenses.get(i);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    private ArrayList<String> getExpenseNames(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Expense 1");
        list.add("Expense 2");
        list.add("Expense 3");
        return list;
    }

    private ArrayList<BigDecimal> getExpenseValue(){
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(13.37));
        list.add(new BigDecimal(150));
        list.add(new BigDecimal(0.56));
        return list;
    }

    private ArrayList<String> getEarningNames(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Earning 1");
        list.add("Earning 2");
        return list;
    }

    private ArrayList<BigDecimal> getEarningValue(){
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(13.37));
        list.add(new BigDecimal(152));
        return list;
    }


    private BigDecimal calculateTotal(ArrayList<Expense> expenses){
        BigDecimal total = new BigDecimal(0);
        for(Expense e : expenses){
            BigDecimal d = new BigDecimal(e.getValue());
            total = total.add(d);
        }
        return total;
    }

    private BigDecimal calculateEarnTotal(ArrayList<Earning> expenses){
        BigDecimal total = new BigDecimal(0);
        for(Earning e : expenses){
            BigDecimal d = new BigDecimal(e.getValue());
            total = total.add(d);
        }
        return total;
    }

    private void setUpActionBar(int month, int day){
        ActionBar actionBar = getSupportActionBar();
        final String[] months={"January","February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        actionBar.setTitle(months[month]+" "+day);
    }

}