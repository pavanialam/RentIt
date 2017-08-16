package com.example.dreamworld.cardslibpoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.ArrayList;
import java.util.Properties;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

public class MainActivity extends AppCompatActivity {

    //Grid View objects
    private CardGridArrayAdapter mCardArrayAdapter;
    private CardGridView gridView;
    public static Properties properties;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkStoragePermission();
        }

        //floating button action to add product
        FloatingActionButton addbutton = (FloatingActionButton) findViewById(R.id.addproduct_id);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductToRent.class);
                startActivity(intent);
            }
        });

        //reading properties file
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(this.getApplicationContext().getAssets().open("credentials.properties"));
                Log.e("TAG", "entered try block");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        //Adding card to the view

        ArrayList<Card> cards = new ArrayList<>();
        mCardArrayAdapter = new CardGridArrayAdapter (this, cards);
        //Staggered grid view
        gridView = (CardGridView) findViewById(R.id.myGrid);

        //Set the empty view
        if (gridView != null) {
            gridView.setAdapter(mCardArrayAdapter);

        }
/*
           gridView.setOnItemClickListener( {

               @Override
               public void onItemClick(LinearListView parent, View view, int position, CardWithList.ListObject object) {

               }
           });*/


        //Load cards
        new LoaderAsyncTask().execute();
    }

    public static Properties getProperties()
    {
        return properties;
    }

    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 99;

    public boolean checkStoragePermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                            .title("Permissions Required!")
                            .content("Permissions are not enabled, do you want to enable it?")
                            .positiveText("Enable")
                            .negativeText("Dismiss");

                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            checkStoragePermission();
                        }
                    });

                    builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    MaterialDialog dialog = builder.build();
                    dialog.show();
                }
                return;
            }
        }
    }

    private ArrayList<Card> initCard() {

        ArrayList<Card> cards = new ArrayList<Card>();
        categories=new ArrayList<String>();
        categories.add(0,"Cameras");
        categories.add(1,"Watches");
        categories.add(2,"Gear Cycles");
        categories.add(3,"VR Devices");
        categories.add(4,"Mobiles");

        ArrayList<Integer> categories_images=new ArrayList<>();
        categories_images.add(0,R.drawable.cameras);
        categories_images.add(1,R.drawable.watches);
        categories_images.add(2,R.drawable.cycles);
        categories_images.add(3,R.drawable.vrdevices);
        categories_images.add(4,R.drawable.mobiles);


        for (int i = 0; i < 5; i++) {

            MaterialLargeImageCard card = new MaterialLargeImageCard(this.getApplicationContext());
            card.setInnerLayout(R.layout.native_material_largeimage_card);
            card.setDrawableIdCardThumbnail(categories_images.get(i));
            //Set the title and subtitle in the card
            card.setTextOverImage(categories.get(i));
            card.build();

            final int finalI = i;
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getApplicationContext(), " Clicked on category ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CategoryWiseMaterialCardActivity.class);
                    intent.putExtra("categoryclicked",categories.get(finalI));
                    startActivity(intent);
                }
            });
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
            //elaborate images
            //SystemClock.sleep(1000); //delay to simulate download, don't use it in a real app

            ArrayList<Card> cards = initCard();
            return cards;

        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
            //displayList();
        }

    }
}
