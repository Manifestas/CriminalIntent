package com.example.manifest.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        if (adapter == null) {
            adapter = new CrimeAdapter(crimeLab.getCrimes());
            crimeRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime crime;
        private TextView titleTextView;
        private TextView dateTextView;
        private ImageView solvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            titleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            dateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            solvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(this.crime.getTitle());
            dateTextView.setText(this.crime.getFormattedDate());
            solvedImageView.setVisibility(crime.isSolved()? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
            startActivity(intent);
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime currentCrime = crimes.get(position);
            holder.bind(currentCrime);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}
