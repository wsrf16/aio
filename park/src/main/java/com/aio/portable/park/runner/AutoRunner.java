package com.aio.portable.park.runner;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.park.config.ApplicationConfig;
import com.aio.portable.park.test.LogTest;
//import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.sandbox.a中文.AA;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoRunner implements ApplicationRunner {

//    @Autowired
//    MyDatabaseTest mybatisTest;

    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.staticBuild();//.setSamplerRate(1f);

    @Autowired
    ApplicationConfig rootConfig;

    @Autowired
    LogTest logTest;


    @Override
    public void run(ApplicationArguments applicationArguments) {
//        logTest.logStyle();
        myDatabaseTest.blah();





        try {
//            DataCertCreate dataCertCreate = new DataCertCreate();
//            String[] info = { "huahua_user", "hnu", "university", "china", "hunan", "changsha", "111111", "11111111", "1" };
//            // 生成公钥
//            boolean createPublicKey = dataCertCreate.createPublicKey(info);
//            System.out.println("PUBLIC KEY CREATE OK, result==" + createPublicKey);
//
//            boolean createPublicKeyBYDecode = dataCertCreate.createPublicKeyBYDecode(info);
//            System.out.println("PUBLIC KEY BY BASE64Encoder CREATE OK, result==" + createPublicKeyBYDecode);
//
//            boolean createPrivateKey = dataCertCreate.createPrivateKey(info);
//            System.out.println("PRIVATE KEY CREATE OK, result==" + createPrivateKey);
//
//            Boolean pfx = dataCertCreate.toPFX(info);
//            System.out.println("transToPFX OK, result==" + pfx);
//
//
//
//
//            CertSugar.createKeyStore(new File("d:\\a"), "", new X500Name(""), "");
//            CertSugar.CertInfo certInfo = new CertSugar.CertInfo();
//            CertSugar.createSubjectCert(certInfo, "","", new File("d:\\a"), "", "cn", "");

            log.setAsync(false).i("111111111111");


            System.out.println(ClassSugar.getPath(AA.class));
            Thread.sleep(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}



