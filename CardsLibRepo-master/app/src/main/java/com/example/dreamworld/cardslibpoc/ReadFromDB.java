package com.example.dreamworld.cardslibpoc;

import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.SelectRequest;

import java.util.List;

public class ReadFromDB extends AsyncTask<String, Void, CardInfo[]> {

	public static CardInfo[] getAllCardInfo( String categoryclicked) throws Exception {

        String domainname;
        switch(categoryclicked) {
            case "Cameras" :
                domainname="cameras_info";
                break;
            case "Watches" :
                domainname="watches_info";
                break;
            case "Gear Cycles" :
                domainname="cycles_info";
                break;
            case "VR Devices" :
                domainname="vr_info";
                break;
            default :
                domainname="mobiles_info";
        }
        String query="select * from"+" "+domainname;
        SelectRequest selectRequest = new SelectRequest(query).withConsistentRead(true);
        List<com.amazonaws.services.simpledb.model.Item> items  = CardInfo.getAwsSimpleDB().select(selectRequest).getItems();
        try {
            com.amazonaws.services.simpledb.model.Item temp1;
            int size = items.size();
            CardInfo[] CardInfoList;
            CardInfoList = new CardInfo[size];
            for (int i = 0; i < size; i++) {
                temp1 = ((com.amazonaws.services.simpledb.model.Item) items.get(i));
                List<com.amazonaws.services.simpledb.model.Attribute> tempAttribute = temp1.getAttributes();
                CardInfoList[i] = new CardInfo();
                for (int j = 0; j < tempAttribute.size(); j++) {
                    if (tempAttribute.get(j).getName().equals("cardName")) {
                        CardInfoList[i].cardName = tempAttribute.get(j).getValue();
                    } else if (tempAttribute.get(j).getName().equals("cardDescription")) {
                        CardInfoList[i].cardDescription = tempAttribute.get(j).getValue();
                    } else if (tempAttribute.get(j).getName().equals("ImageUrl")) {
                        CardInfoList[i].ImageUrl = tempAttribute.get(j).getValue();
                    }
                    else if (tempAttribute.get(j).getName().equals("PricePerDay")) {
                        CardInfoList[i].PricePerDay = Float.valueOf(tempAttribute.get(j).getValue());
                    }
                    else if (tempAttribute.get(j).getName().equals("PricePerWeek")) {
                        CardInfoList[i].PricePerWeek = Float.valueOf(tempAttribute.get(j).getValue());
                    }
                    else if (tempAttribute.get(j).getName().equals("PricePerMonth")) {
                        CardInfoList[i].PricePerMonth = Float.valueOf(tempAttribute.get(j).getValue());
                    }
                    else if (tempAttribute.get(j).getName().equals("Securitydeposit")) {
                        CardInfoList[i].Securitydeposit = Float.valueOf(tempAttribute.get(j).getValue());
                    }else if (tempAttribute.get(j).getName().equals("dateTextView")) {
                        CardInfoList[i].dateTextView = tempAttribute.get(j).getValue();
                    }
                }
            }
            return CardInfoList;
        }
        catch( Exception eex)
        {
            throw new Exception("FIRST EXCEPTION", eex);
        }
    }

	@Override
	protected CardInfo[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			return getAllCardInfo(params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
