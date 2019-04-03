package com.ouxuxi;

import com.ouxuxi.dao.UserDao;
import com.ouxuxi.entity.User;
import com.ouxuxi.util.QiniuCloudUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OcprojectApplicationTests {

    @Resource
    private UserDao userDao;

    @Test
    public void contextLoads() throws QiniuException {
        Auth auth=Auth.create(QiniuCloudUtil.ACCESS_KEY,QiniuCloudUtil.SECRET_KEY);
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager=new BucketManager(auth,cfg);
        bucketManager.delete("ocproject","417d1361-bf59-4328-9496-4fa28ec91f58");

    }
    @Test
    public  void testuserDao(){
        User user=userDao.queryUserById(1);
        System.out.println(user.toString());
    }

    @Test
    public void testGetByUsernameAndPassword(){
        User userTest=new User();
        userTest.setUserLoginName("admin");
        userTest.setUserPassword("admin");
        User user=userDao.getByUsernameAndPassword(userTest);
        System.out.println(user.toString());
    }
}
