package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.suite.algorithm.cipher.AlgorithmSugar;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoder;
import com.aio.portable.swiss.suite.algorithm.transcode.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoding;
import com.aio.portable.swiss.suite.security.authentication.jwt.encryption.TokenEncryption;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jwt.JWTParser;

public abstract class JWTSession implements JWTAction, Transcoding, TokenEncryption {
    private JWTProperties jwtProperties;

    private Transcoder transcode = new TranscoderBase64();

    private Algorithm algorithm;

    @Override
    public Transcoder getTranscode() {
        return transcode;
    }

    @Override
    public void setTranscode(Transcoder transcode) {
        this.transcode = transcode;
    }

    @Override
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    public JWTSession(JWTProperties jwtProperties) {
//        JWTParser.parse()
        this.jwtProperties = jwtProperties;
        Algorithm algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC256, jwtProperties.getSecret());
        setAlgorithm(algorithm);
    }

    public JWTSession() {
        this(new JWTProperties());
    }

    @Override
    public JWTFactory newFactory() {
        return jwtProperties.newFactory();
    }

    @Override
    public String sign(JWTCreator.Builder builder) {
        String token = encode(builder, algorithm);
        String encodedToken = transcode.encode(token);
        return encodedToken;
    }

    @Override
    public Boolean validate(String encodedToken) {
        String decodedToken = transcode.decode(encodedToken);
        return verify(decodedToken, algorithm);
    }

    @Override
    public DecodedJWT parse(String encodedToken) {
        String decodedToken = transcode.decode(encodedToken);
        DecodedJWT parse = parse(decodedToken, algorithm);
        return parse;
    }
}
