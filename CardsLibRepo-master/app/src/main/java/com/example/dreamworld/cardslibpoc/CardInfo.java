package com.example.dreamworld.cardslibpoc;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public  class CardInfo{
    public String cardName;
    public String cardDescription;
    public String ImageUrl;
    public float PricePerDay;
    public float PricePerWeek;
    public float PricePerMonth;
    public float Securitydeposit;
    public String dateTextView;
    public String categoryselected;

    private static AmazonSimpleDB awsSimpleDB;

    //  Get Simple DB connection.
    public static AmazonSimpleDB getAwsSimpleDB() {
        if (awsSimpleDB == null) {
            BasicAWSCredentials credentials = new BasicAWSCredentials(MainActivity.getProperties().getProperty("accessKey"), MainActivity.getProperties().getProperty("secretKey"));
            awsSimpleDB = new AmazonSimpleDBClient(credentials);
        }
        return awsSimpleDB;
    }


}
