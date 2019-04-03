package com.ouxuxi.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.UUID;


public class QiniuCloudUtil {
    // 设置需要操作的账号的AK和SK
    public static final String ACCESS_KEY="P48Y3LbuAQdJ7_wKLcdBGSBpQ7FrtsnD0fJtNnqn";
    public static final String SECRET_KEY="8cU8AWgKO1EEaUa_1ewGN8RBOgDowuk9sSB89_Sg";
    // 要上传的空间
    public static final String bucket= "ocproject";

    // 管理密钥对象
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    /*public String getUpToken() {
        return auth.uploadToken(bucketName, null, 3600, new StringMap().put("insertOnly", 1));
    }*/
    //base64方式上传
    /*public String uploadImg(byte[] base64,String key) throws Exception{
        String file64= Base64.encodeToString(base64,0);
        Integer length=base64.length;
        String url="http://upload-z2.qiniu.com/putb64/" + length + "/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody requestBody= RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                //
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        return imgUrl+key;
    }*/
    //上传图片
    public String upLoadImage(byte[] data)   {
        //图片的外链地址域名
        StringBuffer imgUrl=new StringBuffer("http://pp9sub7xv.bkt.clouddn.com/");

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        String key= UUID.randomUUID().toString();
        System.out.println(key);
        //Auth auth1 = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        try{
            Response response = uploadManager.put(data,key,upToken);
            System.out.println(response);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            System.out.println(putRet.key);
            imgUrl.append(putRet.key);
        }catch (QiniuException e){
            e.printStackTrace();
            Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            }catch (QiniuException ex2) {
              }
        }
        System.out.println("imgUrl"+imgUrl);
        return imgUrl.toString();
    }

    public void contextLoads() throws QiniuException {
        Auth auth=Auth.create(ACCESS_KEY,SECRET_KEY);
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager=new BucketManager(auth,cfg);
        bucketManager.delete("ocproject","417d1361-bf59-4328-9496-4fa28ec91f58");
    }
}
