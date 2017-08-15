package com.example.dreamworld.cardslibpoc;

import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3Imageloader extends AsyncTask<File,Void,String>  {

    public static String loadImage(File file){

        String existingBucketName = "rentit.org";
        String keyName = file.getName();
        BasicAWSCredentials credentials = new BasicAWSCredentials(MainActivity.getProperties().getProperty("accessKey"), MainActivity.getProperties().getProperty("secretKey"));
        AmazonS3Client s3Client1 = new AmazonS3Client(credentials);
        PutObjectRequest por = new PutObjectRequest(existingBucketName, keyName , file);

        //Making the object Public
        por.setCannedAcl(CannedAccessControlList.PublicRead);
        s3Client1.putObject(por);

        String _finalUrl = "http://s3.amazonaws.com/" + existingBucketName + "/" + keyName  ;
        return _finalUrl;
    }

    @Override
    protected String doInBackground(File... params) {
        // TODO Auto-generated method stub
        try {
            return loadImage(params[0]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}