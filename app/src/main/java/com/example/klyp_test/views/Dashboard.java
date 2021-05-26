package com.example.klyp_test.views;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.klyp_test.R;
import com.example.klyp_test.adapter.MovieListAdapter;
import com.example.klyp_test.databinding.ActivityMainBinding;
import com.example.klyp_test.model.Response;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.klyp_test.constants.AppConstant.ERROROCCURED;
import static com.example.klyp_test.constants.AppConstant.FALSE;
import static com.example.klyp_test.constants.AppConstant.INTERNETISSUE;
import static com.example.klyp_test.constants.AppConstant.OOPS;

@AndroidEntryPoint
public class Dashboard extends AppCompatActivity {
    private DashBoardViewModel dashBoardViewModel;
    private ActivityMainBinding activityMainBinding;
    private final HashMap<String, Integer> localColors = new HashMap<>();
    @Inject
    MovieListAdapter movieListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        dashBoardViewModel.errorData.observe(this, this::consumeError);

        localColors.put("red", Color.parseColor("#FF0000"));
        localColors.put("green", Color.parseColor("#008000"));
        localColors.put("yellow", Color.parseColor("#FFFF00"));
        localColors.put("blue", Color.parseColor("#0000FF"));

        activityMainBinding.searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.getText().toString().length() >= 3) {
                    request(v.getText().toString());
                    return true;
                } else {
                    Toast.makeText(Dashboard.this, getString(R.string.pleaseEnterValidName), Toast.LENGTH_LONG).show();
                }
            }
            return false;
        });


    }

    private void request(String v) {
        if (!TextUtils.isEmpty(v)) {
            dashBoardViewModel.getMoviesResult(Dashboard.this, v).observe(Dashboard.this, Dashboard.this::consumeResponse);
        } else {
            Snackbar snackbar = Snackbar.make(activityMainBinding.getRoot(), getString(R.string.enterSomethingToSearch), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void consumeError(String o) {
        if (o.equals(getString(R.string.internet_connection_message)))
            updateView(INTERNETISSUE);
        else
            updateView(OOPS);


    }


    private void consumeResponse(Response o) {
        activityMainBinding.movieList.setVisibility(View.VISIBLE);
        activityMainBinding.statusInformerImage.setVisibility(View.GONE);
        activityMainBinding.statusInformerTitle.setVisibility(View.GONE);
        if (!o.getResponse().equals(FALSE)) {
            if (o.getSearch().size() > 0) {
                //adding Layout Manager to Recycler
                activityMainBinding.movieList.setLayoutManager(new LinearLayoutManager(this));
                movieListAdapter.setData(Dashboard.this, o.getSearch(),localColors);
                activityMainBinding.movieList.setAdapter(movieListAdapter);
            } else {
                updateView(ERROROCCURED);
            }
        } else {
            updateView(ERROROCCURED);
        }


    }

    private void updateView(String caseStr) {
        activityMainBinding.movieList.setVisibility(View.GONE);
        activityMainBinding.statusInformerImage.setVisibility(View.VISIBLE);
        activityMainBinding.statusInformerTitle.setVisibility(View.VISIBLE);
        switch (caseStr) {
            case INTERNETISSUE:
                Toast.makeText(this, getString(R.string.internet_connection_message), Toast.LENGTH_SHORT).show();
                break;

            case OOPS:
                Toast.makeText(this, getString(R.string.tryAgainLater), Toast.LENGTH_SHORT).show();
                break;

            case ERROROCCURED:
                Toast.makeText(this, getString(R.string.sorrySearchMovieNotFound), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
