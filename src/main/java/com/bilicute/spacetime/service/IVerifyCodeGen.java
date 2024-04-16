package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.VerifyCode;

import java.io.IOException;
import java.io.OutputStream;

public interface IVerifyCodeGen {

    String generate(int width, int height, OutputStream os) throws IOException;

    VerifyCode generate(int width, int height) throws IOException;


}
