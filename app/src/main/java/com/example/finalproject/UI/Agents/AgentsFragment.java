package com.example.finalproject.UI.Agents;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.ValorantDatabase;
import com.example.finalproject.pojo.Agent;
import com.example.finalproject.pojo.Weapon;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgentsFragment newInstance(String param1, String param2) {
        AgentsFragment fragment = new AgentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // separated the view from the return statement
        View view = inflater.inflate(R.layout.fragment_agents, container, false);

        //get the shared preference from the settings menu
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        //get the valorant database
        ValorantDatabase db = new ValorantDatabase(getContext());
        //create an array list of Agent objects
        ArrayList<Agent> agentsList = db.getAllAgents();
        //close the connection to the Valorant database
        db.close();

        //assign the agents list Recycler View to a variable
        RecyclerView recyclerView = view.findViewById(R.id.agentsList);

        //toggle the animation on/off depending on the value returned from the settings menu
        if (sharedPreferences.getBoolean("animation_toggle", true)){
            // Load the animation resource file
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.enter_top_left);

            // Create a LayoutAnimationController object and set its animation properties
            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(anim);
            layoutAnimationController.setDelay(0.3f);

            // Set the LayoutAnimationController on the RecyclerView
            recyclerView.setLayoutAnimation(layoutAnimationController);
        }else {
            recyclerView.setLayoutAnimation(null);
        }

        //create a new custom list view adapter and assign it to AgentsListView
        AgentsCustomAdapter adapter = new AgentsCustomAdapter(agentsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}