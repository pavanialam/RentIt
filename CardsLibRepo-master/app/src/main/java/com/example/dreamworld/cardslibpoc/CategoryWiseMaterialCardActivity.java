package com.example.dreamworld.cardslibpoc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class CategoryWiseMaterialCardActivity extends AppCompatActivity {

    //private CardArrayAdapter
    private CardArrayRecyclerViewAdapter mCardArrayAdapter;
    private CardRecyclerView mRecyclerView;
    CardInfo[] cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_material_card);

        ArrayList<Card> cards = new ArrayList<>();
        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        mRecyclerView = (CardRecyclerView) findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        // retrieving data from db
        ReadFromDB db=new ReadFromDB();
        try {
            cardList= db.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Load cards
        new LoaderAsyncTask().execute();
    }

    private ArrayList<Card> initCard() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < cardList.length; i++) {

            final int finalI = i;

            // Set supplemental actions as icon
            ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();
            IconSupplementalAction t1 = new IconSupplementalAction(this, R.id.like_id);
            t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getApplicationContext(), " Like it", Toast.LENGTH_SHORT).show();
                }
            });
            actions.add(t1);

            IconSupplementalAction t2 = new IconSupplementalAction(this, R.id.share_id);
            t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getApplicationContext(), " Share with others ", Toast.LENGTH_SHORT).show();

                }
            });
            actions.add(t2);

            TextSupplementalAction t3=new TextSupplementalAction(this,R.id.expand_id);
            t3.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getApplicationContext(), " Expand activity should be called ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CategoryWiseMaterialCardActivity.this, ExpandableActivity.class);
                    startActivity(intent);

                }
            });
            actions.add(t3);

            MaterialLargeImageCard card = MaterialLargeImageCard.with(getApplicationContext())
                            .setTextOverImage(cardList[i].cardName)
                            .setTitle(cardList[i].cardDescription)
                            .setSubTitle(cardList[i].cardDescription)
                            .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                                @Override
                                public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                                    Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
                                    Picasso.with(getApplicationContext())
                                            .load(cardList[finalI].ImageUrl)
                                            .error(R.drawable.mainimage)
                                            .into((ImageView) viewImage);
                                }
                            })
            .setupSupplementalActions(R.layout.material_supplemental_actions_layout,actions )
            .build();

            //adding to arraylist
            cards.add(card);
        }

        return cards;
    }

    private void updateAdapter(ArrayList<Card> cards) {
        if (cards != null) {
            mCardArrayAdapter.addAll(cards);
        }
    }

    class LoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<Card>> {

        LoaderAsyncTask() {
        }

        @Override
        protected ArrayList<Card> doInBackground(Void... params) {

            ArrayList<Card> cards = initCard();
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
        }
    }
}
