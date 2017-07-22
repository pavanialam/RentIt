package apprentit.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Tab2 extends Fragment {
    RecyclerView recyclerView;
    String Wonders[] = {"Chichen Itza","Christ the Redeemer","Great Wall of China","Machu Picchu","Petra","Taj Mahal","Colosseum"};
    //int  Images[] = {R.drawable.chichen_itza,R.drawable.christ_the_redeemer,R.drawable.great_wall_of_china,R.drawable.machu_picchu,R.drawable.petra,R.drawable.taj_mahal,R.drawable.colosseum};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_tab2,container, false);
        // Recycler view
        recyclerView=(RecyclerView)rootView.findViewById(R.id.Recyclertab2);
//        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerView_Adapter(getListItems()));
    }

    private ArrayList<Model> getListItems(){
        ArrayList<Model> listitems=new ArrayList();
        Model m=new Model("Chichen ",R.drawable.image);
        listitems.add(m);
        m=new Model("Christ",R.drawable.image);
        listitems.add(m);
        m=new Model("Great",R.drawable.image);
        listitems.add(m);
        return listitems;
    }
    @Override
    //set title for the fragment
    public String toString()
    {
        return "tab2";
    }
}