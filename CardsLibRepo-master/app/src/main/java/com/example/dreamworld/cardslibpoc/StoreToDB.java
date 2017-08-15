package com.example.dreamworld.cardslibpoc;

import android.os.AsyncTask;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import java.util.ArrayList;
import java.util.List;


public class StoreToDB extends AsyncTask<CardInfo, Void, Void>  {

	//  Create Domain and save card information in domain
	public  void saveCardInfo(String name,String description,String final_url,String dateTextView,float PricePerDay,float PricePerWeek,float PricePerMonth,float Securitydeposit)
	{
		try {
			CardInfo.getAwsSimpleDB().createDomain(new CreateDomainRequest("card_info"));
			 List<ReplaceableAttribute> attribute= new ArrayList<ReplaceableAttribute>(1);
			 attribute.add(new ReplaceableAttribute().withName("cardName").withValue(name));
			 attribute.add(new ReplaceableAttribute().withName("cardDescription").withValue(description));
             attribute.add(new ReplaceableAttribute().withName("ImageUrl").withValue(final_url));
             attribute.add(new ReplaceableAttribute().withName("dateTextView").withValue(dateTextView));
			 attribute.add(new ReplaceableAttribute().withName("PricePerDay").withValue(Float.toString(PricePerDay)));
			 attribute.add(new ReplaceableAttribute().withName("PricePerWeek").withValue(Float.toString(PricePerWeek)));
			 attribute.add(new ReplaceableAttribute().withName("PricePerMonth").withValue(Float.toString(PricePerMonth)));
			 attribute.add(new ReplaceableAttribute().withName("Securitydeposit").withValue(Float.toString(Securitydeposit)));
			 CardInfo.getAwsSimpleDB().putAttributes(new PutAttributesRequest("card_info", name, attribute));
			
		} catch (Exception e) {
				System.out.println(e.getMessage());
		}
	}

	@Override
	protected Void doInBackground(CardInfo... params) {
		// TODO Auto-generated method stub
		 saveCardInfo(params[0].cardName,params[0].cardDescription,params[0].ImageUrl,params[0].dateTextView,params[0].PricePerDay,params[0].PricePerWeek,params[0].PricePerMonth,params[0].Securitydeposit);
		return null;
	}

   }
